package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class ReserveNow extends AppCompatActivity {
private DatePickerDialog datePickerDialog ;
private Button dateButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_now);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodayDate());
    }

    private String getTodayDate() {
        Calendar cal = Calendar.getInstance() ;
        int year = cal.get(Calendar.YEAR) ;
        int month = cal.get(Calendar.MONTH) ;
        month = month +1 ;
        int day = cal.get(Calendar.DAY_OF_MONTH) ;
        return makeDateString(day,month,year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month + 1 ; 
                String date = makeDateString(day,month,year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance() ;
        int year = cal.get(Calendar.YEAR) ;
        int month = cal.get(Calendar.MONTH) ;
        int day = cal.get(Calendar.DAY_OF_MONTH) ;

        int style = AlertDialog.THEME_HOLO_LIGHT ;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day +" "+year ;
    }

    private String getMonthFormat(int month) {
        if(month == 1){return "JAN" ;}
        if(month == 1){return "FEB" ;}
        if(month == 1){return "MAR" ;}
        if(month == 1){return "APR" ;}
        if(month == 1){return "MAY" ;}
        if(month == 1){return "JUN" ;}
        if(month == 1){return "JUL" ;}
        if(month == 1){return "AUG" ;}
        if(month == 1){return "SEP" ;}
        if(month == 1){return "OCT" ;}
        if(month == 1){return "NOV" ;}
        if(month == 1){return "DEC" ;}
      //Default value
        return "JAN" ;
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}