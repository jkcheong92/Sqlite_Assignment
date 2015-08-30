package sg.edu.nus.assignment2_sqlite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText furnitureText, classText;
    TextView recordsText;
    ScrollView scrollText;
    MyDB myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new MyDB(this);
        furnitureText = (EditText)findViewById(R.id.furnitureText);
        classText = (EditText)findViewById(R.id.classText);
        scrollText = (ScrollView)findViewById(R.id.scrollView);
        getFormattedRecords();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // add record to database
    public void addRecord (View view) {
        myDb.open();
        String furniture = furnitureText.getText().toString();
        String room = classText.getText().toString();
        boolean isInserted = myDb.insertData(furniture, room);
        if (isInserted)
            Toast.makeText(MainActivity.this, "Record Added!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_LONG).show();
        myDb.close();
        // Update records
        getFormattedRecords();
    }

    // Get all records from DB in string format
    public void getFormattedRecords() {
        recordsText = (TextView) findViewById(R.id.recordsText);

        myDb.open();
        Cursor c = myDb.getAllData();
        String output = "";
        if(c.moveToFirst()) {
            do {
                output += c.getString(0) + " ";
                output += c.getString(1) + " ";
                output += c.getString(2) + " ";
                output += "\n";
            } while (c.moveToNext());
        }

        myDb.close();
        recordsText.setText(output);
    }

    // Delete all records from database
    public void deleteAllRecords(View view) {
        myDb.open();
        int rowsDeleted = myDb.deleteAllRecords();
        if (rowsDeleted > 0)
            Toast.makeText(MainActivity.this, rowsDeleted + " rows deleted!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "Nothing is deleted", Toast.LENGTH_LONG).show();
        myDb.close();
        // Update records
        getFormattedRecords();
    }
}
