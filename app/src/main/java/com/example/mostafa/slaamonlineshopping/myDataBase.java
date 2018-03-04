package com.example.mostafa.slaamonlineshopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mostafa on 12/13/2017.
 */

public class myDataBase extends SQLiteOpenHelper {
    private final static String DATABASE_NAME="myData.DB";
    private static final int DATABASE_VERSION = 3;
    SQLiteDatabase obj;
    public myDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String first="Create table "+myDataBaseContract.first_table.TABLE_customer+"("+
                myDataBaseContract.first_table._ID+" integer primary key autoincrement, "+
                myDataBaseContract.first_table.COLUMN_Name+" text, " +
                myDataBaseContract.first_table.COLUMN_email+" text ,"+
                myDataBaseContract.first_table.COLUMN_age+" integer, "+
                myDataBaseContract.first_table.COLUMN_password+" text "+
                ");";
        db.execSQL(first);

        final String second="Create table "+myDataBaseContract.second_table.table_bills+"("+
                myDataBaseContract.second_table._ID+" integer primary key autoincrement,"+
                myDataBaseContract.second_table.COLUMN_products+" text, "+
                myDataBaseContract.second_table.COLUMN_date+" text,"+
                myDataBaseContract.second_table.COLUMN_cus_ID+" integer ,"+
                myDataBaseContract.second_table.COLUMN_total+" text,"+
                myDataBaseContract.second_table.COLUMN_location+" text "+
                ");";
        db.execSQL(second);

        final String third="Create table "+myDataBaseContract.third_table.table_products+"("+
                myDataBaseContract.third_table._ID+" integer primary key autoincrement ,"+
                myDataBaseContract.third_table.COLUMN_name+" text ,"+
                myDataBaseContract.third_table.COLUMN_category+" text ,"+
                myDataBaseContract.third_table.COLUMN_price+" real "+
                ");";
        db.execSQL(third);


        final String fourth="Create table "+myDataBaseContract.fourth_table.TABLE_customer+"("+
                myDataBaseContract.fourth_table._ID+" integer primary key , "+
                myDataBaseContract.fourth_table.COLUMN_Name+" text, " +
                myDataBaseContract.fourth_table.COLUMN_email+" text ,"+
                myDataBaseContract.fourth_table.COLUMN_age+" integer, "+
                myDataBaseContract.fourth_table.COLUMN_password+" text "+
                ");";
        db.execSQL(fourth);


        //fruit
        db.execSQL("insert into "+myDataBaseContract.third_table.table_products+
                " ("+myDataBaseContract.third_table.COLUMN_name+","+
                myDataBaseContract.third_table.COLUMN_category+","+
                myDataBaseContract.third_table.COLUMN_price+")"+
         "values ('Apples ','fruit','2.5')"
        );

        db.execSQL("insert into "+myDataBaseContract.third_table.table_products+
                " ("+myDataBaseContract.third_table.COLUMN_name+","+
                myDataBaseContract.third_table.COLUMN_category+","+
                myDataBaseContract.third_table.COLUMN_price+")"+
                "values ('oranges ','fruit','5')"
        );

        db.execSQL("insert into "+myDataBaseContract.third_table.table_products+
                " ("+myDataBaseContract.third_table.COLUMN_name+","+
                myDataBaseContract.third_table.COLUMN_category+","+
                myDataBaseContract.third_table.COLUMN_price+")"+
                "values ('bananas ','fruit','3.5')"
        );

