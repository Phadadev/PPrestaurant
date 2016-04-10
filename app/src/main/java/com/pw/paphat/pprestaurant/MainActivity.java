package com.pw.paphat.pprestaurant;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        bindWidget();

        //Requrest SQLite (want to use database)
        myManage = new MyManage(this);

        //Test Add Value
        //testAdd();

        //Delete SQLite
        deleteSQLite();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();


    }   //Main Method

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.userEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
    }

    public void clickLogin1(View view) {//use view make see in xml

        Log.d("Test", "Click");

        userString = userEditText.getText().toString().trim();//trim mean deleate blank
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (userString.equals("") || passwordString.equals("")) {//if string must use equals

            //Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง",
                    "กรุณากรอกทุกช่อง ค่ะ");//OOP have in C and java


        } else {

            //No Space

            checkUser();

        }

    }   //clickLogin

    private void checkUser() {

        try {//check data in database

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);//null mean not sequrity , not press user&password
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " + "'" + userString + "'", null);//rawQuery can use any,userTABLE mean us name table
            cursor.moveToFirst();

            String[] resultStrings = new String[cursor.getColumnCount()];//cursor.getColumnCount() will count number of column,Reservations memory
            for (int i = 0; i < cursor.getColumnCount(); i++) {

                resultStrings[i] = cursor.getString(i);

            }   //for
            cursor.close();//return memmory ,especially!!!

            //Check Password
            if (passwordString.equals(resultStrings[2])) {
                //Password True
                Toast.makeText(this, "ยินดีต้อนรับ " + resultStrings[3]
                        , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ServiceActivity.class);//make instant
                intent.putExtra("Officer", resultStrings[3]);
                startActivity(intent);
                finish();//destroy this page

            } else {
                //Passwrod False
                MyAlert myAlert = new MyAlert();
                myAlert.myDialog(this, "Password ผิด", "Passwordผิด กรุณากรอกใหม่");


            }

        } catch (Exception e) {

            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ไม่มี User นี้",
                    "ไม่มี " + userString + " ในฐานขอ้มูลของเรา");

        }
    }   //checkUser

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

            try {

                JSONArray jsonArray = new JSONArray(strJSON);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String strUser = jsonObject.getString(MyManage.column_user);
                    String strPassword = jsonObject.getString(MyManage.column_pass);
                    String strName = jsonObject.getString(MyManage.column_name);

                    myManage.addValueToSQLite(0, strUser, strPassword, strName);

                }// for


            } catch (Exception e) {
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
