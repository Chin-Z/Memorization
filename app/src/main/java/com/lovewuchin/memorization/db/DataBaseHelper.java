package com.lovewuchin.memorization.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lovewuchin.memorization.bean.WordBean;
import com.lovewuchin.memorization.db.table.WordListTable;
import com.lovewuchin.memorization.db.table.WordTable;

/**
 * @author Chin
 * @since 2014/11/27
 */
public class DataBaseHelper extends SQLiteOpenHelper{

    private static final String[] WORD_TABLE_COLUMNS_ALL = {
            WordTable.COLUMN_NAME_ID,
            WordTable.COLUMN_NAME_WORD,
            WordTable.COLUMN_NAME_SYMBOL,
            WordTable.COLUMN_NAME_TRANSLATION,
            WordTable.COLUMN_NAME_EXAMPLES,
            WordTable.COLUMN_NAME_LEVEL,
            WordTable.COLUMN_NAME_PRONUNCIATION,
            WordTable.COLUMN_NAME_WORD_LIST,
            WordTable.COLUMN_NAME_STUDY_COUNT,
            WordTable.COLUMN_NAME_MISTAKE_COUNT
    };

    public static final String DATABASE_NAME = "dict.db";
    public static final int DATABASE_VERSION = 1;

    private static DataBaseHelper sHelper;

    private SQLiteDatabase mWritableDatabase;

    private DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public synchronized static DataBaseHelper getInstance(Context context) {
        if(sHelper == null) {
            sHelper = new DataBaseHelper(context, DataBaseHelper.DATABASE_NAME,
                    null, DataBaseHelper.DATABASE_VERSION);
        }

        return sHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        WordListTable.createTable(sqLiteDatabase);
        WordTable.createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        WordListTable.dropTable(sqLiteDatabase);
        WordTable.dropTable(sqLiteDatabase);

        onCreate(sqLiteDatabase);
    }

    public void beginTransaction () {
        mWritableDatabase = getWritableDatabase();
        mWritableDatabase.beginTransaction();
    }

    public void setTransactionSuccessful () {
        if (mWritableDatabase != null) {
            mWritableDatabase.setTransactionSuccessful();
        }
    }

    public void endTransaction () {
        if (mWritableDatabase != null) {
            mWritableDatabase.endTransaction();
            mWritableDatabase.close();
            mWritableDatabase = null;
        }
    }

    public Cursor queryWordDetailById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(WordTable.TABLE_NAME, WORD_TABLE_COLUMNS_ALL,
                WordTable.COLUMN_NAME_ID + "=?", new String[] { String.valueOf(id) },
                null, null, null);

        return c;
    }

    public long insertWordInTransaction (WordBean word) {
        long id = -1;

        if (mWritableDatabase != null) {
            id = insertWord(word, mWritableDatabase);
        }

        return id;
    }

    public long insertWord(WordBean word) {
        SQLiteDatabase db = getWritableDatabase();
        long id;

        try {
            id = insertWord(word, db);
        } finally {
            if(db !=null) {
                db.close();
                db = null;
            }
        }

        return id;
    }

    public void insertWords(WordBean[] words) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();

            for(int i = 0; i < words.length; i++) {
                insertWord(words[i], db);
            }

            db.endTransaction();
        } finally {
            if(db != null) {
                db.close();
                db = null;
            }
        }
    }

    private long insertWord(WordBean word, SQLiteDatabase db) {
        ContentValues values = new ContentValues(10);
        values.put(WordTable.COLUMN_NAME_WORD, word.getWord());
        values.put(WordTable.COLUMN_NAME_SYMBOL, word.getSymbol());
        values.put(WordTable.COLUMN_NAME_TRANSLATION, word.getTranslation());
        values.put(WordTable.COLUMN_NAME_PRONUNCIATION, word.getPronunciation());
        values.put(WordTable.COLUMN_NAME_EXAMPLES, word.getExample());
        values.put(WordTable.COLUMN_NAME_WORD_LIST, word.getWordList());
        values.put(WordTable.COLUMN_NAME_STUDY_COUNT, word.getStudyCount());
        values.put(WordTable.COLUMN_NAME_MISTAKE_COUNT, word.getMistakeCount());
        values.put(WordTable.COLUMN_NAME_LEVEL, word.getLevel());
        return db.insertOrThrow(WordTable.TABLE_NAME, null, values);
    }
}
