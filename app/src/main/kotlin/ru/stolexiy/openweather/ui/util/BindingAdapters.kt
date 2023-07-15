package ru.stolexiy.openweather.ui.util

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @BindingAdapter("isGone")
    @JvmStatic
    fun viewIsGone(view: View, isGone: Boolean) {
        view.visibility = if (isGone)
            View.GONE
        else
            View.VISIBLE
    }
}