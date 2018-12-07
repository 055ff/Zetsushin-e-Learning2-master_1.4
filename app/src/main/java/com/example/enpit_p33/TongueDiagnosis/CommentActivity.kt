package com.example.enpit_p33.TongueDiagnosis

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Typeface
import android.graphics.drawable.Drawable
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
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayInputStream
import kotlin.math.roundToInt

class   CommentActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private val WC = ViewGroup.LayoutParams.WRAP_CONTENT
    val real = mapOf("紅舌" to "1_1","淡紅舌" to "2_1","淡白舌" to "3_1","紫斑舌" to "4_1")
    val color = listOf("紅舌", "淡紅舌", "淡白舌", "紫斑舌")

    val size_change : Float = 0.7f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //realm初期化
        val realmConfig = RealmConfiguration.Builder()
                .name("zetsushinleaning.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(0)
                .build()
        realm = Realm.getInstance(realmConfig)

        //レイアウト設定
        val scrollView = ScrollView(this)
        setContentView(scrollView)
        //val relativeLayout = RelativeLayout(this)
        //scrollView.addView(relativeLayout)
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        scrollView.addView(linearLayout)

        comment(linearLayout)
    }

    @SuppressLint("ResourceType")
    fun comment(linearLayout: LinearLayout) {
        val miss = intent.getStringArrayExtra("MISS")
        val miss_ans = intent.getStringArrayExtra("MISS_ANS")
        val param = LinearLayout.LayoutParams(WC, WC)

        val back = GradientDrawable()
        back.setStroke(3, Color.BLACK)

        val inlinearLayout_1 = LinearLayout(this)
        inlinearLayout_1.orientation = LinearLayout.VERTICAL

        val title = TextView(this)
        title.text = "2回とも同じ誤りへの対処"
        title.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC))
        title.textSize = 64.0f * size_change
        if (title.parent != null){
            ((title.parent) as ViewGroup).removeView(title)
        }
        inlinearLayout_1.addView(title, param)

        val question_statement = TextView(this)
        question_statement.text = "同じ舌画像で2回とも同じ間違えをしたものをピックアップしました。\n問の舌画像と誤って選択した舌画像で舌色がどのように異なるのか、それぞれの舌画像で特徴的な舌の色を見て確認しましょう。"
        question_statement.textSize = 32.0f * size_change
        inlinearLayout_1.addView(question_statement, LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        val separate_1 = View(this)
        separate_1.background = back
        inlinearLayout_1.addView(separate_1, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5))

        linearLayout.addView(inlinearLayout_1, param)

        for (num in Array(miss_ans.size, { i -> i })) {
            val inlinearLayout_2 = LinearLayout(this)
            inlinearLayout_2.orientation = LinearLayout.HORIZONTAL

            val inlinearLayout_2_1 = LinearLayout(this)
            inlinearLayout_2_1.orientation = LinearLayout.VERTICAL

            val space_2_1_1 = Space(this)
            inlinearLayout_2_1.addView(space_2_1_1, LinearLayout.LayoutParams(100, 50))

            val text_2_1_1 = TextView(this)
            text_2_1_1.text = "問の「" +  color[miss[num].substring(0,1).toInt() - 1] + "」を「" + miss_ans[num] + "」と2回も誤った"
            text_2_1_1.setTextColor(Color.RED)
            text_2_1_1.textSize = 32.0f * size_change
            text_2_1_1.gravity = Gravity.CENTER
            inlinearLayout_2_1.addView(text_2_1_1, param)

            val space_2_1_2 = Space(this)
            inlinearLayout_2_1.addView(space_2_1_2, LinearLayout.LayoutParams(100, 50))

            inlinearLayout_2.addView(inlinearLayout_2_1, param)

            val inlinearLayout_3 = LinearLayout(this)
            inlinearLayout_3.orientation = LinearLayout.HORIZONTAL

            val inlinearLayout_3_1 = LinearLayout(this)
            inlinearLayout_3_1.orientation = LinearLayout.VERTICAL

            val state_1 = TextView(this)
            state_1.text = "問の舌画像" + "「" + color[miss[num].substring(0,1).toInt() - 1] + "」"
            state_1.textSize = 32.0f * size_change
            state_1.gravity = Gravity.CENTER
            inlinearLayout_3_1.addView(state_1, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WC))

            val r_1 = resources.getIdentifier("q" + miss[num], "drawable", packageName) //drawableの画像指定
            val imageView_1 = ImageView(this)
            imageView_1.setImageResource(r_1)
            imageView_1.background
            inlinearLayout_3_1.addView(imageView_1,param)

            inlinearLayout_3.addView(inlinearLayout_3_1, param)

            val inlinearLayout_3_2 = LinearLayout(this)
            inlinearLayout_3_2.orientation = LinearLayout.VERTICAL

            val space_3_2_1 = Space(this)
            inlinearLayout_3_2.addView(space_3_2_1, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100))

            val text_1 = TextView(this)
            text_1.text = "注目点"
            text_1.setTextColor(Color.BLUE)
            text_1.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC))
            text_1.textSize = 32.0f * size_change
            text_1.gravity = Gravity.CENTER
            inlinearLayout_3_2.addView(text_1, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WC))

            val imageView_2 = ImageView(this)
            val r_2 = resources.getIdentifier("icon", "drawable", packageName)  //矢印
            imageView_2.setImageResource(r_2) //imageViewに画像設定
            inlinearLayout_3_2.addView(imageView_2, LinearLayout.LayoutParams(150, WC))

            inlinearLayout_3.addView(inlinearLayout_3_2, param)

            val inlinearLayout_3_3 = LinearLayout(this)
            inlinearLayout_3_3.orientation = LinearLayout.VERTICAL

            val state_2 = TextView(this)
            state_2.text = "  特徴的な色を表示した舌"
            state_2.textSize = 32.0f * size_change
            state_2.gravity = Gravity.CENTER
            inlinearLayout_3_3.addView(state_2, LinearLayout.LayoutParams(WC, WC))

            val imageView_3 = ImageView(this)
            val r_3 = resources.getIdentifier("m" + miss[num] + "_" + real[miss_ans[num]] , "drawable", packageName) //drawableの画像指
            imageView_3.setImageResource(r_3) //imageViewに画像設定
            inlinearLayout_3_3.addView(imageView_3, param)

            inlinearLayout_3.addView(inlinearLayout_3_3, param)

            val space_3_1 = Space(this)
            inlinearLayout_3.addView(space_3_1, LinearLayout.LayoutParams(50, 50))

            val inlinearLayout_3_4 = LinearLayout(this)
            inlinearLayout_3_4.orientation = LinearLayout.VERTICAL




