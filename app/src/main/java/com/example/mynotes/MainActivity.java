package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> mainList=new ArrayList<String>();
    static ArrayAdapter mainAdapter;
     SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
        case R.id.addNotes:
            startActivity(new Intent(this,Main2Activity.class));
        default:
        return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView=findViewById(R.id.listView);
        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.mynotes", Context.MODE_PRIVATE);
        HashSet<String> set=(HashSet<String>)sharedPreferences.getStringSet("notes",null);
        if(set==null)
        {
            mainList.add("Welcome to Remainder");
        }
        else{
            mainList=new ArrayList(set);
        }

        mainAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,mainList);
        listView.setAdapter(mainAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getApplicationContext(),Main2Activity.class);
                i.putExtra("NoteId",position);
                startActivity(i);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               final int itemtodelete=position;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are You Sure")
                        .setMessage("Do you want to delete the note ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            mainList.remove(itemtodelete);
                            mainAdapter.notifyDataSetChanged();

                                HashSet<String> set=new HashSet<String>(MainActivity.mainList);
                                sharedPreferences.edit().putStringSet("notes",set).apply();

                            }
                        })
                        .setNegativeButton("No",null)
                .show();
                return true;
            }
        });

    }
}
