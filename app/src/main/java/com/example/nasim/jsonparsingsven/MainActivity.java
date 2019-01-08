package com.example.nasim.jsonparsingsven;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // find text view
        t1 = findViewById(R.id.t1);
        JsonTask jsonTask = new JsonTask();
        jsonTask.execute();
    }





    // create a class
    public  class JsonTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            // variable declare
            String name;
            String age;
            String description;
            StringBuffer lastBuffer = new StringBuffer();
            try {
                URL url= new URL(" https://api.myjson.com/bins/g5gl4");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                ///read the data
                InputStream inputStream = httpURLConnection.getInputStream();
                // convert byte code into string
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                // excute line by line
                String line="";

                while ((line=bufferedReader.readLine())!=null){
                    stringBuffer.append(line);
                }

                 String file = stringBuffer.toString();

                JSONObject fileobject = new JSONObject(file);
                JSONArray jsonArray = fileobject.getJSONArray("studentInfo");

                for(int i =0;i<jsonArray.length();i++){
                    JSONObject arrayObject = jsonArray.getJSONObject(i);

                    name = arrayObject.getString("name");
                    age = arrayObject.getString("age");
                    description= arrayObject.getString("description");
                    lastBuffer.append(name+"\n\n"+age+"\n\n"+description);

                }
                //return stringBuffer.toString();
               // return  name+"\n\n"+age+"\n\n"+description;
                return lastBuffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
           }
// finally {
//                httpURLConnection.disconnect();
//                try {
//                    bufferedReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            t1.setText(s);
        }
    }

}
