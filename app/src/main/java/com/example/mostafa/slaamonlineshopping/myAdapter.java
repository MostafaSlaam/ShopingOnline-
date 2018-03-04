package com.example.mostafa.slaamonlineshopping;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mostafa on 12/16/2017.
 */

public class myAdapter extends RecyclerView.Adapter<myAdapter.TaskView> {

    private Cursor cursor;
    private Context context;

    public myAdapter(Cursor cursor, Context context) {
        this.cursor = cursor;
        this.context = context;
    }

    @Override
    public TaskView onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.list_items_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        TaskView viewHolder = new TaskView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaskView holder, int position) {

        cursor.moveToPosition(position);

        holder.textView1.setText("Total: "+cursor.getString(4));
        holder.textView2.setText(cursor.getString(1));
        holder.textView3.setText(cursor.getString(2));
        holder.textView4.setText(cursor.getString(5));

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class TaskView extends RecyclerView.ViewHolder  {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;

        public TaskView(View itemView) {
            super(itemView);
            textView1=(TextView)itemView.findViewById(R.id.TV_item1);
            textView2=(TextView)itemView.findViewById(R.id.TV_item2);
            textView3=(TextView)itemView.findViewById(R.id.TV_item3);
            textView4=(TextView)itemView.findViewById(R.id.TV_item4);
        }
    }
}
