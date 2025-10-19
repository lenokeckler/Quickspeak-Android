package com.quickspeak.mobile.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * NAVIGATION DRAWER CONTENT
 * This is what appears inside the sliding sidebar
 */
@Composable
fun NavigationDrawerContent(
    currentScreen: String = "Tester",
    onNavigate: (String) -> Unit = {},
    onLogout: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        DrawerHeader()
        Spacer(modifier = Modifier.height(20.dp))

        // MENU ITEMS LIST
        val menuItems = listOf(
            DrawerItem("Speakers", Icons.Default.VolumeUp),
            DrawerItem("Dictionary", Icons.Default.Book),
            DrawerItem("Languages", Icons.Default.Language),
            DrawerItem("Profile", Icons.Default.AccountCircle)
        )

        // CREATE MENU ITEMS WITH SELECTION LOGIC
        menuItems.forEach { item ->
            DrawerMenuItem(
                item = item,
                isSelected = currentScreen == item.title,
                onClick = {
                    onNavigate(item.title)
                }
            )
        }

        // SPACER TO PUSH BOTTOM ITEMS DOWN
        Spacer(modifier = Modifier.weight(1f))

        // BOTTOM MENU ITEMS
        DrawerMenuItem(
            item = DrawerItem("Settings", Icons.Default.Settings),
            isSelected = currentScreen == "Settings",
            onClick = { onNavigate("Settings") }
        )
        DrawerMenuItem(
            item = DrawerItem("Logout", Icons.Default.ExitToApp),
            isSelected = false,
            onClick = { /* TODO: Handle logout */ }
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

/**
 * DRAWER HEADER
 * Top section of drawer with app name
 */
@Composable
private fun DrawerHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 40.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "QuickSpeak",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * INDIVIDUAL DRAWER MENU ITEM
 */
@Composable
private fun DrawerMenuItem(
    item: DrawerItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp)
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(size = 10.dp)
            )
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = if (isSelected) {
                MaterialTheme.colorScheme.onSecondary
            } else {
                MaterialTheme.colorScheme.onBackground
            },
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = item.title,
            color = if (isSelected) {
                MaterialTheme.colorScheme.onSecondary
            } else {
                MaterialTheme.colorScheme.onBackground
            },
            fontSize = 24.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

/**
 * DATA CLASS FOR DRAWER ITEMS
 */
data class DrawerItem(
    val title: String,
    val icon: ImageVector
)

/**
 * PREVIEW FUNCTIONS
 */
@Preview(showBackground = true, backgroundColor = 0xFF1C1C1E)
@Composable
fun NavigationDrawerContentPreview() {
    MaterialTheme {
        NavigationDrawerContent()
    }
}