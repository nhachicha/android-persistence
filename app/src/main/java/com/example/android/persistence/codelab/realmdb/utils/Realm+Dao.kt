@file:JvmName("RealmUtils") // pretty name for utils class if called from
package com.example.android.persistence.codelab.realmdb.utils

import com.example.android.persistence.codelab.realmdb.BookDao
import com.example.android.persistence.codelab.realmdb.LoanDao
import com.example.android.persistence.codelab.realmdb.UserDao
import io.realm.Realm

fun Realm.userModel(): UserDao {
    return UserDao(this)
}

fun Realm.bookModel(): BookDao {
    return BookDao(this)
}

fun Realm.loanModel(): LoanDao {
    return LoanDao(this)
}
