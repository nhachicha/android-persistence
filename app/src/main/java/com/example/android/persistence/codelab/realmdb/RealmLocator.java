package com.example.android.persistence.codelab.realmdb;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmLocator {

    private static RealmLocator INSTANCE;
    private Realm realm;

    private RealmLocator() {
        realm = Realm.getInstance(
                new RealmConfiguration.Builder()
                        .inMemory()
                        .build());
    }

    public static Realm getInMemoryDatabase() {
        if (INSTANCE == null) {
            INSTANCE = new RealmLocator();
        }
        return INSTANCE.realm;
    }

    public static void destroyInstance() {
        INSTANCE.realm.close();
        INSTANCE = null;
    }
}
