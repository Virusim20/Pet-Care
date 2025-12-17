package com.zabranska.petcare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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

// ----------------------
// 1. ЕКРАН ПОШУКУ (Grid)
// ----------------------

// Модель даних для категорії
data class CategoryItem(val name: String, val icon: ImageVector, val color: Color)

@Composable
fun SearchScreen() {
    // Список категорій (імітація макету)
    val categories = listOf(
        CategoryItem("Veterinary", Icons.Default.LocalHospital, Color(0xFFFFF3E0)), // Помаранчевий
        CategoryItem("Grooming", Icons.Default.ContentCut, Color(0xFFE3F2FD)),      // Блакитний
        CategoryItem("Pet boarding", Icons.Default.Home, Color(0xFFFce4ec)),        // Рожевий
        CategoryItem("Adoption", Icons.Default.Pets, Color(0xFFF3E5F5)),            // Фіолетовий
        CategoryItem("Dog walking", Icons.Default.DirectionsWalk, Color(0xFFE0F2F1)),// М'ятний
        CategoryItem("Training", Icons.Default.ModelTraining, Color(0xFFE8EAF6)),    // Синій
        CategoryItem("Pet taxi", Icons.Default.LocalTaxi, Color(0xFFE1F5FE)),
        CategoryItem("Pet date", Icons.Default.Favorite, Color(0xFFFFEBEE)),
        CategoryItem("Other", Icons.Default.MoreHoriz, Color(0xFFFFFDE7))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        // Заголовок
        Text(
            text = "What are you\nlooking for, Maria?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Сітка (Grid)
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 3 стовпці
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
        // Кругла іконка з фоном
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
                tint = Color.Black.copy(alpha = 0.6f), // Трохи тьмяніший колір іконки
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Текст під іконкою
        Text(
            text = category.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
    }
}

// -----------------------
// 2. ЕКРАН ПРОФІЛЮ (Form)
// -----------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    // Стан полів (можна змінювати текст)
    var fullName by remember { mutableStateOf("Maria Martinez") }
    var email by remember { mutableStateOf("m.martinez@gmail.com") }
    var phone by remember { mutableStateOf("+380 93 123 45 67") }
    var isMale by remember { mutableStateOf(false) } // Для перемикача статі

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Додаємо скрол, якщо екран малий
            .padding(24.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Верхня панель з кнопкою назад (імітація)
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Your profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
            TextButton(onClick = { /* Save action */ }, modifier = Modifier.align(Alignment.CenterEnd)) {
                Text("Save", color = Color(0xFF3F51B5))
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
            // Плюсик біля аватара
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

        // Поле Full Name
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Перемикач Owner (Gender)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Owner", color = Color.Gray)

            // Кастомний перемикач
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
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text("Male")
                }

                Button(
                    onClick = { isMale = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isMale) Color(0xFF3F51B5) else Color.Transparent,
                        contentColor = if (!isMale) Color.White else Color.Gray
                    ),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text("Female")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Поле Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Поле Phone
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Кнопка знизу
        Button(
            onClick = { /* Logout or Action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Log out", fontSize = 16.sp)
        }
    }
}