package com.example.core.ui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner

class CustomSpinner(
    context: Context?,
    attrs: AttributeSet?
) :
    AppCompatSpinner(context, attrs) {
    var listener: OnItemSelectedListener? = null
    override fun setSelection(position: Int) {
        super.setSelection(position)
        if (position == getSelectedItemPosition()) {
            listener?.onItemSelected(null, null, position, 0)
        }
    }

    override fun setOnItemSelectedListener(listener: OnItemSelectedListener?) {
        this.listener = listener
    }
}