package com.example.zhuxiaodong.inoteplus.entities;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by zhuxiaodong on 2018/8/15.
 */

public class FileAsset extends BaseAsset {
    private int fileID;
    private String fileName;
    private File fileObject;

    public FileAsset(int assetID, long dateAdded) {
        super(assetID, dateAdded);
        fileID = assetID;
    }

    public Bitmap getFileObject() {
        return null;
    }
}