///*
            val text_3_4_1 = TextView(this)
            text_3_4_1.text = color[miss[num].substring(0,1).toInt() - 1] + "の\n特徴的な色"
            text_3_4_1.textSize = 32.0f * size_change
            text_3_4_1.gravity = Gravity.CENTER
            inlinearLayout_3_4.addView(text_3_4_1, param)
//*/




/*            //ここから    出題された舌を見分けるための特徴的な色
            val space_3_2 = Space(this)
            inlinearLayout_3_4.addView(space_3_2, LinearLayout.LayoutParams(50, 50))

            val r_4 = resources.getIdentifier("t" + real[color[miss[num].substring(0,1).toInt() - 1]] + "_" + real[miss_ans[num]], "drawable", packageName) //drawableの画像指定
            val imageView_4 = ImageView(this)
            imageView_4.setImageResource(r_4) //imageViewに画像設定
            inlinearLayout_3_4.addView(imageView_4, param)
            //ここまで
*/

///*
            //ここから
            val inlinearLayout_3_4_1 = LinearLayout(this)
            inlinearLayout_3_4_1.orientation = LinearLayout.HORIZONTAL

            val r_3_4_1_1 = resources.getIdentifier("t" + real[color[miss[num].substring(0,1).toInt() - 1]] + "_" +  real[miss_ans[num]] + "_1", "drawable", packageName)
            //val r_3_4_1_1 = resources.getIdentifier("t1_1_2_1_1", "drawable", packageName)
            val imageButton_3_4_1_1 = ImageButton(this)
            imageButton_3_4_1_1.setImageResource(r_3_4_1_1)
            inlinearLayout_3_4_1.addView(imageButton_3_4_1_1, param)
            imageButton_3_4_1_1.setOnClickListener{
                val r = resources.getIdentifier("m" + miss[num] + "_" + real[miss_ans[num]] + "_1" , "drawable", packageName)
                //   val r = resources.getIdentifier("q1_1", "drawable", packageName) //drawableの画像指定
                imageView_3.setImageResource(r) //imageViewに画像設定
            }

            val r_3_4_1_2 = resources.getIdentifier("t" + real[color[miss[num].substring(0,1).toInt() - 1]] + "_" +  real[miss_ans[num]] + "_2", "drawable", packageName)
