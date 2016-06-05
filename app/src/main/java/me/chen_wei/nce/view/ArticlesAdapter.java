package me.chen_wei.nce.view;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import me.chen_wei.nce.R;

/**
 * Created by Hander on 16/6/5.
 * <p/>
 * Email : hander_wei@163.com
 */
public class ArticlesAdapter extends CursorAdapter {

    public static class ViewHolder {
        public final TextView titleTv;
        public final TextView introTv;

        public ViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.tv_article_title);
            introTv = (TextView) view.findViewById(R.id.tv_article_intro);
        }

    }

    public ArticlesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_articles, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setTag(vh);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vh = (ViewHolder) view.getTag();
        vh.titleTv.setText(cursor.getString(ArticleListFragment.COL_ARTICLE_TITLE));
        vh.introTv.setText(cursor.getString(ArticleListFragment.COL_ARTICLE_CONTENT));
    }
}
