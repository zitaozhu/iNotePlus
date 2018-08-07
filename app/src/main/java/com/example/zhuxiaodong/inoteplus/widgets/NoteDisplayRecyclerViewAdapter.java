package com.example.zhuxiaodong.inoteplus.widgets;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhuxiaodong.inoteplus.R;
import com.example.zhuxiaodong.inoteplus.database.NoteDatabaseHelper;

import java.util.ArrayList;

/**
 * Created by zhuxiaodong on 2018/8/7.
 */

public class NoteDisplayRecyclerViewAdapter extends RecyclerView.Adapter<NoteDisplayRecyclerViewAdapter.ViewHolder> {

    private final String SELECT_TITLE = "SELECT title FROM Note";
    private final String SELECT_CONTENT = "select content from Note";

    private LayoutInflater mInflater;
    private ArrayList<String> mTitles = null;
    //private final int MAX_DISPLAY = 20;
    private NoteDatabaseHelper dbHelper;

    public NoteDisplayRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.dbHelper = NoteDatabaseHelper.getInstance(context);
        //this.mTitles = new String[MAX_DISPLAY];

        if(dbHelper.getReadableDatabase() != null) {
            Cursor cursor = dbHelper.getReadableDatabase().rawQuery(SELECT_TITLE, null);

            cursor.moveToFirst();
            mTitles = new ArrayList<String>();
            while(!cursor.isAfterLast()) {
                mTitles.add(cursor.getString(cursor.getColumnIndex("title")));
                cursor.moveToNext();
            }
            cursor.close();
        } else {
            mTitles.add("Create you first Note on iNote!");
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.note_display_item, parent,false);
        //view.setBackgroundColor(Color.RED);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item_title.setText(mTitles.get(position));
        //holder.item_content.setText(mContents[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_title;
        public TextView item_content;
        public ViewHolder(View view){
            super(view);
            item_title = (TextView)view.findViewById(R.id.listdisplay_title);
            item_content = (TextView) view.findViewById(R.id.listdisplay_content);
        }
    }
}
