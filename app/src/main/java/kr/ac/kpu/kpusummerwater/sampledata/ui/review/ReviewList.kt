package kr.ac.kpu.kpusummerwater.sampledata.ui.review

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.content_review_list.*
import kotlinx.android.synthetic.main.fragment_slideshow.*
import kr.ac.kpu.kpusummerwater.R
import kr.ac.kpu.kpusummerwater.slideshow.detail_adapter
import org.jetbrains.anko.find

class ReviewList : AppCompatActivity() {

    lateinit var mRecyclerView:RecyclerView
    lateinit var mDatabase:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        /*
        val reviews = ReviewAdapter(this, Reviews)
        ReviewListView.adapter = reviews
        */

        mDatabase = FirebaseDatabase.getInstance().getReference("Users")
        mRecyclerView = findViewById(R.id.ReviewListView)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))

        logRecyclerView()


        val intent = Intent(this,Review::class.java)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            
            startActivity(intent) //리뷰창으로
        }
    }

    private fun logRecyclerView() {

        var FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<ReviewInfo, ReviewViewHolder>(

            ReviewInfo::class.java,
            R.layout.review_detail,
            ReviewViewHolder::class.java,
            mDatabase
        ){
            override fun populateViewHolder(viewHolder: ReviewViewHolder?, model: ReviewInfo?, position: Int) {

                //viewHolder?.mView.


            }

        }

        mRecyclerView.adapter = FirebaseRecyclerAdapter

    }

    class ReviewViewHolder(var mView:View) : RecyclerView.ViewHolder(mView) {

    }


}