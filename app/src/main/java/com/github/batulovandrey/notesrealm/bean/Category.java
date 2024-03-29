package com.github.batulovandrey.notesrealm.bean;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * @author Andrey Batulov on 17/07/2017
 */

public class Category extends RealmObject {

    private String categoryName;
    private RealmList<Note> notes;

    public Category() {
        // Empty constructor needed by Realm
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public RealmList<Note> getNotes() {
        return notes;
    }

    public void setNotes(RealmList<Note> notes) {
        this.notes = notes;
    }
}