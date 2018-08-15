package com.example.zhuxiaodong.inoteplus.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zhuxiaodong.inoteplus.R;
import com.example.zhuxiaodong.inoteplus.activities.NoteEditingActivity;
import com.example.zhuxiaodong.inoteplus.database.NoteDatabaseHelper;
import com.example.zhuxiaodong.inoteplus.widgets.NoteDisplayRecyclerViewAdapter;

/**
 * Fragment to display note as list of items
 * Created by zhuxiaodong on 2018/8/5.
 */

public class NoteDisplayFragment extends Fragment implements NoteDisplayRecyclerViewAdapter.ItemListener{
    private View mView;
    private RecyclerView displayRecyclerView;
    private NoteDisplayRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private final String KEY = "id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if(mView==null){
            mView=inflater.inflate(R.layout.fragment_note_display,container,false);
        }

        displayRecyclerView = mView.findViewById(R.id.notedisplay_recyclerview);
        displayRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        displayRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NoteDisplayRecyclerViewAdapter(getActivity(), this);
        displayRecyclerView.setAdapter(mAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager= new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        displayRecyclerView.setLayoutManager(staggeredGridLayoutManager);


        return mView;
    }

    public void update() {

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("zzt", "onResume: notedisplayfragment");
        mAdapter.refreshData();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int NoteID, View view) {
        switch (view.getId()) {
            case R.id.displayitem_deletion_btn:
                deleteNote(NoteID);
                break;
            case R.id.displayitem_favorite_btn:
                addNoteToFavLIst(NoteID);
                break;
            default:
                Intent intent = new Intent(getActivity(), NoteEditingActivity.class);
                intent.putExtra(KEY, NoteID);
                startActivity(intent);
                break;
        }
    }

    private void addNoteToFavLIst(int noteID) {
        ContentValues values = new ContentValues();
        values.put("category", "favorite");
        NoteDatabaseHelper.getInstance(getActivity()).getWritableDatabase()
                .update("Note", values, "id = ?", new String[]{String.valueOf(noteID)});
        mAdapter.refreshData();
    }

    private void deleteNote(int noteID) {
        NoteDatabaseHelper.getInstance(getActivity()).getWritableDatabase()
                .delete("Note", "id = ?", new String[]{String.valueOf(noteID)});
        Toast.makeText(getActivity(), "Note deleted", Toast.LENGTH_SHORT).show();
        mAdapter.refreshData();
        displayRecyclerView.invalidate();

    }
}
