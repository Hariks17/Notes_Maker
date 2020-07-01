package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;

import static com.example.mynotes.MainActivity.mainList;

public class Main2Activity extends AppCompatActivity {

    int noteId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EditText editText =findViewById(R.id.notes);
        Intent intent=getIntent();
        noteId=intent.getIntExtra("NoteId",-1);
        if(noteId!=-1)
        {
            editText.setText(mainList.get(noteId));
        }
        else{
            mainList.add("");
            noteId= mainList.size()-1;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mainList.set(noteId,String.valueOf(s));
                MainActivity.mainAdapter.notifyDataSetChanged();
                sharedPreferences=getApplicationContext().getSharedPreferences("com.example.mynotes", Context.MODE_PRIVATE);
                HashSet<String> set=new HashSet<String>(MainActivity.mainList);
                sharedPreferences.edit().putStringSet("notes",set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
