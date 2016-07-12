package com.andcup.android.app.info.changer;

import android.app.Application;
import android.content.Intent;

import com.andcup.android.app.info.changer.manager.ListeningService;
import com.andcup.android.app.info.changer.tools.AndroidUtils;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class EditorApplication extends Application {

    public static EditorApplication INST;

    @Override
    public void onCreate() {
        super.onCreate();
        INST = this;

        ActionManager.getInstance();
        Intent startIntent =new Intent(this,ListeningService.class);
        startService(startIntent);//启动服务
    }
}
