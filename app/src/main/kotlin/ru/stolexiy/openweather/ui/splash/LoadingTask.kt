package ru.stolexiy.openweather.ui.splash

import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import com.google.android.material.progressindicator.LinearProgressIndicator
import timber.log.Timber

class LoadingTask(
    private val loadingTime: Long = 1000,
    private val progressIndicator: LinearProgressIndicator,
) {
    var onLoadingEnd: (() -> Unit)? = null

    fun startLoading() {
        progressIndicator.progress = 0

        ValueAnimator.ofInt(0, 100).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = loadingTime
            addUpdateListener {
                Timber.d("update progress")
                progressIndicator.progress = it.animatedValue as Int
                progressIndicator.invalidate()
            }
            addListener(onEnd = {
                progressIndicator.hide()
                onLoadingEnd?.invoke()
            })
        }.start()
    }
}
