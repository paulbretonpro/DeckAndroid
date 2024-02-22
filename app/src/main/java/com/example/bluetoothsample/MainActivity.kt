package com.example.bluetoothsample

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.bluetoothsample.viewmodel.DeckViewModel
import com.example.bluetoothsample.viewmodel.DeckViewModelFactory
import com.example.bluetoothsample.viewmodel.ShortcutViewModel
import com.example.bluetoothsample.viewmodel.ShortcutViewModelFactory

class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "MainActivity"
    }
    private lateinit var database: AppDataBase // Init database
    private lateinit var bluetoothController: BluetoothController

    // Init DeckViewModel
    private lateinit var deckViewModel: DeckViewModel
    // Init ShortcutViewModel
    private lateinit var shortcutViewModel: ShortcutViewModel

    private fun ensureBluetoothPermission(activity: ComponentActivity) {
        val requestPermissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted: Boolean ->
                if (isGranted) {Log.d(MainActivity.TAG, "Bluetooth connection granted")
                } else { Log.e(MainActivity.TAG, "Bluetooth connection not granted, Bye!")
                         activity.finish()
                }
        }

        requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_ADMIN)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        ensureBluetoothPermission(this)

        bluetoothController = BluetoothController()

        // Initialisation de la base de données Room
        database = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "my_database"
        ).build()

        // Initialisation DeckViewModel
        val deckDao = database.deckDao()
        deckViewModel = ViewModelProvider(this, DeckViewModelFactory(deckDao))[DeckViewModel::class.java]
        // Initialisation DeckViewModel
        val shortcutDao = database.shortcutDao()
        val shortcutbydeckDao = database.shortcutbydeckDao()
        shortcutViewModel = ViewModelProvider(this, ShortcutViewModelFactory(shortcutDao, shortcutbydeckDao))[ShortcutViewModel::class.java]

        setContent {
            Surface(modifier = Modifier.fillMaxSize().padding(16.dp), color = MaterialTheme.colorScheme.background) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    BluetoothUiConnection(bluetoothController)
                    BluetoothDesk(bluetoothController, deckViewModel, shortcutViewModel)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        bluetoothController.release()
    }
}

typealias KeyModifier = Int