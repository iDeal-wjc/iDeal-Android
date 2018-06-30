package com.ideal.test.ideal.activity

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.MarkerOptions
import com.amap.api.maps2d.model.MyLocationStyle
import com.ideal.test.ideal.R
import kotlinx.android.synthetic.main.activity_amap.*


class AmapActivity : Activity() {

    private val nanning = LatLng(22.8170, 108.3665)
    // Near Nanning
    private val myLocation = LatLng(22.9170, 108.3665)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amap)

        map_view.onCreate(savedInstanceState) // 此方法必须重写
        val aMap = map_view.map

        // 显示实时交通状况
        aMap.isTrafficEnabled = true
        // 地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.mapType = AMap.MAP_TYPE_NORMAL

        // It seems we cannot use svg here
        val markerIcon = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(resources, R.drawable.ic_black_map_marker))

        // 绘制marker
        aMap.addMarker(MarkerOptions()
                .title("南宁")
                .snippet("南宁：22.8170, 108.3665")
                .position(nanning)
                .icon(markerIcon)
                .draggable(true))

        // 定位蓝点的位置是手机/模拟器的当前位置
        val myLocationStyle = MyLocationStyle()
        aMap.setMyLocationStyle(myLocationStyle) //设置定位蓝点的Style
        aMap.uiSettings.isMyLocationButtonEnabled = true
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.isMyLocationEnabled = true

        aMap.moveCamera(CameraUpdateFactory.changeLatLng(myLocation))
        aMap.moveCamera(CameraUpdateFactory.zoomTo(13f))
    }

    override fun onDestroy() {
        super.onDestroy()
        map_view.onDestroy()
    }

}