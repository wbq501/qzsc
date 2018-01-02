package com.zoomtk.circle.base;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by win 10 on 2017/10/12.
 */

public abstract class BaseObserver<T> implements Observer<BaseJson<T>>{
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull BaseJson<T> baseJson) {
        onSuccess(baseJson);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailure(e);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onFailure(Throwable e);
    public abstract void onSuccess(BaseJson<T> baseJson);
}
