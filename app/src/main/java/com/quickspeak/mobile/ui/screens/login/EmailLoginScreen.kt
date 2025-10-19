package com.quickspeak.mobile.ui.login

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quickspeak.mobile.ui.theme.BlackGeneral
import com.quickspeak.mobile.ui.theme.CyanDarkMode

@Composable
fun EmailLoginScreen(
    onLogin: (email: String, password: String) -> Unit = { _, _ -> },
    onSignUpClicked: () -> Unit = {},
    onGoogleClicked: () -> Unit = {},
    onMicrosoftClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackGeneral)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .padding(horizontal = 22.dp, vertical = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            IconButton(
                onClick = { onBackClicked() },
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 35.dp)
                    .size(40.dp)
                    .background(Color.White, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(horizontal = 50.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User icon",
                    tint = Color(0xFF2B6EF6),
                    modifier = Modifier.size(35.dp)
                )
                Spacer(Modifier.width(15.dp))
                Text(
                    text = "QuickSpeak",
                    color = Color(0xFF2B6EF6),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 23.sp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Log In",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(text = "Email", color = Color(0xFFB9B9BC), fontSize = 13.sp, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "example@email.com", color = Color(0xFF8E8E90)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF0F0F10), RoundedCornerShape(14.dp)),
                colors = TextFieldDefaults.colors()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Password", color = Color(0xFFB9B9BC), fontSize = 13.sp, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "********", color = Color(0xFF8E8E90)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF0F0F10), RoundedCornerShape(14.dp)),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Text(
                        text = if (passwordVisible) "Hide" else "Show",
                        color = Color(0xFF000000),
                        modifier = Modifier
                            .clickable { passwordVisible = !passwordVisible }
                            .padding(end = 8.dp)
                    )
                },
                colors = TextFieldDefaults.colors()
            )

            Spacer(modifier = Modifier.height(18.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onLogin(email, password) },
                    modifier = Modifier
                        .width(140.dp)  // ancho más pequeño
                        .height(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .align(Alignment.CenterStart), // ahora sí funciona porque estamos en un BoxScope
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CyanDarkMode,
                        contentColor = Color.Black
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Log In", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "arrow")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                Text(text = "Don't have an account? ", color = Color(0xFF9B9B9F))
                Text(
                    text = "Sign Up",
                    color = CyanDarkMode,
                    modifier = Modifier.clickable { onSignUpClicked() }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Divider(modifier = Modifier.weight(1f), color = Color(0xFF2A2A2C))
                Text(text = "  Or log in with  ", color = Color(0xFF9B9B9F), fontSize = 12.sp)
                Divider(modifier = Modifier.weight(1f), color = Color(0xFF2A2A2C))
            }

            Spacer(modifier = Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { onGoogleClicked() },
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Text(text = "G", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.Unspecified)
                }

                IconButton(
                    onClick = { onMicrosoftClicked() },
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Text(text = "M", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.Unspecified)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmailLoginScreenPreview() {
    MaterialTheme {
        EmailLoginScreen()
    }
}
