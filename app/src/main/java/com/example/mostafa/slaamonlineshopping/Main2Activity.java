package com.example.mostafa.slaamonlineshopping;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    final  HashMap<String,Pair<Double,Double>> Product_quantity=new HashMap<>();
    int VoiceCode=1;
    String id;
    final List<String> zzz=new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==VoiceCode) {
            try {


                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Toast.makeText(getApplicationContext(), text.get(0), Toast.LENGTH_SHORT).show();
                final ListView SearchListView = (ListView) findViewById(R.id.LV_results);
                //final ArrayAdapter<String> all_products = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
                SearchListView.setVisibility(View.VISIBLE);
                myDataBase dataBase = new myDataBase(getApplicationContext());
                final Cursor ALLproducts = dataBase.getProduct(text.get(0));
                zzz.clear();
                if (ALLproducts != null) {
                    while (!ALLproducts.isAfterLast()) {
                        zzz.add(ALLproducts.getString(1) + "  " + ALLproducts.getString(3));
                        ALLproducts.moveToNext();
                    }
                }
                Toast.makeText(getApplicationContext(), String.valueOf(zzz.size()), Toast.LENGTH_SHORT).show();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        zzz );
                SearchListView.setAdapter(arrayAdapter);
                SearchListView.setBackgroundColor(Color.BLACK);
            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"TRY AGAIN",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.SearchVoice)
        {
            Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            startActivityForResult(intent,VoiceCode);

        }
        if(item.getItemId()==R.id.Logout)
        {
            myDataBase dataBase=new myDataBase(getApplicationContext());
            dataBase.deleteRemeber();
            Intent intent1=new Intent(Main2Activity.this,MainActivity.class);
            startActivity(intent1);
        }
        if (item.getItemId()==R.id.orders)
        {
            Intent intent1=new Intent(Main2Activity.this,ShowOrders.class);
            intent1.putExtra("id",id);
            startActivity(intent1);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return  true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final ListView listView=(ListView)findViewById(R.id.L_view);
        final EditText quntity=(EditText)findViewById(R.id.ET_q);
        final TextView total=(TextView)findViewById(R.id.TV_total);
        final ArrayAdapter<String> myList=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        myDataBaseContract.total=0;

        total.setText(String.valueOf(myDataBaseContract.total));
        TextView textView=(TextView)findViewById(R.id.TV_hi);
        final Intent intent=getIntent();
        id=intent.getStringExtra("id");
        textView.setText("hi "+intent.getStringExtra("name"));


        final Spinner spinner1=(Spinner)findViewById(R.id.spinner);
        final Spinner spinner2=(Spinner)findViewById(R.id.spinner2);
        final myDataBase dataBase=new myDataBase(this);
        final ArrayAdapter<String> myproducts=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        Cursor cursor=dataBase.getProducts(spinner1.getSelectedItem().toString());
        if (cursor!=null)
        {
            while (!cursor.isAfterLast())
            {
                myproducts.add(cursor.getString(1)+"  "+cursor.getString(3)+" $");
                cursor.moveToNext();
            }
        }

        spinner2.setAdapter(myproducts);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor=dataBase.getProducts(spinner1.getSelectedItem().toString());
                    myproducts.clear();
                if (cursor!=null)
                {
                    while (!cursor.isAfterLast())
                    {
                        myproducts.add(cursor.getString(1)+"  "+cursor.getString(3)+" "+"$");
                        cursor.moveToNext();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final TextView textView2=(TextView)findViewById(R.id.TV_selected);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s=spinner2.getSelectedItem().toString();
                String[] strings=s.split("  ");
                textView2.setText(strings[0]);
                String[] cost=strings[1].split(" ");
               // Toast.makeText(getApplicationContext(),strings[1]+","+cost[0]+","+cost[1],Toast.LENGTH_SHORT).show();
                myDataBaseContract.cost =Double.valueOf(cost[1]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button add_product=(Button)findViewById(R.id.b_add);
        listView.setAdapter(myList);
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!quntity.getText().toString().isEmpty()&&Double.valueOf(quntity.getText().toString())!=0) {
                    try {

                        if (Product_quantity.containsKey(textView2.getText().toString())) {

                           Toast.makeText(getApplicationContext(),"this product selected!!",Toast.LENGTH_SHORT).show();

                        } else {
                            myList.add(textView2.getText().toString() + "  " + quntity.getText().toString());
                            Product_quantity.put(textView2.getText().toString(), new Pair<Double, Double>(myDataBaseContract.cost,Double.valueOf(quntity.getText().toString())));
                            myDataBaseContract.total += (Double.valueOf(quntity.getText().toString()) * myDataBaseContract.cost);
                            total.setText(String.valueOf(myDataBaseContract.total));
                            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Enter Vaild Quntity",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Set Quntity",Toast.LENGTH_SHORT).show();
                }

            }
        });
        listView.setAdapter(myList);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String s=myList.getItem(position);
               String[] strings=s.split("  ");
               Pair<Double,Double>x=Product_quantity.get(strings[0]);
               textView2.setText(strings[0]);
               myDataBaseContract.total-=(x.first*x.second);
               Product_quantity.remove(strings[0]);
               myList.remove(myList.getItem(position));
               total.setText(String.valueOf(myDataBaseContract.total));
               quntity.setText("0");
               ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
           }
       });

        //Search
        final ListView SearchListView=(ListView)findViewById(R.id.LV_results);
     //  final  ArrayAdapter<String> all_products=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1);
        SearchView searchView=(SearchView)findViewById(R.id.SV);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchListView.setVisibility(View.VISIBLE);

                final Cursor ALLproducts=dataBase.getProduct(query);
                zzz.clear();
                if (ALLproducts!=null)
                {
                    while (!ALLproducts.isAfterLast())
                    {
                        zzz.add(ALLproducts.getString(1)+"  "+ALLproducts.getString(3));
                        ALLproducts.moveToNext();
                    }
                }
                Toast.makeText(getApplicationContext(),query+String.valueOf(zzz.size()),Toast.LENGTH_SHORT).show();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        zzz );
                SearchListView.setAdapter(arrayAdapter);
               SearchListView.setBackgroundColor(Color.BLACK);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });


        SearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!quntity.getText().toString().isEmpty()&&Double.valueOf(quntity.getText().toString())!=0) {
                    try {
                       String[] strings=zzz.get(position).split("  ");
                        Toast.makeText(getApplicationContext(),strings[0]+",",Toast.LENGTH_SHORT).show();

                        if (Product_quantity.containsKey(strings[0])) {

                            Toast.makeText(getApplicationContext(),"this product selected!!",Toast.LENGTH_SHORT).show();


                        } else {
                            myList.add(strings[0] + "  " + quntity.getText().toString());
                            Product_quantity.put(strings[0], new Pair<Double, Double>(Double.valueOf(strings[1]),Double.valueOf(quntity.getText().toString())));
                            myDataBaseContract.total += (Double.valueOf(quntity.getText().toString()) *Double.valueOf(strings[1]));
                            total.setText(String.valueOf(myDataBaseContract.total));
                            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                            SearchListView.setVisibility(View.INVISIBLE);

                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Enter Vaild Quntity"+e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Set Quntity",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button button=(Button)findViewById(R.id.B_done);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(myList.getCount()==0)
               {
                   Toast.makeText(getApplicationContext(), "you should add product to make order", Toast.LENGTH_SHORT).show();

               }
               else {
                   String mypp = "";
                   for (int i = 0; i <= myList.getCount() - 1; i++) {
                       mypp += myList.getItem(i);
                   }
                   Intent intent1 = new Intent(Main2Activity.this, MapsActivity.class);
                   intent1.putExtra("products", mypp);
                   intent1.putExtra("total", String.valueOf(myDataBaseContract.total));
                   intent1.putExtra("ID", intent.getStringExtra("id"));
                   startActivity(intent1);
               }

            }
        });


    }
}
