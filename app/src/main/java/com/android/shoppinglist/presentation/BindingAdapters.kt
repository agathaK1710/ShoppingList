package com.android.shoppinglist.presentation

import androidx.databinding.BindingAdapter
import com.android.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorInputName")
fun bindingErrorInputName(textInputLayout: TextInputLayout, errorInputName: Boolean) {
    val message = if (errorInputName) {
        textInputLayout.context.getString(R.string.error_input_name)
    } else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("errorInputCount")
fun bindingErrorInputCount(textInputLayout: TextInputLayout, errorInputCount: Boolean) {
    val message = if (errorInputCount) {
        textInputLayout.context.getString(R.string.error_input_count)
    } else {
        null
    }
    textInputLayout.error = message
}