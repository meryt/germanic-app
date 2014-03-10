package com.meryt.android.bostoll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import com.meryt.android.bostoll.HeaderLoaderListFragment.HeaderClickListener;
import com.meryt.android.bostoll.data.Header;

public class BrowseActivity extends FragmentActivity implements HeaderClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        setupBooks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.browse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // Don't go back if there's nothing left on the stack.
                if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
                    onBackPressed();
                }
                // Don't show the up button if there's no going back.
                getActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount() > 0);
                return true;
            case R.id.action_search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupBooks() {
        VolumeFragment volumes = new VolumeFragment();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.browse_main, volumes);
        t.commit();
    }

    private void browseToBook(String volumeId) {
        HeaderLoaderListFragment headerList = new HeaderLoaderListFragment();
        headerList.addHeaderClickListener(this);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.browse_main, headerList);
        t.addToBackStack(null);
        t.commit();
    }

    public void onVolumeClick(String volumeId) {
        browseToBook(volumeId);
    }

    public void onHeaderClick(String pageId, String pageHeader) {
        browseToPage(pageId, pageHeader);
    }

    private void browseToPage(String pageId, String pageHeader) {
        PageEntryListFragment entryList = new PageEntryListFragment();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString(Header.COL_ID, pageId);
        args.putString(Header.COL_HEADER, pageHeader);
        entryList.setArguments(args);
        t.replace(R.id.browse_main, entryList);
        t.addToBackStack(null);
        t.commit();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(pageHeader);
    }
}
