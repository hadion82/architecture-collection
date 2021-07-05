package com.example.core.util

import androidx.fragment.app.FragmentActivity
import com.kotlinpermissions.KotlinPermissions
import com.kotlinpermissions.ResponsePermissionCallback

object Permissions {

    @JvmStatic
    fun with(activity: FragmentActivity): PermissionBuilder {
        return PermissionBuilder(activity)
    }

    class PermissionBuilder(activity: FragmentActivity) {
        private var core: KotlinPermissions.PermissionCore = KotlinPermissions.with(activity)

        fun permissions(vararg permissions: String): PermissionsExecuteBuilder {
            return PermissionsExecuteBuilder(permissions)
        }

        inner class PermissionsExecuteBuilder(private val permissions: Array<out String>) {

            private var acceptedCallback: ResponsePermissionCallback? = null
            private var deniedCallback: ResponsePermissionCallback? = null
            private var foreverDeniedCallback: ResponsePermissionCallback? = null

            private var hasDenied: Boolean = false

            fun onAccepted(isAcceptedAll: Boolean = true, callback: (List<String>) -> Unit) = apply {
                acceptedCallback = object : ResponsePermissionCallback {
                    override fun onResult(permissionResult: List<String>) {
                        if (!isAcceptedAll || permissions permissionContains permissionResult) {
                            callback(permissionResult)
                        }
                    }
                }
            }

            fun onDenied(callback: (List<String>) -> Unit) = apply {
                deniedCallback = object : ResponsePermissionCallback {
                    override fun onResult(permissionResult: List<String>) {
                        hasDenied = true
                        callback(permissionResult)
                    }
                }
            }

            fun onForeverDenied(hasNoDenied: Boolean = false, callback: (List<String>) -> Unit) = apply {
                foreverDeniedCallback = object : ResponsePermissionCallback {
                    override fun onResult(permissionResult: List<String>) {
                        if(!hasNoDenied || !hasDenied) callback(permissionResult)
                    }
                }
            }

            fun ask() {
                core.permissions(*permissions)
                acceptedCallback?.let {
                    core.onAccepted(it)
                }
                deniedCallback?.let {
                    core.onDenied(it)
                }
                foreverDeniedCallback?.let {
                    core.onForeverDenied(it)
                }
                core.ask()
            }
        }
    }

    infix fun <T> Array<out T>.permissionContains(other: List<T>): Boolean {
        if (this.size != other.size) return false
        forEach { item -> if (!other.contains(item)) return false }
        return true
    }
}