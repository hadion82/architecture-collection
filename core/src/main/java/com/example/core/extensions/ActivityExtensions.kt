package com.example.core.extensions

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * permission
 */
fun Activity.askPermissions(
    permissions: List<String>,
    allGrantedCallback: () -> Unit,
    permissionsDeniedCallback: (Boolean) -> Unit
) {
    Dexter.withActivity(this)
        .withPermissions(permissions)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }

            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    if (it.areAllPermissionsGranted()) {
                        allGrantedCallback()
                    } else {
                        permissionsDeniedCallback(it.isAnyPermissionPermanentlyDenied)
                    }
                }
            }
        })
        .check()
}

/**
 * startActivity
 */
fun Activity.start(cls: Class<*>) {
    startActivity(Intent(this, cls))
}

fun Activity.start(cls: Class<*>, bundle: Bundle?) {
    startActivity(Intent(this, cls).apply {
        bundle?.let { putExtras(bundle) }
    })
}

fun Activity.startAndFinish(cls: Class<*>, bundle: Bundle? = null, vararg flags: Int) {
    startActivity(Intent(this, cls).apply {
        flags.forEach { addFlags(it) }
        bundle?.let { putExtras(bundle) }
    })
    finish()
}

fun Activity.startAndFinishAffinity(
    cls: Class<*>, bundle: Bundle? = null,
    vararg flags: Int = intArrayOf()
) {
    startActivity(Intent(this, cls).apply {
        flags.forEach { addFlags(it) }
        bundle?.let { putExtras(bundle) }
    })
    ActivityCompat.finishAffinity(this)
}

/**
 * navigation graph
 */
fun Activity.navigate(@IdRes navHostId: Int, @IdRes actionId: Int, bundle: Bundle? = null) =
    Navigation.findNavController(this, navHostId).run {
        currentDestination?.getAction(actionId)?.let {
            navigate(actionId, bundle)
        }
    }

/**
 * status bar
 */
fun Activity.setStatusBarColor(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = ContextCompat.getColor(this, color)
    }
}

fun Activity.setStatusLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun Activity.setCompatVectorFromResourcesEnabled(value: Boolean) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(value)
    }
}

fun Activity.toast(
    text: CharSequence,
    duration: Int = Toast.LENGTH_SHORT
) =
    Toast.makeText(this, text, duration).show()

fun Activity.toast(
    @StringRes resId: Int,
    duration: Int = Toast.LENGTH_SHORT
) =
    Toast.makeText(this, resId, duration).show()

inline fun AppCompatActivity.launch(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    crossinline block: suspend () -> Unit
): Job =
    lifecycleScope.launch(dispatcher) { block() }

