package com.example.bluetoothsample

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.view.KeyEvent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BluetoothDisabled
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.bluetoothsample.viewmodel.DeckViewModel
import com.example.bluetoothsample.viewmodel.ShortcutViewModel

@Composable
fun Popup(
    onDismiss: () -> Unit,
    viewModel: DeckViewModel // Ajoutez votre ViewModel ici
) {
    var textValue by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Nom du nouveau deck :")
                TextField(
                    value = textValue,
                    onValueChange = { textValue = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        // Ajouter le nouveau deck à la base de données
                        if (textValue.isNotBlank()) {
                            viewModel.addDeck(textValue)
                        }
                        // Fermer la popup
                        onDismiss()
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Ajouter")
                }
            }
        }
    }
}

@Composable
fun PopupCreateShortcut(
    onDismiss: () -> Unit,
    shortcutViewModel: ShortcutViewModel,
    deck: Deck
) {
    val KeyEventMap = mapOf(
        KeyEvent.KEYCODE_Q to 4,
        KeyEvent.KEYCODE_B to 5,
        KeyEvent.KEYCODE_C to 6,
        KeyEvent.KEYCODE_D to 7,
        KeyEvent.KEYCODE_E to 8,
        KeyEvent.KEYCODE_F to 9,
        KeyEvent.KEYCODE_G to 10,
        KeyEvent.KEYCODE_H to 11,
        KeyEvent.KEYCODE_I to 12,
        KeyEvent.KEYCODE_J to 13,
        KeyEvent.KEYCODE_K to 14,
        KeyEvent.KEYCODE_L to 15,
        KeyEvent.KEYCODE_COMMA to 16,
        KeyEvent.KEYCODE_N to 17,
        KeyEvent.KEYCODE_O to 18,
        KeyEvent.KEYCODE_P to 19,
        KeyEvent.KEYCODE_A to 20,
        KeyEvent.KEYCODE_R to 21,
        KeyEvent.KEYCODE_S to 22,
        KeyEvent.KEYCODE_T to 23,
        KeyEvent.KEYCODE_U to 24,
        KeyEvent.KEYCODE_V to 25,
        KeyEvent.KEYCODE_Z to 26,
        KeyEvent.KEYCODE_X to 27,
        KeyEvent.KEYCODE_Y to 28,
        KeyEvent.KEYCODE_W to 29,


        KeyEvent.KEYCODE_1 to 30,
        KeyEvent.KEYCODE_2 to 31,
        KeyEvent.KEYCODE_3 to 32,
        KeyEvent.KEYCODE_4 to 33,
        KeyEvent.KEYCODE_5 to 34,
        KeyEvent.KEYCODE_6 to 35,
        KeyEvent.KEYCODE_7 to 36,
        KeyEvent.KEYCODE_8 to 37,
        KeyEvent.KEYCODE_9 to 38,
        KeyEvent.KEYCODE_0 to 39,

        KeyEvent.KEYCODE_SPACE to 44,

        KeyEvent.KEYCODE_F1 to 58,
        KeyEvent.KEYCODE_F2 to 59,
        KeyEvent.KEYCODE_F3 to 60,
        KeyEvent.KEYCODE_F4 to 61,
        KeyEvent.KEYCODE_F5 to 62,
        KeyEvent.KEYCODE_F6 to 63,
        KeyEvent.KEYCODE_F7 to 64,
        KeyEvent.KEYCODE_F8 to 65,
        KeyEvent.KEYCODE_F9 to 66,
        KeyEvent.KEYCODE_F10 to 67,
        KeyEvent.KEYCODE_F11 to 68,
        KeyEvent.KEYCODE_F12 to 69,

        //TBC
        //KeyEvent.KEYCODE_ENTER to 40,
        //KeyEvent.KEYCODE_ESCAPE to 41,
        //KeyEvent.KEYCODE_DEL to 42,
        //KeyEvent.KEYCODE_TAB to 43,
        //KeyEvent.KEYCODE_SPACE to 44,
        KeyEvent.KEYCODE_MINUS to 35,
        //KeyEvent.KEYCODE_EQUALS to 46,
        KeyEvent.KEYCODE_LEFT_BRACKET to 34,
        KeyEvent.KEYCODE_RIGHT_BRACKET to 45,
        //KeyEvent.KEYCODE_BACKSLASH to 49,
        //KeyEvent.KEYCODE_POUND to 50,
        KeyEvent.KEYCODE_M to 51,
        //KeyEvent.KEYCODE_APOSTROPHE to 52,
        //KeyEvent.KEYCODE_GRAVE to 53,
        KeyEvent.KEYCODE_SEMICOLON to 54,
        //KeyEvent.KEYCODE_PERIOD to 55,
        //KeyEvent.KEYCODE_SLASH to 56,

        /*KeyEvent.KEYCODE_SCROLL_LOCK to 71,
        KeyEvent.KEYCODE_INSERT to 73,
        KeyEvent.KEYCODE_HOME to 74,
        KeyEvent.KEYCODE_PAGE_UP to 75,
        KeyEvent.KEYCODE_FORWARD_DEL to 76,
        KeyEvent.KEYCODE_MOVE_END to 77,
        KeyEvent.KEYCODE_PAGE_DOWN to 78,
        KeyEvent.KEYCODE_NUM_LOCK to 83,

        KeyEvent.KEYCODE_DPAD_RIGHT to 79,
        KeyEvent.KEYCODE_DPAD_LEFT to 80,
        KeyEvent.KEYCODE_DPAD_DOWN to 81,
        KeyEvent.KEYCODE_DPAD_UP to 82,

        // special key for FR MAC keyboard '@', spend a while to figure it out
        KeyEvent.KEYCODE_AT to 100,*/
    )
    var isOpenSelect by remember { mutableStateOf(false) }
    var selectedKeyEvent by remember { mutableStateOf("Liste key code") }
    var selectedKeyCode = -1

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Ajouter un key code :")
                OutlinedButton(
                    onClick = { isOpenSelect = true },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = selectedKeyEvent)
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                    }
                }
                DropdownMenu(
                    expanded = isOpenSelect,
                    onDismissRequest = { isOpenSelect = false },
                    modifier = Modifier.padding(8.dp)
                ) {
                    KeyEventMap.toList().forEach { (keyEvent, code) ->
                        DropdownMenuItem(text = {
                            Text(text = KeyEvent.keyCodeToString(keyEvent))
                        }, onClick = {
                            selectedKeyEvent = KeyEvent.keyCodeToString(keyEvent)
                            selectedKeyCode = code
                            isOpenSelect = false
                        })
                    }
                }

                Button(
                    onClick = {
                        // Ajouter le nouveau deck à la base de données
                        if (selectedKeyCode > 0) {
                            shortcutViewModel.addShortcut(selectedKeyCode, deck.deckId)
                            onDismiss()
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Ajouter")
                }
            }
        }
    }
}

