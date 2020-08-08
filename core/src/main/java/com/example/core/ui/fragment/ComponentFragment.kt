package com.example.core.ui.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import io.kiwiplus.app.core.list.Submitter

abstract class ComponentFragment<DB : ViewDataBinding> :
    BindingFragment<DB>() {

    fun startAndFinish(cls: Class<*>) {
        startAndFinish(cls, null)
    }

    fun startAndFinish(cls: Class<*>, vararg flags: Int) {
        startAndFinish(cls, null, *flags)
    }

    fun startAndFinish(cls: Class<*>, bundle: Bundle?, vararg flags: Int) {
        activity?.let {
            startActivity(Intent(it, cls).apply {
                flags.forEach { flag ->
                    addFlags(flag)
                }
                bundle?.let {
                    putExtras(bundle)
                }
            })
            it.finish()
        }
    }

    fun startAndFinish(intent: Intent) {
        activity?.let {
            startActivity(intent)
            it.finish()
        }
    }

    fun start(cls: Class<*>) {
        activity?.run {
            startActivity(Intent(this, cls))
        }
    }

    fun start(cls: Class<*>, bundle: Bundle?) {
        activity?.let {
            startActivity(Intent(it, cls).apply {
                bundle?.let {
                    putExtras(bundle)
                }
            })
        }
    }

    fun start(cls: Class<*>, bundle: Bundle?, vararg flags: Int) {
        activity?.let {
            startActivity(Intent(it, cls).apply {
                flags.forEach { flag ->
                    addFlags(flag)
                }
                bundle?.let {
                    putExtras(bundle)
                }
            })
        }
    }

    fun startForResult(cls: Class<*>, requestCode: Int) {
        activity?.run {
            this@ComponentFragment.startActivityForResult(
                Intent(this, cls),
                requestCode
            )
        }
    }

    fun startForResult(cls: Class<*>, requestCode: Int, bundle: Bundle?) {
        activity?.run {
            this@ComponentFragment.startActivityForResult(
                Intent(this, cls).apply {
                    bundle?.let { putExtras(it) }
                },
                requestCode
            )
        }
    }

    fun navigate(resId: Int) {
        try {
            navigateSafe(resId, null)
        } catch (exception: Exception) {
            //ignore
        }
    }

    fun navigate(resId: Int, bundle: Bundle?) {
        try {
            navigateSafe(resId, bundle)
        } catch (exception: Exception) {
            //ignore
        }
    }

    private fun navigateSafe(resId: Int, bundle: Bundle?) {
        findNavController().run {
            currentDestination?.getAction(resId)?.let {
                navigate(resId, bundle)
            }
        }
    }

    fun navigateUp(): Boolean {
        return findNavController().navigateUp()
    }


    fun inputFilter(editText: EditText, filter: InputFilter) {
        editText.filters = arrayOf(filter)
    }

    fun setActionBarTitle(@StringRes resId: Int) {
        activity?.setTitle(resId)
    }

    fun setActionBarTitle(title: CharSequence) {
        activity?.title = title
    }

    fun showActionBar() {
        activity?.actionBar?.show()
    }

    fun hideActionBar() {
        activity?.actionBar?.hide()
    }

    fun hideKeyboard() {
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val view: View = requireActivity().currentFocus ?: View(requireContext())
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}