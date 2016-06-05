package me.chen_wei.nce.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by Hander on 16/6/5.
 * <p/>
 * Email : hander_wei@163.com
 */
public class TestDatabase extends AndroidTestCase {

    private static final String LOG_TAG = "TestDatabase";

    public void testDatabaseExist() throws Throwable{
        NCEDbHelper dbHelper = new NCEDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                Log.e(LOG_TAG, "Table Name=> "+c.getString(0));
                c.moveToNext();
            }
        }
    }
}
