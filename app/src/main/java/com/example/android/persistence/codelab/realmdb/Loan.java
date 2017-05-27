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

import java.util.Date;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Loan implements RealmModel {

    public Date startTime;
    public Date endTime;
    public Book book;
    public User user;

    public Loan() {}

    public Loan(Date startTime, Date endTime, Book book, User user) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.book = book;
        this.user = user;
    }

}
