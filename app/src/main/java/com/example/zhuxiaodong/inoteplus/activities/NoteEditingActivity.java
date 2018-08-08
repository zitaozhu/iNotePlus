package com.example.zhuxiaodong.inoteplus.activities;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhuxiaodong.inoteplus.R;
import com.example.zhuxiaodong.inoteplus.database.NoteDatabaseHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by zhuxiaodong on 2018/8/5.
 */

public class NoteEditingActivity extends AppCompatActivity {
    private Context context;
    private NoteDatabaseHelper dbHelper;

    private int ID;
    private final String KEY = "id";
    private final String GET_MATCH_NOTE = "select * from Note where id = ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        context = this;
        dbHelper = NoteDatabaseHelper.getInstance(this);

        final EditText contentText = (EditText) findViewById(R.id.noteedit_content);
        final EditText titleText = (EditText) findViewById(R.id.noteedit_title);
        Button saveButton = (Button) findViewById(R.id.save_botton);
        Button resotreButton = (Button) findViewById(R.id.restore_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contentText.getText().toString().equals("")) {
                    Toast.makeText(context, "no text to save", Toast.LENGTH_SHORT).show();
                } else {
                    saveText(contentText.getText().toString());

                    ContentValues values = new ContentValues();
                    values.put("title", titleText.getText().toString());
                    values.put("content", contentText.getText().toString());
                    values.put("author", "zzt");
                    values.put("date", System.currentTimeMillis());
                    dbHelper.getWritableDatabase().insert("Note", null, values);
                    //Toast.makeText(context, "note saved successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resotreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = loadText();
                if (!TextUtils.isEmpty(inputText)) {
                    contentText.setText(inputText);
                    contentText.setSelection(inputText.length());
                    Toast.makeText(context, "Restoring succeeded", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intent = getIntent();
        if(intent.getExtras() != null && (ID = intent.getExtras().getInt(KEY)) != -1) {
            SQLiteDatabase db = NoteDatabaseHelper.getInstance(context).getReadableDatabase();
            Cursor cursor = db.rawQuery(GET_MATCH_NOTE + ID, null);
            cursor.moveToFirst();
            titleText.setText(cursor.getString(cursor.getColumnIndex("title")));
            contentText.setText(cursor.getString(cursor.getColumnIndex("content")));
        } else {
            Toast.makeText(this, "STH wrong happend that we counldn't found the note for you", Toast.LENGTH_SHORT).show();
        }
    }



    @TargetApi(Build.VERSION_CODES.M)
    private void saveText(String stringToSave) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = this.openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(stringToSave);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private String loadText() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try{
            in = this.openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
