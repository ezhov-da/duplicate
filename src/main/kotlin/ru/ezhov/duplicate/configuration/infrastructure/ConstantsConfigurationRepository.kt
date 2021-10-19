package ru.ezhov.duplicate.configuration.infrastructure

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.ezhov.duplicate.configuration.domain.ConfigurationRepository
import ru.ezhov.duplicate.configuration.domain.PropertyName

@Service
class ConstantsConfigurationRepository(
        @Value("\${properties.count-on-page}") private val countOnPage: String
) : ConfigurationRepository {

    private val constants = mapOf(PropertyName.COUNT_ON_PAGE to countOnPage)

    override fun by(propertyName: PropertyName, default: String): String = constants.getOrDefault(propertyName, default)
}