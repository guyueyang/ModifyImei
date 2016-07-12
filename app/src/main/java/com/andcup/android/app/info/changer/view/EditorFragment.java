package com.andcup.android.app.info.changer.view;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.andcup.android.app.info.changer.ActionManager;
import com.andcup.android.app.info.changer.R;
import com.andcup.android.app.info.changer.action.BrandAction;
import com.andcup.android.app.info.changer.action.ClearAppDataAction;
import com.andcup.android.app.info.changer.action.DataRemoveAction;
import com.andcup.android.app.info.changer.action.FilesRemoveAction;
import com.andcup.android.app.info.changer.action.ImeiEditAction;
import com.andcup.android.app.info.changer.action.KillAppDataAction;
import com.andcup.android.app.info.changer.action.ListeningFilesRemoveAction;
import com.andcup.android.app.info.changer.action.ModelEditAction;
import com.andcup.android.app.info.changer.action.OtherDevAction;
import com.andcup.android.app.info.changer.action.PhoneEditAction;
import com.andcup.android.app.info.changer.action.WriteFilesAction;
import com.andcup.android.app.info.changer.data.BundleKey;
import com.andcup.android.app.info.changer.data.FileArrayList;
import com.andcup.android.app.info.changer.model.EditorItem;
import com.andcup.android.app.info.changer.rxfileobserver.FileEvent;
import com.andcup.android.app.info.changer.rxfileobserver.RxFileObserver;
import com.andcup.android.app.info.changer.tools.AndroidUtils;
import com.andcup.android.framework.RxFragment;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/22.
 */
public class EditorFragment extends RxFragment{

    @Bind(R.id.rv_list)
    RecyclerView mLvEdit;
    @Bind(R.id.cb_random)
    CheckBox mCbFromUser;
    @Bind(R.id.cb_historical_record)
    CheckBox mCbHistoricalRecord;

    private EditorAdapter mAdapter;
    private boolean       mInputByUser = false;
    private boolean       mIsHistoricalRecord=false;
    private String        mValue;
    private String        mMODEL;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_editor;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        AndroidUtils.CreateFile();
        mLvEdit.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new EditorAdapter(this);
        mLvEdit.setAdapter(mAdapter);
        Boolean historicalRecord=AndroidUtils.readHistoricalRecord(getActivity());
        mCbFromUser.setChecked(historicalRecord);
        mInputByUser=historicalRecord;
//        mAdapter.setHistoricalRecord(true);
        mCbFromUser.setOnCheckedChangeListener((compoundButton, b) -> {
                    mInputByUser = b;
                    mAdapter.setRandom(!mInputByUser);
                    AndroidUtils.saveHistoricalRecord(getActivity(), mInputByUser);
                }
        );

