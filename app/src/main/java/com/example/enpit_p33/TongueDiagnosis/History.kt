package com.example.enpit_p33.TongueDiagnosis

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

//問題に回答するごとに作られる（無印とRも別に作られる）
open class History: RealmObject(){
    @PrimaryKey
    var history_id: Long = 0
    var user_id: String = ""
    var date: String = ""
    var question_id: Int = 0
    var result: RealmList<Result> = RealmList()
}

open class Result: RealmObject(){
    var answer: String = ""
}

open class Question: RealmObject(){
    @PrimaryKey
    var question_id: Int = 0
    var questions: RealmList<QuestionList> = RealmList()
}

//Questionの中の
open class QuestionList: RealmObject(){
    var question_number: Int = 0
    var image_number: String = ""
    var choice1: String = ""
    var choice2: String = ""
    var choice3: String = ""
    var choice4: String = ""
}