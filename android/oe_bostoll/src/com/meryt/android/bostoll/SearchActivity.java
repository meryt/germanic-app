package com.meryt.android.bostoll;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.meryt.android.bostoll.data.DictionaryDatabase;

public class SearchActivity extends FragmentActivity {

    protected SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_browse:
                Intent browseIntent = new Intent(this, BrowseActivity.class);
                startActivity(browseIntent);
                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setupDatabase() {
        DictionaryDatabase dbFile = new DictionaryDatabase(this);
        database = dbFile.getReadableDatabase();

        String[] projection = {"id", "header", "line_num"};

        Cursor c = database.query(
            "pages",
            projection,
            null,
            null,
            null,
            null,
            "header DESC"
        );

    }
}
