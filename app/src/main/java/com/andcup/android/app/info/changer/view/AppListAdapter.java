package com.andcup.android.app.info.changer.view;

import android.content.pm.PackageInfo;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andcup.android.app.info.changer.EditorApplication;
import com.andcup.android.app.info.changer.R;
import com.andcup.android.app.info.changer.data.BundleKey;
import com.andcup.android.app.info.changer.model.EditorItem;
import com.andcup.android.app.info.changer.tools.AndroidUtils;

import java.util.List;

import static android.app.PendingIntent.getActivity;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/24.
 */
public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {

    private LayoutInflater      mInflater;
    private List<PackageInfo>   mAppList;
    private DialogFragment      mDialogFragment;

    public AppListAdapter(DialogFragment dialogFragment){
        mDialogFragment = dialogFragment;
        mInflater = LayoutInflater.from(mDialogFragment.getActivity());
    }

    public void notifyDataSetChanged(List<PackageInfo> appList){
        mAppList = appList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_app, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindView(position);
    }

    @Override
    public int getItemCount() {
        return null == mAppList ? 0 : mAppList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTvTitle;
        private int      mPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_app);
            itemView.setOnClickListener(this);
        }

        public void onBindView(int position){
            mPosition = position;
            String appName = mAppList.get(mPosition).applicationInfo.loadLabel(EditorApplication.INST.getPackageManager()).toString();
            mTvTitle.setText(appName);
        }

        @Override
        public void onClick(View view) {
            if(BundleKey.KEY_DATAS==0){
                EditorItem.DATA.mValue.putString(BundleKey.KEY_DATA, mAppList.get(mPosition).packageName);
                EditorItem.DATA.mName.putString(BundleKey.KEY_DATA,mAppList.get(mPosition).applicationInfo.loadLabel(EditorApplication.INST.getPackageManager()).toString());
                AndroidUtils.saveDataName(mDialogFragment.getActivity(), mAppList.get(mPosition).applicationInfo.loadLabel(EditorApplication.INST.getPackageManager()).toString());
                AndroidUtils.savePageName(mDialogFragment.getActivity(), mAppList.get(mPosition).packageName);
            }else if(BundleKey.KEY_DATAS==1) {
                EditorItem.DATAS.mValue.putString(BundleKey.KEY_DATA, mAppList.get(mPosition).packageName);
                EditorItem.DATAS.mName.putString(BundleKey.KEY_DATA,mAppList.get(mPosition).applicationInfo.loadLabel(EditorApplication.INST.getPackageManager()).toString());
                AndroidUtils.saveDataNames(mDialogFragment.getActivity(), mAppList.get(mPosition).applicationInfo.loadLabel(EditorApplication.INST.getPackageManager()).toString());
                AndroidUtils.savePageNames(mDialogFragment.getActivity(), mAppList.get(mPosition).packageName);
                Log.i("----APPList---DATAS----",mAppList.get(mPosition).packageName+"");
            }else if(BundleKey.KEY_DATAS==2){
                EditorItem.DATA2.mValue.putString(BundleKey.KEY_DATA, mAppList.get(mPosition).packageName);
                EditorItem.DATA2.mName.putString(BundleKey.KEY_DATA,mAppList.get(mPosition).applicationInfo.loadLabel(EditorApplication.INST.getPackageManager()).toString());
                AndroidUtils.saveDataName2(mDialogFragment.getActivity(), mAppList.get(mPosition).applicationInfo.loadLabel(EditorApplication.INST.getPackageManager()).toString());
                AndroidUtils.savePageName2(mDialogFragment.getActivity(), mAppList.get(mPosition).packageName);
                Log.i("--APPList---DATA2", mAppList.get(mPosition).packageName+"");
            }

            mDialogFragment.dismissAllowingStateLoss();
        }
    }
}
