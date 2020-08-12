package kr.ac.kpu.kpusummerwater.sampledata.ui.review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_review.*
import kr.ac.kpu.kpusummerwater.R
import java.time.LocalDate

class Review : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        //창을 끄는 방법? -> 창이 켜지면 num이 0으로 초기화
        ReviewBtn1.setOnClickListener(){
            editAddress.text=null
            editTitle.text=null
            editReview.text=null
            finish()
        }
        ReviewBtn2.setOnClickListener(){
            saveData()
            finish()
        }
    }

    fun saveData(){ //test
        var address = editAddress.text.toString()
        var title = editTitle.text.toString()
        var review = editReview.text.toString()

        var map = mutableMapOf<String,Any>()
        map["address"] = address
        map["title"] = title
        map["review"] = review

        FirebaseDatabase.getInstance().reference
            .child("Review")
            .push() //push
            .setValue(map)
    }
    
    //update는 생략
    
    //delete도 생략
}