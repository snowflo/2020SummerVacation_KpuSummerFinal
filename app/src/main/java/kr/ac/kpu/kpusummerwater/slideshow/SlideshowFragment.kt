package kr.ac.kpu.kpusummerwater.slideshow
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_slideshow.*
import kr.ac.kpu.kpusummerwater.R

class SlideshowFragment : AppCompatActivity() {


    //임시 데이터
    var detailList = arrayListOf<detail_info>(
        detail_info("unknown1", "맛"),
        detail_info("unknown2", "냄새"),
        detail_info("unknown3", "색도"),
        detail_info("unknown4", "ph"),
        detail_info("unknown5", "탁도"),
        detail_info("unknown6", "잔류염소")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        //fetchJson()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_slideshow)

        val mAdapter = detail_adapter(this, detailList)

        detailListView.setOnItemClickListener(){parent,view,position,id->
            if(position==0) {
                // Toast.makeText(this, "ItemOne",Toast.LENGTH_SHORT).show()

                val builder = AlertDialog.Builder(this)
                val dialogView =layoutInflater.inflate(R.layout.pop_up_taste,null)
                builder.setView(dialogView).setNeutralButton("닫기",null).show()
            }
            else if(position ==1){

                val builder = AlertDialog.Builder(this)
                val dialogView =layoutInflater.inflate(R.layout.pop_up_smell,null)
                builder.setView(dialogView).setNeutralButton("닫기",null).show()

            }
            else if(position ==2){

                val builder = AlertDialog.Builder(this)
                val dialogView =layoutInflater.inflate(R.layout.pop_up_color,null)
                builder.setView(dialogView).setNeutralButton("닫기",null).show()
            }
            else if(position ==3){

                val builder = AlertDialog.Builder(this)
                val dialogView =layoutInflater.inflate(R.layout.pop_up_ph,null)
                builder.setView(dialogView).setNeutralButton("닫기",null).show()
            }
            else if(position ==4){

                val builder = AlertDialog.Builder(this)
                val dialogView =layoutInflater.inflate(R.layout.pop_up_dark,null)
                builder.setView(dialogView).setNeutralButton("닫기",null).show()
            }
            else if(position ==5){

                val builder = AlertDialog.Builder(this)
                val dialogView =layoutInflater.inflate(R.layout.pop_up_cl,null)
                builder.setView(dialogView).setNeutralButton("닫기",null).show()
            }
        }
        detailListView.adapter = mAdapter
    }

}