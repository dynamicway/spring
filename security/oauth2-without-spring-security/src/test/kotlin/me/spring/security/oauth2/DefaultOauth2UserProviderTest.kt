package me.spring.security.oauth2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.util.UriComponentsBuilder

internal class DefaultOauth2UserProviderTest {

    private lateinit var defaultOauth2UserProvider: DefaultOauth2UserProvider
    private lateinit var spyRestTemplate: SpyRestTemplate

    @BeforeEach
    internal fun setUp() {
        spyRestTemplate = SpyRestTemplate()
        defaultOauth2UserProvider = DefaultOauth2UserProvider(
            restTemplate = spyRestTemplate
        )
    }

    @Test
    fun getOauth2User_get_accessToken_by_restTemplate() {
        val authenticationCode = getOauth2AuthenticationCodeDummy()
        val accessTokenRequestParameters = LinkedMultiValueMap<String, String>()
        authenticationCode.accessTokenParameters.forEach { accessTokenRequestParameters.add(it.key, it.value) }
        defaultOauth2UserProvider.getOauth2User(authenticationCode)
        val accessTokenRequestUri = UriComponentsBuilder.fromUriString(authenticationCode.accessTokenUri)
                .queryParams(accessTokenRequestParameters)
                .build()
                .toUri()
        assertThat(spyRestTemplate.getForEntityArgumentsUrl).isEqualTo(accessTokenRequestUri)
        assertThat(spyRestTemplate.getForEntityArgumentsResponseType).isEqualTo(Oauth2AccessTokenResponse::class.java)
    }

}