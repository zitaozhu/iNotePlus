package com.example.zhuxiaodong.inoteplus.widgets;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhuxiaodong.inoteplus.R;
import com.example.zhuxiaodong.inoteplus.database.NoteDatabaseHelper;
import com.example.zhuxiaodong.inoteplus.entities.ColorList;

import java.util.ArrayList;

/**
 * Created by zhuxiaodong on 2018/8/7.
 */

public class NoteDisplayRecyclerViewAdapter extends RecyclerView.Adapter<NoteDisplayRecyclerViewAdapter.ViewHolder> {

    private final String SELECT_ALL = "SELECT * FROM Note";

    private LayoutInflater mInflater;
    protected ItemListener mListener;
    private ArrayList<String> mTitles = null;
    private ArrayList<String> mContent = null;
    private ArrayList<Integer> NoteID = null;
    private Context mContext;
    public RelativeLayout relativeLayout;
    //private final int MAX_DISPLAY = 20;
    private NoteDatabaseHelper dbHelper;



    public NoteDisplayRecyclerViewAdapter(Context context, ItemListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.dbHelper = NoteDatabaseHelper.getInstance(context);
        mContext = context;
        mListener = listener;
        //this.mTitles = new String[MAX_DISPLAY];
        mTitles = new ArrayList<String>();
        mContent = new ArrayList<String>();
        NoteID = new ArrayList<>();

        if(dbHelper.getReadableDatabase() != null) {
            Cursor cursor = dbHelper.getReadableDatabase().rawQuery(SELECT_ALL, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                mTitles.add(cursor.getString(cursor.getColumnIndex("title")));
                mContent.add(cursor.getString(cursor.getColumnIndex("content")));
                NoteID.add(cursor.getInt(cursor.getColumnIndex("id")));
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
        holder.item_content.setText(mContent.get(position));
        holder.currentID = NoteID.get(position);
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView item_title;
        public TextView item_content;
        public int currentID = -1;
        public ViewHolder(View view){
            super(view);
            item_title = (TextView)view.findViewById(R.id.listdisplay_title);
            item_content = (TextView) view.findViewById(R.id.listdisplay_content);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
            relativeLayout.setBackgroundColor(Color.parseColor(ColorList.getInstance(mContext).getRandomColor()));
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(currentID);
            }
        }
    }

    public interface ItemListener {
        void onItemClick(int NoteID);
    }
}
