package me.chen_wei.nce.data;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Hander on 16/6/5.
 * <p/>
 * Email : hander_wei@163.com
 *
 * https://github.com/jgilfelt/android-sqlite-asset-helper/
 */
public class NCEDbHelper extends SQLiteAssetHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "nce4.db";

    public NCEDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
