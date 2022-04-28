package com.sebade.relasiroom.utils

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.sebade.relasiroom.R
import com.sebade.relasiroom.model.FormValidation
import com.sebade.relasiroom.model.HistoryFilm

@BindingAdapter("app:validationInput")
fun validtationIsError(
    textInputLayout: TextInputLayout,
    validationInput: Boolean
) {
    if (!validationInput) {
        textInputLayout.isHelperTextEnabled = validationInput
        textInputLayout.helperText = "Form Ini Tidak Boleh Kosong"
        textInputLayout.setHelperTextColor(
            AppCompatResources.getColorStateList(
                textInputLayout.context,
                R.color.set_stroke_selector_error
            )
        )
    } else {
        textInputLayout.isHelperTextEnabled = false
        textInputLayout.helperText = null
    }
}

@BindingAdapter("app:formValidation", requireAll = false)
fun validFormSignUp(
    textInputLayout: TextInputLayout,
    formValidation: FormValidation?
) {
    if (formValidation != null) {
        textInputLayout.isHelperTextEnabled = formValidation.isErr!!
        textInputLayout.helperText = formValidation.errorText
        textInputLayout.setHelperTextColor(
            AppCompatResources.getColorStateList(
                textInputLayout.context,
                R.color.set_stroke_selector_error
            )
        )
    }
}

@BindingAdapter("isEnabled")
fun signUpFormButton(
    materialButton: MaterialButton,
    isValid: Boolean?
) {


    when (isValid) {
        true -> {

            materialButton.isEnabled = isValid
            materialButton.background = AppCompatResources.getDrawable(
                materialButton.context,
                R.drawable.background_pink_corner_round_button
            )
            materialButton.setTextColor(
                AppCompatResources.getColorStateList(
                    materialButton.context,
                    R.color.white
                )
            )
        }
        else -> {
            materialButton.isEnabled = false
            materialButton.setTextColor(
                AppCompatResources.getColorStateList(
                    materialButton.context,
                    R.color.gray
                )
            )
            materialButton.background = AppCompatResources.getDrawable(
                materialButton.context,
                R.drawable.background_gray_corner_round_button
            )
        }
    }

}

@SuppressLint("SetTextI18n")
@BindingAdapter("setNumberToRp")
fun setNumberToRp(tv: TextView, number: String?) {
    if (!number.isNullOrEmpty()) {
//        val myNumber = NumberFormat.getNumberInstance(Locale.US).format(number?.toInt())
        val myNumber = "%,d".format(number.toInt())
        tv.text = "IDR $myNumber"
    }
}


@BindingAdapter("setVisibility")
fun setVisibility(constraintLayout: ConstraintLayout, isShown: Boolean) {
    if (isShown) {
        constraintLayout.visibility = View.VISIBLE
    } else {
        constraintLayout.visibility = View.GONE
    }

}

@BindingAdapter("setImage")
fun setImage(imageView: ImageView, poster: String?) {
    Log.d("TAG", "setImage: ${imageView.id == R.id.iv_poster} ")
    if (!poster.isNullOrEmpty()) {
        Glide.with(imageView.context)
            .load(poster)
            .into(imageView)
    }
}

@BindingAdapter("setIcon")
fun setUploadFotoIcon(imageView: ImageView, isUpload :Boolean?){
    if (isUpload == false){
        imageView.setImageResource(R.drawable.btn_upload)
    }else if (isUpload == true){
        imageView.setImageResource(R.drawable.ic_btn_delete_24)
    }
}

@BindingAdapter("setImageUri")
fun setImageUri(imageView: ImageView, poster: Uri?) {
    Log.d("TAG", "setImage: ${imageView.id == R.id.iv_poster} ")
    if (poster != null) {
        Glide.with(imageView.context)
            .load(poster)
            .into(imageView)
    }
}

@BindingAdapter("app:setDrawable")
fun setDrawable(imageView: ImageView, status: Int) {
    if (status == 1) {
        imageView.setImageResource(R.drawable.kursi_terisi)
    } else if (status == 0) {
        imageView.setImageResource(R.drawable.kursi_terpilih)
    } else {
        imageView.setImageResource(R.drawable.kursi_kosong)
    }
}

/** Start CheckoutFragment */

@BindingAdapter("app:setCheckoutButton")
fun setCheckoutButton(materialButton: MaterialButton, saldoCukup: Boolean) {
    val progres = CircularProgressDrawable(materialButton.context).apply {
        setStyle(CircularProgressDrawable.DEFAULT)
        start()
    }
    if (saldoCukup) {
        materialButton.setBackgroundResource(R.drawable.background_pink_corner_round_button)
        materialButton.text = "BAYAR SEKARANG"
        //materialButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, progres, null, null)

    } else {
        //materialButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, progres, null, null)
        materialButton.setBackgroundResource(R.drawable.background_gray_corner_round_button)
        materialButton.isEnabled = true
        materialButton.text = "Saldo Belum Cukup"
    }
}

@BindingAdapter("app:checkOutTextSaldo")
fun checkOutTextSaldo(tv: TextView, saldoCukup: Boolean) {
    if (saldoCukup) {
        tv.setTextColor(
            AppCompatResources.getColorStateList(
                tv.context,
                R.color.green
            )
        )
    } else {
        tv.setTextColor(
            AppCompatResources.getColorStateList(
                tv.context,
                R.color.red
            )
        )
    }
}

@BindingAdapter("app:checkoutProgessvisibility")
fun checkoutProgessvisibility(fl: FrameLayout, isShown: Boolean) {
    if (isShown) {
        fl.visibility = View.VISIBLE
    } else {
        fl.visibility = View.GONE
    }
}

/** Stop CheckoutFragment */

@BindingAdapter("app:setBackgroundButton")
fun setBackgroundButton(materialButton: MaterialButton, size: Int) {
    if (size > 0) {
        materialButton.setTextColor(
            AppCompatResources.getColorStateList(
                materialButton.context,
                R.color.white
            )
        )
        materialButton.setBackgroundResource(R.drawable.background_pink_corner_round_button)
        materialButton.isEnabled = true

    } else {
        materialButton.setTextColor(
            AppCompatResources.getColorStateList(
                materialButton.context,
                R.color.grayLight
            )
        )
        materialButton.setBackgroundResource(R.drawable.background_gray_corner_round_button)
        materialButton.isEnabled = false
    }
}

/***/
@BindingAdapter("app:sizeList")
fun sizeList(textView: TextView, list: List<HistoryFilm>) {
    if (!list.isNullOrEmpty()) {
        textView.text = "${list.size} Film"
    }
}
/***/