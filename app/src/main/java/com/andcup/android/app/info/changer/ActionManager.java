package com.andcup.android.app.info.changer;

import android.util.Log;

import com.andcup.android.app.info.changer.data.BundleKey;
import com.andcup.android.app.info.changer.model.EditorItem;
import com.andcup.android.app.info.changer.tools.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class ActionManager {

    private static ActionManager mActionManager;

    private List<EditorItem> mEditorList = new ArrayList<>();

    private ActionManager(){

    }

    public static ActionManager getInstance(){
        if( null == mActionManager){
            mActionManager = new ActionManager();

            EditorItem.MODEL.mValue.putString(BundleKey.KEY_DATA, AndroidUtils.readModel(EditorApplication.INST));
            EditorItem.BRAND.mValue.putString(BundleKey.KEY_DATA, AndroidUtils.readBrand(EditorApplication.INST));
            EditorItem.IMEI.mValue.putString(BundleKey.KEY_DATA, AndroidUtils.readImei(EditorApplication.INST));
            EditorItem.PHONE.mValue.putString(BundleKey.KEY_DATA, AndroidUtils.readPhone(EditorApplication.INST));
            EditorItem.DATA.mValue.putString(BundleKey.KEY_DATA,AndroidUtils.readPackageName(EditorApplication.INST));
            EditorItem.DATAS.mValue.putString(BundleKey.KEY_DATA,AndroidUtils.readPackageNames(EditorApplication.INST));
            EditorItem.DATA2.mValue.putString(BundleKey.KEY_DATA,AndroidUtils.readPackageName2(EditorApplication.INST));
            mActionManager.mEditorList.add(EditorItem.MODEL);
            mActionManager.mEditorList.add(EditorItem.IMEI);
            mActionManager.mEditorList.add(EditorItem.BRAND);
            mActionManager.mEditorList.add(EditorItem.PHONE);
            mActionManager.mEditorList.add(EditorItem.DATA);
            mActionManager.mEditorList.add(EditorItem.DATAS);
            mActionManager.mEditorList.add(EditorItem.DATA2);
            mActionManager.mEditorList.add(EditorItem.FOLDER);
            mActionManager.mEditorList.add(EditorItem.DELETEFILES);
            mActionManager.mEditorList.add(EditorItem.FOLDERDATA);
        }
        return mActionManager;
    }

    public void addEditor(EditorItem editorItem){
        if(!mEditorList.contains(editorItem)){
            mEditorList.add(editorItem);
        }
    }

    public void removeEditor(EditorItem editorItem){
        if(mEditorList.contains(editorItem)){
            mEditorList.remove(editorItem);
        }
    }

    public List<EditorItem> getEditorList() {
        return mEditorList;
    }
}
