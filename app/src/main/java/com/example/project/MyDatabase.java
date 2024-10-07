package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {
    private Context context ;
    private static final String DATABASE_NAME = "BookLibrary.db" ;
    private static final int DATABASE_VERSION = 1 ;
    private static final String TABLE_NAME="my_library" ;
    private static final String COLUMN_ID ="_id" ;
    private static final String COLUMN_TITLE = "book_title" ;
    private static final String COLUMN_AUTHOR = "book_author" ;
    private static final String COLUMN_PAGES = "book_pages" ;

    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
        this.context= context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         String query = "CREATE TABLE " + TABLE_NAME +
                         " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         COLUMN_TITLE + " TEXT, " +
                         COLUMN_AUTHOR + " TEXT, " +
                         COLUMN_PAGES + " INTEGER);";
         db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    void addBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues values = new ContentValues() ;
        values.put(COLUMN_TITLE,book.getTitle());
        values.put(COLUMN_AUTHOR,book.getAuthor());
        values.put(COLUMN_PAGES,book.getPages());
       long test = db.insert(TABLE_NAME,null,values) ;
       if(test == -1){
           Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
       }
       else{
           Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
       }
    }
    Cursor readAllData(){
        String query = "SELECT * FROM "+TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase() ;
        Cursor cursor = null ;
        if (db != null) {
            cursor=db.rawQuery(query,null);
        }
        return cursor ;
    }
    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();

        // Sélectionner toutes les lignes de la table
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Parcourir toutes les lignes et ajouter à la liste
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));
                book.setPages(Integer.parseInt(cursor.getString(3)));
                // Ajouter le livre à la liste
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        // Fermer le curseur pour éviter les fuites de mémoire
        cursor.close();

        // Retourner la liste des livres
        return bookList;
    }

    void updateData(String row_id ,String title, String author , String pages){
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues cv = new ContentValues() ;
        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_AUTHOR,author);
        cv.put(COLUMN_PAGES,pages);
       long test = db.update(TABLE_NAME,cv," _id=?",new String[]{String.valueOf(Integer.parseInt(row_id))});
       if(test == -1){
           Toast.makeText(context," Failed to Update !",Toast.LENGTH_SHORT).show();
       }else{
           Toast.makeText(context,"Successfully Updated!",Toast.LENGTH_SHORT).show();
       }
    }

   void deleteOneBook(String row_id){
       SQLiteDatabase db = this.getWritableDatabase() ;
      long test= db.delete(TABLE_NAME,"_id=?",new String[]{row_id});
       if(test == -1){
           Toast.makeText(context,"Delete Failed.",Toast.LENGTH_SHORT).show();
       }else {
           Toast.makeText(context,"successfully Deleted.",Toast.LENGTH_SHORT).show();
       }
    }


    void deleteAllBooks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
    public Book getBookByTitle(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Book book = null;

        String[] projection = {
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_AUTHOR,
                COLUMN_PAGES
        };

        String selection = COLUMN_TITLE + " = ?";
        String[] selectionArgs = {title};

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            book = new Book();
            book.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
            book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)));
            book.setPages(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PAGES)));

            cursor.close();
        }

        return book;
    }

}
