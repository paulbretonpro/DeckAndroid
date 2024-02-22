package com.example.bluetoothsample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bluetoothsample.DeckDao
import com.example.bluetoothsample.ShortcutDao
import com.example.bluetoothsample.ShortcutbydeckDao

class ShortcutViewModelFactory(private val shortcutDao: ShortcutDao, private val shortcutbydeckDao: ShortcutbydeckDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShortcutViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShortcutViewModel(shortcutDao, shortcutbydeckDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
