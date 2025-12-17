package com.zabranska.petcare

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==============================
// 1. ЕКРАН ПОШУКУ (Grid - Вже готовий)
// ==============================

data class CategoryItem(val name: String, val icon: ImageVector, val color: Color)

@Composable
fun SearchScreen() {
    val categories = listOf(
        CategoryItem("Veterinary", Icons.Default.LocalHospital, Color(0xFFFFF3E0)),
        CategoryItem("Grooming", Icons.Default.ContentCut, Color(0xFFE3F2FD)),
        CategoryItem("Pet boarding", Icons.Default.Home, Color(0xFFFce4ec)),
        CategoryItem("Adoption", Icons.Default.Pets, Color(0xFFF3E5F5)),
        CategoryItem("Dog walking", Icons.Default.DirectionsWalk, Color(0xFFE0F2F1)),
        CategoryItem("Training", Icons.Default.ModelTraining, Color(0xFFE8EAF6)),
        CategoryItem("Pet taxi", Icons.Default.LocalTaxi, Color(0xFFE1F5FE)),
        CategoryItem("Pet date", Icons.Default.Favorite, Color(0xFFFFEBEE)),
        CategoryItem("Other", Icons.Default.MoreHoriz, Color(0xFFFFFDE7))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "What are you\nlooking for, Maria?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 24.dp, top = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                CategoryCard(category)
            }
        }
    }
}

@Composable
fun CategoryCard(category: CategoryItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(category.color),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.name,
                tint = Color.Black.copy(alpha = 0.6f),
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = category.name, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
    }
}

// ==============================
// 2. ГОЛОВНИЙ ЕКРАН ПРОФІЛЮ (Список/List)
// ==============================

// Дані для пункту меню
data class ProfileMenuItem(val title: String, val icon: ImageVector)

@Composable
fun ProfileScreen() {
    // Стан для навігації всередині вкладки (Menu <-> Edit Form)
    var showEditForm by remember { mutableStateOf(false) }

    if (showEditForm) {
        // Показуємо форму редагування (код з минулої лаби)
        EditProfileForm(onBack = { showEditForm = false })
    } else {
        // Показуємо МЕНЮ (Список LazyColumn)
        ProfileMenuContent(onEditClick = { showEditForm = true })
    }
}

@Composable
fun ProfileMenuContent(onEditClick: () -> Unit) {
    // Список налаштувань (List Data)
    val menuItems = listOf(
        ProfileMenuItem("Edit profile", Icons.Default.Person),
        ProfileMenuItem("Pet profile", Icons.Default.Pets),
        ProfileMenuItem("Payment methods", Icons.Default.Payment),
        ProfileMenuItem("Language", Icons.Default.Language),
        ProfileMenuItem("Settings", Icons.Default.Settings),
        ProfileMenuItem("Privacy policy", Icons.Default.Lock),
        ProfileMenuItem("Help center", Icons.Default.Help)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // --- Header Профілю ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Maria Martinez", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("m.martinez@gmail.com", fontSize = 14.sp, color = Color.Gray)
            }
        }

        // --- Список (LazyColumn - суть ЛР5) ---
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(menuItems) { item ->
                ProfileMenuItemRow(item, onClick = {
                    if (item.title == "Edit profile") {
                        onEditClick()
                    }
                })
            }
        }

        // Кнопка виходу
        Button(
            onClick = { /* Log out */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Log out", color = Color.Red, fontSize = 16.sp)
        }
    }
}

@Composable
fun ProfileMenuItemRow(item: ProfileMenuItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Іконка зліва з легким фоном
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFE8EAF6)),
            contentAlignment = Alignment.Center
        ) {
            Icon(item.icon, contentDescription = null, tint = Color(0xFF3F51B5))
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Текст
        Text(
            text = item.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF333333),
            modifier = Modifier.weight(1f)
        )

        // Стрілочка справа
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
    }
}

// ==============================
// 3. ФОРМА РЕДАГУВАННЯ (З ЛР4)
// ==============================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileForm(onBack: () -> Unit) {
    var fullName by remember { mutableStateOf("Maria Martinez") }
    var email by remember { mutableStateOf("m.martinez@gmail.com") }
    var phone by remember { mutableStateOf("+380 93 123 45 67") }
    var isMale by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Хедер з кнопкою "Назад"
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Edit Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
            TextButton(onClick = { onBack() }, modifier = Modifier.align(Alignment.CenterEnd)) {
                Text("Save", color = Color(0xFF3F51B5), fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Аватар
        Box {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.size(100.dp)
            )
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Edit photo",
                tint = Color(0xFF3F51B5),
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.BottomEnd)
                    .background(Color.White, CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = fullName, onValueChange = { fullName = it },
            label = { Text("Full name") }, modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Перемикач статі
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Owner", color = Color.Gray)
            Row(
                modifier = Modifier
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(20.dp))
                    .padding(4.dp)
            ) {
                Button(
                    onClick = { isMale = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isMale) Color(0xFF3F51B5) else Color.Transparent,
                        contentColor = if (isMale) Color.White else Color.Gray
                    ),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                    modifier = Modifier.height(36.dp)
                ) { Text("Male") }

                Button(
                    onClick = { isMale = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isMale) Color(0xFF3F51B5) else Color.Transparent,
                        contentColor = if (!isMale) Color.White else Color.Gray
                    ),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                    modifier = Modifier.height(36.dp)
                ) { Text("Female") }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Email") }, modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = phone, onValueChange = { phone = it },
            label = { Text("Phone") }, modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
    }
}