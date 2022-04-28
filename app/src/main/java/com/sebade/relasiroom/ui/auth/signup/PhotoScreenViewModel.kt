package com.sebade.relasiroom.ui.auth.signup

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.sebade.relasiroom.database.DatabaseMov
import com.sebade.relasiroom.database.modeldatabase.UserTable
import com.sebade.relasiroom.model.BackdropState
import com.sebade.relasiroom.network.ApiConfig
import com.sebade.relasiroom.network.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import java.util.*

class PhotoScreenViewModel(val app: Application) : AndroidViewModel(app) {

    var storage = FirebaseStorage.getInstance()
    var sReference = storage.reference
    var database = DatabaseMov.getDatabase(app)

    var choosePhotoOnClick = MutableLiveData<Boolean>()
    var imageUri = MutableLiveData<Uri>()
    private var newUser = MutableLiveData<UserItem>()
    var newUserName = Transformations.switchMap(newUser) {
        MutableLiveData<String>("Welcome, \n${newUser.value?.nama.toString()}")
    }

    var isSetImage = MutableLiveData<Boolean>(false)
    var backDropLoading = MutableLiveData<BackdropState>(BackdropState(false))
    var moveToDashBoard = MutableLiveData<Boolean>()

    fun setNewUser(list: UserItem) {
        newUser.value = list
    }

    fun btnChoosePhoto() {
        choosePhotoOnClick.value = true
    }

    fun btnLanjutkanOnclick() {
        backDropLoading.value = BackdropState(true)
        if (isSetImage.value == true) {
            var ref = sReference.child("/images/new_folder/${UUID.randomUUID().toString()}")
            ref.putFile(imageUri.value!!)
                .addOnSuccessListener {
                    Log.d("photo", "btnLanjutkanOnclick: succes ")

                    ref.downloadUrl.addOnSuccessListener {
                        var uri = it
                        updateImage(uri)
                        Log.d("photo", "btnLanjutkanOnclick: $it")
                    }

                    /*backDropLoading.value = BackdropState(false)*/
                }
                .addOnFailureListener {
                    Log.d("photo", "btnLanjutkanOnclick: failure ")
                    backDropLoading.value = BackdropState(false)
                }
        } else {
            insertUserToTable(newUser.value!!)
        }
    }

    private fun updateImage(uri: Uri) {
        val client = ApiConfig.retrofitService.getUserData()
        client.enqueue(object : retrofit2.Callback<List<UserItem?>> {
            override fun onResponse(
                call: Call<List<UserItem?>>,
                response: Response<List<UserItem?>>
            ) {
                if (response.isSuccessful) {
                    Log.d("update photo", "onResponse: getUserBerhasil")
                    val values = response.body()
                    Log.d("update", "onResponse: $values")
                    val indexUser = values!!.indexOfFirst {
                        it?.username == newUser.value?.username
                    }
                    val newuserItem = values[indexUser]?.apply {
                        url = uri.toString()
                    }
                    val toJson = Gson().toJson(newuserItem)
                    val requestBody =
                        toJson.toRequestBody("application/json; charset=utf-8".toMediaType())
                    val newClient =
                        ApiConfig.retrofitService.updateSaldo(requestBody, indexUser.toString())
                    newClient.enqueue(object : retrofit2.Callback<UserItem> {
                        override fun onResponse(
                            call: Call<UserItem>,
                            response: Response<UserItem>
                        ) {
                            if (newuserItem != null) {
                                insertUserToTable(newuserItem)
                            }
                            Log.d("update photo", "onResponse: update berhasi ")
                        }

                        override fun onFailure(call: Call<UserItem>, t: Throwable) {
                            Log.d("update photo", "onFailure: update gagal")
                            backDropLoading.value = BackdropState(false)
                        }

                    })
                }
            }

            override fun onFailure(call: Call<List<UserItem?>>, t: Throwable) {
                Log.d("update", "onFailure: gagal ")
                backDropLoading.value = BackdropState(false)
            }

        })
    }

    private fun insertUserToTable(newuserItem: UserItem) {
        val toTableDomain =
            UserTable(
                newuserItem.password,
                newuserItem.nama,
                newuserItem.saldo,
                newuserItem.email,
                newuserItem.url,
                newuserItem.username!!
            )
        viewModelScope.launch(Dispatchers.IO){
            database.databaseDao.insertUser(toTableDomain)
            backDropLoading.postValue(BackdropState(false))
            moveToDashBoard.postValue(true)

        }
    }

    fun setUri(fileUri: Uri?) {
        imageUri.value = fileUri
    }


}