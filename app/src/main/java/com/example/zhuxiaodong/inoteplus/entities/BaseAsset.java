package com.example.zhuxiaodong.inoteplus.entities;

/**
 * Created by zhuxiaodong on 2018/8/15.
 */

public class BaseAsset {
    private int assetID;
    private long dateAdded;

    public BaseAsset(int assetID, long dateAdded) {
        this.assetID = assetID;
        this.dateAdded = dateAdded;
    }

    public int getAssetID() {
        return assetID;
    }

    public long getDateAdded() {
        return dateAdded;
    }
}
