package com.sonny.project.thread;

import android.view.View;

import com.library.base.BaseActivity;
import com.library.base.thread.PriorityRunnable;
import com.library.base.thread.ThreadManager;
import com.sonny.project.R;
import com.sonny.project.utils.LUtils;

/**
 * Created by admin on 2016/11/16.
 */

public class ThreadActivity extends BaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.activity_thread;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        startThread();
    }

    private void startThread() {
        for (int i = 0; i < 10; i++) {
            PriorityRunnable priorityRunnable = new MyRunnable(i);
            ThreadManager.getInstances().execute(priorityRunnable);
        }
    }

    class MyRunnable extends PriorityRunnable {

        private int i;

        public MyRunnable(int priority) {
            i = priority;
            if (priority == 9) {
                setPriority(priority);
            }
        }

        @Override
        public void run() {
            LUtils.e("线程[" + i + "]开始");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LUtils.e("线程[" + i + "]结束");
        }
    }
}
