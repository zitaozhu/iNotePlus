package com.example.zhuxiaodong.inoteplus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * database helper to store notes, singleton
 * Created by zhuxiaodong on 2018/8/7.
 */

public class NoteDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static NoteDatabaseHelper mInstance = null;

    private static final String CREATE_NOTE_DB = "create table Note ("
            + "id integer primary key autoincrement, "
            + "title text, "
            + "content text, "
            + "author text, "
            + "category text, "
            + "date numeric)";

    private static final String DB_NAME = "Note.db";
    private static final int DB_VERSION = 1;

//
//    public NoteDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//        this.mContext = context;
//    }

    public static NoteDatabaseHelper getInstance(Context context) {
        /*
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new NoteDatabaseHelper(context);
        }
        return mInstance;
    }

    /**
     * constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */
    private NoteDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
