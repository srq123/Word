package com.example.word;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
//    private DatabaseHelper dbHelper;
    DatabaseHelper dbHelper= null;
    private static SQLiteDatabase database = null;
    public static final String AUTHORITY = "com.example.word.provider";
    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        dbHelper = new DatabaseHelper(getContext(),"Land.db",null,1);
        dbHelper.getWritableDatabase();
        database=dbHelper.getReadableDatabase();
        int deletedRows = 0;
        deletedRows = database.delete("WordBook", selection, selectionArgs);
        return deletedRows;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        Log.e("insert", "执行");
        dbHelper = new DatabaseHelper(getContext(),"Land.db",null,1);
        dbHelper.getWritableDatabase();
        database=dbHelper.getReadableDatabase();
        long newId = database.insert("WordBook", null, values);
        Uri returnUri = Uri.parse("content://" + AUTHORITY + "WordBook/" + newId);
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        dbHelper = new DatabaseHelper(getContext(),"Land.db",null,1);
        dbHelper.getWritableDatabase();
        database=dbHelper.getReadableDatabase();
        Cursor cursor = null;
        cursor = database.query("WordBook", projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        dbHelper = new DatabaseHelper(getContext(),"Land.db",null,1);
        dbHelper.getWritableDatabase();
        database=dbHelper.getReadableDatabase();
        int updateRows = 0;
        updateRows = database.update("WordBook", values, selection, selectionArgs);
        return updateRows;
    }
}
