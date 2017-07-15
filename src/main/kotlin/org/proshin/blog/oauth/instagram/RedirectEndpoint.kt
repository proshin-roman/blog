package org.proshin.blog.oauth.instagram

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Handler
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import mu.KotlinLogging
import org.proshin.blog.configuration.ConfigParameters
import org.proshin.blog.dao.AccessTokens
import org.proshin.blog.model.AccessToken.Provider.INSTAGRAM
import org.proshin.blog.oauth.instagram.response.AccessToken
import org.proshin.blog.oauth.instagram.response.Error
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
open class RedirectEndpoint {

    private val ACCESS_TOKEN_ENDPOINT = "https://api.instagram.com/oauth/access_token"
    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var configParameters: ConfigParameters;
    @Autowired
    lateinit var accessTokens: AccessTokens

    @RequestMapping(value = "/oauth/instagram/redirect")
    fun processCodeForInstagram(@RequestParam(name = "code", required = true) code: String): ResponseEntity<String> {
        Fuel.post(ACCESS_TOKEN_ENDPOINT,
                listOf(
                        "client_id" to configParameters.instagram.clientId,
                        "client_secret" to configParameters.instagram.clientSecret,
                        "grant_type" to "authorization_code",
                        "redirect_uri" to configParameters.instagram.redirectUri,
                        "code" to code))
                .responseString(object : Handler<String> {
                    override fun failure(request: Request, response: Response, error: FuelError) {
                        val error = Error(String(response.data))
                        logger.error("Error has happened $error")
                    }

                    override fun success(request: Request, response: Response, value: String) {
                        val accessToken = accessTokens.create(INSTAGRAM, AccessToken(value).accessToken)
                        logger.info("Access token has been successfully received: $accessToken")
                    }
                })
        return ResponseEntity.ok().build()
    }
}

