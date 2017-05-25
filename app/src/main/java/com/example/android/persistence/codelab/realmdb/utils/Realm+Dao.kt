@file:JvmName("RealmUtils") // pretty name for utils class if called from
package com.example.android.persistence.codelab.realmdb.utils

import android.arch.lifecycle.LiveData
import com.example.android.persistence.codelab.realmdb.BookDao
import com.example.android.persistence.codelab.realmdb.LoanDao
import com.example.android.persistence.codelab.realmdb.UserDao
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults

fun Realm.userModel(): UserDao = UserDao(this)
fun Realm.bookModel(): BookDao = BookDao(this)
fun Realm.loanModel(): LoanDao = LoanDao(this)
fun Realm.checkpoint() {
    commitTransaction()
    beginTransaction()
}

fun <T : RealmModel> asLiveData(data: RealmResults<T>): LiveData<RealmResults<T>> = RealmLiveData(data)
