package com.meryt.android.bostoll.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.meryt.android.bostoll.R;

public class HeaderListAdapter extends SimpleCursorAdapter implements LoaderManager.LoaderCallbacks<Cursor> {

    private SQLiteDatabase database;

    private Context context;

    public HeaderListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.context = context;
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { "rowid _id", Header.COL_ID, Header.COL_HEADER, Header.COL_LINE_NUM };

        DictionaryDatabase dbFile = new DictionaryDatabase(context);
        try {
            database = dbFile.getReadableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            Toast.makeText(context, R.string.err_cant_read_database, Toast.LENGTH_LONG).show();
            return null;
        }

        return new CursorLoader(context, null, projection, null, null, "id") {
            @Override
            public Cursor loadInBackground() {
                return database.query(
                        DictionaryDatabase.TABLE_PAGES,
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
        swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        swapCursor(null);
    }


}
