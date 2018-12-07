package com.example.enpit_p33.TongueDiagnosis

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

// メイン画面の処理
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val set_id = intent.getStringExtra("ID") // インテントからIDを取得

        Text.text = set_id // IDをテキストに表示
        startButton.setOnClickListener{onStartButtonTapped(set_id)} // "テスト開始"が押されたとき
    }

    // テスト問題1に遷移する関数
    fun onStartButtonTapped(set_id: String){
        val intent = Intent(this, AlphaActivity::class.java) // AlphaActivityに遷移するためのインテント
        intent.putExtra("ID", set_id) // インテントにIDを保存
        startActivity(intent) // AlphaActivityに遷移
    }
}
