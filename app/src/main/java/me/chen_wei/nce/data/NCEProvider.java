package me.chen_wei.nce.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Hander on 16/6/5.
 * <p/>
 * Email : hander_wei@163.com
 */
public class NCEProvider extends ContentProvider {

    private static final String LOG_TAG = NCEProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private NCEDbHelper mOpenHelper;

    static final int ARTICLE = 100;
    static final int WORD = 200;
    static final int WORD_SPEC = 201;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = NCEContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, NCEContract.PATH_ARTICLES, ARTICLE);
        matcher.addURI(authority, NCEContract.PATH_WORDS, WORD);
        matcher.addURI(authority, NCEContract.PATH_WORDS + "/#", WORD_SPEC);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new NCEDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "articles/"
            case ARTICLE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        NCEContract.ArticleEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // "words/"
            case WORD: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        NCEContract.WordEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // "words/#/"
            case WORD_SPEC: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        NCEContract.WordEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
