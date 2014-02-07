package com.meryt.android.bostoll;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.meryt.android.bostoll.data.DictionaryDatabase;

public class HeaderLoaderListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    SimpleCursorAdapter mAdapter;

    SQLiteDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_header_browse, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DictionaryDatabase dbFile = new DictionaryDatabase(getActivity());
        database = dbFile.getReadableDatabase();

        mAdapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                new String[] {"header"},
                new int[] { android.R.id.text1 },
                0
       );
       setListAdapter(mAdapter);

       getLoaderManager().initLoader(0,  null, this);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {"rowid _id", "id", "header", "line_num"};

        return new CursorLoader(getActivity(), null, projection, null, null, "id") {
            @Override
            public Cursor loadInBackground() {
                return database.query(
                        "pages",
                        getProjection(),
                        getSelection(),
                        getSelectionArgs(),
                        null,
                        null,
                        getSortOrder(),
                        null
                    );
            }
        };
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }

}

