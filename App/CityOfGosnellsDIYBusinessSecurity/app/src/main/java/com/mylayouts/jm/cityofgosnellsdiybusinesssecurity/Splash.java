package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;


/**
 * James McNeil ---
 *
 * Splash Screen
 */
public class Splash extends ActionBarActivity {

    /*
        URL Of Checklist Data to be retrieved.
     */
    private final String CHECKLIST_URL = "http://www.gosnells.wa.gov.au/feed.rss?listname=Security%20Audit%20Checklist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*
            Creats DownloadJSONTask and fetchs data
         */
        DownloadJSONTask task = new DownloadJSONTask();
        task.execute(new String[]{CHECKLIST_URL});


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *  James McNeil
     *
     *  DownloadJSONTask uses the AsyncTask class
     *  Gets the JSON String from the url
     */
    private class DownloadJSONTask extends AsyncTask<String, Void, String> {

        /**
         *
         * @param urls
         * @return
         */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {

                /*
                    Add Code to fetch JSON String from URL Provided by the city of Gosnells
                 */
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {


                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    StringBuilder sBuilder = new StringBuilder();

                    String line = null;
                    while ((line = buffer.readLine()) != null) {
                        sBuilder.append(line + "\n");
                    }

                    /*
                        Close Stream + Get String
                     */
                    content.close();
                    response = sBuilder.toString();


                } catch (ClientProtocolException e) {
                    Log.e("ClientProtocolException", e.getMessage());
                } catch (IOException e) {
                    Log.e("IOException", e.getMessage());
                }


            }
            Log.i("Response", "\n\n" + response);

            /*

             */
            return response;
        }

        /*
         After JSON String is retrived Wait and then move to Welcome Activity

         !!! Data handling needs to be added here
         */
        @Override
        protected void onPostExecute(String result) {

            try {
                /*
                    Splash Screen will always be visible for at least 3 seconds
                    sleep( time in milliseconds)
                 */
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e("Interrupted", "" + e.getMessage());
            }

            // Start intent and move to welcome Activity
            Intent intent = new Intent(Splash.this, WelcomeActivity.class);
            startActivity(intent);

        }
    }

}




