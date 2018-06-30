package com.ideal.test.ideal.activity

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.MarkerOptions
import com.amap.api.maps2d.model.PolylineOptions
import com.ideal.test.ideal.R
import kotlinx.android.synthetic.main.activity_amap.*


class AmapActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amap)

        map_view.onCreate(savedInstanceState) // 此方法必须重写
        val aMap = map_view.map

        // 显示实时交通状况
        aMap.isTrafficEnabled = true
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.mapType = AMap.MAP_TYPE_NORMAL

        val nanning = LatLng(22.8170, 108.3665)

        //绘制marker
        aMap.addMarker(MarkerOptions()
                .title("南宁")
                .position(nanning)
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(resources, R.drawable.ic_red_map_marker)))
                .draggable(true))

        aMap.moveCamera(CameraUpdateFactory.changeLatLng(nanning))

        // 绘制曲线
        aMap.addPolyline(PolylineOptions()
                .add(LatLng(43.828, 87.621), LatLng(45.808, 126.55))
                .geodesic(true).color(Color.RED))
    }

}