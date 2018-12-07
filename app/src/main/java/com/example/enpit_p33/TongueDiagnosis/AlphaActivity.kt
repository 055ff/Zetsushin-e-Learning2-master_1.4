package com.example.enpit_p33.TongueDiagnosis

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.createObject
import java.util.*
import android.widget.RadioButton
import io.realm.RealmList
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

// テスト問題1を表示する
class AlphaActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private val answerlist:MutableList<String> = mutableListOf("","","","","","","","","","") //ユーザーのリアルタイム解答
    val WC = ViewGroup.LayoutParams.WRAP_CONTENT // viewのサイズを適当にする
    val Q_SIZE = 10 // 問題の数

    private val mylist: MutableList<String> = mutableListOf("1_1", "2_2", "4_1", "2_4", "2_6", "1_3", "4_3", "3_1", "3_3", "2_8") // 指定した問題
    private val myans: MutableList<String> = mutableListOf("紅舌", "紫斑舌", "淡白舌", "淡紅舌",
            "淡紅舌", "淡白舌", "紫斑舌", "紅舌",
            "淡白舌", "淡紅舌", "紅舌", "紫斑舌",
            "淡白舌", "淡紅舌", "紫斑舌", "紅舌",
            "紫斑舌", "紅舌", "淡白舌", "淡紅舌",
            "淡紅舌", "淡白舌", "紅舌", "紫斑舌",
            "淡紅舌", "紅舌", "紫斑舌", "淡白舌",
            "紅舌", "淡紅舌", "淡白舌", "紫斑舌",
            "淡紅舌", "淡白舌", "紫斑舌", "紅舌",
            "紅舌", "淡紅舌", "淡白舌", "紫斑舌") // 指定した選択肢
    private val randomlist= listOf(555, 391, 443, 839, 183, 867, 134, 774, 623, 972,
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
    private val randomlist_A = listOf(555, 391, 443, 839, 183, 867, 134, 774, 623, 972,
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val realmConfig = RealmConfiguration.Builder()
                .name("zetsushinleaning.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(0)
                .build()
        realm = Realm.getInstance(realmConfig) // realmのインスタンス作成 // データベース変更時、データ破棄

        var new_flag = false // 新しく問題を作成するかどうかのフラグ
        val history = (realm.where(History::class.java)
                .equalTo("user_id", intent.getStringExtra("ID"))
                .max("history_id")?.toInt() ?: 0) // ユーザーの解いた最後の履歴のデータベースの番号
        val existence = realm.where(History::class.java)
                .equalTo("history_id", history)
                .findFirst() // 存在する履歴

        //レイアウト設定
        val scrollView = ScrollView(this) // スクロールビューのインスタンス
        setContentView(scrollView) // スクロールビューをセット
        val linearLayout = LinearLayout(this) // リニアレイアウトのインスタンス
        linearLayout.orientation = LinearLayout.VERTICAL // ビューを縦に並べる
        scrollView.addView(linearLayout) // スクロールビューにリニアレイアウトをセット

        // 履歴がなかった時
        if(existence == null) {
            new_flag = true
        }
        createQuestion(linearLayout, new_flag, existence) // 問題の表示
    }

    @SuppressLint("ResourceType")
    fun createQuestion(linearLayout: LinearLayout, new_flag: Boolean, existence: History?) {
        var Id: Int // 使用するテスト問題のID
        var q_list: RealmList<QuestionList>? // 表示する問題のデータベース

        val param = LinearLayout.LayoutParams(WC, WC) // ビューの適当なサイズを指定
        val back = GradientDrawable() // バックグラウンドの指定
        back.setStroke(3, Color.BLACK) // 枠線

        // テスト問題の指定
        if (new_flag) {
            Id = myQuestion(mylist, myans) // 指定した問題と選択肢のリストからデータベースを作成しそのidを保存
            //Id = myQuestion(randomQues(mylist, randomlist), randomAns(myans, randomlist_A, randomQues_Num(mylist, randomlist))) // 指定した問題と選択肢のリストからデータベースを作成しそのidを保存
            // IDから見つけた問題を保存
            q_list = realm.where(Question::class.java).equalTo("question_id", Id).findAll()[0]?.questions
        } else {
            Id = existence!!.question_id // 最後に解いた問題のID
            // IDから見つけた問題を保存
            q_list = realm.where(Question::class.java).equalTo("question_id", Id).findAll()[0]?.questions
        }

        // -----ここからレイアウトの設定------------------------------------------------------------
        // ----ここからレイアウト1の設定--------------------------------------
        val inlinearLayout_1 = LinearLayout(this)
        inlinearLayout_1.orientation = LinearLayout.VERTICAL

        // ---ここからタイトルの生成--------------------------------
        val title = TextView(this)
        title.text = "テスト問題1"
        title.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC)) // 太文字斜め
        title.textSize = 64.0f
        // エラー回避
        if (title.parent != null) {
            ((title.parent) as ViewGroup).removeView(title)
        }
        inlinearLayout_1.addView(title, param)
        // ---ここまでタイトルの生成--------------------------------

        // ---ここから説明文の生成----------------------------------
        val question_statement = TextView(this)
        question_statement.text = "問いに示された舌画像を見て、4つの選択肢の中から正しいと思われる選択肢を1つ選んでください。"
        question_statement.textSize = 32.0f
        inlinearLayout_1.addView(question_statement, param)
        // ---ここまで説明文の生成----------------------------------

        // ----ここまでレイアウト1の設定--------------------------------------
        linearLayout.addView(inlinearLayout_1, param) // レイアウト1のビューを表示

        // ---ここから仕切り線の生成--------------------------------
        val separate_1 = View(this)
        separate_1.background = back
        linearLayout.addView(separate_1, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5))
        // ---ここまで仕切り線の生成--------------------------------


        for (num in Array(Q_SIZE, { i -> i })) {
            // ----ここからレイアウト2の設定----------------------------------
            val inlinearLayout_2 = LinearLayout(this)
            inlinearLayout_2.orientation = LinearLayout.HORIZONTAL

            // ---ここから問番号の生成------------------------------
            val text_2_1 = TextView(this)
            text_2_1.text = "問" + (num+1).toString()
            text_2_1.textSize = 32.0f
            inlinearLayout_2.addView(text_2_1, param)
            // ---ここから問番号の生成------------------------------
            // ----ここまでレイアウト2の設定----------------------------------

            // ----ここからレイアウト3の設定----------------------------------
            val inlinearLayout_3 = LinearLayout(this)
            inlinearLayout_3.orientation = LinearLayout.HORIZONTAL

            // ---ここから問題画像の生成----------------------------
            // drawableから画像を指定
            val r_3_1 = resources.getIdentifier("q" + q_list!![num]?.image_number, "drawable", packageName)
            val imageView_3_1 = ImageView(this)
            imageView_3_1.setImageResource(r_3_1) //imageViewに画像設定
            imageView_3_1.setPadding(0,50,200,50)
            inlinearLayout_3.addView(imageView_3_1, param)
            // ---ここまで問題画像の生成----------------------------

            // ---ここから選択肢の生成------------------------------
            // ラジオグループの中にラジオボタンを保存
            val radioGroup_3_1 = RadioGroup(this)
            val radioButton_3_1 = RadioButton(this)
            val radioButton_3_2 = RadioButton(this)
            val radioButton_3_3 = RadioButton(this)
            val radioButton_3_4 = RadioButton(this)
            radioButton_3_1.text = q_list[num]?.choice1
            radioButton_3_1.textSize = 32.0f
            radioButton_3_2.text = q_list[num]?.choice2
            radioButton_3_2.textSize = 32.0f
            radioButton_3_3.text = q_list[num]?.choice3
            radioButton_3_3.textSize = 32.0f
            radioButton_3_4.text = q_list[num]?.choice4
            radioButton_3_4.textSize = 32.0f
            radioGroup_3_1.addView(radioButton_3_1, param)
            radioGroup_3_1.addView(radioButton_3_2, param)
            radioGroup_3_1.addView(radioButton_3_3, param)
            radioGroup_3_1.addView(radioButton_3_4, param)
            radioGroup_3_1.setPadding(0,50,0,50)
            inlinearLayout_3.addView(radioGroup_3_1, param)

            // ラジオボタンタップ時
            radioGroup_3_1.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                    val radio = findViewById(checkedId) as RadioButton
                    if (radio.isChecked() == true)
                        answerlist[num] = radio.text.toString()
                    Log.d("debug", answerlist[num])
                }
            })
            // ---ここまで選択肢の生成------------------------------
            // ----ここまでレイアウト3の設定----------------------------------

            linearLayout.addView(inlinearLayout_2, param) // レイアウト2のビューを表示
            linearLayout.addView(inlinearLayout_3, param) // レイアウト3のビューを表示

            // ---ここから仕切り線の生成----------------------------
            val separate_2 = View(this)
            separate_2.background = back
            linearLayout.addView(separate_2, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5))
            // ---ここまで仕切り線の生成----------------------------
        }

        // ----ここからレイアウト4の設定--------------------------------------
        val inlinearLayout_4 = LinearLayout(this)
        inlinearLayout_4.orientation = LinearLayout.HORIZONTAL

        // ---ここから空白の生成------------------------------------
        val space_4_1 = Space(this)
        inlinearLayout_4.addView(space_4_1, LinearLayout.LayoutParams(500, 100))
        // ---ここまで空白の生成------------------------------------

        // ---ここから遷移するボタンの生成--------------------------
        val button = Button(this)
        button.text = "次へ"
        button.textSize = 32.0f
        button.gravity = Gravity.CENTER
        inlinearLayout_4.addView(button, LinearLayout.LayoutParams(300, 100))

        button.setOnClickListener { onButtonTapped(Id) } // "次へ"のボタンが押されたとき
        // ---ここまで遷移するボタンの生成--------------------------
        // ----ここまでレイアウト4の設定--------------------------------------

        // ----ここからレイアウト5の設定--------------------------------------
        val inlinearLayout_5 = LinearLayout(this)
        inlinearLayout_5.orientation = LinearLayout.HORIZONTAL

        // ---ここから空白の生成------------------------------------
        val space_5_1 = Space(this)
        inlinearLayout_5.addView(space_5_1, LinearLayout.LayoutParams(100, 100))
        // ---ここまで空白の生成------------------------------------
        // ----ここまでレイアウト5の設定--------------------------------------

        linearLayout.addView(inlinearLayout_4, param) // レイアウト4のビューを表示
        linearLayout.addView(inlinearLayout_5, param) // レイアウト5のビューを表示
        // -----ここまでレイアウトの設定------------------------------------------------------------
    }
    /*
        fun newQuestion(): Int {
            // テスト問題の一番大きいIDの次の数字を取得
            val maxId = realm.where(Question::class.java).max("question_id")
            val nextId = (maxId?.toInt() ?: 0) + 1
    
            //新しい問題を生成
            realm.executeTransaction {
                realm.createObject<Question>(nextId).apply {
                    for (num in Array(Q_SIZE, { i -> i })) {
                        val tmp = realm.where(ZetsuImage::class.java).equalTo("zetsu_color", colorlist[Random().nextInt(4)]).findAll()
                        val image_n = (tmp[Random().nextInt(tmp.size)]?.image_id ?: 0)
                        Collections.shuffle(colorlist)      //舌の選択肢をシャッフル
                        val q = realm.createObject<QuestionList>().apply {
    
                            //question_number プロトタイプ（問題１）の問題順
                            question_number = num + 1
                            image_number = image_n
                            choice1 = colorlist[0]
                            choice2 = colorlist[1]
                            choice3 = colorlist[2]
                            choice4 = colorlist[3]
    
                            Log.d("debug", "テスト問題" + question_number.toString() + ":" + image_number.toString())
                        }
                        questions.add(q)
                    }
                }
            }
            return nextId
        }
    */
