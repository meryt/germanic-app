package com.meryt.android.bostoll;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.meryt.android.bostoll.data.EntryListAdapter;
import com.meryt.android.bostoll.data.Header;

public class PageEntryListFragment extends ListFragment {

    private EntryListAdapter mAdapter;

    public static final int ENTRY_LOADER = 1;

    public interface HeaderClickListener {
        public void onHeaderClick(String pageId);
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

        mAdapter = new EntryListAdapter(
                getActivity(),
                R.layout.list_item_entry,
                null,
                new String[] {"entry"},
                new int[] { R.id.text },
                0
                );
        mAdapter.setPageId(getArguments().getString(Header.COL_ID));
        getActivity().getActionBar().setTitle(getArguments().getString(Header.COL_HEADER));
        setListAdapter(mAdapter);

        getLoaderManager().initLoader(ENTRY_LOADER, savedInstanceState, mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Cursor cursor = (Cursor)l.getItemAtPosition(position);
        // String entryId = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
    }
}

