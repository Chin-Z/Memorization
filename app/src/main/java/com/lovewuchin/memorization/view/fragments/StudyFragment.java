package com.lovewuchin.memorization.view.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;

import com.lovewuchin.memorization.MApplication;
import com.lovewuchin.memorization.R;
import com.lovewuchin.memorization.bean.WordBean;
import com.lovewuchin.memorization.bean.WordListHeaderBean;
import com.lovewuchin.memorization.loader.WordListLoader;
import com.lovewuchin.memorization.util.Utility;
import com.lovewuchin.memorization.view.activities.WordStudyActivity;
import com.lovewuchin.memorization.view.widget.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Chin
 * @since 2014/11/28
 */
public class StudyFragment extends Fragment {

    private FloatingActionButton mFAB;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_study, container, false);

        // Initialize FAB
        mFAB = Utility.findViewById(v, R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utility.isFirstLunch()) {
                    Utility.putBoolean("is_first", false);
                    try {
                        Utility.outAssets("cet-4.dict");
                        loadBook(new File(MApplication.getInstance().getFilesDir().getAbsolutePath() + "/" + "cet-4.dict"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    startActivity(new Intent(getActivity(), WordStudyActivity.class));
                }
            }
        });
        return v;
    }

    private void loadBook(File bookFile) {
        WordListLoader loader = new WordListLoader(getActivity());
        loader.setOnBookLoadListener(new WordListLoader.OnWordListLoadListener() {

            private ProgressDialog dialog;

            @Override
            public void onStart() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        dialog = new ProgressDialog(getActivity());
                        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        dialog.setCancelable(false);
                        dialog.setMessage("");
                        dialog.setTitle(getText(R.string.loading_word).toString().
                                replace("%s", ""));

                        dialog.setButton(ProgressDialog.BUTTON_NEUTRAL, getText(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                                        final CharSequence buttonTxt = dialog.getButton(
                                                ProgressDialog.BUTTON_NEUTRAL).getText();

                                        if (buttonTxt.equals(getText(R.string.cancel))) {
                                            // TODO: Cancel loading;
                                        } else if (buttonTxt.equals(getText(R.string.ok))) {
                                            dialog.dismiss();
                                        }
                                    }
                                });

                        dialog.show();
                    }
                });
            }

            @Override
            public void onLoadWord(final WordBean word) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        dialog.setMessage(word.getWord() + ' ' + word.getSymbol());
                        dialog.incrementProgressBy(1);
                    }
                });
            }

            @Override
            public void onLoadHeader(final WordListHeaderBean header) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        dialog.setTitle(header.getWordListName());
                        dialog.setMax(header.getWordNumber());
                    }
                });
            }

            @Override
            public void onComplete(int state) {
                if(state == WordListLoader.LOAD_STATE_SUCCESS) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            dialog.setMessage(getText(R.string.load_word_success));
                            dialog.getButton(ProgressDialog.BUTTON_NEUTRAL).setText(getText(R.string.ok));
                        }
                    });
                }
            }
        });

        loader.startParse(bookFile);
    }
}
