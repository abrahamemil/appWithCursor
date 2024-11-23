package com.example.mysrib_cursor.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "SRIB Contacts is under construction"
    }
    val text: LiveData<String> = _text
}