package com.turastory.progress_management;

/**
 * Created by soldi on 2018-03-08.
 */

public interface Cancellable {
    void cancel();

    boolean isCanceled();
}
