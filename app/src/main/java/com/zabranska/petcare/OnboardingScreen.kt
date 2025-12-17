package com.zabranska.petcare // Залиш свій пакет

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

// Модель даних для однієї сторінки
data class OnboardingPage(
    val title: String,
    val description: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinished: () -> Unit,      // Дія для кнопки "Get Started"
    onSignInClick: () -> Unit    // Дія для кнопки "Sign In" зверху
) {
    // Тексти згідно з макетом
    val pages = listOf(
        OnboardingPage(
            "Welcome to Pet Care",
            "All types of services for your pet in one place, instantly searchable."
        ),
        OnboardingPage(
            "Proven experts",
            "We interview every specialist before they get to work."
        ),
        OnboardingPage(
            "Reliable reviews",
            "A review can be left only by a user who used the service."
        )
    )

    // Стан пейджера (яка сторінка зараз активна)
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope() // Потрібно для прокрутки кодом

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // --- Верхня панель (Кнопка Sign In) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            TextButton(onClick = onSignInClick) {
                Text(
                    text = "Sign In",
                    color = Color(0xFF658DF1),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // --- Слайдер (Pager) ---
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f) // Займає весь доступний простір по центру
        ) { pageIndex ->
            OnboardingPageContent(page = pages[pageIndex])
        }

        // --- Нижня частина (Індикатори + Кнопка) ---
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Індикатори (крапки)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                repeat(pages.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color(0xFFE0A95D) else Color(0xFFE0E0E0)
                    val width = if (pagerState.currentPage == iteration) 24.dp else 8.dp

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .height(8.dp)
                            .width(width)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }

            // Логіка кнопки: Next або Get Started
            val isLastPage = pagerState.currentPage == pages.size - 1
            val buttonText = if (isLastPage) "Get Started" else "Next"

            Button(
                onClick = {
                    if (isLastPage) {
                        onFinished()
                    } else {
                        // Гортаємо на наступну сторінку
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = buttonText, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // ЗАГЛУШКА ДЛЯ КАРТИНКИ
        // Встав сюди свої картинки собак через painterResource(R.drawable.your_dog_image)
        Icon(
            painter = painterResource(id = R.drawable.ic_paw), // Використовуємо лапку з 1-ї лаби
            contentDescription = null,
            tint = Color(0xFF658DF1),
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = page.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}