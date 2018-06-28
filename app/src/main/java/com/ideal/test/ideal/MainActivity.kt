package com.ideal.test.ideal

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationListener
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
            if (! permissionsGranted) return@setOnClickListener
            val mAMapLocationListener = AMapLocationListener { location ->
                if (location.errorCode == 0) {
                    aMapLocation = location
                    Toast.makeText(this, location.toString(), Toast.LENGTH_LONG).show()
                    locationClient?.stopLocation()
                }
            }
            locationClient = AMapLocationClient(applicationContext)
            locationClient?.setLocationListener(mAMapLocationListener)
            locationClient?.startLocation()
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
