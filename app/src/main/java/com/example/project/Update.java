package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update extends AppCompatActivity {
EditText title_input,author_input,pages_input ;
Button update_button,delete_button ;
String id,title,author,pages ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        title_input=findViewById(R.id.title2);
        author_input=findViewById(R.id.author2);
        pages_input=findViewById(R.id.pages2);
        update_button=findViewById(R.id.update_button);
        delete_button=findViewById(R.id.delete_button);
        //First na5dho data w n7otoha fl edit text
        getandsetIntentData();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("UpdateActivity", "Before updateData");
                String newTitle = title_input.getText().toString();
                String newAuthor = author_input.getText().toString();
                String newPages = pages_input.getText().toString();
                MyDatabase myDb = new MyDatabase(Update.this);
                myDb.updateData(id,newTitle,newAuthor,newPages);
                Log.d("UpdateActivity", "After updateData");
                // Indiquer la réussite de la mise à jour
                setResult(RESULT_OK);
                // Fermer l'activité de mise à jour
                Intent intent = new Intent(Update.this,MainActivity.class);
                startActivity(intent);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               confirmDialaog();
            }
        });
    }
// lire les donnees provenant de lintent envoyees lorsque on clicke sur un item de la recyclerview intent envoye depuis la classe customadapter
    void getandsetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("author") && getIntent().hasExtra("pages")){
           //Getting data from intent
            id=getIntent().getStringExtra("id");
            title=getIntent().getStringExtra("title");
            author=getIntent().getStringExtra("author");
            pages=getIntent().getStringExtra("pages");
            // setting data
            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);
        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialaog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete "+ title+" ?");
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface,int i){
                MyDatabase myDb = new MyDatabase(Update.this);
                myDb.deleteOneBook(id);
                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface,int i){

            }
        });
        builder.create().show();
    }

}