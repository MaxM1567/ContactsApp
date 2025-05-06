package com.example.phonecontacts

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat

@Composable
fun ContactListScreen(context: Context) {
    val contacts = remember { mutableStateOf(listOf<Contact>()) }

    LaunchedEffect(Unit) {
        contacts.value = getContacts(context)
    }

    ContactList(contacts = contacts.value) { contact ->
        makeCall(context, contact.number)
    }
}

fun getContacts(context: Context): List<Contact> {
    val contacts = mutableListOf<Contact>()
    val resolver = context.contentResolver
    val cursor = resolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null, null, null, null
    )

    cursor?.use {
        val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

        while (it.moveToNext()) {
            val name = it.getString(nameIndex)
            val number = it.getString(numberIndex)
            contacts.add(Contact(name, number))
        }
    }
    return contacts
}


fun makeCall(context: Context, number: String) {
    val intent = Intent(Intent.ACTION_CALL).apply {
        data = Uri.parse("tel:$number")
    }
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
        == PackageManager.PERMISSION_GRANTED
    ) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Нет разрешения на звонок", Toast.LENGTH_SHORT).show()
    }
}