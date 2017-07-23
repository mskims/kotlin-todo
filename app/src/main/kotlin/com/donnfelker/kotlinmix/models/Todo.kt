package com.donnfelker.kotlinmix.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

// Kotlin
// https://realm.io/docs/java/latest/#kotlin
// A current limitation of the Kotlin annotation processor indicates that adding
// the annotation @RealmClass is required in some cases.
@RealmClass
open class Todo(
        @PrimaryKey var id: String? = null,
        var title: String? = null,
        var description: String? = null
) : RealmObject() {
    // aa
}
