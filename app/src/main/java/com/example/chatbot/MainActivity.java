package com.example.chatbot;

import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.chatbot.Model.BotModel;
import com.example.chatbot.Model.ChatModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    private FloatingActionButton btn_float;
    private List<ChatModel> list_chat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list_of_message);
        editText = (EditText) findViewById(R.id.user_input);
        btn_float = (FloatingActionButton) findViewById(R.id.floatButton);

        btn_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                //чтобы очищалось поле
                editText.setText("");
                ChatModel model = new ChatModel(text, true);
                list_chat.add(model);
                new BotAPI().execute(list_chat);
            }
        });
    }
//отсылаем адрес на сайт с ботом
    private class BotAPI extends AsyncTask<List<ChatModel>,Void, String> {
        String stream = null;
        List<ChatModel> models;
        String text = editText.getText().toString();

        @Override
        protected String doInBackground(List<ChatModel>... lists) {
            String url = String.format("http://sandbox.api.simsimi.com/request.p?key=%s&lc=en&ft=0.0&text=%s",
                    getString(R.string.bot_key), text);
            models = lists[0];
            HttpDataHandler httpDataHandler = new HttpDataHandler();
            stream = httpDataHandler.getHTTP_Response(url);
            return stream;

        }
        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            BotModel result = gson.fromJson(s, BotModel.class);

            ChatModel chatModel = new ChatModel(result.getResponse(), false);
            models.add(chatModel);
            CustomList listShow = new CustomList(models, getApplicationContext());
            listView.setAdapter(listShow);
        }
    }
}