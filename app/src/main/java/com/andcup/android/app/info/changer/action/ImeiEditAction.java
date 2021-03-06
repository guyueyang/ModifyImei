package com.andcup.android.app.info.changer.action;

import com.andcup.android.app.info.changer.manager.DevManager;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class ImeiEditAction implements AbsAction {


    String mImei;

    public ImeiEditAction(String imei){
        mImei = imei;
    }

    @Override
    public void execute() {
        DevManager.editImei(mImei);
    }
}
