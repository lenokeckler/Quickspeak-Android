package com.quickspeak.mobile.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import com.quickspeak.mobile.ui.theme.BlackGeneral


@Composable
fun AvatarItem(
    name: String,
    country: String,
    url: String,
    // color de fondo del "card"
    bgColor: Color = MaterialTheme.colorScheme.secondary,
    // offsets para ajustar manualmente la posición del texto (en dp)
    nameOffsetX: Dp = 0.dp,
    nameOffsetY: Dp = 8.dp,
    countryOffsetX: Dp = 0.dp,
    countryOffsetY: Dp = 30.dp,
    width: Dp = 185.dp,
    height: Dp = 265.dp,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.height(12.dp))
    Box(
        modifier = modifier
            .size(width = width, height = height)
            .clip(RoundedCornerShape(25.dp))
            .background(bgColor)
            .padding(8.dp)
    ) {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = "Avatar $name",
            modifier = Modifier
                .fillMaxWidth()
                .height(height - 80.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )


        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = nameOffsetX, y = nameOffsetY)
                .zIndex(1f)
        )


        Text(
            text = country,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = countryOffsetX, y = countryOffsetY)
                .zIndex(1f)
        )

    }
}

@Composable
fun AvatarGrid(
    avatars: List<Triple<String, String, String>>,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        Color(0xFF65C6FF),
        Color(0xFFFF6B6B),
        Color(0xFF51CF66),
        Color(0xFFFFD43B),
        Color(0xFFD8B5FF),
        Color(0xFFFFA94D)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(570.dp)
            .clipToBounds()
    ) {
        avatars.forEachIndexed { i, triple ->
            AvatarItem(
                name = triple.first,
                country = triple.second,
                url = triple.third,
                bgColor = colors.getOrElse(i) { MaterialTheme.colorScheme.secondary },

                nameOffsetX = 0.dp,
                nameOffsetY = 8.dp,
                countryOffsetX = 0.dp,
                countryOffsetY = 30.dp,
                modifier = Modifier
                    .align(
                        when (i) {
                            0 -> Alignment.TopStart
                            1 -> Alignment.TopCenter
                            2 -> Alignment.TopEnd
                            3 -> Alignment.BottomStart
                            4 -> Alignment.BottomCenter
                            else -> Alignment.BottomEnd
                        }
                    )
                    .offset(
                        x = when (i) { 0 -> -80.dp; 2 -> 80.dp; 3 -> -80.dp; 5 -> 80.dp; else -> 0.dp },
                        y = when (i) { 0 -> 30.dp; 1 -> 30.dp; 2 -> 30.dp; 3 -> 0.dp; 4 -> 0.dp; 5 -> 0.dp; else -> (-10).dp }
                    )
            )
        }
    }
}

@Composable
fun LoginContent(
    onSignUpClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        Text(
            text = "Speak with Endless\nPersonalities",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            ),
            textAlign = TextAlign.Center,
            lineHeight = 38.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Learn languages for fun",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onSignUpClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = "Sign Up for Free",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already a member? ",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    fontSize = 16.sp
                )
            )
            Text(
                text = "Log in",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
        Spacer(modifier = Modifier.height(77.dp))
    }
}

@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackGeneral)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AvatarGrid(
                    avatars = listOf(
                        Triple("Beatrix", "German", "https://api.dicebear.com/9.x/avataaars/png?seed=Beatrix&backgroundColor=65c6ff"),
                        Triple("Vanessa", "Portuguese", "https://api.dicebear.com/9.x/avataaars/png?seed=Vanessa&backgroundColor=ff6b6b"),
                        Triple("Walter", "Bengali", "https://api.dicebear.com/9.x/avataaars/png?seed=Walter&backgroundColor=51cf66"),
                        Triple("Ralph", "Hindi", "https://api.dicebear.com/9.x/avataaars/png?seed=Ralph&backgroundColor=ffd43b"),
                        Triple("Sara", "Spanish", "https://api.dicebear.com/9.x/avataaars/png?seed=Sara&backgroundColor=d8b5ff"),
                        Triple("Leon", "French", "https://api.dicebear.com/9.x/avataaars/png?seed=Leon&backgroundColor=ffa94d")
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LoginContent(onSignUpClick, onLoginClick)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun AvatarGridPreview() {
    MaterialTheme {
        AvatarGrid(
            avatars = listOf(
                Triple("Beatrix", "German", "https://api.dicebear.com/9.x/avataaars/png?seed=Beatrix&backgroundColor=65c6ff"),
                Triple("Vanessa", "Portuguese", "https://api.dicebear.com/9.x/avataaars/png?seed=Vanessa&backgroundColor=ff6b6b"),
                Triple("Walter", "Bengali", "https://api.dicebear.com/9.x/avataaars/png?seed=Walter&backgroundColor=51cf66"),
                Triple("Ralph", "Hindi", "https://api.dicebear.com/9.x/avataaars/png?seed=Ralph&backgroundColor=ffd43b"),
                Triple("Sara", "Spanish", "https://api.dicebear.com/9.x/avataaars/png?seed=Sara&backgroundColor=d8b5ff"),
                Triple("Leon", "French", "https://api.dicebear.com/9.x/avataaars/png?seed=Leon&backgroundColor=ffa94d")
            ),
            modifier = Modifier.width(800.dp).height(800.dp)
        )
    }
}
