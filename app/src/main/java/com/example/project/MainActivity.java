package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView ;
FloatingActionButton add_button,delete_all_button ;
MyDatabase myDb  ;
ArrayList<String> book_id,book_title,book_author,book_pages ;
CustomAdapter customAdapter ;
private FirebaseAuth auth ;
private static final int UPDATE_REQUEST_CODE = 1;

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView) ;
        delete_all_button = findViewById(R.id.delete_all_button);
        add_button = findViewById(R.id.add_button) ;
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
        delete_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteAllBooks();
                refresh();
            }
        });
        myDb = new MyDatabase(MainActivity.this) ;
        book_id=new ArrayList<>();
        book_author=new ArrayList<>();
        book_title=new ArrayList<>();
        book_pages=new ArrayList<>();
        storeDataInArrays();
        customAdapter = new CustomAdapter(MainActivity.this,this,book_id,book_title,book_author,book_pages);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        refresh();


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == UPDATE_REQUEST_CODE && resultCode == RESULT_OK){
            Log.d("MainActivity", "Before refresh");
            refresh();
            Log.d("MainActivity", "After refresh");
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDb.readAllData() ;
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()) {
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
            customAdapter = new CustomAdapter(MainActivity.this,this, book_id, book_title, book_author, book_pages);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            customAdapter.notifyDataSetChanged();
        }
    }


    public void refresh() {
        List<Book> books = myDb.getAllBooks();
        book_id.clear();
        book_title.clear();
        book_author.clear();
        book_pages.clear();

        for (Book book : books) {
            book_id.add(String.valueOf(book.getId()));
            book_title.add(book.getTitle());
            book_author.add(book.getAuthor());
            book_pages.add(String.valueOf(book.getPages()));
        }
        customAdapter.notifyDataSetChanged();
    }


    private void startUpdateActivity(String bookId) {
        Intent intent = new Intent(this, Update.class);
        intent.putExtra("id", bookId);
        startActivityForResult(intent, UPDATE_REQUEST_CODE);
    }



    private void refreshList() {
        if (getParent() != null) {
            getParent().recreate();
        }
    }

    public void SignOut(View view) {
        auth=FirebaseAuth.getInstance() ;
        auth.signOut();
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish(); // Optionally, you can finish the current activity to prevent going back to it.
        Toast.makeText(MainActivity.this, "See you next time!", Toast.LENGTH_SHORT).show();
    }
}