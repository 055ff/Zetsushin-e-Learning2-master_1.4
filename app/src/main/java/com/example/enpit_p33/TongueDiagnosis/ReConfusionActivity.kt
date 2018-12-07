package com.example.enpit_p33.TongueDiagnosis



import android.content.Intent

import android.support.v7.app.AppCompatActivity

import android.os.Bundle

import android.view.KeyEvent

import android.view.View

import io.realm.Realm

import io.realm.RealmConfiguration

import kotlinx.android.synthetic.main.activity_re_confusion.*
import java.math.BigDecimal


class ReConfusionActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    private val Q_SIZE = 10



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_re_confusion)

        val realmConfig = RealmConfiguration.Builder()

                .name("zetsushinleaning.realm")

                .deleteRealmIfMigrationNeeded()

                .schemaVersion(0)

                .build()

        realm = Realm.getInstance(realmConfig)



        confusion()

    }



    fun confusion(){

        val x = Array(Q_SIZE, { i -> i }).toList()



        val A_x = intArrayOf(0, 0, 0, 0)

        val B_x = intArrayOf(0, 0, 0, 0)

        val C_x = intArrayOf(0, 0, 0, 0)

        val D_x = intArrayOf(0, 0, 0, 0)



        val tmp1 = realm.where(Question::class.java).equalTo("question_id",intent.getIntExtra("BETA", 0)).findAll()

        val tmp2 = realm.where(History::class.java).equalTo("question_id",intent.getIntExtra("BETA", 0)).findAll()





        for (num in x.indices) {    //1~20

            when(tmp1[0]?.questions!![num]?.image_number?.substring(0,1)?.toInt()){

                1 -> when(tmp2[0]?.result!![num]?.answer){

                    "紅舌" -> A_x[0]++

                    "淡紅舌" -> A_x[1]++

                    "淡白舌" -> A_x[2]++

                    "紫斑舌" -> A_x[3]++

                }

                2 -> when(tmp2[0]?.result!![num]?.answer) {

                    "紅舌" -> B_x[0]++

                    "淡紅舌" -> B_x[1]++

                    "淡白舌" -> B_x[2]++

                    "紫斑舌" -> B_x[3]++

                }

                3 -> when(tmp2[0]?.result!![num]?.answer) {

                    "紅舌" -> C_x[0]++

                    "淡紅舌" -> C_x[1]++

                    "淡白舌" -> C_x[2]++

                    "紫斑舌" -> C_x[3]++

                }

                4 -> when(tmp2[0]?.result!![num]?.answer) {

                    "紅舌" -> D_x[0]++

                    "淡紅舌" -> D_x[1]++

                    "淡白舌" -> D_x[2]++

                    "紫斑舌" -> D_x[3]++

                }

            }

        }

        ca_a1.text = A_x[0].toString()

        ca_a2.text = A_x[1].toString()

        ca_a3.text = A_x[2].toString()

        ca_a4.text = A_x[3].toString()

        ca_b1.text = B_x[0].toString()

        ca_b2.text = B_x[1].toString()

        ca_b3.text = B_x[2].toString()

        ca_b4.text = B_x[3].toString()

        ca_c1.text = C_x[0].toString()

        ca_c2.text = C_x[1].toString()

        ca_c3.text = C_x[2].toString()

        ca_c4.text = C_x[3].toString()

        ca_d1.text = D_x[0].toString()

        ca_d2.text = D_x[1].toString()

        ca_d3.text = D_x[2].toString()

        ca_d4.text = D_x[3].toString()


        val figureA = (A_x[0]+A_x[1]+A_x[2]+A_x[3])
        val rate_a = A_x[0].toDouble() / figureA.toDouble()
        ca_ansa.text = String.format("%.2f", rate_a) + "(=" + A_x[0].toString() + "/" + figureA.toString() + ")"

        val figureB = (B_x[0]+B_x[1]+B_x[2]+B_x[3])
        val rate_b = B_x[1].toDouble() / figureB.toDouble()
        ca_ansb.text = String.format("%.2f", rate_b) + "(=" + B_x[1].toString() + "/" + figureB.toString() + ")"

        val figureC = (C_x[0]+C_x[1]+C_x[2]+C_x[3])
        val rate_c = C_x[2].toDouble() / figureC.toDouble()
        ca_ansc.text = String.format("%.2f", rate_c) + "(=" + C_x[2].toString() + "/" + figureC.toString() + ")"

        val figureD = (D_x[0]+D_x[1]+D_x[2]+D_x[3])
        val rate_d = D_x[3].toDouble() / figureD.toDouble()
        ca_ansd.text = String.format("%.2f", rate_d) + "(=" + D_x[3].toString() + "/" + figureD.toString() + ")"

        val score = (A_x[0] + B_x[1] + C_x[2] + D_x[3])*10

        ca_score2.text = score.toString()

    }



    fun onButtonTapped(view: View?) {

        val user = intent.getStringExtra("ID")

        val alpha = intent.getIntExtra("ALPHA", 0)

        val beta = intent.getIntExtra("BETA", 0)

        val intent = Intent(this, ResultActivity::class.java)

        intent.putExtra("ALPHA", alpha)

        intent.putExtra("BETA", beta)

        intent.putExtra("ID", user)

        startActivity(intent)

    }



    override fun dispatchKeyEvent(event: KeyEvent): Boolean {

        if (event.getAction() === KeyEvent.ACTION_DOWN) {

            when (event.getKeyCode()) {

                KeyEvent.KEYCODE_BACK ->

                    return true

            }

        }

        return super.dispatchKeyEvent(event)

    }

    override fun onDestroy() {

        super.onDestroy()

        realm.close()

    }

}