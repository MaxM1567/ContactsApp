package com.example.phonecontacts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContactList(contacts: List<Contact>, onContactClick: (Contact) -> Unit) {
    LazyColumn {
        items(contacts) { contact ->
            ContactItem(contact = contact, onClick = { onContactClick(contact) })
            Divider()
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = contact.name)
        Text(text = contact.number)
    }
}