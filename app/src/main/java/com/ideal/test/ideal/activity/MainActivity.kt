package com.ideal.test.ideal.activity

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.ideal.test.ideal.MyApplication
import com.ideal.test.ideal.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        private const val SDK_PERMISSION_REQUEST = 1
    }

    private var locationClient: AMapLocationClient? = null
    private var aMapLocation: AMapLocation? = null
    private var permissionsGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()

        button_locate.setOnClickListener { _ ->
            if (!permissionsGranted) return@setOnClickListener
            val mAMapLocationListener = AMapLocationListener { location ->
                if (location.errorCode == 0) {
                    aMapLocation = location
                    Toast.makeText(this, "${location.country} ${location.province} ${location.city}", Toast.LENGTH_LONG).show()
                    locationClient?.stopLocation()
                    locationClient?.onDestroy()
                }
            }
            val option = AMapLocationClientOption()
            // 获取最近3s内精度最高的一次定位结果：
            option.isOnceLocationLatest = true

            locationClient = AMapLocationClient(applicationContext)
            locationClient?.setLocationOption(option)
            locationClient?.setLocationListener(mAMapLocationListener)
            locationClient?.stopLocation()
            locationClient?.startLocation()
        }

        button_show_map.setOnClickListener { _ ->
            val intent = Intent(this, AmapActivity::class.java)
            startActivity(intent)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun checkPermissions() {
        val myApplication = application as? MyApplication ?: return
        val permission = myApplication.getPermissionList(this)
        if (permission.isNotEmpty()) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            requestPermissions(permission.toTypedArray(), SDK_PERMISSION_REQUEST)
        } else {
            permissionsGranted = true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            SDK_PERMISSION_REQUEST -> {
                permissionsGranted = true
            }
        }
    }
}
