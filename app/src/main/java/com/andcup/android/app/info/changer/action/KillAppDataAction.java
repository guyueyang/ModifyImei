package com.andcup.android.app.info.changer.action;

import android.util.Log;

import com.andcup.android.app.info.changer.manager.DataManager;

/**
 * Created by Administrator on 2016/3/16.
 */
public class KillAppDataAction implements AbsAction{

    private String mPackage;

    public KillAppDataAction(String packageName){
        mPackage = packageName;
    }

    @Override
    public void execute() {
        DataManager.kill(mPackage);
        Log.i("----------关闭-------",mPackage);
    }
}