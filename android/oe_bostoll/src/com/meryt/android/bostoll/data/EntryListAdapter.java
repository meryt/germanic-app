package com.meryt.android.bostoll.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.meryt.android.bostoll.R;

public class EntryListAdapter extends SimpleCursorAdapter implements LoaderManager.LoaderCallbacks<Cursor> {

    private SQLiteDatabase database;

    private Context context;

    private String pageId;

    public EntryListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.context = context;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cursor item = (Cursor) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.list_item_entry, null);
        TextView entryView = (TextView) convertView.findViewById(R.id.text);

        String htmlText = item.getString(item.getColumnIndexOrThrow("entry"));
        entryView.setText(Html.fromHtml(htmlText));
        return convertView;
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {"rowid _id", "page_id", "headword", "entry", "line_num"};

        DictionaryDatabase dbFile = new DictionaryDatabase(context);
        database = dbFile.getReadableDatabase();

        return new CursorLoader(context, null, projection, "page_id = ?", new String[] { pageId }, "_id") {
            @Override
            public Cursor loadInBackground() {
                return database.query(
                        "entries",
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
