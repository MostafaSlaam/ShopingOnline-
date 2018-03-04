package com.example.mostafa.slaamonlineshopping;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Calendar;

public class SignUp extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDataBaseContract.total=0;

       final EditText name=(EditText) findViewById(R.id.s_name);
       final EditText email=(EditText)findViewById(R.id.s_email);
       final EditText pass=(EditText)findViewById(R.id.s_pass);
        CalendarView calendarView=(CalendarView)findViewById(R.id.calendarView);
        final myDataBase dataBase=new myDataBase(this);
        calendarView.setDate(1990);
        final int[] b = {0};
         b[0]=0;
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                b[0] =Calendar.getInstance().get(Calendar.YEAR)-year;
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = dataBase.check_email(email.getText().toString());
            if (name.getText().toString().isEmpty()||pass.getText().toString().isEmpty()||email.getText().toString().isEmpty()||b[0]==0)
            {
                Snackbar.make(view, "Enter all data", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
                else   if (cursor.getCount() != 0){
                    Snackbar.make(view, "This Email is Already used"+b[0], Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }

            else {
                dataBase.add_customer(name.getText().toString(), email.getText().toString(), pass.getText().toString(), b[0]);
                CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
                if (checkBox.isChecked()) {
                    Cursor cursor11=dataBase.checkRemember();
                    Toast.makeText(getApplicationContext(),String.valueOf(cursor.getCount()),Toast.LENGTH_SHORT).show();
                    if(cursor11.getCount()!=0)
                    dataBase.deleteRemeber();
                    dataBase.Remember(dataBase.fetch_customers().getCount(),name.getText().toString(), email.getText().toString(), pass.getText().toString(), b[0]);
                }
                Intent intent=new Intent(SignUp.this,Main2Activity.class);
                intent.putExtra("id",String.valueOf(dataBase.fetch_customers().getCount()));
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("email", email.getText().toString());
                intent.putExtra("age",String.valueOf(b[0]));
                intent.putExtra("password",pass.getText().toString());
                startActivity(intent);
            }
            }
        });
    }

}
