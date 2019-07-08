package com.example.chatbot;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//получили ли мы данные от сервера
public class HttpDataHandler {

    static String stream = null;

    public HttpDataHandler() {
    }

    public String getHTTP_Response(String urlAdress) {
        try {
            URL url = new URL(urlAdress);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //считываем данные из определенного url и записываем в строку
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
              //  каждую строчку записываем в line в конец
                while((line = br.readLine()) != null) {
                    sb.append(line);
                }
                stream = sb.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stream;
    }
}

