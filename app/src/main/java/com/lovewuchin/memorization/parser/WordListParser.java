package com.lovewuchin.memorization.parser;

import android.util.Log;

import com.lovewuchin.memorization.bean.WordBean;
import com.lovewuchin.memorization.bean.WordListHeaderBean;
import com.lovewuchin.memorization.util.Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Chin
 * @since 2014/11/27
 */
public class WordListParser {

    private static final String TAG = "WordListParser";
    private WordListHeaderBean mHeader;
    private BufferedReader mReader;

    public WordListParser(File file) {
        try {
            InputStream in = new FileInputStream(file);
            mReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            // Parse book header;
            String line = mReader.readLine();
            if(line.startsWith(WordListParseScheme.HEADER_TOKEN_START)) {
                mHeader = new WordListHeaderBean();
                String name = mReader.readLine();
                String number = mReader.readLine();
                mHeader.setWordListName(name);
                mHeader.setWordNumber(Integer.valueOf(number));
            }

            // Move the cursor to the first word;
            while((line = mReader.readLine()) == null ||
                    !WordListParseScheme.WORD_TOKEN_START.equalsIgnoreCase(line.trim())) {
                continue;
            }
        } catch (FileNotFoundException e) {
            Log.d(TAG, e.getLocalizedMessage());
        } catch (IOException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
    }

    public void close() {
        if(mReader != null) {
            try {
                mReader.close();
                mReader = null;
                mHeader = null;
            } catch (IOException e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
        }
    }

    public WordListHeaderBean getBookHeader() {
        return mHeader;
    }

    /**
     * The format like:
     * word [symbol] translations
     */
    public WordBean getNextWord() throws IOException {
        String line = mReader.readLine().trim();
        if(!Utility.isEmpty(line) && !WordListParseScheme.WORD_TOKEN_END.equalsIgnoreCase(line)) {
            WordBean word = new WordBean();
            int offset = line.indexOf('[');
            if(offset < 0) {
                offset = line.indexOf(" ");
                if(offset < 0) {
                    offset = line.length();
                }
            }

            word.setWord(line.substring(0, offset).trim());
            word.setSymbol(line.substring(offset).trim());

            String trans = mReader.readLine();
            line = mReader.readLine();
            while(!Utility.isEmpty(line)) {
                trans = trans + "\n" + line;
                line = mReader.readLine();
            }
            word.setTranslation(trans);

            return word;
        }

        return null;
    }
}