//            val r_3_4_1_2 = resources.getIdentifier("t1_1_2_1_1", "drawable", packageName)
            val imageButton_3_4_1_2 = ImageButton(this)
            imageButton_3_4_1_2.setImageResource(r_3_4_1_2)
            inlinearLayout_3_4_1.addView(imageButton_3_4_1_2, param)
            imageButton_3_4_1_2.setOnClickListener{
                val r = resources.getIdentifier("m" + miss[num] + "_" + real[miss_ans[num]] + "_2" , "drawable", packageName)
                //   val r = resources.getIdentifier("q2_1", "drawable", packageName) //drawableの画像指定
                imageView_3.setImageResource(r) //imageViewに画像設定
            }

            val inlinearLayout_3_4_2 = LinearLayout(this)
            inlinearLayout_3_4_2.orientation = LinearLayout.HORIZONTAL

            val r_3_4_2_1 = resources.getIdentifier("t" + real[color[miss[num].substring(0,1).toInt() - 1]] + "_" +  real[miss_ans[num]] + "_3", "drawable", packageName)
            //val r_3_4_2_1 = resources.getIdentifier("t1_1_2_1_1", "drawable", packageName)
            val imageButton_3_4_2_1 = ImageButton(this)
            imageButton_3_4_2_1.setImageResource(r_3_4_2_1)
            inlinearLayout_3_4_2.addView(imageButton_3_4_2_1, param)
            imageButton_3_4_2_1.setOnClickListener{
                val r = resources.getIdentifier("m" + miss[num] + "_" + real[miss_ans[num]] + "_3" , "drawable", packageName)
                //   val r = resources.getIdentifier("q3_1", "drawable", packageName) //drawableの画像指定
                imageView_3.setImageResource(r) //imageViewに画像設定
            }
            val r_3_4_2_2 = resources.getIdentifier("t" + real[color[miss[num].substring(0,1).toInt() - 1]] + "_" +  real[miss_ans[num]] + "_4", "drawable", packageName)
            //val r_3_4_2_2 = resources.getIdentifier("t1_1_2_1_1", "drawable", packageName)
            val imageButton_3_4_2_2 = ImageButton(this)
            imageButton_3_4_2_2.setImageResource(r_3_4_2_2)
            inlinearLayout_3_4_2.addView(imageButton_3_4_2_2, param)
            imageButton_3_4_2_2.setOnClickListener{
                val r = resources.getIdentifier("m" + miss[num] + "_" + real[miss_ans[num]] + "_4" , "drawable", packageName)
                //   val r = resources.getIdentifier("q4_1", "drawable", packageName) //drawableの画像指定
                imageView_3.setImageResource(r) //imageViewに画像設定
            }

            val inlinearLayout_3_4_3 = LinearLayout(this)
            inlinearLayout_3_4_3.orientation = LinearLayout.VERTICAL

            val space_3_4_3_1 = Space(this)
            inlinearLayout_3_4_3.addView(space_3_4_3_1, LinearLayout.LayoutParams(50, 50))

            val button_3_4_3_1 = Button(this)
            button_3_4_3_1.text = "全色表示"
            button_3_4_3_1.textSize = 32.0f * size_change
            inlinearLayout_3_4_3.addView(button_3_4_3_1, param)
            button_3_4_3_1.setOnClickListener{
                val r = resources.getIdentifier("m" + miss[num] + "_" + real[miss_ans[num]] , "drawable", packageName) //drawableの画像指
                imageView_3.setImageResource(r) //imageViewに画像設定
            }

            inlinearLayout_3_4.addView(inlinearLayout_3_4_1, param)
            inlinearLayout_3_4.addView(inlinearLayout_3_4_2, param)
            inlinearLayout_3_4.addView(inlinearLayout_3_4_3, param)
            //ここまで
