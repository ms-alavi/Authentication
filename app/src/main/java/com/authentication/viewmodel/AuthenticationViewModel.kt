package com.authentication.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.authentication.model.local.User
import com.authentication.model.repository.Repository
import com.authentication.model.repository.TokenContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: Repository, private val sharedPreferences: SharedPreferences
) : AppViewModel() {
    private val mTAG="AuthenticationViewModel"
    private val _userViewModel = MutableLiveData<User>()
    val userViewModel: LiveData<User> = _userViewModel
    private val _indicator = MutableLiveData<Boolean>()
    val indicator: LiveData<Boolean> = _indicator

    fun login(username: String, pass: String): Completable {
        return repository.login(username, pass)
    }

    fun getUserInfo() {
        _indicator.value=false
        repository.getUser()
            .doFinally{
                _indicator.value=true
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<User> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }
                override fun onSuccess(t: User) {
                    _userViewModel.value = t
                }
                override fun onError(e: Throwable) {
                    Log.d(mTAG,e.message.toString())
                }
            })
    }

    /**
     * This method clears the sharedPreferences
     */
    fun removeToken() {
        TokenContainer.update(null, null)
        sharedPreferences.edit().apply {
            putString("access_token", null)
            putString("refresh_token", null)
        }.apply()
    }

}