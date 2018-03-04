package com.example.mostafa.slaamonlineshopping;

import android.provider.BaseColumns;

/**
 * Created by mostafa on 12/13/2017.
 */

public class myDataBaseContract  {

    public static double cost;
    public static double total=0;
    public static String loc=null;

    public class first_table implements BaseColumns{
    public static final String TABLE_customer="Customers";
    public static final String COLUMN_Name="Name";
    public static final String COLUMN_age="Age";
    public static final String COLUMN_email="e_mail";
    public static final String COLUMN_password="Password";
    }
    public class second_table implements BaseColumns {
        public static final String table_bills = "Bills";
        public static final String COLUMN_cus_ID = "cus_ID";
        public static final String COLUMN_products = "products";
        public static final String COLUMN_total = "total";
        public static final String COLUMN_date = "date";
        public static final String COLUMN_location = "location";
    }
    public class third_table implements BaseColumns {
        public static final String table_products = "products";
        public static final String COLUMN_name = "name";
        public static final String COLUMN_category = "category";
        public static final String COLUMN_price = "price";
    }

    public class fourth_table implements BaseColumns
    {
        public static final String TABLE_customer="RememberMe";
        public static final String COLUMN_Name="Name";
        public static final String COLUMN_age="Age";
        public static final String COLUMN_email="e_mail";
        public static final String COLUMN_password="Password";
    }

}
