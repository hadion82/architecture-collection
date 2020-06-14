package com.example

import android.text.Editable
import androidx.core.text.isDigitsOnly

fun Editable?.toSafeLong(defValue: Long = 0): Long =
    if(this.isNullOrBlank() || !this.isDigitsOnly())
        defValue
    else this.toString().toLong()