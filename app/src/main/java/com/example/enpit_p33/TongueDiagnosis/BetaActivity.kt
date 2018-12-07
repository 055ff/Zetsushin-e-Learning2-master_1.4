package com.example.enpit_p33.TongueDiagnosis

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.createObject
import java.util.*
import android.widget.RadioButton
import io.realm.RealmList
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton


class BetaActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private var answerlist:MutableList<String> = mutableListOf("","","","","","","","","","") //ユーザーのリアルタイム解答
    private val colorlist:List<String> = listOf("紅舌","淡紅舌","淡白舌","紫斑舌") // 選択肢リスト
    private val WC = ViewGroup.LayoutParams.WRAP_CONTENT
    private val Q_SIZE = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //realm初期化
        val realmConfig = RealmConfiguration.Builder()
                .name("zetsushinleaning.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(0)
                .build()
        realm = Realm.getInstance(realmConfig)

        val scrollView = ScrollView(this)
        setContentView(scrollView)
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        scrollView.addView(linearLayout)

        createQuestion(linearLayout)
    }

    @SuppressLint("ResourceType")
    fun createQuestion(linearLayout: LinearLayout) {
        var Id: Int = 0 // 使用するテスト問題のID
        var q_list: RealmList<QuestionList>?
        val mylist = listOf("1_1", "2_2", "4_1", "2_4", "2_6", "1_3", "4_3", "3_1", "3_3", "2_8")
        val myans: List<String> = listOf("紅舌", "紫斑舌", "淡白舌", "淡紅舌",
                "淡紅舌", "淡白舌", "紫斑舌", "紅舌",
                "淡白舌", "淡紅舌", "紅舌", "紫斑舌",
                "淡白舌", "淡紅舌", "紫斑舌", "紅舌",
                "紫斑舌", "紅舌", "淡白舌", "淡紅舌",
                "淡紅舌", "淡白舌", "紅舌", "紫斑舌",
                "淡紅舌", "紅舌", "紫斑舌", "淡白舌",
                "紅舌", "淡紅舌", "淡白舌", "紫斑舌",
                "淡紅舌", "淡白舌", "紫斑舌", "紅舌",
                "紅舌", "淡紅舌", "淡白舌", "紫斑舌")

        //randomlistの一行目をコメントアウトするとR2、二行目をコメントアウトするとR3
        val randomlist = listOf(555, 391, 443, 839, 183, 867, 134, 774, 623, 972,
            488, 577, 565, 89, 86, 367, 102, 991, 292, 503,
            876, 214, 268, 351, 929, 319, 83, 288, 343, 88,
            501, 385, 88, 547, 631, 480, 662, 881, 769, 822,
            650, 685, 217, 565, 20, 936, 713, 782, 733, 346,
            590, 346, 485, 955, 389, 472, 484, 969, 758, 235,
            281, 792, 246, 799, 250, 220, 301, 592, 154, 269,
            827, 676, 785, 500, 341, 279, 475, 279, 797, 479,
            304, 675, 804, 700, 225, 557, 605, 993, 82, 229,
            212, 790, 678, 961, 225, 880, 13, 229, 552, 100,
            33, 934, 367, 238, 810, 403, 742, 208, 536, 718,
            836, 727, 220, 387, 636, 205, 309, 374, 568, 22,
            986, 47, 468, 951, 373, 739, 490, 914, 904, 847,
            799, 4, 71, 796, 788, 586, 989, 685, 208, 994,
            144, 535, 213, 148, 732, 396, 95, 205, 258, 603,
            806, 828, 793, 4, 711, 611, 393, 693, 22, 264,
            446, 208, 251, 348, 751, 747, 836, 207, 63, 407,
            100, 204, 583, 73, 777, 697, 674, 121, 157, 192,
            655, 856, 178, 337, 397, 554, 621, 92, 340, 325,
            255, 287, 77, 931, 300, 403, 110, 514, 389, 817,
            723, 733, 99, 502, 612, 922, 93, 136, 521, 768,
            978, 338, 438, 844, 260, 906, 898, 937, 863, 397,
            50, 153, 828, 151, 499, 281, 186, 893, 573, 289,
            604, 443, 293, 59, 792, 713, 497, 788, 111, 712,
            110, 722, 829, 870, 952, 512, 302, 101, 354, 36)  //乱数表250、ファイルから読む込めるように改良する予定
        val randomlist_A = listOf(555, 391, 443, 839, 183, 867, 134, 774, 623, 972,
            488, 577, 565, 89, 86, 367, 102, 991, 292, 503,
            876, 214, 268, 351, 929, 319, 83, 288, 343, 88,
            501, 385, 88, 547, 631, 480, 662, 881, 769, 822,
            650, 685, 217, 565, 20, 936, 713, 782, 733, 346,
            590, 346, 485, 955, 389, 472, 484, 969, 758, 235,
            281, 792, 246, 799, 250, 220, 301, 592, 154, 269,
            827, 676, 785, 500, 341, 279, 475, 279, 797, 479,
            304, 675, 804, 700, 225, 557, 605, 993, 82, 229,
            212, 790, 678, 961, 225, 880, 13, 229, 552, 100,
            33, 934, 367, 238, 810, 403, 742, 208, 536, 718,
            836, 727, 220, 387, 636, 205, 309, 374, 568, 22,
            986, 47, 468, 951, 373, 739, 490, 914, 904, 847,
            799, 4, 71, 796, 788, 586, 989, 685, 208, 994,
            144, 535, 213, 148, 732, 396, 95, 205, 258, 603,
            806, 828, 793, 4, 711, 611, 393, 693, 22, 264,
            446, 208, 251, 348, 751, 747, 836, 207, 63, 407,
            100, 204, 583, 73, 777, 697, 674, 121, 157, 192,
            655, 856, 178, 337, 397, 554, 621, 92, 340, 325,
            255, 287, 77, 931, 300, 403, 110, 514, 389, 817,
            723, 733, 99, 502, 612, 922, 93, 136, 521, 768,
            978, 338, 438, 844, 260, 906, 898, 937, 863, 397,
            50, 153, 828, 151, 499, 281, 186, 893, 573, 289,
            604, 443, 293, 59, 792, 713, 497, 788, 111, 712,
            110, 722, 829, 870, 952, 512, 302, 101, 354, 36)  //乱数表250、ファイルから読む込めるように改良する予定

        // テスト問題の指定
        Id = myQuestion(randomQues(mylist, randomlist), randomAns(myans, randomlist_A, randomQues_Num(mylist, randomlist)), randomQues_Num(mylist, randomlist))// randomQuestion(intent.getIntExtra("ALPHA", 0))
        q_list = realm.where(Question::class.java).equalTo("question_id", Id).findAll()[0]?.questions


        val param = LinearLayout.LayoutParams(WC, WC)
        val back = GradientDrawable()
        back.setStroke(3, Color.BLACK)

        val inlinearLayout_1 = LinearLayout(this)
        inlinearLayout_1.orientation = LinearLayout.VERTICAL

        val title = TextView(this)
        title.text = "テスト問題R1"
        title.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC))
        title.textSize = 64.0f
        if (title.parent != null) {
            ((title.parent) as ViewGroup).removeView(title)
        }
        inlinearLayout_1.addView(title, param)

        val question_statement = TextView(this)
        question_statement.text = "前の問題と選択肢をランダムに並び変えています。\n問いに示された舌画像を見て、4つの選択肢の中から正しいと思われる選択肢を1つ選んでください。"
        question_statement.textSize = 32.0f
        inlinearLayout_1.addView(question_statement, param)

        val separate_1 = View(this)
        separate_1.background = back
        inlinearLayout_1.addView(separate_1, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5))

        linearLayout.addView(inlinearLayout_1, param)

        for (num in Array(Q_SIZE, { i -> i })) {

            val inlinearLayout_2 = LinearLayout(this)
            inlinearLayout_2.orientation = LinearLayout.HORIZONTAL

            val text = TextView(this)
            text.text = "問" + (num+1).toString()
            text.textSize = 32.0f
            inlinearLayout_2.addView(text, param)

            val inlinearLayout_3 = LinearLayout(this)
            inlinearLayout_3.orientation = LinearLayout.HORIZONTAL

            val r_1 = resources.getIdentifier("q" + q_list!![num]?.image_number, "drawable", packageName) //drawableの画像指定
            val imageView_1 = ImageView(this)
            imageView_1.setImageResource(r_1) //imageViewに画像設定
            imageView_1.setPadding(0,50,200,50)
            inlinearLayout_3.addView(imageView_1, param)

            val radioGroup = RadioGroup(this)
            val radioButton1 = RadioButton(this)
            val radioButton2 = RadioButton(this)
            val radioButton3 = RadioButton(this)
            val radioButton4 = RadioButton(this)
            radioButton1.text = q_list[num]?.choice1
            radioButton1.textSize = 32.0f
            radioButton2.text = q_list[num]?.choice2
            radioButton2.textSize = 32.0f
            radioButton3.text = q_list[num]?.choice3
            radioButton3.textSize = 32.0f
            radioButton4.text = q_list[num]?.choice4
            radioButton4.textSize = 32.0f
            radioGroup.addView(radioButton1, param)
            radioGroup.addView(radioButton2, param)
            radioGroup.addView(radioButton3, param)
            radioGroup.addView(radioButton4, param)
            radioGroup.setPadding(0,50,0,50)
            inlinearLayout_3.addView(radioGroup, param)
            // ラジオボタンタップ時
            radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                    val radio = findViewById(checkedId) as RadioButton
                    if (radio.isChecked() == true)
                        answerlist[num] = radio.text.toString()
                    Log.d("debug", answerlist[num])
                }
            })

            linearLayout.addView(inlinearLayout_2, param)
            linearLayout.addView(inlinearLayout_3, param)

            val separate_2 = View(this)
            separate_2.background = back
            linearLayout.addView(separate_2, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5))
        }

        val inlinearLayout_4 = LinearLayout(this)
        inlinearLayout_4.orientation = LinearLayout.HORIZONTAL

        val space_1 = Space(this)
        inlinearLayout_4.addView(space_1, LinearLayout.LayoutParams(500, 100))

        val button = Button(this)
        button.text = "次へ"
        button.textSize = 32.0f
        button.gravity = Gravity.CENTER
        inlinearLayout_4.addView(button, LinearLayout.LayoutParams(300, 100))


        val inlinearLayout_5 = LinearLayout(this)
        inlinearLayout_5.orientation = LinearLayout.HORIZONTAL

        val space_2 = Space(this)
        inlinearLayout_5.addView(space_2, LinearLayout.LayoutParams(100, 100))

        // ボタンタップ時
        button.setOnClickListener { onButtonTapped(Id) }
        linearLayout.addView(inlinearLayout_4, param)
        linearLayout.addView(inlinearLayout_5, param)
    }

    fun randomQues(mylist: List<String>, randomlist: List<Int>): MutableList<String> {

        var random_count = 0
        val Ques_num = 10   //繰り返す数、問題数と等しいため問題数変更時のバグのもととなる、改善予定
        var ran_num : MutableList<Int> = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        var ques_num = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        while (random_count < Ques_num) {
            ran_num[random_count] = randomlist[random_count]
            ques_num[random_count] = random_count
            random_count++
        }
        var l_num = 0
        var s_num = 0
        var Change_F = 0
        var cha_ran = 0
        var cha_num = 0
        var sort_point = 0
        while (l_num < Ques_num) {
            cha_ran = ran_num[l_num]
            cha_num = ques_num[l_num]
            sort_point = l_num
            while (s_num < Ques_num) {
                if (cha_ran > ran_num[s_num] && (sort_point == s_num || sort_point < s_num)) {
                    ran_num[sort_point] = ran_num[s_num]
                    ques_num[sort_point] = ques_num[s_num]
                    sort_point = s_num
                    ran_num[sort_point] = cha_ran
                    ques_num[sort_point] = cha_num
                    Change_F = 1
                } else {
                }
                s_num++

            }
            if (Change_F == 0) {
                l_num++

            }
            Change_F = 0
            s_num = 0
        }


        var Q_count = 0
        var New_Ques = mutableListOf("0", "0", "0", "0", "0", "0", "0", "0", "0", "0")

        while (Q_count < Ques_num) {
            New_Ques[Q_count] = mylist[ques_num[Q_count]]
            Q_count++
        }
        return New_Ques
    }

     fun randomQues_Num(mylist: List<String>, randomlist: List<Int>): MutableList<Int> {

        var random_count = 0
        val Ques_num = 10   //繰り返す数、問題数と等しいため問題数変更時のバグのもととなる、改善予定
        var ran_num : MutableList<Int> = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        var ques_num = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        while (random_count < Ques_num) {
            ran_num[random_count] = randomlist[random_count]
            ques_num[random_count] = random_count
            random_count++
        }
        var l_num = 0
        var s_num = 0
        var Change_F = 0
        var cha_ran = 0
        var cha_num = 0
        var sort_point = 0
        while (l_num < Ques_num) {
            cha_ran = ran_num[l_num]
            cha_num = ques_num[l_num]
            sort_point = l_num
            while (s_num < Ques_num) {
                if (cha_ran > ran_num[s_num] && (sort_point == s_num || sort_point < s_num)) {
                    ran_num[sort_point] = ran_num[s_num]
                    ques_num[sort_point] = ques_num[s_num]
                    sort_point = s_num
                    ran_num[sort_point] = cha_ran
                    ques_num[sort_point] = cha_num
                    Change_F = 1
                } else {
                }
                s_num++

            }
            if (Change_F == 0) {
                l_num++

            }
            Change_F = 0
            s_num = 0
        }
        return ques_num
    }

    fun randomAns(myans: List<String>, randomlist: List<Int>, ques_num: MutableList<Int>): MutableList<String> {
        var Ans_count = 0
        var Ans_cho = 0 //選択肢 1 - 4
        var ran_ans : MutableList<Int>   = mutableListOf(0, 0, 0, 0)
        var choice_num : MutableList<Int>  = mutableListOf(0, 0, 0, 0)
        var New_Ans : MutableList<String>  = mutableListOf("0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0")
        var ran_num : MutableList<Int>  = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        var cha_ans = 0
        var cha_a_num = 0
        val Ques_num = 10
        var l_num = 0
        var s_num = 0
        var Change_F = 0
        var sort_point = 0
        while (Ans_count < Ques_num * 4) {
            ran_num[Ans_count] = randomlist[Ans_count]
      //      ques_num[random_count] = random_count
            Ans_count++
        }

        Ans_count = 0

        while(Ans_count < Ques_num){
            Ans_cho = 0
            while(Ans_cho < 4){     //選択肢が4つから変更されたらバグが出るので改善する
                ran_ans[Ans_cho] = randomlist[Ans_cho + 4 * Ans_count]
                choice_num[Ans_cho] = Ans_cho
                Ans_cho++
            }

            var A1 = ran_ans[0]
            var A2 = ran_ans[1]
            var A3 = ran_ans[2]
            var A4 = ran_ans[3]
            var AAA = Ans_count
            var N_f = 0
            while(l_num < 4){
                cha_ans = ran_ans[l_num]
                cha_a_num = choice_num[l_num]
                sort_point = l_num
                while (s_num < 4){
                    if(cha_ans > ran_ans[s_num] && sort_point < s_num){
                        ran_ans[sort_point] = ran_ans[s_num]
                        choice_num[sort_point] = choice_num[s_num]
                        sort_point = s_num
                        ran_ans[s_num] = cha_ans
                        choice_num[s_num] = cha_a_num
                        Change_F = 1
                    }else{}
                    s_num++
                }
                if (Change_F == 0){
                    l_num++
                }
                Change_F = 0
                s_num = 0
            }
            l_num = 0
            var Q_count = 0
            while (Q_count < 4){
                New_Ans[Q_count + 4 * Ans_count] = myans[choice_num[Q_count] + 4 * ques_num[Ans_count]]
                Q_count++
            }

             var AA1 = ran_ans[0]
            var AA2 = ran_ans[1]
            var AA3 = ran_ans[2]
            var AA4 = ran_ans[3]
            var AAAA = Ans_count
val n0 = New_Ans[0 + 4 * Ans_count]
    val n1 = New_Ans[1 + 4 * Ans_count]
    val n2 = New_Ans[2 + 4 * Ans_count]
    val n3 = New_Ans[3 + 4 * Ans_count]

            Ans_count++
        }



        return New_Ans
    }

    fun myQuestion(mylist: MutableList<String>, myans: MutableList<String>, numbers: MutableList<Int>): Int {
        // テスト問題の一番大きいIDの次の数字を取得
        val maxId = realm.where(Question::class.java).max("question_id")
        val nextId = (maxId?.toInt() ?: 0) + 1
        // val alphaId = realm.where(History::class.java).equalTo("history_id", intent.getIntExtra("ALPHA", 0).toLong()).findFirst()?.question_id
        // val numbers = realm.where(Question::class.java).equalTo("question_id", alphaId).findFirst()?.questions
        //val numbers = listOf(10,5,6,3,1,2,8,7,4,9)

        //新しい問題を生成
        realm.executeTransaction {
            realm.createObject<Question>(nextId).apply {
                for (num in Array(Q_SIZE, { i -> i })) {
                    val q = realm.createObject<QuestionList>().apply {
                        question_number = numbers[num] + 1
                        image_number = mylist[num]
                        choice1 = myans[0 + num * 4]
                        choice2 = myans[1 + num * 4]
                        choice3 = myans[2 + num * 4]
                        choice4 = myans[3 + num * 4]

                        Log.d("debug", "テスト問題" + question_number.toString() + ":" + image_number.toString())
                    }
                    questions.add(q)
                }
            }
        }
        return nextId
    }

    fun onButtonTapped(Id: Int) {
        val nextId = (realm.where(History::class.java).max("history_id")?.toLong() ?: 0L) + 1
        val user = intent.getStringExtra("ID")
        val alpha = intent.getIntExtra("ALPHA", 0)
        val intent = Intent(this, ReConfusionActivity::class.java)
        intent.putExtra("BETA", Id)
        intent.putExtra("ID", user)
        intent.putExtra("ALPHA", alpha)
        for(i in answerlist.indices) {
            if(answerlist[i].equals("")){
                alert("まだすべて解答していません。") { yesButton {} }.show()
                return
            }
        }
        realm.executeTransaction {
            realm.createObject<History>(nextId).apply {
                user_id = intent.getStringExtra("ID")
                question_id = Id
                for (i in answerlist.indices) {
                    val a = realm.createObject<Result>().apply {
                        answer = answerlist[i]
                    }
                    result.add(a)
                }
                date = Date().toString()
            }
            Log.d("debug", realm.where(QuestionList::class.java).findAll().size.toString())
            Log.d("debug", "beta_id:" + intent.getIntExtra("BETA", 0).toString())
            Log.d("debug", "alpha_id:" + intent.getIntExtra("ALPHA", 0).toString())
            startActivity(intent)
        }
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