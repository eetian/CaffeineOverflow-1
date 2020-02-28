package com.caffeineoverflow.repositories.service.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "caffeineoverflow";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        updateDatabase(db,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        updateDatabase(db,oldVersion,newVersion);
    }
    /*
    //added dummy data in coffee and coffesize tables
    //https://www.homegrounds.co/what-coffee-has-the-most-caffeine/
    //https://us.keepcup.com/size-guide

     */
    public void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion==0) {
            db.execSQL("CREATE TABLE COFFEE(_id INTEGER PRIMARY KEY AUTOINCREMENT, COFFEENAME TEXT,CAFFEINEAMOUNT REAL)");
            db.execSQL("CREATE TABLE COFFEESIZE(_id INTEGER PRIMARY KEY AUTOINCREMENT, SIZENAME TEXT,QUANTITY REAL)");
            db.execSQL("CREATE TABLE USER(_id INTEGER PRIMARY KEY AUTOINCREMENT, HEIGHT REAL, WEIGHT REAL,AGE INTEGER)");
            db.execSQL("CREATE TABLE LOG(_id INTEGER PRIMARY KEY AUTOINCREMENT, DATE STRING, COFFEEID INTEGER,COFFEESIZEID INTEGER)");
            insertCoffeeItem(db,"Decaf Coffee (instant coffee)",.38); //mg per oz
            insertCoffeeItem(db,"Decaf Coffee (brewed)",.5);
            insertCoffeeItem(db,"Drip Coffee",15);
            insertCoffeeItem(db,"Espresso",51.34);
            insertCoffeeSizeItem(db,"Small",8); //oz
            insertCoffeeSizeItem(db,"Regular",12);
            insertCoffeeSizeItem(db,"Grande",16);
            insertUser(db,158,50,25); //cm,kg

        }

    }

    public void insertCoffeeItem(SQLiteDatabase db,String coffeeName,double caffeineAmount){
        ContentValues coffeeItem = new ContentValues();
        coffeeItem.put("COFFEENAME",coffeeName);
        coffeeItem.put("CAFFEINEAMOUNT",caffeineAmount);
        db.insert("COFFEE",null,coffeeItem);

    }

    public void insertCoffeeSizeItem(SQLiteDatabase db,String sizeName,double quantity) {
        ContentValues coffeeSizeItem = new ContentValues();
        coffeeSizeItem.put("SIZENAME", sizeName);
        coffeeSizeItem.put("QUANTITY", quantity);
        db.insert("COFFEESIZE", null, coffeeSizeItem);
    }

    public void insertUser(SQLiteDatabase db,double height, double weight, int age){
        ContentValues user = new ContentValues();
        user.put("HEIGHT",height);
        user.put("WEIGHT",weight);
        user.put("AGE",age);
        db.insert("USER",null,user);
    }

    //insert into Log table
    public void insertIntoLog(String date,int coffeeId,int coffeeSizeId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues logItems = new ContentValues();
        logItems.put("DATE",date);
        logItems.put("COFFEEID",coffeeId);
        logItems.put("COFFEESIZEID",coffeeSizeId);
        db.insert("LOG",null,logItems);

    }

    //select queries
    public Cursor getCoffeeList(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("COFFEE",
                new String[]{"_id","COFFEENAME","CAFFEINEAMOUNT"},
                null,
                null,
                null,null,null);
        return cursor;

    }

    public Cursor getCoffeeSizeList(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("COFFEESIZE",
                new String[]{"_id","SIZENAME","QUANTITY"},
                null,
                null,
                null,null,null);
        return cursor;

    }

    public Cursor getUserDetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("USER",
                new String[]{"_id","HEIGHT","WEIGHT","AGE"},
                null,
                null,
                null,null,null);
        return cursor;

    }

    public Cursor getLogDetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("LOG",
                new String[]{"_id","DATE","COFFEEID","COFFEESIZEID"},
                null,
                null,
                null,null,null);
        return cursor;
    }


}