@Composable
fun BluetoothUiConnection(bluetoothController: BluetoothController) {
    val context = LocalContext.current
    var isButtonInitVisible by remember { mutableStateOf(true) }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = CenterHorizontally) {
    }
        if (isButtonInitVisible) {
            Button(
                onClick = { bluetoothController.init(context.applicationContext)
                            isButtonInitVisible = false }
            ) {
                Text(text = "Initialize Bluetooth device with HID profile")
            }
        }
        else {

            val btOn = bluetoothController.status is BluetoothController.Status.Connected
            if(!btOn) {
                Button(
                    onClick = { context.startActivity(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)) }
                ) {
                    Text(text = "discover and Pair new devices")
                }
            }
            val waiting = bluetoothController.status is BluetoothController.Status.Waiting
            val disconnected = bluetoothController.status is BluetoothController.Status.Disconnected
            if (waiting or disconnected) {
                Button(
                    onClick = { bluetoothController.connectHost() }
                ) {
                    Text(text = "Bluetooth connect to host")
                }
            }
            Text(
                //modifier=Modifier.align(Alignment.CenterHorizontally),
                text = bluetoothController.status.display,

            )

            Icon(
                if (btOn) Icons.Default.Bluetooth else Icons.Default.BluetoothDisabled,
                "bluetooth",
                modifier = Modifier.size(100.dp),
                tint = if (btOn) Color.Blue else Color.Black,
            )
            if (btOn) {
                Button(
                    onClick = { bluetoothController.release()}
                ) {
                    Text(text = "Bluetooth disconnect from host")
                }
            }


        }
}

