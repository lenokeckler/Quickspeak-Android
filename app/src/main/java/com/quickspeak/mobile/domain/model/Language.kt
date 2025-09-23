package com.quickspeak.mobile.domain.model

data class Language(
    val name: String,
    val countryCode: String, // ej: "BR", "US", "ES"
    val isNative: Boolean = false
) {
    fun flagUrl(style: String = "flat", size: Int = 64): String {
        return "https://flagsapi.com/${countryCode.uppercase()}/$style/$size.png"
    }
}
