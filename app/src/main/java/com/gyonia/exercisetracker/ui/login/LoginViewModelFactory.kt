package com.gyonia.exercisetracker.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.gyonia.exercisetracker.data.LoginDataSource
import com.gyonia.exercisetracker.data.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(val preferences: SharedPreferences) : ViewModelProvider.Factory, ViewModelStoreOwner, LifecycleOwner {

    private var viewModelStore = ViewModelStore()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource(preferences)
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    override fun getViewModelStore(): ViewModelStore {
        return viewModelStore
    }

    override fun getLifecycle(): Lifecycle {
        return LifecycleRegistry(this)
    }
}