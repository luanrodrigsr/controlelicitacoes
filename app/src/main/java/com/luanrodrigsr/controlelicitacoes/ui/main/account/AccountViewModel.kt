package com.luanrodrigsr.controlelicitacoes.ui.main.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccountViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Esta parte do aplicativo ainda está em desenvolvimento."
    }
    val text: LiveData<String> = _text
}