        mCbHistoricalRecord.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mIsHistoricalRecord = isChecked;
            mAdapter.setRandom(!mIsHistoricalRecord);
            mAdapter.setHistoricalRecord(mIsHistoricalRecord);
            mAdapter.notifyDataSetChanged();
        });
    }

    @OnClick(R.id.btn_action)
    public void oneKeyApply() {
        onEditor();
    }

    @OnClick(R.id.btn_open)
    public void onOpen(){
        AndroidUtils.OpenAPP(getActivity(), EditorItem.DATA2.mValue.getString(BundleKey.KEY_DATA));
    }
    @OnClick(R.id.btn_imei)
    public void onIMEI(){
        mAdapter.setRandom(!mInputByUser);
        EditorItem.IMEI.mValue.putString(BundleKey.KEY_DATA,"");
        mAdapter.setHistoricalRecord(true);
        mAdapter.notifyDataSetChanged();
    }

    public void onEditor(){
        List<EditorItem> editorItemList = ActionManager.getInstance().getEditorList();
        for(int i = 0; i< editorItemList.size(); i++){
            genData(editorItemList.get(i));
            execute(editorItemList.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }

    private void genData(EditorItem editorItem){
        switch (editorItem){
            case FOLDER:
            case FOLDERDATA:
            case DATA:
            case DATAS:
            case DATA2:
                break;
            case PHONE:
                if(!mInputByUser){
                    editorItem.mValue.putString(BundleKey.KEY_DATA, AndroidUtils.randomPhone());
                }
                break;
            case IMEI:
                if(!mInputByUser){
                    editorItem.mValue.putString(BundleKey.KEY_DATA, AndroidUtils.randomImei(15));
                }
                if(mIsHistoricalRecord){
//                    editorItem.mValue.putString(BundleKey.KEY_DATA, AndroidUtils.randomImei(15));
                        try {
                            editorItem.mValue.putString(BundleKey.KEY_DATA, AndroidUtils.getCopy(getActivity()));
                            Log.i("剪切板---------",AndroidUtils.getCopy(getActivity())+"");
                        } catch (Exception e) {
                            editorItem.mValue.putString(BundleKey.KEY_DATA, "");
                        }
                }
                break;
            case BRAND:
                String model = mMODEL;
                if(TextUtils.isEmpty(model)){
                    if(!mInputByUser){
                        model = AndroidUtils.randomModel(getActivity());
                    }
                }
                editorItem.mValue.putString(BundleKey.KEY_DATA, model.split(" ")[0]);
                break;
            case MODEL:
                mMODEL=AndroidUtils.randomModel(getActivity());
                editorItem.mValue.putString(BundleKey.KEY_DATA,mMODEL);
                break;
            case DELETEFILES:
                editorItem.DELETEFILES.mValue.putStringArrayList(BundleKey.KEY_DATA, FileArrayList.getFileList());
                break;
        }
    }

    private void execute(EditorItem editorItem){
        switch (editorItem){
            case FOLDER:
                new FilesRemoveAction(EditorItem.FOLDER.mValue.getStringArray(BundleKey.KEY_DATA)).execute();
                break;
            case FOLDERDATA:
                new DataRemoveAction(EditorItem.FOLDERDATA.mValue.getStringArray(BundleKey.KEY_DATA)).execute();
                break;
            case BRAND:
                new BrandAction(EditorItem.BRAND.mValue.getString(BundleKey.KEY_DATA)).execute();
                break;
            case IMEI:
                new ImeiEditAction(EditorItem.IMEI.mValue.getString(BundleKey.KEY_DATA)).execute();
                break;
            case MODEL:
                new ModelEditAction(EditorItem.MODEL.mValue.getString(BundleKey.KEY_DATA)).execute();
                break;
            case PHONE:
                new PhoneEditAction(EditorItem.PHONE.mValue.getString(BundleKey.KEY_DATA)).execute();
                new OtherDevAction().execute();
                if(!mIsHistoricalRecord){
//                    String writeStr=EditorItem.BRAND.mValue.getString(BundleKey.KEY_DATA)+" "+EditorItem.MODEL.mValue.getString(BundleKey.KEY_DATA)+"|"+EditorItem.IMEI.mValue.getString(BundleKey.KEY_DATA);
//                    new WriteFilesAction(writeStr).execute();
                }
                break;
            case DATA2:
                new KillAppDataAction(EditorItem.DATA2.mValue.getString(BundleKey.KEY_DATA)).execute();
                AndroidUtils.savePackageName2(getActivity(), EditorItem.DATA2.mValue.getString(BundleKey.KEY_DATA));
                break;
            case DATA:
                new ClearAppDataAction(EditorItem.DATA.mValue.getString(BundleKey.KEY_DATA)).execute();
                AndroidUtils.savePackageName(getActivity(), EditorItem.DATA.mValue.getString(BundleKey.KEY_DATA));
                break;
            case DATAS:
                new ClearAppDataAction(EditorItem.DATAS.mValue.getString(BundleKey.KEY_DATA)).execute();
                AndroidUtils.savePackageNames(getActivity(), EditorItem.DATAS.mValue.getString(BundleKey.KEY_DATA));
                break;
            case DELETEFILES:
                new ListeningFilesRemoveAction(EditorItem.DELETEFILES.mValue.getStringArrayList(BundleKey.KEY_DATA)).execute();
                break;
        }
    }

}
