package me.chen_wei.nce.view;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import me.chen_wei.nce.R;
import me.chen_wei.nce.data.NCEContract;

public class ArticleListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = ArticleListFragment.class.getSimpleName();

    private ListView mListView;
    private ArticlesAdapter mAdapter;

    private int mPosition = ListView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";

    private static final int ARTICLES_LOADER = 0;

    static final int COL_ARTICLE_ID = 0;
    static final int COL_ARTICLE_TITLE = 1;
    static final int COL_ARTICLE_CONTENT = 2;
    static final int COL_ARTICLE_TITLE_ZH = 3;
    static final int COL_ARTICLE_CONTENT_ZH = 4;

    private static final String[] ARTICLES_COLUMNS = {
            NCEContract.ArticleEntry._ID,
            NCEContract.ArticleEntry.COLUMN_TITLE,
            NCEContract.ArticleEntry.COLUMN_CONTENT,
            NCEContract.ArticleEntry.COLUMN_TITLE_ZH,
            NCEContract.ArticleEntry.COLUMN_CONTENT_ZH
    };

    public interface Callback {
        void onItemSelected(String title, String content);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAdapter = new ArticlesAdapter(getActivity(), null, 0);
        View rootView = inflater.inflate(R.layout.fragment_article_list, container, false);
        mListView = (ListView) rootView.findViewById(R.id.lv_articles);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    String title = cursor.getString(COL_ARTICLE_TITLE);
                    String content = cursor.getString(COL_ARTICLE_CONTENT);
                    ((Callback) getActivity()).onItemSelected(title, content);
                }
                mPosition = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(ARTICLES_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri articlesUri = NCEContract.ArticleEntry.CONTENT_URI;
        return new CursorLoader(getActivity(),
                articlesUri,
                ARTICLES_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION) {
            mListView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
