package me.chen_wei.nce.view;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.chen_wei.nce.R;
import me.chen_wei.nce.data.NCEContract;
import me.chen_wei.nce.data.NCEDbHelper;

/**
 * Created by Hander on 16/6/5.
 * <p/>
 * Email : hander_wei@163.com
 */
public class ArticleFragment extends Fragment {

    private static final String LOG_TAG = ArticleFragment.class.getSimpleName();

    private static final int WORDS_LOADER = 1;

    private static final String ARG_CONTENT = "content";

    private static final String REGEX = "\\w+";

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");

    private String content;
    private TextView mContentTv;
    private ScrollView mScrollerView;
    private static final String SCROLLER_X = "scroller_x";
    private static final String SCROLLER_Y = "scroller_y";

    private int mScrollerX;
    private int mScrollerY;

    private static final String TEXTVIEW_VISIBLE = "text_view_visible";


    public ArticleFragment() {

    }

    static final int COL_ID = 0;
    static final int COL_WORD = 1;
    static final int COL_LEVEL = 2;

    private static final String[] WORDS_COLUMNS = {
            NCEContract.WordEntry._ID,
            NCEContract.WordEntry.COLUMN_WORD,
            NCEContract.WordEntry.COLUMN_LEVEL
    };

    public static ArticleFragment newInstance(String content) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            content = getArguments().getString(ARG_CONTENT);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.article, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int level = -1;
        switch (id) {
            case R.id.level_0:
                break;
            case R.id.level_1:
                level = 1;
                break;
            case R.id.level_2:
                level = 2;
                break;
            case R.id.level_3:
                level = 3;
                break;
            case R.id.level_4:
                level = 4;
                break;
            case R.id.clear:
                setDefaultTextView();
                return super.onOptionsItemSelected(item);
            default:
                level = -1;
        }
        new HighLightTask().execute(level);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);
        mScrollerView = (ScrollView) rootView.findViewById(R.id.sv);

        mContentTv = (TextView) rootView.findViewById(R.id.tv_article_content);
        mContentTv.setMovementMethod(LinkMovementMethod.getInstance());

        mContentTv.setLineSpacing(0f, 1.5f);
        mContentTv.setText(content);

        setDefaultTextView();

        return rootView;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            //TODO 恢复ScrollerView滚动位置
            mScrollerX = savedInstanceState.getInt(SCROLLER_X);
            mScrollerY = savedInstanceState.getInt(SCROLLER_Y);
            mScrollerView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollerView.smoothScrollTo(mScrollerX, mScrollerY);
//                    mScrollerView.scrollTo(scrollerX, scrollerY);
                }
            });
        }
    }

    /**
     * 默认点击单词高亮
     */
    private void setDefaultTextView() {
        if (content != null) {
            //利用正则表达式给文本内容添加ClickableSpan
            Pattern p = Pattern.compile(REGEX);
            Matcher m = p.matcher(content);
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(content);
            while (m.find()) {
//            Log.e(LOG_TAG, m.group() + " == > " + m.start() + " = " + m.end());
                spanText.setSpan(new CustomClickableSpan(), m.start(), m.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }

            mContentTv.setText(spanText);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO 保存ScrollerView 滚动位置
        outState.putInt(SCROLLER_X, mScrollerView.getScrollX());
        outState.putInt(SCROLLER_Y, mScrollerView.getScrollY());
//        outState.putInt(TEXTVIEW_VISIBLE, mContentTv.getViewTreeObserver().);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    class CustomClickableSpan extends ClickableSpan {

        @Override
        public void onClick(View view) {
            TextView tv = (TextView) view;
            Spanned s = (Spanned) tv.getText();
            int start = s.getSpanStart(this);
            int end = s.getSpanEnd(this);
//            Toast.makeText(getActivity(), s.subSequence(start, end), Toast.LENGTH_LONG).show();
            s.subSequence(start, end);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
//            ds.bgColor = getResources().getColor(R.color.colorAccent);
        }
    }

    class HighLightTask extends AsyncTask<Integer, Void, Spannable> {

        private Spannable highlightWords(int level) {
            Spannable spanText = null;
            if (level < 0) {
                return null;
            }
            if (content != null) {
                Pattern p = Pattern.compile(REGEX);
                Matcher m = p.matcher(content);
                spanText = Spannable.Factory.getInstance().newSpannable(content);
                NCEDbHelper dbHelper = new NCEDbHelper(getActivity());
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = null;
                while (m.find()) {
                    String[] args = {m.group()};
                    cursor = db.query(
                            NCEContract.WordEntry.TABLE_NAME,
                            WORDS_COLUMNS,
                            "word=?",
                            args,
                            null,
                            null,
                            null
                    );
                    if (cursor.moveToPosition(0) && cursor.getInt(COL_LEVEL) <= level) {
                        spanText.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), m.start(), m.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }
//                mContentTv.setText(spanText);
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                return spanText;
            }
            return null;
        }

        @Override
        protected Spannable doInBackground(Integer... levels) {
            Spannable text = highlightWords(levels[0]);
            return text;
        }

        @Override
        protected void onPostExecute(Spannable spannable) {
            super.onPostExecute(spannable);
            if (spannable != null) {
                mContentTv.setText(spannable);
//                mScrollerView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mScrollerView.smoothScrollTo(mScrollerX, mScrollerY);
//                    }
//                });
            }
        }
    }
}
