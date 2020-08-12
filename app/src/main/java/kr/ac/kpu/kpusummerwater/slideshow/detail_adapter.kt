package kr.ac.kpu.kpusummerwater.slideshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kr.ac.kpu.kpusummerwater.R

class detail_adapter(val context:Context, val detailList:ArrayList<detail_info>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //뷰 생성
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_detail, null)

        //생성된 뷰를 item.xml과 연결
        val detailImage =view.findViewById<ImageView>(R.id.detailImg)
        val detailTitle = view.findViewById<TextView>(R.id.detailName)

        //데이터 옮기기
        val details = detailList[position]
        //item.xml      //detail_info.class
        val resourceId = context.resources.getIdentifier(details.img, "drawable", context.packageName)
        detailImage.setImageResource(resourceId)
        detailTitle.text = details.title
        return view
    }

    override fun getItem(position: Int): Any {
        return detailList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return detailList.size
    }

}