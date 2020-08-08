package com.example.core.extensions

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController

fun Fragment.askPermissions(
    permissions: List<String>,
    allGrantedCallback: () -> Unit,
    permissionsDeniedCallback: (Boolean) -> Unit
) {
    activity?.askPermissions(permissions, allGrantedCallback, permissionsDeniedCallback)
}

fun Fragment.navigate(actionId: Int, bundle: Bundle? = null) {
    findNavController(this).navigate(actionId, bundle)
}

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