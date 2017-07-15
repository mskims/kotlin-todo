package com.donnfelker.kotlinmix;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class KotlinMixApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("kotlinmix.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
