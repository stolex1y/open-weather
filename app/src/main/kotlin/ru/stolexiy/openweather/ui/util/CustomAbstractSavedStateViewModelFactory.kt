package ru.stolexiy.openweather.ui.util

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.savedstate.SavedStateRegistryOwner

@Suppress("UNCHECKED_CAST")
class CustomAbstractSavedStateViewModelFactory<out T : ViewModel>(
    owner: SavedStateRegistryOwner,
    private val viewModelProducer: (SavedStateHandle) -> T,
    defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = viewModelProducer(handle) as T

    companion object {
        inline fun <reified T : ViewModel> Fragment.assistedViewModels(
            noinline ownerProducer: () -> ViewModelStoreOwner = { this },
            noinline viewModelProducer: (SavedStateHandle) -> T
        ) = viewModels<T>(ownerProducer) {
            CustomAbstractSavedStateViewModelFactory(this, viewModelProducer, this.arguments)
        }

        @Composable
        inline fun <reified T : ViewModel> assistedViewModels(
            noinline viewModelProducer: (SavedStateHandle) -> T
        ) = viewModel<T>(
            factory = CustomAbstractSavedStateViewModelFactory(
                LocalSavedStateRegistryOwner.current,
                viewModelProducer,
                null
            )
        )
    }
}
