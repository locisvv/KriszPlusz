package com.somniatores.kriszplusz;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import static android.R.layout.simple_list_item_2;

public class MainActivity extends AppCompatActivity {

    private static final String AUTHORITY = "com.somniatores.kriszplusz.managers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = getContentResolver().query(Uri.parse("content://"
                + AUTHORITY + "/" + "songs"), null, null, null, null);
        int to[] = { android.R.id.text1, android.R.id.text2 };
        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(this, simple_list_item_2, cursor, new String[]{"number", "title"}, to);
        ListView lvContact = (ListView) findViewById(R.id.listView);
        lvContact.setAdapter(adapter);

    }
}
