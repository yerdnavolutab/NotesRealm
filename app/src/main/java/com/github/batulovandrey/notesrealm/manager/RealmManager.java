package com.github.batulovandrey.notesrealm.manager;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Andrey Batulov on 17/07/2017
 */

public class RealmManager {

    private Realm mRealm;

    public RealmManager(Context context) {
        mRealm = Realm.getInstance(
                new RealmConfiguration.Builder(context)
                .name("notesRealm.realm")
                .build());
    }

    public Realm getRealm() {
        return mRealm;
    }
}
