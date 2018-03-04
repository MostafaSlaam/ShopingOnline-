package com.example.mostafa.slaamonlineshopping;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=(Button)findViewById(R.id.button2);
        Button button1=(Button)findViewById(R.id.button1);
        myDataBaseContract.total=0;

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDataBase dataBase=new myDataBase(getApplicationContext());
               // dataBase.onUpgrade(dataBase.obj,2,3);
                Cursor cursor=dataBase.checkRemember();
                Toast.makeText(getApplicationContext(),String.valueOf(cursor.getCount()),Toast.LENGTH_SHORT).show();
                if(cursor.getCount()!=0)
                {
                    Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                    intent.putExtra("id",cursor.getString(0));
                    intent.putExtra("name",cursor.getString(1));
                    intent.putExtra("email",cursor.getString(2));
                    intent.putExtra("age",cursor.getString(3));
                    intent.putExtra("password",cursor.getString(4));
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }
            }
        });

    }
}
