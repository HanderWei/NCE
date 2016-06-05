package me.chen_wei.nce.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.chen_wei.nce.R;

public class MainActivity extends AppCompatActivity implements ArticleListFragment.Callback {

    private static final String ARG_TITLE = "title";
    private static final String ARG_CONTENT = "content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemSelected(String title, String content) {
        Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_CONTENT, content);
        intent.putExtras(args);
        startActivity(intent);
    }
}
