package com.example.zhuxiaodong.inoteplus.widgets;

import com.example.zhuxiaodong.inoteplus.R;
import com.example.zhuxiaodong.inoteplus.entities.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuxiaodong on 2018/8/5.
 */

public class ItemDataUtils {
    private ItemDataUtils() {
    }

    public static List<ItemBean> getItemBeans(){
        List<ItemBean> itemBeans=new ArrayList<>();
        itemBeans.add(new ItemBean(R.drawable.sidebar_purse,"My Purse",false));
        itemBeans.add(new ItemBean(R.drawable.sidebar_decoration,"My Decorations",false));
        itemBeans.add(new ItemBean(R.drawable.sidebar_favorit,"My Favorites",false));
        itemBeans.add(new ItemBean(R.drawable.sidebar_album,"My Album",false));
        itemBeans.add(new ItemBean(R.drawable.sidebar_file,"My Files",false));
        return  itemBeans;
    }
}
