package com.martinez.conia_app

import com.google.firebase.database.FirebaseDatabase

object Persistencia {
    private var mDatabase: FirebaseDatabase? = null

    val database: FirebaseDatabase
        get() {
            if (mDatabase == null) {
                mDatabase = FirebaseDatabase.getInstance()
                mDatabase!!.setPersistenceEnabled(true)
            }
            return mDatabase as FirebaseDatabase
        }

}