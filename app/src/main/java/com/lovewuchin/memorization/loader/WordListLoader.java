package com.lovewuchin.memorization.loader;

import android.content.Context;
import android.util.Log;

import com.lovewuchin.memorization.bean.WordBean;
import com.lovewuchin.memorization.bean.WordListHeaderBean;
import com.lovewuchin.memorization.db.DataBaseHelper;
import com.lovewuchin.memorization.parser.WordListParser;

import java.io.File;
import java.io.IOException;

/**
 * @author Chin
 * @since 2014/11/28
 */
public class WordListLoader extends Thread{

    public static final int LOAD_STATE_SUCCESS = 0;
    public static final int LOAD_STATE_ERROR = 1;
    public static final int LOAD_STATE_CANCEL = 3;

    private static final String TAG = "WordListLoader";

    private boolean mCancel;
    private WordListParser mParser;
    private File mBookFile;
    private OnWordListLoadListener mOnBookLoadListener;
    private Context mContext;

    public static interface OnWordListLoadListener {
        public void onStart();
        public void onLoadHeader(WordListHeaderBean header);
        public void onComplete(int state);
        public void onLoadWord(WordBean word);
    }

    public WordListLoader(Context context) {
        mContext = context;
    }

    public void setOnBookLoadListener(OnWordListLoadListener l) {
        mOnBookLoadListener = l;
    }

    public void startParse(File bookFile) {
        if(mOnBookLoadListener == null) {
            throw new RuntimeException("The OnBookLoadListener is null, " +
                    "you have to call setOnBookLoadListener() method first!");
        }

        mBookFile = bookFile;
        start();
        mOnBookLoadListener.onStart();
    }

    @Override
    public void run() {
        mParser = new WordListParser(mBookFile);
        mOnBookLoadListener.onLoadHeader(mParser.getBookHeader());
        final DataBaseHelper mDatabaseHelper = DataBaseHelper.getInstance(mContext);
        int counter = 0;

        try {
            mDatabaseHelper.beginTransaction();

            while (true) {
                if(mCancel) {
                    mOnBookLoadListener.onComplete(LOAD_STATE_CANCEL);
                    return;
                }

                WordBean word = mParser.getNextWord();
                // If word is null, load finished.
                if(word == null) {
                    if(mParser.getBookHeader().getWordNumber() == counter) {
                        mOnBookLoadListener.onComplete(LOAD_STATE_SUCCESS);
                        mDatabaseHelper.setTransactionSuccessful();
                    } else {
                        mOnBookLoadListener.onComplete(LOAD_STATE_ERROR);
                    }
                    return;
                } else {
                    mOnBookLoadListener.onLoadWord(word);
                    counter++;
                    // Insert word to database;
                    mDatabaseHelper.insertWordInTransaction(word);
                }

                // Give chance for UI thread change its state;
                Thread.sleep(10);
            }
        } catch (IOException e) {
            Log.d(TAG, e.getLocalizedMessage());
            mOnBookLoadListener.onComplete(LOAD_STATE_ERROR);
        } catch (InterruptedException e) {
            Log.d(TAG, e.getLocalizedMessage());
            mOnBookLoadListener.onComplete(LOAD_STATE_ERROR);
        } finally {
            mDatabaseHelper.endTransaction();

            if(mParser != null) {
                mParser.close();
                mParser = null;
            }
        }
    }
}
