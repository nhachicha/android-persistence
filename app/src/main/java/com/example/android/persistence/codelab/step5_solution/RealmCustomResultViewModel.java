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

package com.example.android.persistence.codelab.step5_solution;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.example.android.persistence.codelab.realmdb.Loan;
import com.example.android.persistence.codelab.realmdb.RealmLocator;
import com.example.android.persistence.codelab.realmdb.utils.DatabaseInitializer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;

import static com.example.android.persistence.codelab.realmdb.utils.RealmUtils.loanModel;


public class RealmCustomResultViewModel extends AndroidViewModel {

    private LiveData<String> mLoansResult;

    private Realm mDb;

    public RealmCustomResultViewModel(Application application) {
        super(application);

    }

    public LiveData<String> getLoansResult() {
        return mLoansResult;
    }

    public void createDb() {
        mDb = RealmLocator.getInMemoryDatabase();

        // Populate it with initial data
        DatabaseInitializer.populateAsync(mDb);

        // Receive changes
        subscribeToDbChanges();
    }

    private void subscribeToDbChanges() {
        LiveData<RealmList<Loan>> loans
                = loanModel(mDb).findLoansByNameAfter("Mike", getYesterdayDate());

        // Instead of exposing the list of Loans, we can apply a transformation and expose Strings.
        mLoansResult = Transformations.map(loans,
                new Function<RealmList<Loan>, String>() {
            @Override
            public String apply(RealmList<Loan> loans) {
                StringBuilder sb = new StringBuilder();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                        Locale.US);

                for (Loan loan : loans) {
                    sb.append(String.format("%s\n  (Returned: %s)\n",
                            loan.getBook().getTitle(),
                            simpleDateFormat.format(loan.getEndTime())));
                }
                return sb.toString();
            }
        });
    }

    private Date getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, -1);
        return calendar.getTime();
    }

    @Override
    protected void onCleared() {
        RealmLocator.destroyInstance();
        super.onCleared();
    }
}
