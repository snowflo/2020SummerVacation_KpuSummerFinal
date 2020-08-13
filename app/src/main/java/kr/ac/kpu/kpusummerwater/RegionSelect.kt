package kr.ac.kpu.kpusummerwater

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.activity_region_select.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import java.io.IOException
import java.util.*

class RegionSelect : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    // 지도 조작을 위한 객체.
    val markerOptions : MarkerOptions = MarkerOptions()
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    // 위치값 얻어오기 객체
    lateinit var locationRequest: LocationRequest // 위치 요청
    lateinit var locationCallback: RegionSelect.MyLocationCallBack // 내부 클래스, 위치 변경 후 지도에 표시.

    //val polyLineOptions = PolylineOptions().width(5f).color(Color.RED)
    //경로를 표시할 펜 구성. 다각으로 꺽어지는 선, 굵기는 5f, 색상은 빨강.

    val REQUEST_ACCESS_FINE_LOCATION = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_region_select)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationInit()



        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION // 위치에 대한 권한 요청
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) { // 허용되지 않았다면 다시 확인.
                alert("사진 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다.", "권한이 필요한 이유") {
                    yesButton { ActivityCompat.requestPermissions(this@RegionSelect, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
                    }
                    noButton {
                    } }.show()
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
        // 컨택스트 만들어질 Layout Resource 리스트
        val intent = Intent(this, WaterView::class.java)

        var si:String = "why" //시
        var dong:String = "this" //동

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
                    addLocationListener()
                } else {
                    toast("권한 거부 됨")
                }
                return
            }
        }
    }

    fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        locationCallback = MyLocationCallBack() // 내부 클래스 조작용 객체 생성
        locationRequest = LocationRequest() // 위치 요청.

        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        // 위치 요청의 우선순위 = 높은 정확도 우선.
        locationRequest.interval = 10000 // 내 위치 지도 전달 간격
        locationRequest.fastestInterval = 5000 // 지도 갱신 간격.

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val guro = LatLng(37.485284, 126.901451)
        //mMap.addMarker(MarkerOptions().position(guro).title("구로디지털단지역"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(guro,15f))


    }
    

    override fun onResume() { // 잠깐 쉴 때.
        super.onResume()
        addLocationListener()
    }
/*
    fun removeLocationLister(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
*/
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
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                Log.d("MapsActivity", "위도: $latitude, 경도 : $longitude")

                markerOptions.position(latLng) // 마커를 latLng으로 설정
                mMap.addMarker(markerOptions) // googleMap에 marker를 표시.

                //polyLineOptions.add(latLng) // polyline 기준을 latLng으로 설정
                //mMap.addPolyline(polyLineOptions) // googleMap에 ployLine을 그림.
                var addressName : String? = getCurrentAddress(latitude,longitude) //주소 얻어옴
                runOnUiThread{
                    whereiam.text=addressName
                }
            }
        }
    }

    fun getCurrentAddress(latitude: Double, longitude: Double): String? {

        //지오코더... GPS를 주소로 변환
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>
        addresses = try {
            geocoder.getFromLocation(
                latitude,
                longitude,
                7
            )
        } catch (ioException: IOException) {

            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show()
            return "지오코더 서비스 사용불가"
        } catch (illegalArgumentException: IllegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
            return "잘못된 GPS 좌표"
        }
        if (addresses == null || addresses.size === 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show()
            return "주소 미발견"
        }
        val address: Address = addresses[0]
        return address.getAddressLine(0).toString() + "\n"
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