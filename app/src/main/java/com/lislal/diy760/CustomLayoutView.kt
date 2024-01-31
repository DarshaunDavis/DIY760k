package com.lislal.diy760

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.lislal.diy760.databinding.CustomLayoutBinding // Make sure to replace with your actual binding class generated from your XML

class CustomLayoutView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: CustomLayoutBinding = CustomLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        // Optional: Initialize your custom view and set up any listeners or default values here
    }
}
