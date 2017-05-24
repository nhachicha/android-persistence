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

import io.realm.Realm;
import io.realm.RealmList;

public class LoanDao extends RealmDao {

    public LoanDao(Realm realm) { super(realm); }

    public LiveData<RealmList<Loan>> findLoansByNameAfter(String userName, Date after) {
        return new RealmLiveData<>(realm
                .where(Loan.class)
                .like("user.name", userName)
                .greaterThan("endTime", after)
                .findAll());
    }

    public LiveData<RealmList<Loan>> findAllLoans() {
        return new RealmLiveData<>(realm.where(Book.class).findAll());
    }

    public void addLoan(final Date from, final Date to, final String userId, final String bookId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.where(User.class).equalTo("id", userId).findFirst();
                Book book = realm.where(Book.class).equalTo("id", bookId).findFirst();
                Loan loan = new Loan(from, to, book, user);
                realm.copyFromRealm(loan);
                book.getLoans().add(loan);
                user.getLoans().add(loan);
            }
        });
    }

    void deleteAll() {
        realm.delete(Book.class);
    }
}