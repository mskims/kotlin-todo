package com.donnfelker.kotlinmix

import android.app.Application

import io.realm.Realm
import io.realm.RealmConfiguration

class KotlinMixApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val config = RealmConfiguration
                .Builder()
//                .deleteRealmIfMigrationNeeded()
                .name("kotlinmix.realm")
                .build()

        Realm.setDefaultConfiguration(config)
    }
}
