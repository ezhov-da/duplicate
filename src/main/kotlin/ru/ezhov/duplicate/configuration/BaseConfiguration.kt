package ru.ezhov.duplicate.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["ru.ezhov.duplicate"])
class BaseConfiguration {
}
