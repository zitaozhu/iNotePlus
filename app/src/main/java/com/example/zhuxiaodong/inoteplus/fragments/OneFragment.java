package com.example.zhuxiaodong.inoteplus.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhuxiaodong.inoteplus.R;

/**
 * Created by zhuxiaodong on 2018/8/5.
 */

public class OneFragment extends Fragment {
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mView==null){
            mView=inflater.inflate(R.layout.one_fragment,container,false);
        }
        return mView;
    }
}
