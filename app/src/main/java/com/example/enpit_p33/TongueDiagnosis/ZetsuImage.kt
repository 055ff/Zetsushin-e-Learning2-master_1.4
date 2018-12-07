package com.example.enpit_p33.TongueDiagnosis

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ZetsuImage: RealmObject(){
    @PrimaryKey
    var image_id: Long = 0 // かぶらない番号
    var image_number: String = "" // 画像の番号
}