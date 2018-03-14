package com.library.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sonnyjack.utils.log.LogUtils;


/**
 * Created by admin on 2016/5/10.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    public View mRootLayout;

    public abstract int getContentViewId();

    public void onBeforeInitUI(Bundle savedInstanceState) {

    }

    public abstract void initUI();

    public void initLogic() {

    }

    public void initData() {
    }

    @Override
    public void onClick(View v) {

    }

    public void addOnClick(View view) {
        if (null == view) {
            return;
        }
        view.setOnClickListener(this);
    }

    public void addOnClick(int id) {
        if (null == mRootLayout) {
            return;
        }
        View view = mRootLayout.findViewById(id);
        addOnClick(view);
    }

    /*************************************************************************
     * 生命周期方法
     *************************************************************************/

    @Override
    public void onAttach(Context context) {
        log(getClass().getSimpleName() + " is onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        log(getClass().getSimpleName() + " is onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        log(getClass().getSimpleName() + " is onCreateView");
        if (mRootLayout == null) {
            mRootLayout = inflater.inflate(getContentViewId(), null);
        }
        onBeforeInitUI(savedInstanceState);
        initUI();
        initLogic();
        initData();
        return mRootLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log(getClass().getSimpleName() + " is onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        log(getClass().getSimpleName() + " is onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        log(getClass().getSimpleName() + " is onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        log(getClass().getSimpleName() + " is onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        log(getClass().getSimpleName() + " is onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log(getClass().getSimpleName() + " is onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log(getClass().getSimpleName() + " is onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log(getClass().getSimpleName() + " is onDetach");
    }

    public void log(String message) {
        LogUtils.i(message);
    }
}
