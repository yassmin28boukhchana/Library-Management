package com.example.project;

public class Book {
    int id ;
    String title ;
    String author ;
    int pages ;

    public Book() {
    }
    public Book( String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getAuthor() {return author;}

    public void setAuthor(String author) {this.author = author;}

    public int getPages() {return pages;}

    public void setPages(int pages) {this.pages = pages;}
}
