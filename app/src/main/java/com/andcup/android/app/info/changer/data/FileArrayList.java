package com.andcup.android.app.info.changer.data;

import com.andcup.android.app.info.changer.action.WriteFilesAction;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/24.
 */
public class FileArrayList {
    static ArrayList<String> fileList=new ArrayList<>();

    public static void  addArrayList(String event,String s){
        if(event.equals("[CREATE]")){
            fileList.add(s);
            new WriteFilesAction(s).execute();
        }
    }
    public static void deleteArrayList(){
            fileList.clear();
    }

    public static ArrayList<String> getFileList() {
        return fileList;
    }

    public static void setFileList(ArrayList<String> fileList) {
        FileArrayList.fileList = fileList;
    }
}
