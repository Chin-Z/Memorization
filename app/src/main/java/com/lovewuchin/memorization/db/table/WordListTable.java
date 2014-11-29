package com.lovewuchin.memorization.db.table;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author Chin
 * @since 2014/11/28
 */
public class WordListTable extends Table {

    public static final String TABLE_NAME = "word_list";
    public static final String COLUMN_NAME_WORD_LIST_NAME = "_word_list_name";
    public static final String COLUMN_NAME_WORD_NUMBER = "_word_list_number";

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  IF NOT EXISTS " + TABLE_NAME + " ("
                + COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_WORD_LIST_NAME + " TEXT,"
                + COLUMN_NAME_WORD_NUMBER + " INTEGER"
                + ");");
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
    }

}
