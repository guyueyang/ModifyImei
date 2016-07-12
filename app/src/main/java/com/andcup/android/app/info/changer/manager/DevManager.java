package com.andcup.android.app.info.changer.manager;

import android.util.Log;

import com.andcup.android.app.info.changer.EditorApplication;
import com.andcup.android.app.info.changer.tools.AndroidUtils;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class DevManager {

    public static void editModel(String model){
//        String mModel=model.split(" ")[1];
//        String [] brand=model.split(" ");
//        String str="";
//        if(brand.length==2){
//            str=brand[1];
//        }else if(brand.length==3){
//            str=brand[1]+" "+brand[2];
//        }else if(brand.length==4){
//            str=brand[1]+" "+brand[2]+" "+brand[3];
//        }
        AndroidUtils.saveModel(EditorApplication.INST, model);
    }

    public static void editImei(String imei){
        AndroidUtils.saveImei(EditorApplication.INST, imei);
    }

    public static void editBrand(String brand){
        AndroidUtils.saveBrand(EditorApplication.INST, brand);
    }

    public static void editPhone(String phone){
        AndroidUtils.savePhone(EditorApplication.INST, phone);
    }

    public static void writeStr(String writeStr){
        AndroidUtils.writeFile(writeStr,true);
        Log.i("写入文件", writeStr);
    }

    public static void editOtherDev(){
        AndroidUtils.saveOtherDev(EditorApplication.INST);
    }
}
