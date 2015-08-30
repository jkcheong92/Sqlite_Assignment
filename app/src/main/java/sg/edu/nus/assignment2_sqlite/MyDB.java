package sg.edu.nus.assignment2_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Asus on 8/30/2015.
 */
public class MyDB {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    final Context context;


    public MyDB(Context ctx) {
        this.context = ctx;
        dbHelper = new DatabaseHelper(this.context);
    }

    public MyDB open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    // Add data to DB
    public boolean insertData(String furniture, String room) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(dbHelper.COL_2, furniture);
        initialValues.put(dbHelper.COL_3, room);

        long result = db.insert(dbHelper.TABLE_NAME, null, initialValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    // Get all data from DB
    public Cursor getAllData() {
        String[] columns = {dbHelper.COL_1, dbHelper.COL_2, dbHelper.COL_3};
        return db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null);
    }

    // Delete all records
    public int deleteAllRecords() {
        int rowNum = db.delete(dbHelper.TABLE_NAME, null, null);
        return rowNum;
    }
}
