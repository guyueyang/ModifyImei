package com.andcup.android.app.info.changer.action;

import com.andcup.android.app.info.changer.manager.FileDataManager;
import com.andcup.android.app.info.changer.manager.FileManager;

import java.util.ArrayList;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class DataRemoveAction implements AbsAction {

    private String[] mFiles;
    private ArrayList<String> mFileDeletes;

    public DataRemoveAction(String[] files){
        mFiles = files;
    }
    @Override
    public void execute() {
        FileDataManager.clear(mFiles);
    }
}
