package com.andcup.android.app.info.changer.manager;


import android.content.Context;

import com.andcup.android.app.info.changer.tools.CacheManager;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class DataManager {

    public static void clear(String packageName) {
        CacheManager.cleanAppData2(packageName);
    }
    public static void kill(String packageName){
        CacheManager.kill(packageName);
    }
}
