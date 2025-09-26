package com.quickspeak.mobile.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.ui.draw.alpha
import com.quickspeak.mobile.data.UserRepository
import com.quickspeak.mobile.domain.model.Achievement
import com.quickspeak.mobile.domain.model.LearningStats
import com.quickspeak.mobile.domain.model.UserProfile
import com.quickspeak.mobile.ui.theme.*

/**
 * PROFILE SCREEN
 * Shows user profile information, stats, and achievements
 * Based on the web version adapted for mobile with system UI padding
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val isDarkTheme = isSystemInDarkTheme()
    val userProfile = UserRepository.userProfile
    val learningStats = UserRepository.learningStats
    val achievements = UserRepository.achievements

    // MAIN CONTAINER WITH GRADIENT BACKGROUND
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = if (isDarkTheme) {
                    Brush.verticalGradient(
                        colors = listOf(
                            BlackGeneral,
                            Color(0xFF2C006E)
                        )
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(
                            WhiteGeneral,
                            Color(0x7C7C01F6)
                        )
                    )
                }
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                // TOP BAR WITH BACK BUTTON AND SETTINGS
                ProfileTopBar(
                    onBackClick = onBackClick,
                    onSettingsClick = onSettingsClick,
                    isDarkTheme = isDarkTheme
                )
            }

            item {
                // USER PROFILE CARD
                ProfileCard(
                    userProfile = userProfile,
                    isDarkTheme = isDarkTheme
                )
            }

            item {
                // LEARNING STATS SECTION
                LearningStatsSection(
                    learningStats = learningStats,
                    isDarkTheme = isDarkTheme
                )
            }

            item {
                // ACHIEVEMENTS SECTION
                AchievementsSection(
                    achievements = achievements,
                    isDarkTheme = isDarkTheme
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

/**
 * TOP BAR COMPONENT
 * Contains back button, title, and settings button
 */
@Composable
private fun ProfileTopBar(
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    isDarkTheme: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = if (isDarkTheme) CyanDarkMode else CyanLightMode,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "Profile",
            color = if (isDarkTheme) CyanDarkMode else CyanLightMode,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onSettingsClick) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = if (isDarkTheme) CyanDarkMode else CyanLightMode,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

/**
 * PROFILE CARD COMPONENT
 * Shows user avatar, name, email, and basic info
 */
@Composable
private fun ProfileCard(
    userProfile: UserProfile,
    isDarkTheme: Boolean
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = if (isDarkTheme) Color(0xFF232323) else Color.White,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // AVATAR
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(
                        width = 4.dp,
                        color = if (isDarkTheme) CyanDarkMode else CyanLightMode,
                        shape = CircleShape
                    )
            ) {
                AsyncImage(
                    model = "https://api.dicebear.com/9.x/avataaars/svg?seed=${userProfile.avatarSeed}",
                    contentDescription = "User Avatar",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // NAME
            Text(
                text = userProfile.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme) Color.White else Color.Black
            )

            // EMAIL
            Text(
                text = userProfile.email,
                fontSize = 16.sp,
                color = if (isDarkTheme) Color(0xFF9CA3AF) else Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // PROFILE INFO GRID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileInfoItem(
                    label = "Native",
                    value = userProfile.nativeLanguage,
                    icon = "🏠",
                    isDarkTheme = isDarkTheme
                )
                ProfileInfoItem(
                    label = "Learning",
                    value = "${userProfile.learningLanguages.size} Languages",
                    icon = "📚",
                    isDarkTheme = isDarkTheme
                )
                ProfileInfoItem(
                    label = "Level",
                    value = userProfile.preferredDifficulty,
                    icon = "🎯",
                    isDarkTheme = isDarkTheme
                )
            }
        }
    }
}

/**
 * PROFILE INFO ITEM
 * Small info card for profile details
 */