@Composable
fun BluetoothDesk(bluetoothController: BluetoothController, viewModel: DeckViewModel, shortcutViewModel: ShortcutViewModel) {

    var isPopupVisible by remember { mutableStateOf(false) }
    var isPopupCreateShortcut by remember { mutableStateOf(false) }
    var isOpenSelect by remember { mutableStateOf(false) }
    val connected = bluetoothController.status as? BluetoothController.Status.Connected ?: return
    val context = LocalContext.current
    val keyboardSender = KeyboardSender(connected.btHidDevice, connected.hostDevice)

    // init list deck
    val allDecks by viewModel.allDecks.observeAsState(emptyList())
    var selectedItem by remember { mutableStateOf<Deck?>(null) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp), horizontalAlignment = CenterHorizontally
    ) {
        // Bouton pour ouvrir la popup et Bouton pour ouvrir le select sur la même ligne
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Bouton pour ouvrir le select
            Button(
                onClick = { isOpenSelect = true }
            ) {
                Text("Choisir son deck")
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bouton pour ajouter un nouveau Deck
            Button(
                onClick = { isPopupVisible = true }
            ) {
                Text("Ajouter un deck")
            }
        }

        // Affichage de la popup si nécessaire
        if (isPopupVisible) {
            Popup(
                onDismiss = {
                    isPopupVisible = false
                },
                viewModel = viewModel
            )
        }

        if (isPopupCreateShortcut) {
            selectedItem?.let {
                PopupCreateShortcut(
                    onDismiss = {
                        isPopupCreateShortcut = false
                    },
                    shortcutViewModel,
                    it
                )
            }
        }



        // init selectedItem with last create
        LaunchedEffect(allDecks) {
            selectedItem = allDecks.lastOrNull()
        }

        // DropdownMenu pour choisir le deck
        Column(Modifier.fillMaxWidth(), horizontalAlignment = CenterHorizontally) {
            DropdownMenu(
                expanded = isOpenSelect,
                onDismissRequest = { isOpenSelect = false },
                modifier = Modifier.padding(8.dp)
            ) {
                if (allDecks.isEmpty()) {
                    DropdownMenuItem(text = {
                        Text(text = "Aucun deck" )
                    }, onClick = {
                        isOpenSelect = false
                    })
                } else {
                    allDecks.forEach { item ->
                        DropdownMenuItem(text = {
                            Text(text = item.label)
                        }, onClick = {
                            selectedItem = item
                            isOpenSelect = false
                        })
                    }
                }
            }
        }
    }


    fun press(shortcut: Shortcut, releaseModifiers: Boolean = true) {
        @SuppressLint("MissingPermission")
        val result = keyboardSender.sendKeyboard(shortcut.shortcutKey, shortcut.modifiers, releaseModifiers)
        if (!result) Toast.makeText(context,"can't find keymap for $shortcut",Toast.LENGTH_LONG).show()
    }

    fun alphanum (){
        press(Shortcut(KeyEvent.KEYCODE_A))
        press(Shortcut(KeyEvent.KEYCODE_B))
        press(Shortcut(KeyEvent.KEYCODE_C))
        press(Shortcut(KeyEvent.KEYCODE_D))
        press(Shortcut(KeyEvent.KEYCODE_E))
        press(Shortcut(KeyEvent.KEYCODE_F))
        press(Shortcut(KeyEvent.KEYCODE_G))
        press(Shortcut(KeyEvent.KEYCODE_H))
        press(Shortcut(KeyEvent.KEYCODE_I))
        press(Shortcut(KeyEvent.KEYCODE_J))
        press(Shortcut(KeyEvent.KEYCODE_K))
        press(Shortcut(KeyEvent.KEYCODE_L))
        press(Shortcut(KeyEvent.KEYCODE_M))
        press(Shortcut(KeyEvent.KEYCODE_N))
        press(Shortcut(KeyEvent.KEYCODE_O))
        press(Shortcut(KeyEvent.KEYCODE_P))
        press(Shortcut(KeyEvent.KEYCODE_Q))
        press(Shortcut(KeyEvent.KEYCODE_R))
        press(Shortcut(KeyEvent.KEYCODE_S))
        press(Shortcut(KeyEvent.KEYCODE_T))
        press(Shortcut(KeyEvent.KEYCODE_U))
        press(Shortcut(KeyEvent.KEYCODE_V))
        press(Shortcut(KeyEvent.KEYCODE_W))
        press(Shortcut(KeyEvent.KEYCODE_X))
        press(Shortcut(KeyEvent.KEYCODE_Y))
        press(Shortcut(KeyEvent.KEYCODE_Z))

        press(Shortcut(KeyEvent.KEYCODE_SPACE))

        press(Shortcut(KeyEvent.KEYCODE_1,listOf(Shortcut.LEFT_SHIFT,Shortcut.RIGHT_SHIFT)))
        press(Shortcut(KeyEvent.KEYCODE_2,listOf(Shortcut.LEFT_SHIFT,Shortcut.RIGHT_SHIFT)))
        press(Shortcut(KeyEvent.KEYCODE_3,listOf(Shortcut.LEFT_SHIFT,Shortcut.RIGHT_SHIFT)))
        press(Shortcut(KeyEvent.KEYCODE_4,listOf(Shortcut.LEFT_SHIFT,Shortcut.RIGHT_SHIFT)))
        press(Shortcut(KeyEvent.KEYCODE_5,listOf(Shortcut.LEFT_SHIFT,Shortcut.RIGHT_SHIFT)))
        press(Shortcut(KeyEvent.KEYCODE_6,listOf(Shortcut.LEFT_SHIFT,Shortcut.RIGHT_SHIFT)))
        press(Shortcut(KeyEvent.KEYCODE_7,listOf(Shortcut.LEFT_SHIFT,Shortcut.RIGHT_SHIFT)))
        press(Shortcut(KeyEvent.KEYCODE_8,listOf(Shortcut.LEFT_SHIFT,Shortcut.RIGHT_SHIFT)))
        press(Shortcut(KeyEvent.KEYCODE_9,listOf(Shortcut.LEFT_SHIFT,Shortcut.RIGHT_SHIFT)))

      }

    Column( modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)) {

        Spacer(modifier = Modifier.size(20.dp))

        Row(modifier = Modifier
            .align(CenterHorizontally)) {
            if(selectedItem !== null) {
                Text(text = selectedItem!!.label, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            } else {
                Text(text = "Aucun deck selectionner", fontStyle = FontStyle.Italic, fontSize = 24.sp)
            }
        }

        Spacer(modifier = Modifier.size(10.dp))
    }

    Spacer(modifier = Modifier.padding(48.dp))

    Button(
        onClick = { isPopupCreateShortcut = true },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Add shortcut")
    }

    Spacer(modifier = Modifier.padding(48.dp))

    Button(
        onClick = { viewModel.deleteAllDecks() },
        modifier = Modifier.fillMaxWidth() // étend le bouton sur toute la largeur
    ) {
        Text(text = "Clear all decks")
    }
}
