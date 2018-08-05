package com.example.zhuxiaodong.inoteplus.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zhuxiaodong.inoteplus.R;

/**
 * Created by zhuxiaodong on 2018/8/5.
 */

public class TitleFragment extends Fragment {
    private ImageButton mLeftMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_title, container, false);

        mLeftMenu = (ImageButton) view.findViewById(R.id.id_title_left_btn);
        mLeftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "btnnnnnnnnnnnnnn in titlefragment", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
