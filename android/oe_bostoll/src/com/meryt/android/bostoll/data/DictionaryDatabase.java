package com.meryt.android.bostoll.data;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DictionaryDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "oe_bostoll.sqlite";

    private static final int DATABASE_VERSION = 1;

    public DictionaryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
