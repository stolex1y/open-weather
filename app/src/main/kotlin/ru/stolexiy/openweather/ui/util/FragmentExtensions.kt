package ru.stolexiy.openweather.ui.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

object FragmentExtensions {
    fun Fragment.repeatOnViewLifecycle(
        state: Lifecycle.State = Lifecycle.State.RESUMED,
        block: suspend () -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state) {
                block()
            }
        }
    }
}
