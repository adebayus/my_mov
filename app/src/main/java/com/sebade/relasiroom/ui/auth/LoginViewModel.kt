package com.sebade.relasiroom.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.sebade.relasiroom.database.DatabaseMov
import com.sebade.relasiroom.database.modeldatabase.UserTable
import com.sebade.relasiroom.network.ApiConfig
import com.sebade.relasiroom.network.NetworkResponse
import com.sebade.relasiroom.network.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

//import com.sebade.mymovieticket.database.DatabaseMyMovie
//import com.sebade.mymovieticket.network.responsemodel.UserItem
//import com.sebade.mymovieticket.model.UserItem

//import com.sebade.mymovieticket.network.ApiConfig
//import com.sebade.mymovieticket.network.responsemodel.NetworkResponse

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    private val database = DatabaseMov.getDatabase(application)

    val navigatedToSignUpFragment = MutableLiveData<Boolean>()

    private var _usernameIsValid = MediatorLiveData<Boolean>().apply {
        value = true
        addSource(username) {
            value = true
        }
    }
    var usernameIsValid: LiveData<Boolean> = _usernameIsValid

    private var _passwordIsValid = MediatorLiveData<Boolean>().apply {
        value = true
        addSource(password) {
            value = true
        }

    }
    var passwordIsValid: LiveData<Boolean> = _passwordIsValid

    private val _progressbarIsShow = MutableLiveData<Boolean>()
    var progressbarIsShow: LiveData<Boolean> = _progressbarIsShow


    private fun validateform(userName: String?, passWord: String?): Boolean {
        val isValidUsername =
            userName != null && userName.isNotBlank()
        val isValidPass =
            passWord != null && passWord.isNotBlank()

        _usernameIsValid.value = isValidUsername
        _passwordIsValid.value = isValidPass

        return isValidUsername && isValidPass
    }

    private val _isUserFound = MutableLiveData<Boolean>()
    var isUserFound: LiveData<Boolean> = _isUserFound

    fun btnSignIn() {
        /*var result = usernameIsValid.value == true && passwordIsValid.value == true*/
        _progressbarIsShow.value = true
        if (validateform(username.value, password.value)) {
            Log.d("TAG", "btnSignIn: ")
            val client = ApiConfig.retrofitService.getAllData()
            client.enqueue(object : retrofit2.Callback<NetworkResponse> {
                override fun onResponse(
                    call: Call<NetworkResponse>,
                    response: Response<NetworkResponse>
                ) {
                    if (response.isSuccessful) {
                        var userList: List<UserItem?>? = response.body()?.user
                        Log.d("TAG", "onResponse: $userList ")
                        userList!!.let {
                            for (item in userList) {
                                val usernameFound = item?.username == username.value
                                val passwordFound = item?.password == password.value
                                if (usernameFound && passwordFound) {
                                    Log.d("testUSer", "onResponse: $item")
                                    item?.let {
                                        insertToTableUser(it)
                                    }
                                    break
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<NetworkResponse>, t: Throwable) {
                    Log.d("TAG", "onFailure: ${t.message}")
                    _progressbarIsShow.value = false
                }

            })
        } else {
            _progressbarIsShow.value = false
        }

    }

    private fun insertToTableUser(userItem: UserItem) {
        with(userItem) {
            Log.d("testUSer", "onResponse: $this")
            val userTableModel = username?.let { UserTable( password, nama, saldo, email, url, it) }
            viewModelScope.launch(Dispatchers.IO) {
                if (userTableModel != null) {
                    database.databaseDao.insertUser(userTableModel)
                }
                withContext(Dispatchers.Main) {
                    _isUserFound.value = true
                }
            }
        }
    }

    fun btnSignUpOnClick() {
        navigatedToSignUpFragment.value = true
    }

    fun navigatedHomepageDone() {
        _isUserFound.value = false
        _progressbarIsShow.value = false
    }

}


//fun isValid() {
//    validateform(username.value, password.value)
//}

//    private var _usernameIsError = MutableLiveData<Boolean>()
//    var usernameIsError: LiveData<Boolean> = _usernameIsError
//    private var _passwordIsError = MutableLiveData<Boolean>()
//    var passwordIsError: LiveData<Boolean> = _passwordIsError


//    private val _isValidForm = MediatorLiveData<Boolean>().apply {
////        value = false
//        addSource(username) { user_name ->
//            val pass_word = password.value
//            _usernameIsError.value = true
////            value = validateform(user_name, pass_word)
////           validateform(user_name, pass_word)
//            Log.d("TAG", "addSource: test")
//        }
//
//        addSource(password) { pass_word ->
//            val user_name = username.value
////            value = validateform(pass_word, user_name)
//            validateform(pass_word, user_name)
//        }
//    }
//    var isValidForm: LiveData<Boolean> = _isValidForm

//    fun onTouchFunction() {
//        _usernameIsError.value == true
//    }


//    private fun validated(): Boolean {
//        if(username.value == "" && password.value == ""){
//            return false
//        } else if (username.value == "")
//    }

//    private fun checkUser(bodyResponse: List<UserItem?>?) {
//        for (x in bodyResponse!!) {
//            Log.d("TAG", "checkUser: $x")
//            if (x?.username == username.value && x?.password == password.value) {
//                Log.d("TAG", "checkUser: isFound ")
//            } else {
//                Log.d("TAG", "checkUser: notFound")
//            }
//        }
//    }