package ru.stolexiy.openweather.ui.util

import android.content.Context
import android.graphics.Color
import android.view.Menu
import android.view.MenuInflater
import androidx.annotation.AttrRes
import androidx.annotation.MenuRes
import androidx.core.view.forEach
import com.google.android.material.color.MaterialColors

object MenuUtils {
    fun MenuInflater.inflateWithTintIcons(
        @MenuRes menuRes: Int,
        menu: Menu,
        @AttrRes tintColorRes: Int,
        context: Context,
    ) {
        this.inflate(menuRes, menu)
        val iconTint = MaterialColors.getColor(
            context,
            tintColorRes,
            Color.BLACK
        )
        menu.forEach {
            it.icon?.setTint(iconTint)
        }
    }
}
