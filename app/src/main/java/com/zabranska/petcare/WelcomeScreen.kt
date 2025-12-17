package com.zabranska.petcare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zabranska.petcare.R // Переконайся, що імпортуєш правильний R файл
import kotlinx.coroutines.delay

// Кольори взяті з UI Kit (приблизно)
val BrandBlueLight = Color(0xFF6889FF) // Верхній світлий
val BrandBlueDark = Color(0xFF3F51B5)  // Нижній темний (ближче до індиго)

@Composable
fun WelcomeScreen(
    onTimeout: () -> Unit
) {
    // Градієнтний фон (Vertical Gradient)
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(BrandBlueLight, BrandBlueDark)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
    ) {

        // --- 1. ТЕКСТ (Заголовок) ---
        // Якщо потрібно відобразити текст як на макеті "Laboratory 1..."
        // Якщо ні - цей блок можна прибрати
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 60.dp, start = 24.dp)
        ) {
            Text(
                text = "Laboratory 1. Welcome",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Loader",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 28.sp,
                fontWeight = FontWeight.Light
            )
        }

        // --- 2. ЛАПКИ (Центральна композиція) ---
        // Я розміщую їх вручну через Box + Offset, щоб точно повторити
        // хаотичний рух "слідів", як на картинці
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            // Лапка 1 (Найнижча)
            PawIcon(modifier = Modifier.offset(x = (-40).dp, y = 100.dp).size(30.dp), rotation = -15f)

            // Лапка 2
            PawIcon(modifier = Modifier.offset(x = 20.dp, y = 60.dp).size(32.dp), rotation = 15f)

            // Лапка 3
            PawIcon(modifier = Modifier.offset(x = (-20).dp, y = 0.dp).size(34.dp), rotation = -10f)

            // Лапка 4
            PawIcon(modifier = Modifier.offset(x = 30.dp, y = (-50).dp).size(36.dp), rotation = 20f)

            // Лапка 5 (Верхня)
            PawIcon(modifier = Modifier.offset(x = 0.dp, y = (-110).dp).size(38.dp), rotation = 0f)
        }
    }

    // Таймер на 3 секунди
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }
}

// Допоміжний компонент для лапки
@Composable
fun PawIcon(modifier: Modifier = Modifier, rotation: Float) {
    Icon(
        painter = painterResource(id = R.drawable.ic_paw),
        contentDescription = "Paw",
        tint = Color.White,
        modifier = modifier.rotate(rotation)
    )
}

// Extension для повороту (щоб код був чистішим)
fun Modifier.rotate(degrees: Float) = this.then(Modifier.graphicsLayer(rotationZ = degrees))