//*/



            inlinearLayout_3.addView(inlinearLayout_3_4, param)

            val inlinearLayout_4 = LinearLayout(this)
            inlinearLayout_4.orientation = LinearLayout.HORIZONTAL

            val inlinearLayout_4_1 = LinearLayout(this)
            inlinearLayout_4_1.orientation = LinearLayout.VERTICAL

            val state_3 = TextView(this)
            Log.d("debug", miss_ans[num])
            state_3.text = "誤って選択した\n舌の代表的な舌画像" + "「" + miss_ans[num] + "」"
            state_3.textSize = 32.0f * size_change
            state_3.gravity = Gravity.CENTER
            inlinearLayout_4_1.addView(state_3, param)

            val r_5 = resources.getIdentifier("q" + real[miss_ans[num]], "drawable", packageName) //drawableの画像指定
            val imageView_5 = ImageView(this)
            imageView_5.setImageResource(r_5) //imageViewに画像設定
            inlinearLayout_4_1.addView(imageView_5, param)

            inlinearLayout_4.addView(inlinearLayout_4_1, param)

            val inlinearLayout_4_2 = LinearLayout(this)
            inlinearLayout_4_2.orientation = LinearLayout.VERTICAL

            val space_4_2_1 = Space(this)
            inlinearLayout_4_2.addView(space_4_2_1, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100))

            val text_2 = TextView(this)
            text_2.text = "注目点"
            text_2.setTextColor(Color.BLUE)
            text_2.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC))
            text_2.textSize = 32.0f * size_change
            text_2.gravity = Gravity.CENTER
            inlinearLayout_4_2.addView(text_2, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WC))

            val imageView_6 = ImageView(this)
            val r_6 = resources.getIdentifier("icon", "drawable", packageName)
            imageView_6.setImageResource(r_6) //imageViewに画像設定
            inlinearLayout_4_2.addView(imageView_6, LinearLayout.LayoutParams(150, WC))

            inlinearLayout_4.addView(inlinearLayout_4_2, param)

            val mat: Matrix = imageView_6.imageMatrix



            val inlinearLayout_4_3 = LinearLayout(this)
            inlinearLayout_4_3.orientation = LinearLayout.VERTICAL

            val state_4 = TextView(this)
            state_4.text = "\n特徴的な色を表示した舌"
            state_4.textSize = 32.0f * size_change
            state_4.gravity = Gravity.CENTER
            inlinearLayout_4_3.addView(state_4, LinearLayout.LayoutParams(WC, WC))

            val r_7 = resources.getIdentifier("m" + real[miss_ans[num]] + "_" +  real[color[miss[num].substring(0,1).toInt() - 1]], "drawable", packageName) //drawableの画像指
            val imageView_7 = ImageView(this)
            imageView_7.setImageResource(r_7) //imageViewに画像設定
            inlinearLayout_4_3.addView(imageView_7, param)

            inlinearLayout_4.addView(inlinearLayout_4_3, param)

            val space_4_1 = Space(this)
            inlinearLayout_4.addView(space_4_1, LinearLayout.LayoutParams(50, 50))

            val inlinearLayout_4_4 = LinearLayout(this)
            inlinearLayout_4_4.orientation = LinearLayout.VERTICAL





