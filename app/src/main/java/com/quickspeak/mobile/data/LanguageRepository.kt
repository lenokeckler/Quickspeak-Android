package com.quickspeak.mobile.data

import androidx.compose.runtime.mutableStateListOf
import com.quickspeak.mobile.domain.model.Language

object LanguageRepository {

    private val _learningLanguages = mutableStateListOf<Language>()
    val learningLanguages: List<Language> get() = _learningLanguages

    private val _allAvailableLanguages = listOf(
        Language("English", "US"),
        Language("Português", "BR"),
        Language("Deutsch", "DE"),
        Language("Español", "ES"),
        Language("Hindi", "IN"),
        Language("Français", "FR"),
        Language("漢語", "CN"),
        Language("Русский", "RU"),
        Language("العربية", "AE"),
        Language("日本語", "JP"),
        Language("한국어", "KR"),
        Language("Italiano", "IT"),
        Language("Nederlands", "NL"),
        Language("Português", "PT"),
        Language("Polski", "PL"),
        Language("Türkçe", "TR"),
        Language("Svenska", "SE"),
        Language("Norsk", "NO"),
        Language("Dansk", "DK"),
        Language("Suomi", "FI")
    )

    val availableLanguages: List<Language>
        get() = _allAvailableLanguages.filter { available ->
            _learningLanguages.none { learning -> learning.countryCode == available.countryCode }
        }

    init {
        _learningLanguages.addAll(
            listOf(
                Language("English", "US", true),
                Language("Português", "BR"),
                Language("Deutsch", "DE")
            )
        )
    }

    fun addLanguageToLearning(language: Language) {
        if (_learningLanguages.none { it.countryCode == language.countryCode }) {
            _learningLanguages.add(language)
        }
    }

    fun removeLanguageFromLearning(language: Language) {
        _learningLanguages.removeAll { it.countryCode == language.countryCode }
    }
}