package com.gyonia.exercisetracker.ui.login

import androidx.lifecycle.*
import com.gyonia.exercisetracker.data.LoginDataSource
import com.gyonia.exercisetracker.data.LoginRepository
import com.gyonia.exercisetracker.viewmodel.ExerciseViewModel

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory, ViewModelStoreOwner, LifecycleOwner {

    private var viewModelStore = ViewModelStore()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var exerciseViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource()
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