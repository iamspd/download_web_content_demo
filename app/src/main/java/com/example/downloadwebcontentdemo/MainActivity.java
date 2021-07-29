package com.example.downloadwebcontentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

           // I will require the URL and HttpUrlConnection instances to
           // read the string given in the param and do the
           // operations

            URL url;
            HttpURLConnection httpURLConnection = null;
            String result = "";

            // there could be instances where the url string is
            // broken or an invalid url had passed
            // so, the operation on the url string should be done
            // in try, catch block

            try{

                // the url that is provided in the onCreate method via
                // params passed in AsyncTask
                url = new URL(urls[0]);

                // now, to have the url data accessed
                // create the connection, this is equivalent to opening a
                // Web Browser.

                httpURLConnection = (HttpURLConnection) url.openConnection();

                // I will going to enter the url string into the browser window and
                // let it establish the connection
                InputStream inputStream = httpURLConnection.getInputStream();

                // now, I will let the browser read this data contained in the url
                InputStreamReader reader = new InputStreamReader(inputStream);

                // I will read the string
                int data = reader.read();

                // loop through the data one character at a time
                while (data != -1) {

                    // current character from the stream (url)
                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

                return result;

            } catch (Exception e){

                e.printStackTrace();

                return "Failed";
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask downloadTask = new DownloadTask();
        String result = null;

        try {
            result = downloadTask.execute("https://www.google.com/").get();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();

        }

        Log.i("Contents: ", result);
    }
}