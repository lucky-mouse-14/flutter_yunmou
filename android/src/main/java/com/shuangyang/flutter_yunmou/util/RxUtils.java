package com.hikvision.cloud.util;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {
    /**
     * 发布在IO线程，观察在主线程
     */
    public static <T> ObservableTransformer<T, T> io2Main() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发布在IO线程，观察在IO线程
     */
    public static <T> ObservableTransformer<T, T> io2io() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public static void clearDisposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static void clearDisposables(Disposable... disposable) {
        for (Disposable disposable1 : disposable) {
            if (disposable1 != null && !disposable1.isDisposed()) {
                disposable1.dispose();
            }
        }
    }
}
