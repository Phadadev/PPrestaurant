package com.pw.paphat.pprestaurant;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceActivity extends AppCompatActivity {

    //Explicit
    private TextView officerTextView;
    private Spinner spinner;
    private ListView listView;

    private String officerString, deskString, foodString, amountString;
    private String[] foodStrings, priceStrings, iconStrings;//read from json to adapter feed to listview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        //Bind Widget
        officerTextView = (TextView) findViewById(R.id.officerTextView);
        spinner = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.listView);

        //Show Officer
        officerString = getIntent().getStringExtra("Officer");
        officerTextView.setText(officerString);


        //Create Spinner
        createSpiner();

        MyConnectedFood myConnectedFood = new MyConnectedFood();
        myConnectedFood.execute();

    }   // Main Method

    public class MyConnectedFood extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://swiftcodingthai.com/9Apr/php_get_food.php").build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                Log.d("10April", "Connected Error ==> " + e.toString());
                return null;
            }


        }// doInBackground

        @Override
        protected void onPostExecute(String strJSON) {
            super.onPostExecute(strJSON);

            Log.d("10April", "strJSON ==> " + strJSON);

            try {

                JSONArray jsonArray = new JSONArray(strJSON);

                foodStrings = new String[jsonArray.length()];
                priceStrings = new String[jsonArray.length()];
                iconStrings = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    foodStrings[i] = jsonObject.getString(MyManage.column_food);
                    priceStrings[i] = jsonObject.getString(MyManage.column_price);
                    iconStrings[i] = jsonObject.getString(MyManage.column_source);


                }   //For

                //Crete ListView
                MyAdapter myAdapter = new MyAdapter(ServiceActivity.this,
                        foodStrings, priceStrings, iconStrings);
                listView.setAdapter(myAdapter);


            } catch (Exception e) {
                Log.d("10April", "Error strJSON ==> " + e.toString());


            }


        }// onPost


    }//My ConnectedFood Class


    private void createSpiner() {

        final String[] deskStrings = {"โต๊ะที่1", "โต๊ะที่2", "โต๊ะที่3", "โต๊ะที่4", "โต๊ะที่5", "โต๊ะที่6", "โต๊ะที่7", "โต๊ะที่8", "โต๊ะที่9", "โต๊ะที่10",};

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, deskStrings
        );

        spinner.setAdapter(stringArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                deskString = deskStrings[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                deskString = deskStrings[0];
            }
        });

    }//Create Spiner


}   //Main Class