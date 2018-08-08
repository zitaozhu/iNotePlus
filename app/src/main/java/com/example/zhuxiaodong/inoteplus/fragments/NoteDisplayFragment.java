package com.example.zhuxiaodong.inoteplus.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhuxiaodong.inoteplus.R;
import com.example.zhuxiaodong.inoteplus.activities.NoteEditingActivity;
import com.example.zhuxiaodong.inoteplus.widgets.NoteDisplayRecyclerViewAdapter;

/**
 * Created by zhuxiaodong on 2018/8/5.
 */

public class NoteDisplayFragment extends Fragment implements NoteDisplayRecyclerViewAdapter.ItemListener{
    private View mView;
    private RecyclerView displayRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private final String KEY = "id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if(mView==null){
            mView=inflater.inflate(R.layout.fragment_note_display,container,false);
        }

        displayRecyclerView =(RecyclerView) mView.findViewById(R.id.notedisplay_recyclerview);
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

    @Override
    public void onItemClick(int NoteID) {
        Intent intent = new Intent(getActivity(), NoteEditingActivity.class);
        intent.putExtra(KEY, NoteID);
        startActivity(intent);
    }
}