        //vegetables
        db.execSQL("insert into "+myDataBaseContract.third_table.table_products+
                " ("+myDataBaseContract.third_table.COLUMN_name+","+
                myDataBaseContract.third_table.COLUMN_category+","+
                myDataBaseContract.third_table.COLUMN_price+")"+
                "values ('Leafy green ','Vegetables','5.5')"
        );
        db.execSQL("insert into "+myDataBaseContract.third_table.table_products+
                " ("+myDataBaseContract.third_table.COLUMN_name+","+
                myDataBaseContract.third_table.COLUMN_category+","+
                myDataBaseContract.third_table.COLUMN_price+")"+
                "values ('cabbage ','Vegetables','3.5')"
        );
        db.execSQL("insert into "+myDataBaseContract.third_table.table_products+
                " ("+myDataBaseContract.third_table.COLUMN_name+","+
                myDataBaseContract.third_table.COLUMN_category+","+
                myDataBaseContract.third_table.COLUMN_price+")"+
                "values ('sweet potato ','Vegetables','3.5')"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ myDataBaseContract.first_table.TABLE_customer);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ myDataBaseContract.third_table.table_products);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ myDataBaseContract.second_table.table_bills);
        onCreate(sqLiteDatabase);
    }

    public Cursor fetch_products()
    {
        obj=getReadableDatabase();
        Cursor cursor=obj.query(myDataBaseContract.third_table.table_products,null,null,null,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();
        obj.close();
        return cursor;
    }
    public Cursor check_email(String email)
    {
        obj=getReadableDatabase();
        String[]arg={email};
        Cursor cursor= obj.query(myDataBaseContract.first_table.TABLE_customer,null,myDataBaseContract.first_table.COLUMN_email+"=?",arg,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();
        obj.close();
        return cursor;
    }
    public Cursor getCustomer(String email,String pass)
    {
        obj=getReadableDatabase();
        String[]arg={email,pass};
        Cursor cursor= obj.query(myDataBaseContract.first_table.TABLE_customer,null,myDataBaseContract.first_table.COLUMN_email+"=? AND "+myDataBaseContract.first_table.COLUMN_password+"=?",arg,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();
        obj.close();
        return cursor;
    }
    public void add_customer(String name,String email,String password,int age)
    {
        ContentValues values=new ContentValues();
        values.put(myDataBaseContract.first_table.COLUMN_Name,name);
        values.put(myDataBaseContract.first_table.COLUMN_email,email);
        values.put(myDataBaseContract.first_table.COLUMN_password,password);
        values.put(myDataBaseContract.first_table.COLUMN_age,age);
        obj=getWritableDatabase();
        obj.insert(myDataBaseContract.first_table.TABLE_customer,null,values);
        obj.close();
    }
    public void Remember(int id,String name,String email,String password,int age)
    {
    ContentValues values=new ContentValues();
        values.put(myDataBaseContract.fourth_table._ID,id);
    values.put(myDataBaseContract.fourth_table.COLUMN_Name,name);
    values.put(myDataBaseContract.fourth_table.COLUMN_email,email);
    values.put(myDataBaseContract.fourth_table.COLUMN_password,password);
    values.put(myDataBaseContract.fourth_table.COLUMN_age,age);
    obj=getWritableDatabase();
    obj.insert(myDataBaseContract.fourth_table.TABLE_customer,null,values);
    obj.close();
}
    public Cursor checkRemember()
    {
    obj=getReadableDatabase();
    Cursor cursor=obj.query(myDataBaseContract.fourth_table.TABLE_customer,null,null,null,null,null,null);
    if (cursor!=null)
        cursor.moveToFirst();
    obj.close();
    return cursor;
}


    public void deleteRemeber()
    {

    obj=getWritableDatabase();
    obj.execSQL("delete from "+myDataBaseContract.fourth_table.TABLE_customer);
    obj.close();
}
  public  Cursor getProducts(String cat)
    {
        obj=getReadableDatabase();
        String[] arg={cat};
        Cursor cursor=obj.query(myDataBaseContract.third_table.table_products,null,
                myDataBaseContract.third_table.COLUMN_category+"=?",arg,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();
        obj.close();
        return cursor;
    }
    public Cursor fetch_customers()
    {
        obj=getReadableDatabase();
        //String[] row={myDataBaseContract.third_table.COLUMN_name,myDataBaseContract.third_table.COLUMN_category,myDataBaseContract.third_table.COLUMN_price};
        Cursor cursor=obj.query(myDataBaseContract.first_table.TABLE_customer,null,null,null,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();
        obj.close();
        return cursor;
    }
    public Cursor getProduct(String name)
    {
        obj=getReadableDatabase();
        String[]arg={"%"+name+"%"};
        Cursor cursor= obj.query(myDataBaseContract.third_table.table_products,null,myDataBaseContract.third_table.COLUMN_name+" like ?",arg,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();
        obj.close();
        return cursor;
    }
    public void add_order(String products,String date,String custID,String total,String loc)
    {
        ContentValues values=new ContentValues();
        values.put(myDataBaseContract.second_table.COLUMN_products,products);
        values.put(myDataBaseContract.second_table.COLUMN_date,date);
        values.put(myDataBaseContract.second_table.COLUMN_cus_ID,custID);
        values.put(myDataBaseContract.second_table.COLUMN_total,total);
        values.put(myDataBaseContract.second_table.COLUMN_location,loc);
        obj=getWritableDatabase();
        obj.insert(myDataBaseContract.second_table.table_bills,null,values);
        obj.close();
    }
    public  Cursor getOrders(String catsID)
    {
        obj=getReadableDatabase();
        String[] arg={catsID};
        Cursor cursor=obj.query(myDataBaseContract.second_table.table_bills,null,
                myDataBaseContract.second_table.COLUMN_cus_ID+"=?",arg,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();
        obj.close();
        return cursor;
    }
}
