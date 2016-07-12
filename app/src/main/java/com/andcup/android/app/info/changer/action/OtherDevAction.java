package com.andcup.android.app.info.changer.action;

import com.andcup.android.app.info.changer.manager.DevManager;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/25.
 */
public class OtherDevAction implements AbsAction{

    @Override
    public void execute() {
        DevManager.editOtherDev();
    }
}
