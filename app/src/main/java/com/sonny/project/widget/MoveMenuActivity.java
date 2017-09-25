package com.sonny.project.widget;

import android.view.LayoutInflater;
import android.view.View;

import com.library.base.BaseActivity;
import com.sonny.project.R;
import com.sonnyjack.library.widget.SonnyJackMenu;

/**
 * Created by linqs on 2017/9/25.
 */

public class MoveMenuActivity extends BaseActivity {

    private SonnyJackMenu mSonnyJackMenu;

    @Override
    public int getContentViewId() {
        return R.layout.activity_move_menu;
    }

    @Override
    public void initUI() {
        mSonnyJackMenu = new SonnyJackMenu(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_menu, null);
        mSonnyJackMenu.addAttach(getActivity(), view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mSonnyJackMenu) {
            mSonnyJackMenu.removeAttach();
        }
    }
}