@Composable
private fun ProfileInfoItem(
    label: String,
    value: String,
    icon: String,
    isDarkTheme: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = icon,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (isDarkTheme) Color(0xFF9CA3AF) else Color(0xFF6B7280),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = if (isDarkTheme) Color.White else Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * LEARNING STATS SECTION
 * Shows learning progress and statistics
 */
@Composable
private fun LearningStatsSection(
    learningStats: LearningStats,
    isDarkTheme: Boolean
) {
    Column {
        Text(
            text = "Learning Progress",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkTheme) Color.White else Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = if (isDarkTheme) Color(0xFF232323) else Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // STREAK AND GOAL PROGRESS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatCard(
                        title = "Current Streak",
                        value = "${learningStats.currentStreak}",
                        subtitle = "days",
                        icon = "🔥",
                        color = if (isDarkTheme) RedDarkMode else RedLightMode,
                        isDarkTheme = isDarkTheme
                    )
                    StatCard(
                        title = "Weekly Goal",
                        value = "${learningStats.weeklyProgress}/${learningStats.weeklyGoal}",
                        subtitle = "minutes",
                        icon = "🎯",
                        color = if (isDarkTheme) CyanDarkMode else CyanLightMode,
                        isDarkTheme = isDarkTheme
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ALL TIME STATS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatCard(
                        title = "Words Learned",
                        value = "${learningStats.totalWordsLearned}",
                        subtitle = "total",
                        icon = "📚",
                        color = Color(0xFF10B981),
                        isDarkTheme = isDarkTheme
                    )
                    StatCard(
                        title = "Conversations",
                        value = "${learningStats.totalConversations}",
                        subtitle = "completed",
                        icon = "💬",
                        color = Color(0xFF8B5CF6),
                        isDarkTheme = isDarkTheme
                    )
                }
            }
        }
    }
}

/**
 * STAT CARD COMPONENT
 * Individual statistic display card
 */
@Composable
private fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: String,
    color: Color,
    isDarkTheme: Boolean
) {
    Surface(
        modifier = Modifier
            .width(140.dp)
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.1f),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = color.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = icon,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                fontSize = 10.sp,
                color = if (isDarkTheme) Color(0xFF9CA3AF) else Color(0xFF6B7280),
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitle,
                fontSize = 8.sp,
                color = if (isDarkTheme) Color(0xFF9CA3AF) else Color(0xFF6B7280),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * ACHIEVEMENTS SECTION
 * Shows unlocked and locked achievements
 */
@Composable
private fun AchievementsSection(
    achievements: List<Achievement>,
    isDarkTheme: Boolean
) {
    Column {
        Text(
            text = "Achievements",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkTheme) Color.White else Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = if (isDarkTheme) Color(0xFF232323) else Color.White,
            shadowElevation = 8.dp
        ) {
            LazyRow(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(achievements) { achievement ->
                    AchievementCard(
                        achievement = achievement,
                        isDarkTheme = isDarkTheme
                    )
                }
            }
        }
    }
}

/**
 * ACHIEVEMENT CARD COMPONENT
 * Individual achievement display
 */
@Composable
private fun AchievementCard(
    achievement: Achievement,
    isDarkTheme: Boolean
) {
    val cardColor = if (achievement.isUnlocked) {
        if (isDarkTheme) Color(0xFF1F2937) else Color(0xFFF3F4F6)
    } else {
        if (isDarkTheme) Color(0xFF111827) else Color(0xFFE5E7EB)
    }

    val borderColor = if (achievement.isUnlocked) {
        if (isDarkTheme) CyanDarkMode else CyanLightMode
    } else {
        if (isDarkTheme) Color(0xFF374151) else Color(0xFF9CA3AF)
    }

    Surface(
        modifier = Modifier
            .width(120.dp)
            .height(130.dp),
        shape = RoundedCornerShape(16.dp),
        color = cardColor,
        border = androidx.compose.foundation.BorderStroke(
            width = if (achievement.isUnlocked) 2.dp else 1.dp,
            color = borderColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // ACHIEVEMENT ICON
            Text(
                text = achievement.iconEmoji,
                fontSize = if (achievement.isUnlocked) 32.sp else 24.sp,
                modifier = Modifier.alpha(if (achievement.isUnlocked) 1f else 0.5f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ACHIEVEMENT TITLE
            Text(
                text = achievement.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (achievement.isUnlocked) {
                    if (isDarkTheme) Color.White else Color.Black
                } else {
                    if (isDarkTheme) Color(0xFF6B7280) else Color(0xFF9CA3AF)
                },
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // ACHIEVEMENT DESCRIPTION
            Text(
                text = achievement.description,
                fontSize = 8.sp,
                color = if (achievement.isUnlocked) {
                    if (isDarkTheme) Color(0xFF9CA3AF) else Color(0xFF6B7280)
                } else {
                    if (isDarkTheme) Color(0xFF4B5563) else Color(0xFF9CA3AF)
                },
                textAlign = TextAlign.Center,
                lineHeight = 10.sp
            )
        }
    }
}

/**
 * PREVIEW FUNCTIONS
 */
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    QuickSpeakTheme {
        ProfileScreen()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF232323)
@Composable
fun ProfileScreenDarkPreview() {
    QuickSpeakTheme(darkTheme = true) {
        ProfileScreen()
    }
}