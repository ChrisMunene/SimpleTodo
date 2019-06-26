package com.example.simpletodo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList <String> items;
    ArrayAdapter <String> itemsAdapter;
    ListView listViewItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listViewItems = (ListView) findViewById(R.id.listViewItems);
        listViewItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    public void onAddItem(View v){
        EditText todoInput = (EditText) findViewById(R.id.todoInput);
        String itemText = todoInput.getText().toString();
        itemsAdapter.add(itemText);
        todoInput.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), "Item Added Successfully!", Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener() {
        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                Toast.makeText(getApplicationContext(), "Item Removed Successfully!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private File getDataFile(){
        return new File(getFilesDir(), "todo.txt");
    }

    private void readItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading file", e);
            items = new ArrayList<>();
        }
    }

    private void writeItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing to file", e);
        }
    }
}
