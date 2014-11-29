package com.lovewuchin.memorization.view.activities;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.lovewuchin.memorization.R;
import com.lovewuchin.memorization.bean.WordBean;
import com.lovewuchin.memorization.db.DataBaseHelper;
import com.lovewuchin.memorization.db.table.WordTable;
import com.lovewuchin.memorization.util.Utility;
import com.lovewuchin.memorization.view.common.CommonActivity;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * @author Chin
 * @since 2014/11/29
 */
public class WordStudyActivity extends CommonActivity{

    private TextView mTextWord;
    private TextView mTextSymbol;
    private TextView mTextTranslation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.inflateMenu(R.menu.menu_study);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();

        //get random from data
        Random random = new Random();
        int s = random.nextInt(2000);
        new LoadWordTask(this).execute((long) s);
    }

    private void findViews() {
        mTextWord = Utility.findViewById(this, R.id.name);
        mTextSymbol = Utility.findViewById(this, R.id.symbol);
        mTextTranslation = Utility.findViewById(this, R.id.translation);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_word_study;
    }

    private void setData (WordBean word) {
        mTextWord.setText(word.getWord());
        mTextSymbol.setText(word.getSymbol());
        mTextTranslation.setText(word.getTranslation());
    }

    private static final class LoadWordTask extends AsyncTask<Long, Void, WordBean> {

        private WeakReference<WordStudyActivity> mWeakRef;

        public LoadWordTask(WordStudyActivity activity) {
            mWeakRef = new WeakReference<WordStudyActivity>(activity);
        }

        @Override
        protected WordBean doInBackground(Long... id) {
            final WordStudyActivity activity = mWeakRef.get();
            if (activity != null) {
                DataBaseHelper helper = DataBaseHelper.getInstance(activity);
                Cursor cursor = helper.queryWordDetailById(id[0]);
                if (cursor.moveToFirst()) {
                    WordBean word = new WordBean();
                    word.setWord(cursor.getString(
                            cursor.getColumnIndexOrThrow(WordTable.COLUMN_NAME_WORD)));
                    word.setSymbol(cursor.getString(
                            cursor.getColumnIndexOrThrow(WordTable.COLUMN_NAME_SYMBOL)));
                    word.setTranslation(cursor.getString(
                            cursor.getColumnIndexOrThrow(WordTable.COLUMN_NAME_TRANSLATION)));
                    word.setExample(cursor.getString(
                            cursor.getColumnIndexOrThrow(WordTable.COLUMN_NAME_EXAMPLES)));
                    word.setWordList(cursor.getString(
                            cursor.getColumnIndexOrThrow(WordTable.COLUMN_NAME_WORD_LIST)));
                    word.setLevel(cursor.getInt(
                            cursor.getColumnIndexOrThrow(WordTable.COLUMN_NAME_LEVEL)));
                    word.setPronunciation(cursor.getString(
                            cursor.getColumnIndexOrThrow(WordTable.COLUMN_NAME_PRONUNCIATION)));
                    word.setStudyCount(cursor.getInt(
                            cursor.getColumnIndexOrThrow(WordTable.COLUMN_NAME_STUDY_COUNT)));
                    return word;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(WordBean wordBean) {
            super.onPostExecute(wordBean);
            final WordStudyActivity activity = mWeakRef.get();
            if (activity != null && wordBean != null) {
                activity.setData(wordBean);
            }
        }
    }

}
