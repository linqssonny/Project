package com.library.base.thread;

import java.util.Comparator;

/**
 * Created by admin on 2016/11/16.
 */

public class PriorityComparator<T extends PriorityRunnable> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        if (null == o1 || null == o2) {
            return 0;
        }
        return o2.getPriority() - o1.getPriority();
    }
}
