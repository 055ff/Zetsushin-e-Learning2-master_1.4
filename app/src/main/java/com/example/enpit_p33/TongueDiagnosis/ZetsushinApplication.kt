package com.example.enpit_p33.TongueDiagnosis

import android.app.Application
import android.text.format.DateFormat
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.createObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

// 一番最初にする処理
class ZetsushinApplication : Application(){
    private lateinit var realm: Realm

    // 画像番号
    val image = listOf("1_1", "1_2", "1_3", "2_1", "2_2", "2_3", "2_4", "2_5",
                           "2_6", "2_7", "2_8", "3_1", "3_3", "4_1", "4_2", "4_3",
                           "4_4", "4_5", "4_6", "4_7", "4_8")

    override fun onCreate(){
        super.onCreate()

        Realm.init(this) // realmの初期化

        saveDate()

        Log.d("2debug", "History: " + realm.where(History::class.java).findAll().size.toString())
        Log.d("debug", "Zetsushin: " + realm.where(Zetsushin::class.java).findAll().size.toString())
        Log.d("debug", "Question: " + realm.where(Question::class.java).findAll().size.toString())
        Log.d("debug", "QuestionList: " + realm.where(QuestionList::class.java).findAll().size.toString())
        Log.d("debug", "Result: " + realm.where(Result::class.java).findAll().size.toString())
        Log.d("debug", "ZetsuImage: " + realm.where(ZetsuImage::class.java).findAll().size.toString())
        Log.d("debug", "ZetsuImage_size: " + image.size.toString())

        /*
        for(i in Array(imagelist.size, {i -> i})){
            imagelist[i].split("-")[0]
        }
        */


    }

    // 画像の番号をデータベースに保存
    private fun saveDate(){
        val realmConfig = RealmConfiguration.Builder()
                .name("zetsushinleaning.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(0)
                .build()
        realm = Realm.getInstance(realmConfig)

        realm.executeTransaction {
            realm.where(ZetsuImage::class.java).findAll().deleteAllFromRealm()
            //realm.where(Zetsushin::class.java).findAll().deleteAllFromRealm()
            realm.where(QuestionList::class.java).findAll().deleteAllFromRealm()
            realm.where(Question::class.java).findAll().deleteAllFromRealm()
            realm.where(Result::class.java).findAll().deleteAllFromRealm()
            realm.where(History::class.java).findAll().deleteAllFromRealm()
            for(j in Array(image.size, { i -> i })){
                    realm.createObject<ZetsuImage>(j).apply {
                        image_number = image[j]
                }
            }
        }
    }
}