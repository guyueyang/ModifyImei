package com.andcup.android.app.info.changer.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.andcup.android.app.info.changer.ActionManager;
import com.andcup.android.app.info.changer.R;
import com.andcup.android.app.info.changer.data.BundleKey;
import com.andcup.android.app.info.changer.model.EditorItem;
import com.andcup.android.app.info.changer.tools.AndroidUtils;
import com.andcup.android.framework.binding.widget.RxTextView;
import com.andcup.android.framework.tools.DialogFragmentJumper;
import com.andcup.android.framework.tools.FragmentJumper;

import static android.app.PendingIntent.getActivity;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class EditorAdapter extends RecyclerView.Adapter<EditorAdapter.ViewHolder> {

    public static EditorItem[] EDITOR_ITEMS = new EditorItem[]{
            EditorItem.MODEL,
            EditorItem.IMEI,
            EditorItem.BRAND,
            EditorItem.PHONE,
            EditorItem.DATA2,
            EditorItem.DATA,
            EditorItem.DATAS,
            EditorItem.DELETEFILES,
            EditorItem.FOLDER,
            EditorItem.FOLDERDATA
    };

    private LayoutInflater  mLayoutInflater;
    private Fragment        mFragment;
    private boolean         mIsRandom = true;
    private boolean         mIsFirstRun = true;
    private boolean         mIsHistoricalRecord=false;

    public EditorAdapter(Fragment fragment) {
        mFragment       = fragment;
        mLayoutInflater = LayoutInflater.from(mFragment.getActivity());
    }

    public void setRandom(boolean random){
        mIsRandom = random;
    }
    public void setHistoricalRecord(boolean historicalRecord){
        mIsHistoricalRecord=historicalRecord;
    }

    @Override
    public int getItemViewType(int position) {
        return EDITOR_ITEMS[position].mTitle;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.list_item_editor, null);
        if(viewType == EditorItem.FOLDER.mTitle){
            itemView = mLayoutInflater.inflate(R.layout.list_item_editor_files, null);
        }else if(viewType == EditorItem.DATA.mTitle){
            itemView = mLayoutInflater.inflate(R.layout.list_item_editor_data, null);
        }else if(viewType == EditorItem.DELETEFILES.mTitle){
            itemView = mLayoutInflater.inflate(R.layout.list_item_editor_delete, null);
        }else if(viewType == EditorItem.DATAS.mTitle){
            itemView = mLayoutInflater.inflate(R.layout.list_item_editor_data, null);
        }else if(viewType == EditorItem.DATA2.mTitle){
            itemView = mLayoutInflater.inflate(R.layout.list_item_editor_data, null);
        }else if(viewType == EditorItem.FOLDERDATA.mTitle){
            itemView = mLayoutInflater.inflate(R.layout.list_item_editor_files_data, null);
        }

        if(viewType == EditorItem.MODEL.mTitle ||
                viewType == EditorItem.IMEI.mTitle ||
                viewType == EditorItem.BRAND.mTitle ||
                viewType == EditorItem.PHONE.mTitle){
            return new EditViewHolder(itemView);
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindView(position);
    }

    @Override
    public int getItemCount() {
        return EDITOR_ITEMS.length;
    }

    class EditViewHolder extends ViewHolder{

        private EditText mEtText;

        public EditViewHolder(View itemView) {
            super(itemView);
            mEtText = (EditText) itemView.findViewById(R.id.et_input);
            RxTextView.afterTextChangeEvents(mEtText).subscribe(textViewAfterTextChangeEvent -> {
                if(!mIsRandom){
                    EDITOR_ITEMS[mPosition].mValue.putString(BundleKey.KEY_DATA, mEtText.getText().toString());
                }
            });
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            if(EDITOR_ITEMS[mPosition] == EditorItem.MODEL ||
               EDITOR_ITEMS[mPosition] == EditorItem.IMEI ||
               EDITOR_ITEMS[mPosition] == EditorItem.BRAND ||
               EDITOR_ITEMS[mPosition] == EditorItem.PHONE){
                String value = EDITOR_ITEMS[mPosition].mValue.getString(BundleKey.KEY_DATA);
                if(EDITOR_ITEMS[mPosition] == EditorItem.IMEI){
                    if(mIsHistoricalRecord){
                        Log.i("------","不复制");
                        mIsHistoricalRecord=false;
                        mEtText.setFocusable(true);
                        mEtText.setFocusableInTouchMode(true);
                        mEtText.requestFocus();
                    }else {
                        AndroidUtils.copy(value, mFragment.getActivity());
                        Log.i("------", "复制");
                    }
                }
                if(EDITOR_ITEMS[mPosition]==EditorItem.PHONE){
                    mEtText.setVisibility(View.GONE);
                }
                if(EDITOR_ITEMS[mPosition]==EditorItem.BRAND){
                    mEtText.setVisibility(View.GONE);
                }

                mEtText.setText(value);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        protected int mPosition;
        protected CheckBox mCbItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mCbItem = (CheckBox) itemView.findViewById(R.id.cb_action);
        }

        public void onBindView(int position) {
            mPosition = position;
            itemView.setOnClickListener(this);
            mCbItem.setOnCheckedChangeListener(this);
            mCbItem.setText(EDITOR_ITEMS[mPosition].getTitle(mFragment.getActivity()));

//            if(EDITOR_ITEMS[mPosition] == EditorItem.FOLDER ||
//                    EDITOR_ITEMS[mPosition] == EditorItem.DATA ||
//                    EDITOR_ITEMS[mPosition] == EditorItem.DELETEFILES
//                    ){
//                if(mIsFirstRun){
//                    mCbItem.setChecked(false);
//                    mIsFirstRun = false;
//                }
//            }
            boolean mdata=AndroidUtils.readData(mFragment.getActivity());
            boolean mdatas=AndroidUtils.readDatas(mFragment.getActivity());
            boolean mdata2=AndroidUtils.readData2(mFragment.getActivity());
            boolean mFolder=AndroidUtils.readFolder(mFragment.getActivity());
            boolean mFolderData=AndroidUtils.readDataFolder(mFragment.getActivity());
            boolean mDeletefiles=AndroidUtils.readDeleteftles(mFragment.getActivity());
            if(EDITOR_ITEMS[mPosition]==EditorItem.DATA){
                mCbItem.setChecked(mdata);
                if(mdata){
                    String title = EDITOR_ITEMS[mPosition].getTitle(mFragment.getContext());
                    String mdataName=AndroidUtils.readDataName(mFragment.getActivity());
                    EditorItem.DATA.mName.putString(BundleKey.KEY_DATA,mdataName);
                        if(!TextUtils.isEmpty(mdataName)){
                            title += "[" + mdataName + "]";
                        }
                    mCbItem.setText(title);
                }
            }
            if(EDITOR_ITEMS[mPosition]==EditorItem.DATAS){
                mCbItem.setChecked(mdatas);
                if(mdatas){
                    String title = EDITOR_ITEMS[mPosition].getTitle(mFragment.getContext());
                    String mdataName=AndroidUtils.readDataNames(mFragment.getActivity());
                    EditorItem.DATAS.mName.putString(BundleKey.KEY_DATA,mdataName);
                    if(!TextUtils.isEmpty(mdataName)){
                        title += "[" + mdataName + "]";
                    }
                    mCbItem.setText(title);
                }
            }

            if(EDITOR_ITEMS[mPosition]==EditorItem.DATA2){
                mCbItem.setChecked(mdata2);
                if(mdata2){
                    String title = EDITOR_ITEMS[mPosition].getTitle(mFragment.getContext());
                    String mdataName=AndroidUtils.readDataName2(mFragment.getActivity());
                    EditorItem.DATA2.mName.putString(BundleKey.KEY_DATA,mdataName);
                    if(!TextUtils.isEmpty(mdataName)){
                        title += "[" + mdataName + "]";
                    }
                    mCbItem.setText(title);
                }
            }
            if(EDITOR_ITEMS[mPosition]==EditorItem.FOLDER){
                mCbItem.setChecked(mFolder);
                FragmentJumper fragmentJumper = new FragmentJumper(mFragment.getChildFragmentManager());
                if(mFolder){
                    fragmentJumper.at(R.id.fr_action_container).to(FilesFragment.class).go();
                }else{
                    fragmentJumper.at(R.id.fr_action_container).to(FilesFragment.class).finish();
                }
            }
            if(EDITOR_ITEMS[mPosition]==EditorItem.FOLDERDATA){
                mCbItem.setChecked(mFolderData);
                FragmentJumper fragmentJumper = new FragmentJumper(mFragment.getChildFragmentManager());
                if(mFolderData){
                    fragmentJumper.at(R.id.fr_action_container_data).to(FilesDataFragment.class).go();
                }else{
                    fragmentJumper.at(R.id.fr_action_container_data).to(FilesDataFragment.class).finish();
                }
            }
            if(EDITOR_ITEMS[mPosition]==EditorItem.DELETEFILES){
                mCbItem.setChecked(mDeletefiles);
            }
            if(EDITOR_ITEMS[mPosition]==EditorItem.BRAND){
                mCbItem.setVisibility(View.GONE);
            }
            if(EDITOR_ITEMS[mPosition]==EditorItem.PHONE){
                mCbItem.setVisibility(View.GONE);
            }



        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b){
                ActionManager.getInstance().addEditor(EDITOR_ITEMS[mPosition]);
            }else{
                ActionManager.getInstance().removeEditor(EDITOR_ITEMS[mPosition]);
            }

            if(EDITOR_ITEMS[mPosition] == EditorItem.FOLDER) {
                FragmentJumper fragmentJumper = new FragmentJumper(mFragment.getChildFragmentManager());
                if (b) {
                    fragmentJumper.at(R.id.fr_action_container).to(FilesFragment.class).go();
                    AndroidUtils.saveFolder(mFragment.getActivity(), true);
                } else {
                    fragmentJumper.at(R.id.fr_action_container).to(FilesFragment.class).go();
                    AndroidUtils.saveFolder(mFragment.getActivity(), false);
                }
            }else if(EDITOR_ITEMS[mPosition] == EditorItem.FOLDERDATA) {
                    FragmentJumper fragmentJumper = new FragmentJumper(mFragment.getChildFragmentManager());
                    if (b) {
                        fragmentJumper.at(R.id.fr_action_container_data).to(FilesDataFragment.class).go();
                        AndroidUtils.saveDataFolder(mFragment.getActivity(), true);
                    } else {
                        fragmentJumper.at(R.id.fr_action_container_data).to(FilesDataFragment.class).go();
                        AndroidUtils.saveDataFolder(mFragment.getActivity(), false);
                    }
            }else if(EDITOR_ITEMS[mPosition] == EditorItem.DATA){
                DialogFragmentJumper fragmentJumper = new DialogFragmentJumper(mFragment.getChildFragmentManager());
                if(b){
                    BundleKey.KEY_DATAS=0;
                    fragmentJumper.to(AppListFragment.class).go();
                    AndroidUtils.saveData(mFragment.getActivity(),true);
                }else{
                    fragmentJumper.to(AppListFragment.class).finish();
                    AndroidUtils.saveData(mFragment.getActivity(), false);
                }
            }else if(EDITOR_ITEMS[mPosition]==EditorItem.DELETEFILES){
                if(b){
                    AndroidUtils.saveDeleteftles(mFragment.getActivity(),true);
                }else {
                    AndroidUtils.saveDeleteftles(mFragment.getActivity(),false);
                }
            }else if(EDITOR_ITEMS[mPosition] == EditorItem.DATAS){
                DialogFragmentJumper fragmentJumper = new DialogFragmentJumper(mFragment.getChildFragmentManager());
                if(b){
                    BundleKey.KEY_DATAS=1;
                    fragmentJumper.to(AppListFragment.class).go();
                    AndroidUtils.saveDatas(mFragment.getActivity(),true);
                }else{
                    fragmentJumper.to(AppListFragment.class).finish();
                    AndroidUtils.saveDatas(mFragment.getActivity(), false);
                }
            }
            else if(EDITOR_ITEMS[mPosition] == EditorItem.DATA2){
                DialogFragmentJumper fragmentJumper = new DialogFragmentJumper(mFragment.getChildFragmentManager());
                if(b){
                    BundleKey.KEY_DATAS=2;
                    fragmentJumper.to(AppListFragment.class).go();
                    AndroidUtils.saveData2(mFragment.getActivity(), true);
                }else{
                    fragmentJumper.to(AppListFragment.class).finish();
                    AndroidUtils.saveData2(mFragment.getActivity(), false);
                }
            }
        }
    }
}
