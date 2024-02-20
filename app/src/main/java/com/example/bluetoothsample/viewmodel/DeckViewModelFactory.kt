package com.example.bluetoothsample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bluetoothsample.DeckDao

class DeckViewModelFactory(private val deckDao: DeckDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeckViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeckViewModel(deckDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
