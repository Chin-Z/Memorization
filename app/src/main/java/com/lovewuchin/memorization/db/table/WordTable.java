package com.lovewuchin.memorization.db.table;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author Chin
 * @since 2014/11/28
 */
public class WordTable extends Table {

    public static final String ORDER_BY = WordTable.COLUMN_NAME_WORD + " ASC ";

    public static final String TABLE_NAME                = "vocabulary";
    public static final String COLUMN_NAME_WORD          = "_word";
    public static final String COLUMN_NAME_SYMBOL        = "_symbol";
    public static final String COLUMN_NAME_TRANSLATION   = "_translation";
    public static final String COLUMN_NAME_PRONUNCIATION = "_pronunciation";
    public static final String COLUMN_NAME_EXAMPLES      = "_examples";
    public static final String COLUMN_NAME_WORD_LIST     = "_word_list";
    public static final String COLUMN_NAME_STUDY_COUNT   = "_study_count";
    public static final String COLUMN_NAME_MISTAKE_COUNT = "_mistake_count";
    public static final String COLUMN_NAME_LEVEL         = "_level";

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  IF NOT EXISTS " + TABLE_NAME + " ("
                + COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_WORD + " TEXT,"
                + COLUMN_NAME_SYMBOL + " TEXT,"
                + COLUMN_NAME_TRANSLATION + " TEXT,"
                + COLUMN_NAME_PRONUNCIATION + " TEXT,"
                + COLUMN_NAME_EXAMPLES + " TEXT,"
                + COLUMN_NAME_WORD_LIST + " TEXT,"
                + COLUMN_NAME_STUDY_COUNT + " INTEGER,"
                + COLUMN_NAME_MISTAKE_COUNT + " INTEGER,"
                + COLUMN_NAME_LEVEL + " INTEGER"
                + ");");
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
    }

}
