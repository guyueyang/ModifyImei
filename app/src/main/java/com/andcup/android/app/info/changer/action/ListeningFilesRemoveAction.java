package com.andcup.android.app.info.changer.action;

import com.andcup.android.app.info.changer.manager.FileManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/17.
 */
public class ListeningFilesRemoveAction implements  AbsAction {

    private String[] mFiles;
    private ArrayList<String> mFileDeletes;

    public ListeningFilesRemoveAction(ArrayList<String> fileDelte){
        mFileDeletes = fileDelte;
    }

    @Override
    public void execute() {
        FileManager.clearList(mFileDeletes);
    }
}
