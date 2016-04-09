package com.pw.paphat.pprestaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by paphat on 4/9/2016 AD.
 */
public class MyOpenHelper extends SQLiteOpenHelper{//can just SQLO ,you canright

    //Explicit
    public static final String database_name = "Restaurant.db";//anything name but use .db!!!
    private static final int database_version =1;

    //Create table on database
    private static final String create_user_table = "create table userTABLE (" +
            "_id interger primary key, " +
            "User text, " +
            "Password text, " +
            "Name text);";//small type name,expectially first must be use _id only!!!

    private static final String create_food_table = "create table foodTABLE (" +
            "_id integer primary key," +
            "Food text, " +
            "Price text, " +
            "Source text);";


    public MyOpenHelper(Context context) {
        super(context,database_name,null,database_version);//null mean can into but not sequrity
    }// Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_food_table);
        sqLiteDatabase.execSQL(create_user_table);//create table in database
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}//Main Class
