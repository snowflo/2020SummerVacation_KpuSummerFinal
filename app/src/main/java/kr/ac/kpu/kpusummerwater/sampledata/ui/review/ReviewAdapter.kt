package kr.ac.kpu.kpusummerwater.sampledata.ui.review

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kr.ac.kpu.kpusummerwater.R

class ReviewAdapter (val context: Context,val id: Int ,val ReviewList:ArrayList<ReviewInfo>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.review_detail, null)

        //생성된 뷰를 item.xml과 연결
        val RegionTitle = view.findViewById<TextView>(R.id.RegionTitle)
        val ReviewTitle =view.findViewById<TextView>(R.id.ReviewTitle)
        val ReviewMaker = view.findViewById<TextView>(R.id.ReviewMaker)
        val ReviewDate:TextView = view.findViewById<TextView>(R.id.ReviewDate)

        //데이터 옮기기
        val Reviews = ReviewList[position]
        //item.xml      //detail_info.class

        //RegionTitle.text = Reviews.id.toString()
        ReviewTitle.text = Reviews.title
        ReviewMaker.text = Reviews.maker
        ReviewDate.text = Reviews.date
        return view
    }

    override fun getItem(position: Int): Any {
        return ReviewList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return ReviewList.size
    }

}