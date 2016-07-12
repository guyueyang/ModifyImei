package com.andcup.android.app.info.changer.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.andcup.android.app.info.changer.R;
import com.andcup.android.app.info.changer.data.BundleKey;
import com.andcup.android.app.info.changer.model.EditorItem;
import com.andcup.android.app.info.changer.tools.AndroidUtils;
import com.andcup.android.framework.RxFragment;
import com.andcup.android.framework.binding.widget.RxTextView;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class FilesFragment extends RxFragment {

    @Bind(R.id.et_files)
    EditText mEtFiles;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_files;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        bindEditText();
    }

    private void bindEditText(){
        String lastFiles = AndroidUtils.readFiles(getActivity());
        if(!TextUtils.isEmpty(lastFiles)){
            String[] files = lastFiles.split("\\n");
            EditorItem.FOLDER.mValue.putStringArray(BundleKey.KEY_DATA, files);
        }
        mEtFiles.setText(lastFiles);

        RxTextView.textChanges(mEtFiles).subscribe(charSequence -> {
            String value = mEtFiles.getText().toString();
            String[] files = value.split("\\n");
            EditorItem.FOLDER.mValue.putStringArray(BundleKey.KEY_DATA, files);
            AndroidUtils.saveFiles(getActivity(), value);
        });
    }
}
