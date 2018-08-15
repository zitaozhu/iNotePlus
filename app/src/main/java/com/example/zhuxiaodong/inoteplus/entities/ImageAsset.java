package com.example.zhuxiaodong.inoteplus.entities;

import android.graphics.Bitmap;

/**
 * Created by zhuxiaodong on 2018/8/15.
 */

public class ImageAsset extends BaseAsset {
    private int imageID;
    private String imageName;
    private Bitmap imageObject;

    public ImageAsset(int assetID, long dateAdded) {
        super(assetID, dateAdded);
        imageID = assetID;
    }

    public Bitmap getImageObject() {
        return null;
    }

}
