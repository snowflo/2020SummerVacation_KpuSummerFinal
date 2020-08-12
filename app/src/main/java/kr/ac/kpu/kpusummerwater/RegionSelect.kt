package kr.ac.kpu.kpusummerwater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_region_select.*

//confict test aa
class RegionSelect : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region_select)

        val si_items = resources.getStringArray(R.array.cities) //서울 ~ 제주
        val myAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, si_items)
        //     컨택스트                 만들어질 Layout Resource              리스트
        val intent = Intent(this, WaterView::class.java)

        var si:String = "why" //시
        var dong:String = "this" //동
        // intent로 데이터 넘겨주기

        val waterbundle = Bundle()
        waterbundle.putSerializable("Si", si)
        waterbundle.putSerializable("Dong", dong)


        choose.setOnClickListener(){//버튼
            //여기에 이제 스피너 조건 넣어서 만족하는 조건을 다음 3번째 엑티비티에 인텐트 넘겨주면 됨
            var waterData = data(si,dong)
            intent.putExtra("WaterData",waterData)
            startActivity(intent)
        }

        spinner.adapter = myAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) { //모든 항목 해제
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { //시 선택
                si = spinner.selectedItem as String
                Experiment.text=si
                when(position){
                    0 -> {

                        val items = resources.getStringArray(R.array.seoul) //서울
                        val arrayAdapter2 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items) //서울의 리스트
                        //코드가 겹치는 부분
                        spinner2.adapter = arrayAdapter2

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }

                    }
                    1 -> {

                        val items = resources.getStringArray(R.array.busan)
                        val arrayAdapter3 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter3

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                    2 -> {

                        val items = resources.getStringArray(R.array.daegu)
                        val arrayAdapter4 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter4

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                    3 -> {

                        val items = resources.getStringArray(R.array.incheon)
                        val arrayAdapter5 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter5

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                    4 -> {

                        val items = resources.getStringArray(R.array.gwangju)
                        val arrayAdapter6 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter6

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                    5 -> {

                        val items = resources.getStringArray(R.array.daejeon)
                        val arrayAdapter7 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter7

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                    6 -> {

                        val items = resources.getStringArray(R.array.ulsan)
                        val arrayAdapter8 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter8

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                    7 -> {

                        val items = resources.getStringArray(R.array.sejong)
                        val arrayAdapter9 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter9

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                    8 -> {

                        val items = resources.getStringArray(R.array.gyeonggi)
                        val arrayAdapter10 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter10

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                    9 -> {

                        val items = resources.getStringArray(R.array.gangwon)
                        val arrayAdapter11 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter11

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                    10 -> {

                        val items = resources.getStringArray(R.array.chungbuk)
                        val arrayAdapter12 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter12

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                    11 -> {

                        val items = resources.getStringArray(R.array.chungnam)
                        val arrayAdapter13 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter13

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                    12 -> {

                        val items = resources.getStringArray(R.array.jeonbuk)
                        val arrayAdapter14 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter14

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                    13 -> {

                        val items = resources.getStringArray(R.array.jeonnam)
                        val arrayAdapter15 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter15

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                    14 -> {

                        val items = resources.getStringArray(R.array.gyeongbuk)
                        val arrayAdapter16 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter16

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }

                    15 -> {

                        val items = resources.getStringArray(R.array.gyeongnam)
                        val arrayAdapter17 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter17

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }

                    16 -> {

                        val items = resources.getStringArray(R.array.jeju)
                        val arrayAdapter18 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, items)
                        spinner2.adapter = arrayAdapter18

                        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                dong = spinner2.selectedItem as String
                                Experiment2.text = dong
                            }
                        }
                    }
                }
            }
        }
    }
}

class data(var Si: String?, var Dong: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Si)
        parcel.writeString(Dong)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<data> {
        override fun createFromParcel(parcel: Parcel): data {
            return data(parcel)
        }

        override fun newArray(size: Int): Array<data?> {
            return arrayOfNulls(size)
        }
    }
}