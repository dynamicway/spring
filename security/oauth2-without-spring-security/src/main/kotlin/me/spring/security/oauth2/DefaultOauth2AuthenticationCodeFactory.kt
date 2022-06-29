package me.spring.security.oauth2

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest

@ConfigurationProperties(prefix = "authentication.oauth2")
@ConstructorBinding
class DefaultOauth2AuthenticationCodeFactory(private val clients: HashMap<String, ResourceServer>): Oauth2AuthenticationCodeFactory {

    override fun getOauth2AuthenticationCodeBy(servletRequest: ServletRequest): Oauth2AuthenticationCode {
        val httpServletRequest = servletRequest as HttpServletRequest
        val resourceServerName = httpServletRequest.requestURI.substringAfterLast("auth/")
        val authenticationCode = httpServletRequest.getParameter("code")
        val resourceServer = clients[resourceServerName] ?: throw IllegalArgumentException("not supported resource server")


        return Oauth2AuthenticationCode(
            accessTokenUri = resourceServer.accessTokenUri,
            accessTokenParameters = resourceServer.accessTokenParameter,
            userInfoHttpMethod = resourceServer.userInfoHttpMethod,
            userInfoUri = resourceServer.userInfoUri,
            resourceServerId = resourceServer.resourceServerId,
            userInfoAttributes = resourceServer.userInfoAttributes.attributes,
            code = authenticationCode
        )
    }

}