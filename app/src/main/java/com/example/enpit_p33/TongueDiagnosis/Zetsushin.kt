package com.example.enpit_p33.TongueDiagnosis

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Zetsushin: RealmObject() {
    @PrimaryKey
    var user_id: String = ""
    var password: String = ""
}