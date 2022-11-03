package com.aajeevika.collectioncenter.utility

import android.R
import android.graphics.drawable.Drawable
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.util.ArrayList

@BindingAdapter("app:disableSpace")
fun EditText.disableSpace(value: Boolean?) {
    if (value == true) {
        this.doOnTextChanged { text, _, _, _ ->
            if(text?.contains(" ") == true)
                this.setText(text.toString().replace(" ", ""))
        }
    }
}

@BindingAdapter(value = ["app:loadImageFromNetwork", "app:placeholder"], requireAll = false)
fun ImageView.loadImageFromNetwork(image: String?, placeholder: Drawable?) {
    if(!image.isNullOrEmpty()) {
        val requestBuilder = Glide.with(this).load(image)
        placeholder?.run { requestBuilder.placeholder(this) }
        requestBuilder.into(this)
    } else {
        setImageDrawable(placeholder)
    }
}

@BindingAdapter("app:camelCaseText")
fun TextView.camelCaseText(value: String?) {
    value?.let {
        val finalValue = StringBuilder()

        it.trim().split(" ").forEach { splitText ->
            if (splitText.isNotEmpty()) finalValue.append(splitText[0].uppercaseChar() + splitText.substring(1)).append(" ")
        }

        text = finalValue.trim().toString()
    }
}

@BindingAdapter("app:dropDownMenu")
fun AutoCompleteTextView.enableDropDown(menu: ArrayList<String>?) {
    menu?.let {
        val adapter = ArrayAdapter(this.context, R.layout.simple_list_item_1, it)
        this.setAdapter(adapter)
        this.threshold = 1
        this.setOnClickListener {
            UtilityActions.closeKeyboard(context, this)
            this.showDropDown()
        }
    }
}