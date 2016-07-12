package com.andcup.android.app.info.changer.manager;

import android.os.Environment;
import android.text.TextUtils;

import com.andcup.android.app.info.changer.data.FileArrayList;
import com.andcup.android.app.info.changer.tools.AndroidUtils;
import com.andcup.android.app.info.changer.tools.CacheManager;

import java.util.ArrayList;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class FileDataManager {

    public static void clear(String[] files){
        if( null == files){
            return;
        }
        for(String file : files){
            if(TextUtils.isEmpty(file)){
                continue;
            }
//            AndroidUtils.deleteFile(Environment.getExternalStorageDirectory() + file);
//            AndroidUtils.deleteFile(Environment.getDataDirectory()+"/data"+file);
            CacheManager.cleanData(file);
//            Log.i("目录--------",Environment.getRootDirectory()+"/data/data");
//            Log.i("目录--------",Environment.getDataDirectory()+"/data");

        }
    }

    public static void clearList(ArrayList<String> files) {
        try {
            String fileStr=AndroidUtils.readFile();
            String [] file=fileStr.split("\r\n");
            for (int i = 0; null != file && i < file.length; i++) {
                if(file[i].equals(AndroidUtils.mFilePath)){

                }else {
                    AndroidUtils.deleteFile(file[i]);
                }
            }
            AndroidUtils.writeFile("",false);
            FileArrayList.deleteArrayList();
        } catch (Exception e) {

        }
    }

}
