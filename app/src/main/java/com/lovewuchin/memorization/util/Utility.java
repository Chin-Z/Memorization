package com.lovewuchin.memorization.util;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.lovewuchin.memorization.MApplication;
import com.lovewuchin.memorization.R;
import com.lovewuchin.memorization.view.main.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Chin
 * @since 2014/11/27
 */
public class Utility {

    public static final String PREFERENCE = "memorization";

    public static <T> T findViewById(View v, int id) {
        return (T) v.findViewById(id);
    }

    public static <T> T findViewById(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    public static boolean isEmpty(String string) {
        if (string == null || string.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void outAssets(String pathName) throws IOException {
        InputStream is = MApplication.getInstance().getResources().getAssets().open(pathName);
        FileOutputStream fos = MApplication.getInstance().openFileOutput(pathName, Context.MODE_PRIVATE);
        byte[] buffer = new byte[400000];
        int count = 0;
        while ((count = is.read(buffer)) > 0) {
            fos.write(buffer, 0, count);
        }
        fos.close();
        is.close();
    }

    public static SharedPreferences getSharedPreference() {
        return MApplication.getInstance().getSharedPreferences(PREFERENCE, Context.MODE_WORLD_READABLE);
    }

    public static String getString(String key, String defaultValue) {
        return getSharedPreference().getString(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getSharedPreference().getBoolean(key, defaultValue);
    }

    public static void putString(String key, String value) {
        getSharedPreference().edit().putString(key, value).commit();
    }

    public static void putBoolean(String key, boolean value) {
        getSharedPreference().edit().putBoolean(key, value).commit();
    }

    public static boolean isFirstLunch() {
        return getBoolean("is_first", true);
    }

}
