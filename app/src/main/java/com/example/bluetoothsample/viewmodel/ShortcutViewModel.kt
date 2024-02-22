package com.example.bluetoothsample.viewmodel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothsample.Deck
import com.example.bluetoothsample.Shortcut
import com.example.bluetoothsample.ShortcutDao
import com.example.bluetoothsample.Shortcutbydeck
import com.example.bluetoothsample.ShortcutbydeckDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShortcutViewModel(private val shortcutDao: ShortcutDao, private val shortcutbydeckDao: ShortcutbydeckDao) : ViewModel() {
    // Méthode pour créer un shortcut
    fun addShortcut(keyCode: Int, deckId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val shortcut = shortcutDao.insert(Shortcut(shortcutKey = keyCode))
                var scbd = shortcutbydeckDao.insert(Shortcutbydeck(deckId = deckId, shortcudId = shortcut))
            } catch (e: Exception) {
                Log.e("DEBUG", "Erreur lors de l'ajout du shortcut for deck : ${e.message}")
            }
        }
    }
}