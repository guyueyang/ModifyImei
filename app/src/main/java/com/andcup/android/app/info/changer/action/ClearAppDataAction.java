package com.andcup.android.app.info.changer.action;

import android.content.Context;

import com.andcup.android.app.info.changer.manager.DataManager;

import java.util.ArrayList;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class ClearAppDataAction implements AbsAction{

    private String mPackage;

    public ClearAppDataAction(String packageName){
        mPackage = packageName;
    }

    @Override
    public void execute() {
        DataManager.clear(mPackage);
    }
}
