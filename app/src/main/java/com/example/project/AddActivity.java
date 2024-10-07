package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
EditText title,author,pages ;
Button add_Button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title=findViewById(R.id.title);
        author=findViewById(R.id.author);
        pages=findViewById(R.id.pages);
        add_Button=findViewById(R.id.add_button);
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabase mydb = new MyDatabase(AddActivity.this);
                Book book = new Book(title.getText().toString().trim(),author.getText().toString().trim(),Integer.valueOf(pages.getText().toString().trim()));
                mydb.addBook(book);
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}