package ru.ezhov.duplicate.configuration.infrastructure

import org.springframework.stereotype.Service
import ru.ezhov.duplicate.configuration.domain.ConfigurationRepository
import ru.ezhov.duplicate.configuration.domain.PropertyName

@Service
class ConstantsConfigurationRepository : ConfigurationRepository {

    private val constants = mapOf(PropertyName.COUNT_ON_PAGE to "30")

    override fun by(propertyName: PropertyName, default:String): String = constants.getOrDefault(propertyName, default)
}