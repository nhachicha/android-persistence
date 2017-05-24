package com.example.android.persistence.codelab.realmdb.utils;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import io.realm.RealmModel;
import io.realm.RealmResults;

public class RealmLiveData<T> extends LiveData<T> {

    private RealmResults<? extends RealmModel> data;

    public RealmLiveData(RealmResults<? extends RealmModel> data) {
        this.data = data;
    }

    @Nullable
    @Override
    public T getValue() {
        return super.getValue();
    }

}
