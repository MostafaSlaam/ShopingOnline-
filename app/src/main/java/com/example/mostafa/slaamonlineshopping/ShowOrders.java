package com.example.mostafa.slaamonlineshopping;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class ShowOrders extends AppCompatActivity {
    myAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        setContentView(R.layout.activity_show_orders);
        recyclerView=(RecyclerView)findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        myDataBase dataBase=new myDataBase(getApplicationContext());
        Cursor cursor=dataBase.getOrders(intent.getStringExtra("id"));
        Toast.makeText(getApplicationContext(),String.valueOf(cursor.getCount()),Toast.LENGTH_SHORT).show();
        adapter=new myAdapter(cursor,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}
