package com.example.enpit_p33.TongueDiagnosis

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

// ログイン処理
class
LoginActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val realmConfig = RealmConfiguration.Builder()
                .name("zetsushinleaning.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(0)
                .build()
        realm = Realm.getInstance(realmConfig) // realmのインスタンス作成 // データベース変更時、データ破棄
        next_button.setOnClickListener{ onNextButtonTapped()} // "次へ"のボタンを押したとき
        new_button.setOnClickListener{ onCreateUserID()} // "新しいアカウント"のボタンを押したとき
    }

    // IDとパスワードを識別し、次の画面へ遷移する関数
    fun onNextButtonTapped(){
        val intent = Intent(this, MainActivity::class.java) // MainActivityに遷移するためのインテント

        val id = user_id.text.toString() // ユーザID
        val pass = user_password.text.toString() // パスワード

        val user_account = realm.where(Zetsushin::class.java)
                                            .equalTo("user_id", id)
                                            .findFirst() //idが一致したデータベースを取得

        try{
            // アカウントがある場合
            if (user_account != null) {
                // パスワードが一致した場合
                if (pass.equals(user_account.password)) {
                    intent.putExtra("ID", id) // インテントにIDを保存
                    startActivity(intent) // MainActivityに遷移
                }
                // パスワードが一致しなかった場合
                else {
                    alert("IDかパスワードが違います") {yesButton {  }}.show()
                }
            }
            // アカウントがない場合
            else {
                alert("IDかパスワードが違います") {yesButton {  }}.show()
            }
            // エラーが起きた場合
        }catch (e: IllegalStateException){
            alert("エラーが起きました"){yesButton {  }}.show()
        }finally{
        }
    }

    // 新しいアカウント作成画面に遷移する関数
    fun onCreateUserID(){
        val intent = Intent(this, CreateActivity::class.java) // CreateActivityに遷移するためのインテント
        startActivity(intent) // CreateActivityに遷移
    }

    // Activityを削除するときの処理
    override fun onDestroy(){
        super.onDestroy()
        realm.close() // realmをクローズ
    }
}
