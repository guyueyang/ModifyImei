package com.andcup.android.app.info.changer.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andcup.android.app.info.changer.R;
import com.andcup.android.app.info.changer.data.BundleKey;
import com.andcup.android.app.info.changer.tools.AndroidUtils;
import com.andcup.android.framework.RxDialogFragment;
import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/24.
 */

public class AppListFragment extends RxDialogFragment {

    @Bind(R.id.rv_list)
    RecyclerView mRvList;

    AppListAdapter mAdapter;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        super.onActivityCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppThemeDialogFragment);
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        getDialog().setCanceledOnTouchOutside(true);

        mRvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new AppListAdapter(this);
        mRvList.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged(AndroidUtils.getInstalledApps());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_app_list;
    }
}
