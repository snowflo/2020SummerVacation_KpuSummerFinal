package kr.ac.kpu.kpusummerwater.slideshow
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_slideshow.*
import kr.ac.kpu.kpusummerwater.R


//리스트 뷰 메인
class SlideshowFragment : AppCompatActivity() {

    val baseUrl = "http://english.kwater.or.kr/news/repoList.do?brdId=KO26&s_mid=36&pageNo="
    var pages: Int = 1
    var maxPages :Int =3

    //임시 데이터
    var detailList = arrayListOf<detail_info>(
        detail_info("ic_baseline_restaurant_menu_24", "맛"),
        detail_info("ic_baseline_local_cafe_24", "냄새"),
        detail_info("ic_baseline_invert_colors_24", "색도"),
        detail_info("ic_baseline_bubble_chart_24", "ph"),
        detail_info("ic_baseline_texture_24", "탁도"),
        detail_info("ic_baseline_blur_circular_24", "잔류염소")
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