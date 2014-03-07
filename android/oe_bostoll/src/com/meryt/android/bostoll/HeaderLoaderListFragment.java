package com.meryt.android.bostoll;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.meryt.android.bostoll.data.Header;
import com.meryt.android.bostoll.data.HeaderListAdapter;

public class HeaderLoaderListFragment extends ListFragment {

    HeaderListAdapter mAdapter;

    List<HeaderClickListener> clickListeners = new ArrayList<HeaderClickListener>();

    public interface HeaderClickListener {
        public void onHeaderClick(String pageId, String pageTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_header_browse, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new HeaderListAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                new String[] { Header.COL_HEADER },
                new int[] { android.R.id.text1 },
                0
       );
       setListAdapter(mAdapter);

       getLoaderManager().initLoader(0, savedInstanceState, mAdapter);
       getActivity().getActionBar().setTitle(R.string.title_browse);
    }

    public void addHeaderClickListener(HeaderClickListener listener) {
        clickListeners.add(listener);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Cursor cursor = (Cursor)l.getItemAtPosition(position);
        String pageId = cursor.getString(cursor.getColumnIndexOrThrow(Header.COL_ID));
        String pageHeader = cursor.getString(cursor.getColumnIndexOrThrow(Header.COL_HEADER));
        for (HeaderClickListener listener : clickListeners) {
            listener.onHeaderClick(pageId, pageHeader);
        }
    }
}

