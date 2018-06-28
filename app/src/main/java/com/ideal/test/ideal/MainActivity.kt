package com.ideal.test.ideal

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationListener



class MainActivity : AppCompatActivity() {

    private val SDK_PERMISSION_REQUEST = 1
    //声明AMapLocationClient类对象
    private var locationClient: AMapLocationClient? = null
    private var aMapLocation: AMapLocation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun checkPermissions() {
        val myApplication = application as? MyApplication ?: return
        val permission = myApplication.getPermissionList(this)
        if (permission.isNotEmpty()) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            requestPermissions(permission.toTypedArray(), SDK_PERMISSION_REQUEST)
            return
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            SDK_PERMISSION_REQUEST -> {
                //异步获取定位结果
                val mAMapLocationListener = AMapLocationListener { location ->
                    if (location != null && location.errorCode == 0) {
                        aMapLocation = location
                    }
                }

                //初始化定位
                locationClient = AMapLocationClient(applicationContext)
                //设置定位回调监听
                locationClient?.setLocationListener(mAMapLocationListener)
                //启动定位
                locationClient?.startLocation()
            }
        }
    }
}
