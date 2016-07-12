package com.andcup.android.app.info.changer.model;

import android.content.Context;
import android.os.Bundle;

import com.andcup.android.app.info.changer.R;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public enum EditorItem {

    IMEI(R.string.item_imei),
    MODEL(R.string.item_model),
    BRAND(R.string.item_brand),
    PHONE(R.string.item_phone),
    DATA(R.string.item_cache),
    DATAS(R.string.item_cache2),
    DATA2(R.string.item_cache3),
    DELETEFILES(R.string.item_delete_files),
    FOLDER(R.string.item_files),
    FOLDERDATA(R.string.itme_files_data);

    public Bundle mValue = new Bundle();
    public Bundle mName  = new Bundle();
    public int    mTitle;

    EditorItem(int titleId){
        mTitle = titleId;
    }

    public void setValue(Bundle mValue) {
        this.mValue = mValue;
    }
    public Bundle getValue() {
        return mValue;
    }
    public Bundle getmName() {
        return mName;
    }

    public void setmName(Bundle mName) {
        this.mName = mName;
    }

    public String getTitle(Context context){
        return context.getResources().getString(mTitle);
    }
}
