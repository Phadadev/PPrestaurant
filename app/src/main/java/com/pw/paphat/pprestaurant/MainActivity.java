package com.pw.paphat.pprestaurant;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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



    }   //Main Method

    private void deleteSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE,null);//null mean non sequirity
        sqLiteDatabase.delete(MyManage.food_table,null,null);//null null mean delete all talbe
        sqLiteDatabase.delete(MyManage.user_table,null,null);
    }

    private void testAdd() {
        myManage.addValueToSQLite(0, "user", "pass", "name");
        myManage.addValueToSQLite(1, "food", "price", "source");
    }
}   //Main Class
