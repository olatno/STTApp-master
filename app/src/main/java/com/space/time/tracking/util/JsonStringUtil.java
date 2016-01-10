package com.space.time.tracking.util;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by olatu on 09/01/2016.
 */
public class JsonStringUtil {

    public static String requestContent(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        Log.d("***httpclient == null**", " "+String.valueOf(httpclient == null));
        String result = null;
        HttpGet httpget = new HttpGet(url);
        Log.d("***httpget == null**", " "+String.valueOf(httpget == null));
        HttpResponse response = null ;
        InputStream instream = null;

        try {
            Log.d("***response1 == null**", " "+String.valueOf(response == null));
            response = httpclient.execute(httpget);
            Log.d("***response2 == null**", " "+String.valueOf(response == null));
            org.apache.http.HttpEntity entity = response.getEntity();
            Log.d("***entity == null**", " "+String.valueOf(entity == null));

            if (entity != null) {
                instream = entity.getContent();
                Log.d("***instream == null**", " "+String.valueOf(instream == null));
                result = convertStreamToString(instream);
            }

        } catch (Exception e) {
            // manage exceptions
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }

        return sb.toString();
    }
}