///*
            val text_4_4_1 = TextView(this)
            text_4_4_1.text = miss_ans[num] + "の\n特徴的な色"
            text_4_4_1.textSize = 32.0f * size_change
            text_4_4_1.gravity = Gravity.CENTER
            inlinearLayout_4_4.addView(text_4_4_1, param)
//*/



/*
            //ここから
            val space_4_2 = Space(this)
            inlinearLayout_4_4.addView(space_4_2, LinearLayout.LayoutParams(100, 50))
            // テンプレート
            val r_8 = resources.getIdentifier("t" + real[miss_ans[num]] + "_" + real[color[miss[num].substring(0,1).toInt() - 1]], "drawable", packageName) //drawableの画像指定
            val imageView_8 = ImageView(this)
            imageView_8.setImageResource(r_8) //imageViewに画像設定
            inlinearLayout_4_4.addView(imageView_8, param)
            //ここまで
*/



///*
            //ここから
            val inlinearLayout_4_4_1 = LinearLayout(this)
            inlinearLayout_4_4_1.orientation = LinearLayout.HORIZONTAL

            val r_4_4_1_1 = resources.getIdentifier("t" + real[miss_ans[num]] + "_" +  real[color[miss[num].substring(0,1).toInt() - 1]] + "_1", "drawable", packageName)
            //val r_4_4_1_1 = resources.getIdentifier("t1_1_2_1_1", "drawable", packageName)
            val imageButton_4_4_1_1 = ImageButton(this)
            imageButton_4_4_1_1.setImageResource(r_4_4_1_1)
            inlinearLayout_4_4_1.addView(imageButton_4_4_1_1, param)
            imageButton_4_4_1_1.setOnClickListener{
                val r = resources.getIdentifier("m" + real[miss_ans[num]] + "_" +  real[color[miss[num].substring(0,1).toInt() - 1]] + "_1", "drawable", packageName)
//                val r = resources.getIdentifier("q1_1", "drawable", packageName) //drawableの画像指定
                imageView_7.setImageResource(r) //imageViewに画像設定
            }

            val r_4_4_1_2 = resources.getIdentifier("t" + real[miss_ans[num]] + "_" +  real[color[miss[num].substring(0,1).toInt() - 1]] + "_2", "drawable", packageName)
            // val r_4_4_1_2 = resources.getIdentifier("t1_1_2_1_1", "drawable", packageName)
            val imageButton_4_4_1_2 = ImageButton(this)
            imageButton_4_4_1_2.setImageResource(r_4_4_1_2)
            inlinearLayout_4_4_1.addView(imageButton_4_4_1_2, param)
            imageButton_4_4_1_2.setOnClickListener{
                val r = resources.getIdentifier("m" + real[miss_ans[num]] + "_" +  real[color[miss[num].substring(0,1).toInt() - 1]] + "_2", "drawable", packageName)
//                val r = resources.getIdentifier("q2_1", "drawable", packageName) //drawableの画像指定
                imageView_7.setImageResource(r) //imageViewに画像設定
            }

            val inlinearLayout_4_4_2 = LinearLayout(this)
            inlinearLayout_4_4_2.orientation = LinearLayout.HORIZONTAL

            val r_4_4_2_1 = resources.getIdentifier("t" + real[miss_ans[num]] + "_" +  real[color[miss[num].substring(0,1).toInt() - 1]] + "_3", "drawable", packageName)
            //val r_4_4_2_1 = resources.getIdentifier("t1_1_2_1_1", "drawable", packageName)
            val imageButton_4_4_2_1 = ImageButton(this)
            imageButton_4_4_2_1.setImageResource(r_4_4_2_1)
            inlinearLayout_4_4_2.addView(imageButton_4_4_2_1, param)
            imageButton_4_4_2_1.setOnClickListener{
                val r = resources.getIdentifier("m" + real[miss_ans[num]] + "_" +  real[color[miss[num].substring(0,1).toInt() - 1]] + "_3", "drawable", packageName)
                //              val r = resources.getIdentifier("q3_1", "drawable", packageName) //drawableの画像指定
                imageView_7.setImageResource(r) //imageViewに画像設定
            }

            val r_4_4_2_2 = resources.getIdentifier("t" + real[miss_ans[num]] + "_" +  real[color[miss[num].substring(0,1).toInt() - 1]] + "_4", "drawable", packageName)
            //val r_4_4_2_2 = resources.getIdentifier("t1_1_2_1_1", "drawable", packageName)
            val imageButton_4_4_2_2 = ImageButton(this)
            imageButton_4_4_2_2.setImageResource(r_4_4_2_2)
            inlinearLayout_4_4_2.addView(imageButton_4_4_2_2, param)
            imageButton_4_4_2_2.setOnClickListener{
                val r = resources.getIdentifier("m" + real[miss_ans[num]] + "_" +  real[color[miss[num].substring(0,1).toInt() - 1]] + "_4", "drawable", packageName)
//                val r = resources.getIdentifier("q4_1", "drawable", packageName) //drawableの画像指定
                imageView_7.setImageResource(r) //imageViewに画像設定
            }
            val inlinearLayout_4_4_3 = LinearLayout(this)
            inlinearLayout_4_4_3.orientation = LinearLayout.VERTICAL

            val space_4_4_3_1 = Space(this)
            inlinearLayout_4_4_3.addView(space_4_4_3_1, LinearLayout.LayoutParams(50, 50))

            val button_4_4_3_1 = Button(this)
            button_4_4_3_1.text = "全色表示"
            button_4_4_3_1.textSize = 32.0f * size_change
            inlinearLayout_4_4_3.addView(button_4_4_3_1, param)
            button_4_4_3_1.setOnClickListener{
                val r = resources.getIdentifier("m" + real[miss_ans[num]] + "_" +  real[color[miss[num].substring(0,1).toInt() - 1]], "drawable", packageName) //drawableの画像指
                imageView_7.setImageResource(r) //imageViewに画像設定
            }

            inlinearLayout_4_4.addView(inlinearLayout_4_4_1, param)
            inlinearLayout_4_4.addView(inlinearLayout_4_4_2, param)
            inlinearLayout_4_4.addView(inlinearLayout_4_4_3, param)
            //ここまで
