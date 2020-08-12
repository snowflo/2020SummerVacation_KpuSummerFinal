package kr.ac.kpu.kpusummerwater

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_water_view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kr.ac.kpu.kpusummerwater.sampledata.ui.review.GiveMessage
import kr.ac.kpu.kpusummerwater.sampledata.ui.review.Login
import kr.ac.kpu.kpusummerwater.sampledata.ui.review.Review
import kr.ac.kpu.kpusummerwater.sampledata.ui.review.ReviewList
import kr.ac.kpu.kpusummerwater.slideshow.SlideshowFragment
import kr.ac.kpu.kpusummerwater.ui.Review.News
import okhttp3.*
import org.jetbrains.anko.toast
import java.io.IOException
import java.time.LocalDate

//WaterViewWaterView..
class WaterView : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration

    var currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_view)
        main_navigationView.setNavigationItemSelectedListener(this) //navigation 리스너

        //툴바 앱바 지정
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //데이터 들어오는지 체크
        if (intent.hasExtra("WaterData")) {

            var SiTest:String? = null
            var DongTest:String? = null


            //데이터 들어오는지 체크
            var data = intent.getParcelableExtra<data>("WaterData")
            if (data != null) { //debug
                siText.text = data.Si
                dongText.text = data.Dong
                Toast.makeText(this, "시: ${data.Si}, 동: ${data.Dong} ", Toast.LENGTH_SHORT).show()

                data.Si?.let { fetchJson(it) }
            }
            else{
                Toast.makeText(this, "전달된 내용이 없습니다", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }

        refreshButton.setOnClickListener(){
            val intent = intent
            finish()
            startActivity(intent)
        }

    }
    //옵션 선택
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //네비게이션 드로어 옵션선택
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        lateinit var itemIntent:Intent
        when(item.itemId){ //이벤트는 activity 변경만
            R.id.item1-> itemIntent = Intent(this, MainActivity::class.java) //홈
            R.id.item3-> itemIntent = Intent(this, SlideshowFragment::class.java) //상세
            R.id.item4-> itemIntent = Intent(this, News::class.java) //뉴스
            R.id.item5-> itemIntent = Intent(this, Login::class.java) //로그인
            /*
            R.id.item2->
                if(currentUser!=null) { //피드백
                    itemIntent = Intent(this, Review::class.java)
                }else{
                    itemIntent = Intent(this,GiveMessage::class.java)
                }
             */
        }
            startActivity(itemIntent)
        return false
    }
    //뒤로가기 재정의 (네비게이션 드로어 - > 뒤로가기 프로그램 종료 방지함)
    override fun onBackPressed() { //뒤로가기 처리
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers()
            // 테스트를 위해 뒤로가기 버튼시 Toast 메시지
            Toast.makeText(this, "back btn clicked", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }

    fun fetchJson(cityName: String){

        var sitenm: String = "데이터가 없습니다."

        if(cityName == "서울특별시"){
            sitenm = "\"일운정수장\""
        } else if(cityName == "부산광역시"){
            sitenm = "\"고령정수장\""
        } else if(cityName == "대구광역시"){
            sitenm = "\"광주1정수장\""
        } else if(cityName == "인천광역시"){
            sitenm = "\"광주2정수장\""
        } else if(cityName == "광주광역시"){
            sitenm = "\"광주3정수장\""
        } else if(cityName == "대전광역시"){
            sitenm = "\"단양정수장\""
        } else if(cityName == "울산광역시"){
            sitenm = "\"영춘정수장\""
        } else if(cityName == "세종특별자치시"){
            sitenm = "\"동두천정수장\""
        }else if(cityName == "경기도"){
            sitenm = "\"물야정수장\""
        }else if(cityName == "강원도"){
            sitenm = "\"봉화정수장\""
        }else if(cityName == "충청북도"){
            sitenm = "\"석포정수장\""
        } else if(cityName == "충청남도"){
            sitenm = "\"소천정수장\""
        } else if(cityName == "전라북도"){
            sitenm = "\"재산정수장\""
        } else if(cityName == "전라남도"){
            sitenm = "\"춘양정수장\""
        } else if(cityName == "경상북도"){
            sitenm = "\"곤명정수장\""
        } else if(cityName == "경상남도"){
            sitenm = "\"감천정수장\""
        } else if(cityName == "제주특별자치도"){
            sitenm = "\"예천정수장\""
        } else{
            sitenm = "\"대야정수장\""
        }

        val onlyDate: LocalDate = LocalDate.now()


        //val url = "http://opendata.kwater.or.kr/openapi-data/service/pubd/waterinfos/waterquality/daywater/list?serviceKey=ZqjrQQ5DoczvhNkENLU%2BBTlJBXmacSi%2BSTnkdmFdQOkTZf8jxK%2BpgnQnONs5h35H%2BYhNIN7QSto2e9NhB%2Bgj1g%3D%3D&sgccd<99999&sitecd<99999&stdt="+onlyDate+"&eddt="+onlyDate+"&_type=json"
        //val url = "http://opendata.kwater.or.kr/openapi-data/service/pubd/waterinfos/waterquality/daywater/list?serviceKey=ZqjrQQ5DoczvhNkENLU%2BBTlJBXmacSi%2BSTnkdmFdQOkTZf8jxK%2BpgnQnONs5h35H%2BYhNIN7QSto2e9NhB%2Bgj1g%3D%3D&sgccd<99999&sitecd<99999&stdt=20190815&eddt=20190815&_type=json"
        var url = "http://opendata.kwater.or.kr/openapi-data/service/pubd/waterinfos/waterquality/daywater/list?serviceKey=ZqjrQQ5DoczvhNkENLU%2BBTlJBXmacSi%2BSTnkdmFdQOkTZf8jxK%2BpgnQnONs5h35H%2BYhNIN7QSto2e9NhB%2Bgj1g%3D%3D&sgccd<99999&sitecd<99999&stdt=20200708&eddt=20200708&_type=json"
        var request = Request.Builder().url(url).build()
        var client = OkHttpClient()

        // Response가 있으면 실행하는 구문 okhttp를 사용
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                var body = response?.body()?.string()

                var PH : String = "데이터가 없습니다"
                var Color: String = "데이터가 없습니다"
                var Dark : String = "데이터가 없습니다"
                var Taste: String = "데이터가 없습니다"
                var Smell : String = "데이터가 없습니다"
                var Cl: String = "데이터가 없습니다"

                var PH_good : String = "데이터가 없습니다"
                var Color_good = "데이터가 없습니다"
                var Dark_good : String = "데이터가 없습니다"
                var Taste_good : String = "데이터가 없습니다"
                var Smell_good : String = "데이터가 없습니다"
                var Cl_good : String = "데이터가 없습니다"

                var parser = JsonParser()
                var resultvalue: String = "데이터가 없습니다."
                var size = parser.parse(body.toString()).asJsonObject.get("response").asJsonObject.get("body").asJsonObject.get("items").asJsonObject.get("item").asJsonArray.size()
                var rootObj = parser.parse(body.toString()).asJsonObject.get("response").asJsonObject.get("body").asJsonObject.get("items").asJsonObject.get("item")

                // 반복문을 실시하면서 데이터의 조건에 따라서 출력값 대입
                for(i in 0 until size-1) {

                    var city= rootObj.asJsonArray.get(i).asJsonObject.get("sitenm")
                    if(sitenm == city.toString()){

                        PH = rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString()

                        if((PH.toDouble()<=7.20)&&(PH.toDouble()>=6.80)){
                            PH_good = "매우 좋음"
                        } else if(((PH.toDouble()>7.20)&&(PH.toDouble()<=7.50))||((PH.toDouble()>=6.50)&&(PH.toDouble()<6.80))){
                            PH_good = "좋음"
                        } else if(((PH.toDouble()>7.50)&&(PH.toDouble()<=7.80))||((PH.toDouble()>=6.20)&&(PH.toDouble()<6.50))){
                            PH_good = "보통"
                        } else if(((PH.toDouble()>7.80)&&(PH.toDouble()<=8.10))||((PH.toDouble()>=5.90)&&(PH.toDouble()<6.20))){
                            PH_good = "나쁨"
                        } else{
                            PH_good = "매우 나쁨"
                        }

                        Color = rootObj.asJsonArray.get(i).asJsonObject.get("data3").toString()

                        if((Color.toInt() == 0)){
                            Color_good = "매우 좋음"
                        } else{
                            Color_good = "매우 나쁨"
                        }

                        Dark = rootObj.asJsonArray.get(i).asJsonObject.get("data5").toString()

                        if((Dark.toDouble()>=0.00)&&(Dark.toDouble()<=0.15)){
                            Dark_good = "매우 좋음"
                        } else if((Dark.toDouble()<=0.30)){
                            Dark_good = "좋음"
                        } else if((Dark.toDouble()<=0.50)){
                            Dark_good = "보통"
                        } else if((Dark.toDouble()<=0.65)){
                            Dark_good = "나쁨"
                        } else {
                            Dark_good = "매우 나쁨"
                        }

                        Cl = rootObj.asJsonArray.get(i).asJsonObject.get("data6").toString()

                        if((Cl.toDouble()<=4.0)&&(Cl.toDouble()>=0.1)){
                            Cl_good = "매우 좋음"
                        } else {
                            Cl_good = "매우 나쁨"
                        }

                        Taste = rootObj.asJsonArray.get(i).asJsonObject.get("data1").toString()

                        if(Taste == "\"적합\""){
                            Taste_good = "매우 좋음"
                        } else{
                            Taste_good = "매우 나쁨"
                        }
                        Smell = rootObj.asJsonArray.get(i).asJsonObject.get("data2").toString()

                        if(Smell == "\"적합\""){
                            Smell_good = "매우 좋음"
                        } else{
                            Smell_good = "매우 나쁨"
                        }

                        if((rootObj.asJsonArray.get(i).asJsonObject.get("data1").toString() == "\"적합\"")&&(rootObj.asJsonArray.get(i).asJsonObject.get("data2").toString() == "\"적합\"")&&(rootObj.asJsonArray.get(i).asJsonObject.get("data3").toString().toInt() == 0)&&(rootObj.asJsonArray.get(i).asJsonObject.get("data6").toString().toDouble()<=4.0)&&(rootObj.asJsonArray.get(i).asJsonObject.get("data6").toString().toDouble()>=0.1)){

                            if((rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()<=7.20)&&(rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()>=6.80)&&(rootObj.asJsonArray.get(i).asJsonObject.get("data5").toString().toDouble()>=0.00)&&(rootObj.asJsonArray.get(i).asJsonObject.get("data5").toString().toDouble()<=0.15)){
                                resultvalue = "매우 좋음"
                            }
                            else if(((rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()>7.20)&&(rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()<=7.50))||((rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()>=6.50)&&(rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()<6.80))&&(rootObj.asJsonArray.get(i).asJsonObject.get("data5").toString().toDouble()<=0.30)){
                                resultvalue = "좋음"
                            }
                            else if(((rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()>7.50)&&(rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()<=7.80))||((rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()>=6.20)&&(rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()<6.50))&&(rootObj.asJsonArray.get(i).asJsonObject.get("data5").toString().toDouble()<=0.50)){
                                resultvalue = "보통"
                            }
                            else if(((rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()>7.80)&&(rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()<=8.10))||((rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()>=5.90)&&(rootObj.asJsonArray.get(i).asJsonObject.get("data4").toString().toDouble()<6.20))&&(rootObj.asJsonArray.get(i).asJsonObject.get("data5").toString().toDouble()<=0.65)){
                                resultvalue = "나쁨"
                            }
                            else
                                resultvalue = "매우 나쁨"
                        }
                        else
                        {
                            resultvalue = "매우 나쁨"
                        }
                    }
                }
                runOnUiThread{
                    var random_val = 1
                    if(resultvalue == "매우 좋음"){
                        emoImage.setImageResource(R.drawable.very_good)
                        textLayout.setBackgroundColor(resources.getColor(R.color.very_good))
                        detailLayout.setBackgroundColor(resources.getColor(R.color.very_good))
                        if((random_val % 3) == 0) {
                            messageText.text = "캬~ 물이 이보다 좋을 수 있을까요~"
                        }
                        else if((random_val % 3) == 1){
                            messageText.text = "벌컥 벌컥 들이켜요! 여름엔 시원한 물~"
                        }
                        else if((random_val % 3) == 2){
                            messageText.text = "하루에 물 2L는 필수인거 아시죠?~"
                        }
                    } else if(resultvalue == "좋음") {
                        emoImage.setImageResource(R.drawable.good)
                        textLayout.setBackgroundColor(resources.getColor(R.color.good))
                        detailLayout.setBackgroundColor(resources.getColor(R.color.good))

                        if((random_val % 3) == 0) {
                            messageText.text = "캬~ 물 맛 좋다!!"
                        }
                        else if((random_val % 3) == 1){
                            messageText.text = "깊은 산속 옹달샘~ 보다 훨씬 맛있어요"
                        }
                        else if((random_val % 3) == 2){
                            messageText.text = "오늘도 시원한 물 한 잔으로 시작해요!"
                        }
                    } else if(resultvalue == "보통") {
                        emoImage.setImageResource(R.drawable.normal)
                        textLayout.setBackgroundColor(resources.getColor(R.color.normal))
                        detailLayout.setBackgroundColor(resources.getColor(R.color.normal))

                        if((random_val % 3) == 0) {
                            messageText.text = "다른 물을 찾아보는게 어떨까요?"
                        }
                        else if((random_val % 3) == 1){
                            messageText.text = "잘못하다가 배탈이 날 수도 있어요 ㅠㅠ"
                        }
                        else if((random_val % 3) == 2){
                            messageText.text = "나 말고 과장님께 가져다 드려요!"
                        }
                    } else if(resultvalue == "나쁨") {
                        emoImage.setImageResource(R.drawable.bad)
                        textLayout.setBackgroundColor(resources.getColor(R.color.bad))
                        detailLayout.setBackgroundColor(resources.getColor(R.color.bad))
                        if((random_val % 3) == 0) {
                            messageText.text = "이건 물이 아니라 액체입니다"
                        }
                        else if((random_val % 3) == 1){
                            messageText.text = "제가 먼저 먹어볼게요... 으으윽"
                        }
                        else if((random_val % 3) == 2){
                            messageText.text = "베어그릴스 형은 이 물 마셔도 끄떡 없을거에요"
                        }
                    } else {
                        emoImage.setImageResource(R.drawable.very_bad)
                        textLayout.setBackgroundColor(resources.getColor(R.color.very_bad))
                        detailLayout.setBackgroundColor(resources.getColor(R.color.very_bad))
                        if((random_val % 3) == 0) {
                            messageText.text = "이 물을 마셨다면 병원부터 가보세요"
                        }
                        else if((random_val % 3) == 1){
                            messageText.text = "이건 정수된 물이 아니에요. 똥물이지."
                        }
                        else if((random_val % 3) == 2){
                            messageText.text = "직접 정수해서 드셔야합니다. 숯, 모래, 페트병을 준비하세요."
                        }
                    }

                    dateText.text = onlyDate.toString()
                    classText.text = resultvalue
                    PHText2.text = PH
                    PHText3.text = PH_good
                    colorText2.text = Color
                    colorText3.text = Color_good
                    darkText2.text = Dark
                    darkText3.text = Dark_good
                    tasteText2.text = Taste
                    tasteText3.text = Taste_good
                    smellText2.text = Smell
                    smellText3.text = Smell_good
                    clText2.text = Cl
                    clText3.text = Cl_good
                }




            }
            override fun onFailure(call: Call, e: IOException) {
                println("리퀘스트 실패")
            }
        })
    }

}