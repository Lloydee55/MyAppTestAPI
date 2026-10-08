package com.example.myinernetapplication;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ViewModelMainActivity extends ViewModel {
    private MutableLiveData<DogImage> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> errButton = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public LiveData<DogImage> getMutableLiveData() {
        return mutableLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getErrButton() {
        return errButton;
    }

    public void loadDogeImage() {
        disposable.add(loadDogeImageRx().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> {
                    isLoading.setValue(true);
                    errButton.setValue(false);
                })
                .doAfterTerminate(() -> isLoading.setValue(false))
                .subscribe(image -> mutableLiveData.setValue(image),
                        err -> errButton.setValue(true))
                );
    }

    private Single<DogImage> loadDogeImageRx() {
            return ApiFactory.getApiService().loadDogImage();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
