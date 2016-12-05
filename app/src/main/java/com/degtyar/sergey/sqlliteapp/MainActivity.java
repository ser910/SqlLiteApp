package com.degtyar.sergey.sqlliteapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button add;
    Button clear;
    ListView listView;
    DbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.et_str);
        add= (Button) findViewById(R.id.btn_add);
        clear = (Button) findViewById(R.id.btn_clear);
        listView = (ListView) findViewById(R.id.lv_tbl);
        helper = new DbHelper(this);
        updateList();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                String name = editText.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put(DbHelper.COLUMN_NAME, name);
                long rowId = db.insert(DbHelper.TABLE_NAME, null, cv);
                db.close();
                updateList();
                editText.setText("");
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                db.delete(DbHelper.TABLE_NAME, null, null);
                updateList();
                long[] pattern = {100,500,100,500,100,500,100,100,100,100,100,100,100,500,100,500,100,500};
                vibrator.vibrate(pattern,-1);

            }
        });
    }

    private void updateList() {
        List<String> names = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(DbHelper.TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            int columnIndex = cursor.getColumnIndex(DbHelper.COLUMN_NAME);
            do {
                String name = cursor.getString(columnIndex);
                names.add(name);
            } while (cursor.moveToNext());
        }
        if(!cursor.isClosed())
            cursor.close();
        db.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
    }

}
