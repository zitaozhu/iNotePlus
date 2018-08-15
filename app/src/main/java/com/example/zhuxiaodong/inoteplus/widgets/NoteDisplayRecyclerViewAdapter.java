package com.example.zhuxiaodong.inoteplus.widgets;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhuxiaodong.inoteplus.R;
import com.example.zhuxiaodong.inoteplus.database.NoteDatabaseHelper;
import com.example.zhuxiaodong.inoteplus.entities.ColorList;

import java.util.ArrayList;

/**
 * Adapter for recyclerview that display the notes
 * Created by zhuxiaodong on 2018/8/7.
 */

public class NoteDisplayRecyclerViewAdapter extends RecyclerView.Adapter<NoteDisplayRecyclerViewAdapter.ViewHolder> {

    private final String FAVORITE_NOTE_QUERY = "select category from Note where id = ";
    private final String SELECT_ALL = "SELECT * FROM Note";

    private LayoutInflater mInflater;
    private ItemListener mListener;
    private ArrayList<String> mTitles = null;
    private ArrayList<String> mContent = null;
    private ArrayList<Integer> NoteID = null;
    private Context mContext;
    private RelativeLayout relativeLayout;
    private ImageButton fav_button;
    private ImageButton delete_button;
    //private final int MAX_DISPLAY = 20;
    private NoteDatabaseHelper dbHelper;



    public NoteDisplayRecyclerViewAdapter(Context context, ItemListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.dbHelper = NoteDatabaseHelper.getInstance(context);
        mContext = context;
        mListener = listener;
        //this.mTitles = new String[MAX_DISPLAY];
        mTitles = new ArrayList<>();
        mContent = new ArrayList<>();
        NoteID = new ArrayList<>();

        initData();
    }


    private void initData() {
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

    public void refreshData() {
        mTitles.clear();
        mContent.clear();
        NoteID.clear();
        initData();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_note_display, parent,false);
        //view.setBackgroundColor(Color.RED);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item_title.setText(mTitles.get(position));
        holder.item_content.setText(mContent.get(position));
        holder.currentID = NoteID.get(position);

        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(FAVORITE_NOTE_QUERY + holder.currentID, null);
        cursor.moveToFirst();
        String category = cursor.getString(cursor.getColumnIndex("category"));

//        if(category != null && category.equals("favorite")) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                Log.d("zzt", "onBindViewHolder: " + category);
//                fav_button.invalidate();
//                fav_button.setImageResource(R.drawable.baseline_favorite_black_18);
//            }
//        }
        cursor.close();
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
            item_title = view.findViewById(R.id.listdisplay_title);
            item_content = view.findViewById(R.id.listdisplay_content);
            relativeLayout = view.findViewById(R.id.relativeLayout);
            relativeLayout.setBackgroundColor(Color.parseColor(ColorList.getInstance(mContext).getRandomColor()));
            fav_button = view.findViewById(R.id.displayitem_favorite_btn);
            delete_button = view.findViewById(R.id.displayitem_deletion_btn);

            view.setOnClickListener(this);
            fav_button.setOnClickListener(this);
            delete_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(currentID, v);
            }
        }
    }

    public interface ItemListener {
        void onItemClick(int NoteID, View v);
    }


}
