package com.example.admincam.restservice;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;

        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        try {
            restService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void restService() throws IOException {
        //URL url = new URL("http://services.groupkt.com/country/get/all");
        URL url = new URL ("http://54.86.42.99:4000/login");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("user", "17f2eed7d0994382d471aeb442682e59");
        params.put("password", "0192023a7bbd73250516f069df18b500");
        params.put("versionTerminosCondiciones","v1.0");
        params.put("versionAvisoPrivacidad", "v1.0");



        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);





//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setReadTimeout(10000);
//        conn.setConnectTimeout(15000);
//        conn.setRequestMethod("POST");
//        conn.setDoInput(true);
//        conn.setDoOutput(true);






        try {
            InputStream in = new BufferedInputStream(conn.getInputStream());
            System.out.print(in);




            String rest = convertStreamToString(in);
            System.out.print(rest);


            JSONObject result= new JSONObject(rest);

            JSONObject response = result.getJSONObject("response");

            String companies = response.getString("companies");

            TextView text = (TextView)findViewById(R.id.scrolltext);

            text.setText(companies);

            readStream(in);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }


    }



    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    //PRcesar los datos
    private void readStream(InputStream in) {
    }


}


