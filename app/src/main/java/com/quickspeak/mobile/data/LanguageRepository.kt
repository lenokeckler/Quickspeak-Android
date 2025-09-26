package com.quickspeak.mobile.data

import androidx.compose.runtime.mutableStateListOf
import com.quickspeak.mobile.domain.model.Language

object LanguageRepository {

    private val _learningLanguages = mutableStateListOf<Language>()
    val learningLanguages: List<Language> get() = _learningLanguages

    private val _allAvailableLanguages = listOf(
        Language("English", "US"),
        Language("Portuguese", "BR"),
        Language("German", "DE"),
        Language("Spanish", "ES"),
        Language("Hindi", "IN"),
        Language("French", "FR"),
        Language("Chinese", "CN"),
        Language("Russian", "RU"),
        Language("Arabic", "AE"),
        Language("Japanese", "JP"),
        Language("Korean", "KR"),
        Language("Italian", "IT"),
        Language("Dutch", "NL"),
        Language("Portuguese", "PT"),
        Language("Polish", "PL"),
        Language("Turkish", "TR"),
        Language("Swedish", "SE"),
        Language("Norwegian", "NO"),
        Language("Danish", "DK"),
        Language("Finnish", "FI"),
        Language("Irish", "IE")
    )

    val availableLanguages: List<Language>
        get() = _allAvailableLanguages.filter { available ->
            _learningLanguages.none { learning -> learning.countryCode == available.countryCode }
        }

    init {
        _learningLanguages.addAll(
            listOf(
                Language("English", "US", true),
                Language("Portuguese", "BR"),
                Language("German", "DE")
            )
        )
    }

    fun addLanguageToLearning(language: Language) {
        if (_learningLanguages.none { it.countryCode == language.countryCode }) {
            _learningLanguages.add(language)
        }
    }

    fun removeLanguageFromLearning(language: Language) {
        // Cannot remove native language
        if (language.isNative) return
        _learningLanguages.removeAll { it.countryCode == language.countryCode }
    }

    fun setAsNativeLanguage(language: Language) {
        // Remove native status from all languages first
        val updatedLanguages = _learningLanguages.map { lang ->
            if (lang.countryCode == language.countryCode) {
                lang.copy(isNative = true)
            } else {
                lang.copy(isNative = false)
            }
        }

        _learningLanguages.clear()
        _learningLanguages.addAll(updatedLanguages)
    }

    fun getNativeLanguage(): Language? {
        return _learningLanguages.find { it.isNative }
    }

    fun canRemoveLanguage(language: Language): Boolean {
        return !language.isNative
    }
}