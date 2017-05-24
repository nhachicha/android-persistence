/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.persistence.codelab.realmdb;

import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmResults;

public class UserDao extends RealmDao<UserDao> {

    public UserDao(Realm realm) {
        super(realm);
    }

    public RealmResults<User> loadAllUsers() {
        return realm.where(User.class).findAll();
    }

    public User loadUserById(String id) {
        return realm.where(User.class).equalTo("id", id).findFirst();
    }

    public RealmResults<User> findByNameAndLastName(String firstName, String lastName) {
        return realm.where(User.class)
                .equalTo("name", firstName)
                .equalTo("lastName", lastName)
                .findAll();
    }

    public void insertUser(final User user) {
        if (user != null) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (realm.where(User.class).equalTo("id", user.getId()).count() == 0) ;
                    realm.insert(user);
                }
            });
        }
    }

    public void insertOrUpdateUsers(final User... users) {
        if(users != null) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(Arrays.asList(users));
                }
            });
        }
    }

    public void deleteUserById(final String id) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(User.class).equalTo("id", id).findAll().deleteAllFromRealm();
            }
        });
    }

    public void deleteUsersByName(final String badName) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(User.class)
                        .like("name", badName)
                        .or()
                        .like("lastName", badName)
                        .findAll()
                        .deleteAllFromRealm();
            }
        });
    }

}