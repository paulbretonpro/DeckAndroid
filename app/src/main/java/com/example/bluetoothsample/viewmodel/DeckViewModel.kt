package com.example.bluetoothsample.viewmodel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothsample.Deck
import com.example.bluetoothsample.DeckDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeckViewModel(private val deckDao: DeckDao) : ViewModel() {
    val allDecks: LiveData<List<Deck>> = deckDao.getAll()

    // Méthode pour ajouter un decks
    fun addDeck(deckName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deck = Deck(label = deckName)
                deckDao.insert(deck)
            } catch (e: Exception) {
                Log.e("DEBUG", "Erreur lors de l'ajout du deck : ${e.message}")
            }
        }
    }

    // Méthode pour supprimer tous les decks
    fun deleteAllDecks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deckDao.deleteAllDeck()
            } catch (e: Exception) {
                Log.e("DEBUG", "Erreur lors de la suppression de tous les decks : ${e.message}")
            }
        }
    }
}