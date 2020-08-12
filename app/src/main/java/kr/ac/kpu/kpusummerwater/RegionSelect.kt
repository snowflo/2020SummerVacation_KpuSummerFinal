package kr.ac.kpu.kpusummerwater

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_region_select.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

//confict test aa
class RegionSelect : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    // 지도 조작을 위한 객체.
    val markerOptions : MarkerOptions = MarkerOptions()
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    // 위치값 얻어오기 객체
    lateinit var locationRequest: LocationRequest // 위치 요청
    lateinit var locationCallback: RegionSelect.MyLocationCallBack // 내부 클래스, 위치 변경 후 지도에 표시.

    val polyLineOptions = PolylineOptions().width(5f).color(Color.RED)
    //경로를 표시할 펜 구성.
    // 다각으로 꺽어지는 선, 굵기는 5f, 색상은 빨강.

    val REQUEST_ACCESS_FINE_LOCATION = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        // 어플이 사용되는 동안 화면 끄지 않기.

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // 세로모드 고정.

        setContentView(R.layout.activity_region_select)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        // as 는 형변환
        mapFragment.getMapAsync(this)
        // 비동기 -> 기다리지 않고 처리하는 것(타이밍을 맞추지 않고 처리)
        // 전화기, 무전기
        locationInit()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION // 위치에 대한 권한 요청
            )
            != PackageManager.PERMISSION_GRANTED
// 사용자 권한 체크로
// 외부 저장소 읽기가 허용되지 않았다면
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) { // 허용되지 않았다면 다시 확인.
                alert(
                    "사진 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다.",

                    "권한이 필요한 이유"
                ) {
                    yesButton {
                        // 권한 허용
                        ActivityCompat.requestPermissions(
                            this@RegionSelect,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            REQUEST_ACCESS_FINE_LOCATION
                        )
                    }
                    noButton {
                        // 권한 비허용
                    }
                }.show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION
                )
            }
        } else {
            addLocationListener()
        }

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
// 권한이 승인 됐다면
                    addLocationListener()
                } else {
// 권한이 거부 됐다면
                    toast("권한 거부 됨")
                }
                return
            }
        }
    }

    fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        // 현재 사용자 위치를 저장.
        locationCallback = MyLocationCallBack() // 내부 클래스 조작용 객체 생성
        locationRequest = LocationRequest() // 위치 요청.

        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        // 위치 요청의 우선순위 = 높은 정확도 우선.
        locationRequest.interval = 10000 // 내 위치 지도 전달 간격
        locationRequest.fastestInterval = 5000 // 지도 갱신 간격.

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onResume() { // 잠깐 쉴 때.
        super.onResume()
        addLocationListener()
    }

    fun removeLocationLister(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        // 어플이 종료되면 지도 요청 해제.
    }

    @SuppressLint("MissingPermission")
    // 위험 권한 사용시 요청 코드가 호출되어야 하는데,
    // 없어서 발생됨. 요청 코드는 따로 처리 했음.
    fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
        //위치 권한을 요청해야 함.
        // 액티비티가 잠깐 쉴 때,
        // 자신의 위치를 확인하고, 갱신된 정보를 요청
    }

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)

            val location = p0?.lastLocation
            // 위도 경도를 지도 서버에 전달하면,
            // 위치에 대한 지도 결과를 받아와서 저장.

            location?.run {
                val latLng = LatLng(latitude, longitude) // 위도 경도 좌표 전달.
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                // 지도에 애니메이션 효과로 카메라 이동.
                // 좌표 위치로 이동하면서 배율은 17 (0~19까지 범위가 존재.)

                Log.d("MapsActivity", "위도: $latitude, 경도 : $longitude")

//              markerOptions.position(latLng) // 마커를 latLng으로 설정
//
//              mMap.addMarker(markerOptions) // googleMap에 marker를 표시.

                polyLineOptions.add(latLng) // polyline 기준을 latLng으로 설정

                mMap.addPolyline(polyLineOptions) // googleMap에 ployLine을 그림.

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