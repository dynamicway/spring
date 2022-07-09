package me.spring.security.oauth2

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = [ResourceServerRequestFactory::class])
class SecurityConfig {

    @Bean
    fun oauth2AuthenticationCodeFilter(
        resourceServerRequestFactory: ResourceServerRequestFactory,
        oauth2UserProvider: Oauth2UserProvider
    ) = FilterRegistrationBean<Oauth2AuthenticationCodeFilter>()
            .apply {
                filter = Oauth2AuthenticationCodeFilter(
                    resourceServerRequestFactory,
                    oauth2UserProvider
                )
                urlPatterns = listOf("/auth/*")
            }

}