/*        // テスト問題の一番大きいIDの次の数字を取得
        //nextId 次の問題
        val maxId = realm.where(Question::class.java).max("question_id")
        val nextId = (maxId?.toInt() ?: 0) + 1

        //新しい問題を生成
        realm.executeTransaction {
            //tmp ランダムに作成する問題のIDを取得
            //n 問題数をリストにする
            val tmp = realm.where(Question::class.java).equalTo("question_id", Id).findAll()[0]?.questions!!
            val n = Array(tmp.size, {i -> i}).toList()

            //nをシャッフルする
*/
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
/*
            //<>という名前のデータベースを作る
            //()ID
            realm.createObject<Question>(nextId).apply {
                for (num in Array(tmp.size, { i -> i })) {
                    Collections.shuffle(colorlist)
                    val q = realm.createObject<QuestionList>().apply {
                        question_number = (tmp[n[num]]?.question_number ?: 0)
                        image_number = (tmp[n[num]]?.image_number ?: 0)
                        choice1 = colorlist[0]
                        choice2 = colorlist[1]
                        choice3 = colorlist[2]
                        choice4 = colorlist[3]

                        Log.d("debug", "テスト問題" + question_number.toString() + ":" + image_number.toString())
                    }
                    questions.set(q)
                }
            }
        }
        return nextId
*/

    fun myQuestion(mylist: MutableList<String>, myans: MutableList<String>): Int {
        // テスト問題の一番大きいIDの次の数字を取得
        val maxId = realm.where(Question::class.java).max("question_id")
        val nextId = (maxId?.toInt() ?: 0) + 1

        //新しい問題を生成
        //ランダムにも使える
        realm.executeTransaction {
            realm.createObject<Question>(nextId).apply {
                for (num in Array(Q_SIZE, { i -> i })) {
                    val q = realm.createObject<QuestionList>().apply {
                        question_number = num + 1
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

    fun onButtonTapped(Id: Int){
        val nextId = (realm.where(History::class.java).max("history_id")?.toLong() ?: 0L) + 1
        val user = intent.getStringExtra("ID")
        val intent = Intent(this, ConfusionActivity::class.java)
        intent.putExtra("ALPHA", Id)
        intent.putExtra("ID", user)
        for(i in answerlist.indices) {
            if(answerlist[i].equals("")){
                alert("まだすべて解答していません。") { yesButton {} }.show()
                return
            }
        }
        realm.executeTransaction{
            realm.createObject<History>(nextId).apply {
                user_id = intent.getStringExtra("ID")
                question_id = Id
                for(i in answerlist.indices){
                    val a = realm.createObject<Result>().apply {
                        answer = answerlist[i]
                        Log.d("debug", "解答" + answerlist[i])
                    }
                    result.add(a)
                }
                date = Date().toString()
            }
        }
        Log.d("debug", realm.where(QuestionList::class.java).findAll().size.toString())
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