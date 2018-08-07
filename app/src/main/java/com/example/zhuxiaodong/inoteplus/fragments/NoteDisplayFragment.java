package com.example.zhuxiaodong.inoteplus.fragments;

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
import com.example.zhuxiaodong.inoteplus.widgets.NoteDisplayRecyclerViewAdapter;

/**
 * Created by zhuxiaodong on 2018/8/5.
 */

public class NoteDisplayFragment extends Fragment {
    private View mView;
    private RecyclerView recyclerView_one;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if(mView==null){
            mView=inflater.inflate(R.layout.fragment_note_display,container,false);
        }

        recyclerView_one=(RecyclerView)mView.findViewById(R.id.notedisplay_recyclerview);
        recyclerView_one.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView_one.setLayoutManager(mLayoutManager);
        mAdapter = new NoteDisplayRecyclerViewAdapter(getActivity());
        recyclerView_one.setAdapter(mAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager= new StaggeredGridLayoutManager(2,OrientationHelper.VERTICAL);
        recyclerView_one.setLayoutManager(staggeredGridLayoutManager);
        return mView;
    }
}
