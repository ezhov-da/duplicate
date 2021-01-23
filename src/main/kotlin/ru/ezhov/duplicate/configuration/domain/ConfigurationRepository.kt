package ru.ezhov.duplicate.configuration.domain

interface ConfigurationRepository {
    fun by(propertyName: PropertyName, default: String): String
}