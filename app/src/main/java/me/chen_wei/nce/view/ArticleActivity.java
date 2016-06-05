package me.chen_wei.nce.view;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import me.chen_wei.nce.R;

public class ArticleActivity extends AppCompatActivity {

    private static final String ARG_TITLE = "title";
    private static final String ARG_CONTENT = "content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString(ARG_TITLE);
        actionBar.setTitle(title);

        String content = bundle.getString(ARG_CONTENT);
        ArticleFragment fragment = ArticleFragment.newInstance(content);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_article, fragment).commit();
    }
}
