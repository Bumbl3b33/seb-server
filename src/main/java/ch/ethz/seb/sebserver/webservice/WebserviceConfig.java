/*
 * Copyright (c) 2019 ETH Zürich, Educational Development and Technology (LET)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ch.ethz.seb.sebserver.webservice;

import org.cryptonode.jncryptor.AES256JNCryptor;
import org.cryptonode.jncryptor.JNCryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import ch.ethz.seb.sebserver.gbl.Constants;
import ch.ethz.seb.sebserver.gbl.profile.WebServiceProfile;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@WebServiceProfile
@OpenAPIDefinition(info = @Info(title = "SEB Server", version = "v1"))
@SecurityScheme(
        name = "oauth2",
        type = SecuritySchemeType.OAUTH2,
        bearerFormat = "JWT",
        flows = @OAuthFlows(
                password = @OAuthFlow(
                        tokenUrl = "/oauth/token"

                )

        ))
public class WebserviceConfig {

    @Lazy
    @Bean
    public JNCryptor jnCryptor() {
        final AES256JNCryptor aes256jnCryptor = new AES256JNCryptor();
        aes256jnCryptor.setPBKDFIterations(Constants.JN_CRYPTOR_ITERATIONS);
        return aes256jnCryptor;
    }

}
