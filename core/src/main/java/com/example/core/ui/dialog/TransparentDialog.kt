package com.example.core.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class TransparentDialog<DB: ViewDataBinding>: DialogFragment() {

    lateinit var binding: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = createViewDataBinding(
            inflater, container, savedInstanceState
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        dialog?.window?.let {
//            it.requestFeature(Window.FEATURE_NO_TITLE)
//            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        }
        super.onActivityCreated(savedInstanceState)
    }

    abstract fun createViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): DB
}