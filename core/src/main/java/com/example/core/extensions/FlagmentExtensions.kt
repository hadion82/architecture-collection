package com.example.core.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController

fun Fragment.askPermissions(
    permissions: List<String>,
    allGrantedCallback: () -> Unit,
    permissionsDeniedCallback: (Boolean) -> Unit
) {
    activity?.askPermissions(permissions, allGrantedCallback, permissionsDeniedCallback)
}

/**
 * startActivity
 */
fun Fragment.start(cls: Class<*>) =
    activity?.let { startActivity(Intent(it, cls)) }

fun Fragment.start(cls: Class<*>, bundle: Bundle?) =
    activity?.let {
        startActivity(Intent(it, cls).apply {
            bundle?.let { putExtras(bundle) }
        })
    }

fun Fragment.startAndFinish(cls: Class<*>, bundle: Bundle? = null, vararg flags: Int) =
    activity?.let {
        startActivity(Intent(it, cls).apply {
            flags.forEach { addFlags(it) }
            bundle?.let { putExtras(bundle) }
        })
        it.finish()
    }

fun Fragment.startAndFinishAffinity(
    cls: Class<*>, bundle: Bundle? = null,
    vararg flags: Int = intArrayOf()
) = activity?.let {
    startActivity(Intent(it, cls).apply {
        flags.forEach { addFlags(it) }
        bundle?.let { putExtras(bundle) }
    })
    ActivityCompat.finishAffinity(it)
}

fun Fragment.navigate(actionId: Int, bundle: Bundle? = null) {
    findNavController().run {
        currentDestination?.getAction(actionId)?.let {
            navigate(actionId, bundle)
        }
    }
}

fun Fragment.navigateUp() =
    findNavController().navigateUp()

fun Fragment.toast(
    text: CharSequence,
    duration: Int = Toast.LENGTH_SHORT
) =
    activity?.toast(text, duration)

fun Fragment.toast(
    @StringRes resId: Int,
    duration: Int = Toast.LENGTH_SHORT
) =
    activity?.toast(resId, duration)