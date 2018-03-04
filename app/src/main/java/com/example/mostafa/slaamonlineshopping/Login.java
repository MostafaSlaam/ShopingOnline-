package com.example.mostafa.slaamonlineshopping;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText email=(EditText)findViewById(R.id.editText);
        final EditText pass=(EditText)findViewById(R.id.editText2);
        final myDataBase dataBase=new myDataBase(this);
    myDataBaseContract.total=0;
        Button button=(Button)findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Cursor cursor1=dataBase.check_email(email.getText().toString());
                 Cursor cursor=dataBase.getCustomer(email.getText().toString(),pass.getText().toString());
                if(cursor1.getCount()==0)
                {
                    Toast.makeText(getApplicationContext(),"this Email not found",Toast.LENGTH_SHORT).show();
                }
                else if(cursor.getCount()==0) {
                    Toast.makeText(getApplicationContext(),"wrong password",Toast.LENGTH_SHORT).show();

                }else
                    {
                        CheckBox checkBox=(CheckBox)findViewById(R.id.checkBox2);
                        if(checkBox.isChecked()) {
                            Cursor cursor11=dataBase.checkRemember();
                            Toast.makeText(getApplicationContext(),String.valueOf(cursor.getCount()),Toast.LENGTH_SHORT).show();
                            if(cursor11.getCount()!=0)
                                dataBase.deleteRemeber();

                            dataBase.Remember(Integer.valueOf(cursor.getString(0))
                                    , cursor.getString(1), cursor.getString(2),cursor.getString(4), Integer.valueOf(cursor.getString(3)));
                        }
                            Intent intent=new Intent(Login.this,Main2Activity.class);
                        intent.putExtra("id",cursor.getString(0));
                        intent.putExtra("name",cursor.getString(1));
                        intent.putExtra("email",cursor.getString(2));
                        intent.putExtra("age",cursor.getString(3));
                        intent.putExtra("password",cursor.getString(4));
                        startActivity(intent);
                    }
            }
        });


    }
}
