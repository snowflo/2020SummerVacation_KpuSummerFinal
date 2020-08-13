package kr.ac.kpu.kpusummerwater.ui.Review

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_news.*
import kr.ac.kpu.kpusummerwater.R
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.*

class News : AppCompatActivity() {
    val weburl = "https://www.kwater.or.kr/news/repoList.do?brdId=KO26&s_mid=36"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        //네트워크 관련 작업이 이루어지기 때문에 Async Task를 이용해야 한다.
        MyAsyncTask().execute(weburl)

        //아이템 사이에 구분선 넣어 주기
        rv_news_list.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
    }

    //AsyncTask 정의
    inner class MyAsyncTask: AsyncTask<String, String, String>(){ //input, progress update type, result type
        var newsList: ArrayList<Item> = arrayListOf()

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): String? {
            var doc: Document = Jsoup.connect("http://english.kwater.or.kr/news/repoList.do?brdId=KO26&s_mid=36").get()
            val elts: Elements = doc.select(".bodo_list").select("div[class=text]")

            elts.forEachIndexed { index, elem ->
                val title = elem.select("p[class=title]").text()
                //추출한 자료를 가지고 데이터 객체를 만들어 ArrayList에 추가해 준다.
                if(index == 8){
                    var mNews = Item(title, "http://www.kwater.or.kr/news/repoView.do?brdId=KO26&s_mid=36&seq=120303")
                    newsList.add(mNews)
                }else if(index == 9){
                    var mNews = Item(title, "http://www.kwater.or.kr/news/repoView.do?brdId=KO26&s_mid=36&seq=120299")
                    newsList.add(mNews)
                }else{
                    var mNews = Item(title, "http://www.kwater.or.kr/news/repoView.do?brdId=KO26&s_mid=36&seq="+"${120524-index}")
                    newsList.add(mNews)
                }
            }
            return doc.title()
        }

        @SuppressLint("WrongConstant")
        override fun onPostExecute(result: String?) {
            progressBar.visibility = View.GONE
            //문서제목 출력
            //tv_Title.setText(result)

            //어답터 설정
            rv_news_list.layoutManager = LinearLayoutManager(this@News, LinearLayout.VERTICAL, false)
            var adapter = NewsAdapter(newsList, this@News)
            rv_news_list.adapter = adapter
        }
    }
    //데이터 클래스 객체 생성
    data class Item(val title: String, val url:String)
}