package com.ideal.test.ideal.activity

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.*
import com.ideal.test.ideal.R
import kotlinx.android.synthetic.main.activity_amap.*


class AmapActivity : Activity(), AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter {

    private val nanning = LatLng(22.8170, 108.3665)
    // Near Nanning
    private val myLocation = LatLng(22.9170, 108.3665)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amap)

        map_view.onCreate(savedInstanceState) // 此方法必须重写
        setUpMap(map_view.map)
    }

    private fun setUpMap(aMap: AMap) {
        // 显示实时交通状况
        aMap.isTrafficEnabled = true
        // 地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.mapType = AMap.MAP_TYPE_NORMAL
        aMap.setOnMarkerClickListener(this)
        aMap.setOnInfoWindowClickListener(this)
        aMap.setInfoWindowAdapter(this)
        addMarker(aMap)

        // 定位蓝点的位置是手机/模拟器的当前位置
        val myLocationStyle = MyLocationStyle()
        aMap.setMyLocationStyle(myLocationStyle) //设置定位蓝点的Style
        aMap.uiSettings.isMyLocationButtonEnabled = true
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
//        aMap.isMyLocationEnabled = true

        aMap.moveCamera(CameraUpdateFactory.changeLatLng(myLocation))
        // 1f显示全局地图
        aMap.moveCamera(CameraUpdateFactory.zoomTo(10f))
    }

    private fun addMarker(aMap: AMap) {
        // Cannot use svg here
        val markerIcon = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(resources, R.drawable.ic_black_map_marker))

        // 绘制marker
        aMap.addMarker(MarkerOptions()
                .title("南宁")
                .snippet("南宁：22.8170, 108.3665")
                .position(nanning)
                .icon(markerIcon)
                .draggable(true))
    }

    override fun getInfoWindow(marker: Marker): View {
        val view = layoutInflater.inflate(R.layout.infor_window, null)
        val info = view.findViewById(R.id.info) as TextView
        val orderNumber = view.findViewById(R.id.orderNumber) as TextView
        orderNumber.text = "2本"
        orderNumber.setTextColor(Color.parseColor("#FF0000"))
        info.text = marker.title
        return view
    }

    override fun onInfoWindowClick(marker: Marker) {
        println("点击了输入框")
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        marker.showInfoWindow()
        return false
    }

    override fun getInfoContents(marker: Marker): View? = null

    override fun onDestroy() {
        super.onDestroy()
        map_view.onDestroy()
    }

}