package com.andcup.android.app.info.changer.action;

import com.andcup.android.app.info.changer.manager.DevManager;

/**
 * Created by Administrator on 2016/2/26.
 */
public class WriteFilesAction implements AbsAction {


    String mWriteStr;

    public WriteFilesAction(String writeStr){
        mWriteStr = writeStr;
    }

    @Override
    public void execute() {
        DevManager.writeStr(mWriteStr);
    }
}
