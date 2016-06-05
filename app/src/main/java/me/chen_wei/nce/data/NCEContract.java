package me.chen_wei.nce.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Hander on 16/6/5.
 * <p/>
 * Email : hander_wei@163.com
 */
public class NCEContract {

    public static final String CONTENT_AUTHORITY = "me.chen_wei.nce.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ARTICLES = "articles";
    public static final String PATH_WORDS = "words";

    public static final class ArticleEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTICLES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTICLES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTICLES;

        public static final String TABLE_NAME = "articles";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_TITLE_ZH = "title_zh";
        public static final String COLUMN_CONTENT_ZH = "content_zh";
    }

    public static final class WordEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORDS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WORDS;

        public static final String CONTENT_ITEM_TPYE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WORDS;

        public static final String TABLE_NAME = "words";

        public static final String COLUMN_WORD = "word";
        public static final String COLUMN_LEVEL = "level";

        public static Uri buildWordUri(String word){
            return CONTENT_URI.buildUpon().appendPath(word).build();
        }
    }
}
