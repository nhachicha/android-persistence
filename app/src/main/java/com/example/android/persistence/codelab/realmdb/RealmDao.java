package com.example.android.persistence.codelab.realmdb;

import io.realm.Realm;

public abstract class RealmDao<DAO> {

    protected Realm realm;

    public RealmDao(Realm realm) {
        this.realm = realm;
    }

}
