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

import android.arch.lifecycle.LiveData;

import com.example.android.persistence.codelab.realmdb.utils.RealmLiveData;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;


public class BookDao extends RealmDao {

    public BookDao(Realm realm) { super(realm); }

    public Book loadBookById(String id) {
        return realm.where(Book.class).equalTo("id", id).findFirst();
    }

    public LiveData<RealmList<Book>> findBooksBorrowedByName(String userName) {
        return new RealmLiveData<>(realm
                .where(Book.class)
                .like("loan.user.name", userName)
                .findAll());
    }

    public LiveData<List<Book>> findBooksBorrowedByNameAfter(String userName, Date after) {
        return new RealmLiveData<>(realm
                .where(Book.class)
                .like("loan.user.name", userName)
                .greaterThan("loan.endTime", after)
                .findAll());
    }

    public LiveData<List<Book>> findBooksBorrowedByUser(String userId) {
        return new RealmLiveData<>(realm
                .where(Book.class)
                .like("loan.user.id", userId)
                .findAll());
    }

    public LiveData<List<Book>> findBooksBorrowedByUserAfter(String userId, Date after) {
        return new RealmLiveData<>(realm
                .where(Book.class)
                .like("loan.user.id", userId)
                .greaterThan("loan.endTime", after)
                .findAll());
    }

    public LiveData<List<Book>> findAllBooks() {
        return new RealmLiveData<>(realm.where(Book.class).findAll());
    }

    public void insertBook(final Book book) {
        if (book != null) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (realm.where(Book.class).equalTo("id", book.getId()).count() == 0) ;
                    realm.insert(book);
                }
            });
        }
    }

    void createOrUpdateBook(final Book book) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(book);
            }
        });
    }

    void deleteAll() {
        realm.delete(Book.class);
    }
}
