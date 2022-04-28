package com.sebade.relasiroom.ui.auth.signup

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.sebade.relasiroom.model.BackdropState
import com.sebade.relasiroom.model.FormValidation
import com.sebade.relasiroom.model.SignUpFormModel
import com.sebade.relasiroom.model.SignUpResponse
import com.sebade.relasiroom.network.ApiConfig
import com.sebade.relasiroom.network.UserItem
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response

class SignUpViewModel(val app: Application) : AndroidViewModel(app) {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val nama = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    val signUpFormModel = SignUpFormModel()
    val formValidation = MediatorLiveData<SignUpFormModel>().apply {

        addSource(password) {
            setValidation(password = true)
            Log.d("form", ": asdadasd ")
            value = signUpFormModel
        }
        addSource(username) {
            setValidation(username = true)
            Log.d("form", ": asdadasd ")
            value = signUpFormModel
        }
        addSource(nama) {
            setValidation(nama = true)
            Log.d("form", ": asdadasd ")
            value = signUpFormModel
        }
        addSource(email) {
            setValidation(email = true)
            Log.d("form", ": asdadasd ")
            value = signUpFormModel

        }
    }

    val passwordValid = Transformations.switchMap(formValidation) {
        MutableLiveData<FormValidation>(formValidation.value?.password)
    }
    val usernameValid = Transformations.switchMap(formValidation) {
        MutableLiveData<FormValidation>(formValidation.value?.username)
    }
    val namaValid = Transformations.switchMap(formValidation) {
        MutableLiveData<FormValidation>(formValidation.value?.nama)
    }
    val emailValid = Transformations.switchMap(formValidation) {
        MutableLiveData<FormValidation>(formValidation.value?.email)
    }

    var buttonValid = MediatorLiveData<Boolean>().apply {
        addSource(passwordValid) {
            value = buttonValidation()
        }
        addSource(usernameValid) {
            value = buttonValidation()
        }
        addSource(namaValid) {
            value = buttonValidation()
        }
        addSource(emailValid) {
            value = buttonValidation()
        }
    }

    private fun buttonValidation(): Boolean {
        return (usernameValid.value?.isErr == false && passwordValid.value?.isErr == false
                && namaValid.value?.isErr == false && emailValid.value?.isErr == false)
    }

    private fun setValidation(
        username: Boolean? = null,
        password: Boolean? = null,
        nama: Boolean? = null,
        email: Boolean? = null
    ) {
        if (username == true)
        /** Username */
        {
            val formValidation = FormValidation()
            if (this.username.value!!.isEmpty()) {
                formValidation.isErr = true
                formValidation.errorText = "Username Tidak Boleh Kosong"
                signUpFormModel.username = formValidation
            } else {
                formValidation.isErr = false
                formValidation.errorText = ""
                signUpFormModel.username = formValidation
            }
        } else if (password == true)
        /** Password */
        {
            val formValidation = FormValidation()
            if (this.password.value!!.isEmpty()) {
                formValidation.isErr = true
                formValidation.errorText = "Password Tidak Boleh Kosong"
                signUpFormModel.password = formValidation
            } else {
                formValidation.isErr = false
                formValidation.errorText = ""
                signUpFormModel.password = formValidation
            }
        } else if (nama == true)
        /** Nama */
        {
            val formValidation = FormValidation()
            if (this.nama.value!!.isEmpty()) {
                formValidation.isErr = true
                formValidation.errorText = "Nama Tidak Boleh Kosong"
                signUpFormModel.nama = formValidation
            } else {
                formValidation.isErr = false
                formValidation.errorText = ""
                signUpFormModel.nama = formValidation
            }
        } else if (email == true)
        /** Email */
        {
            val formValidation = FormValidation()
            if (this.email.value!!.isEmpty()) {
                formValidation.isErr = true
                formValidation.errorText = "Email Tidak Boleh Kosong"
                signUpFormModel.email = formValidation
            } else {
                formValidation.isErr = false
                formValidation.errorText = ""
                signUpFormModel.email = formValidation
            }
        }
    }

    val navigatedToFoto = MutableLiveData<Boolean>()
    val isDaftarSuccess = MutableLiveData<SignUpResponse>()
    val backdoorLoading = MutableLiveData<BackdropState>()
    val newUser = MutableLiveData<UserItem>()

    fun daftarOnclick() {
        val client = ApiConfig.retrofitService.getUserData()
        backdoorLoading.value = BackdropState(true)
        client.enqueue(object : retrofit2.Callback<List<UserItem?>> {
            override fun onResponse(
                call: Call<List<UserItem?>>,
                response: Response<List<UserItem?>>
            ) {
                if (response.isSuccessful) {
                    var signUpResponse : SignUpResponse
                    val listUser = response.body()
                    if (listUser != null) {
                        signUpResponse = SignUpResponse()
                        for (index in listUser.indices){
                            val item = listUser[index]
                            val usernameFound = (item?.username == nama.value.toString())
                            val emailFound = (item?.email == email.value.toString())

                            if (usernameFound){
                                signUpResponse.isUsernameSame = usernameFound
                            }
                            if (emailFound){
                                signUpResponse.isEmailSame = emailFound
                            }
                            if ( usernameFound || emailFound){
                                signUpResponse.isSuccess = false
                                break
                            }
                            else {
                                signUpResponse.isSuccess = true
                            }
                        }
                        if (signUpResponse.isSuccess == true) {
                            Log.d("post", "onResponse: $signUpResponse ")
                            postUser(listUser)
                        }else {
                            backdoorLoading.value = BackdropState(false)
                            Log.d("post", "onResponse: $signUpResponse")
                        }
                    }else {
                        postUser(listUser)
                    }
                }
            }

            override fun onFailure(call: Call<List<UserItem?>>, t: Throwable) {
                Log.d("post", "onfail: ${t.message}")
            }

        })
    }

    private fun postUser(listUser: List<UserItem?>?) {
        val mutableList = listUser as MutableList<UserItem?>?
        val postUser = UserItem(
            nama = this.nama.value.toString(),
            password = this.password.value.toString(),
            username = this.username.value.toString(),
            email = this.email.value.toString(),
            saldo = "200000"
        )
        mutableList?.add(postUser)

        val toJson = Gson().toJson(mutableList as List<UserItem?>?)
        val requestBody =
            toJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        val client = ApiConfig.retrofitService.postUser(requestBody)
        client.enqueue(object : retrofit2.Callback<List<UserItem?>?> {
            override fun onResponse(
                call: Call<List<UserItem?>?>,
                response: Response<List<UserItem?>?>
            ) {
                Log.d("postUser", "onResponse: ${response.body()} ")
                backdoorLoading.value = BackdropState(false)
                newUser.value = postUser
                navigatedToFoto.value = true
            }

            override fun onFailure(call: Call<List<UserItem?>?>, t: Throwable) {
                Log.d("postUser", "onFailure: ${t.message} ")
                backdoorLoading.value = BackdropState(false)
            }

        })
    }
}