package com.quickspeak.mobile.domain.model

data class Language(
    val name: String,
    val countryCode: String, // ej: "BR", "US", "ES"
    val isNative: Boolean = false
) {
    /**
     * Returns the flag URL using Circle Flags API
     * @param style Ignored - Circle Flags only provides circular style
     * @param size Ignored - SVG format is scalable
     * @return SVG flag URL
     */
    fun flagUrl(style: String = "flat", size: Int = 64): String {
        return "https://hatscripts.github.io/circle-flags/flags/${countryCode.lowercase()}.svg"
    }

    /**
     * Alternative method with cleaner API for Circle Flags
     * @return SVG flag URL
     */
    fun circleFlagUrl(): String {
        return "https://hatscripts.github.io/circle-flags/flags/${countryCode.lowercase()}.svg"
    }
}