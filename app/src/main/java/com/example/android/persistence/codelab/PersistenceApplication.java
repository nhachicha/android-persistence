package com.example.android.persistence.codelab;

import android.app.Application;

import io.realm.Realm;

public class PersistenceApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
