package com.alonebums.project.sp;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alonebums.project.R;
import com.library.base.BaseActivity;
import com.library.utils.sp.SPUtils;
import com.library.utils.toast.ToastUtils;

/**
 * SharedPreferences用法
 * Created by admin on 2016/10/26.
 */

public class SpActivity extends BaseActivity {

    private EditText mEtKey, mEtValue;

    @Override
    public int getContentViewId() {
        return R.layout.activity_sp;
    }

    @Override
    public void initUI() {
        mEtKey = (EditText) findViewById(R.id.et_sp_key);
        mEtValue = (EditText) findViewById(R.id.et_sp_value);
    }

    @Override
    public void initLogic() {
        super.initLogic();
        addOnClick(R.id.btn_save);
        addOnClick(R.id.btn_get);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_save:
                save();
                break;
            case R.id.btn_get:
                get();
                break;
        }
    }

    //保存
    private void save() {
        String key = mEtKey.getText().toString().trim();
        if (TextUtils.isEmpty(key)) {
            ToastUtils.showShortMsg(getActivity(), "the key is empty");
            return;
        }
        String value = mEtValue.getText().toString().trim();
        if (TextUtils.isEmpty(value)) {
            ToastUtils.showShortMsg(getActivity(), "the value is empty");
            return;
        }
        //default file name
        SPUtils.getInstance().put(key, value);
        //SPUtils.getInstance().put("file name", key, value);
        showMessage("save success");
    }

    //获取
    private void get() {
        String key = mEtKey.getText().toString().trim();
        if (TextUtils.isEmpty(key)) {
            ToastUtils.showShortMsg(getActivity(), "the key is empty");
            return;
        }
        //default file name
        String value = SPUtils.getInstance().getString(key);
        //String value = SPUtils.getInstance().getString("file name", key);
        mEtValue.setText(value);


        showMessage(value);
        //ToastUtils.showShortMsg(getActivity(), value);
    }
}
