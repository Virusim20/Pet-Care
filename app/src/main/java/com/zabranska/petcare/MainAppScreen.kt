package com.zabranska.petcare

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

// Sealed Class для навігації
sealed class BottomNavItem(val title: String, val icon: ImageVector) {
    object Search : BottomNavItem("Search", Icons.Default.Search)
    object Appointments : BottomNavItem("Appointments", Icons.Default.DateRange)
    object Explore : BottomNavItem("Explore", Icons.Default.Explore)
    object Profile : BottomNavItem("Profile", Icons.Default.Person)
}

@Composable
fun MainAppScreen() {
    val items = listOf(
        BottomNavItem.Search,
        BottomNavItem.Appointments,
        BottomNavItem.Explore,
        BottomNavItem.Profile
    )

    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Search) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = selectedItem == item,
                        onClick = { selectedItem = item },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF3F51B5),
                            selectedTextColor = Color(0xFF3F51B5),
                            indicatorColor = Color(0xFFE8EAF6)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (selectedItem) {
                BottomNavItem.Search -> SearchScreen()     // Екран з сіткою (ЛР4/ЛР5)
                BottomNavItem.Appointments -> PlaceholderScreen("Appointments")
                BottomNavItem.Explore -> PlaceholderScreen("Explore")
                BottomNavItem.Profile -> ProfileScreen()   // Екран профілю (ЛР4/ЛР5)
            }
        }
    }
}

// Проста заглушка для екранів, які ще не намальовані
@Composable
fun PlaceholderScreen(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium)
    }
}