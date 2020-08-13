package kr.ac.kpu.kpusummerwater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainIntent = Intent(this, RegionSelect::class.java)
        mainButton.setOnClickListener() {
            startActivity(mainIntent)
        }
    }
}



