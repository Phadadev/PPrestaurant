package com.pw.paphat.pprestaurant;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Requrest SQLite (want to use database)
        myManage = new MyManage(this);

        //Test Add Value
        //testAdd();

        //Delete SQLite
        deleteSQLite();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();


    }   //Main Method

    private void synJSONtoSQLite() {

        MyMainConnected myMainConnected = new MyMainConnected();//will use must be make instant class
        myMainConnected.execute();//will start

    }

    //Inner Class
    public class MyMainConnected extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();//this instance!!!
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://swiftcodingthai.com/9Apr/php_get_user_paphat.php").build();//don't use www.
                Response response = okHttpClient.newCall(request).execute();//Response mean something which read income

                return response.body().string();


            } catch (Exception e) {
                return null;//when error will return null
            }


        }// doInBackgrond

        @Override
        protected void onPostExecute(String strJSON) {
            super.onPostExecute(strJSON);

            Log.d("PP_Restaurant", "strJSON ==>" + strJSON);

            try{

                JSONArray jsonArray = new JSONArray(strJSON);
                for (int i=0;i<jsonArray.length();i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String strUser = jsonObject.getString(MyManage.column_user);
                    String strPassword = jsonObject.getString(MyManage.column_pass);
                    String strName = jsonObject.getString(MyManage.column_name);

                    myManage.addValueToSQLite(0, strUser, strPassword, strName);

                }// for



            }catch (Exception e){
                Log.d("PP_Restaurant", "Error ==>" + e.toString());
            }



        } // onPost
    } //MyMainConnected Class

    private void deleteSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);//null mean non sequirity
        sqLiteDatabase.delete(MyManage.food_table, null, null);//null null mean delete all talbe
        sqLiteDatabase.delete(MyManage.user_table, null, null);
        //sqLiteDatabase.delete(MyManage.food_table,)
    }

    private void testAdd() {
        myManage.addValueToSQLite(0, "user", "pass", "name");
        myManage.addValueToSQLite(1, "food", "price", "source");
    }
}   //Main Class