//*/




            inlinearLayout_4.addView(inlinearLayout_4_4, param)

            val inlinearLayout_5 = LinearLayout(this)
            inlinearLayout_5.orientation = LinearLayout.HORIZONTAL

            val space_5_1 = Space(this)
            inlinearLayout_5.addView(space_5_1, LinearLayout.LayoutParams(550, 50))

            val text_3 = TextView(this)
            text_3.text = "\n対比"
            text_3.setTextColor(Color.BLUE)
            text_3.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC))
            text_3.textSize = 32.0f * size_change
            text_3.gravity = Gravity.CENTER
            inlinearLayout_5.addView(text_3, param)

            val imageView_9 = ImageView(this)
            val r_9 = resources.getIdentifier("icon_reverse", "drawable", packageName)
            imageView_9.setImageResource(r_9) //imageViewに画像設定
            inlinearLayout_5.addView(imageView_9, LinearLayout.LayoutParams(WC, 100))

            linearLayout.addView(inlinearLayout_2, param)
            linearLayout.addView(inlinearLayout_3, param)

            //val space_1 = Space(this)
            //linearLayout.addView(space_1, LinearLayout.LayoutParams(100, 50))
            linearLayout.addView(inlinearLayout_5, param)

            linearLayout.addView(inlinearLayout_4, param)

            val separate_2 = View(this)
            separate_2.background = back
            linearLayout.addView(separate_2, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5))
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
