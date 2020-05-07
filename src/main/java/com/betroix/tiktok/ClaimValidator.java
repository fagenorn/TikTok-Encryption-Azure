package com.betroix.tiktok;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;

public class ClaimValidator implements OAuth2TokenValidator<Jwt> {
    private final String claimType;
    private final String claimValue;

    ClaimValidator(String claimType, String claimValue) {
        this.claimType = claimType;
        this.claimValue = claimValue;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        OAuth2Error error = new OAuth2Error("invalid_token", "The required claim is missing or invalid", null);

        String value = jwt.getClaimAsString(claimType);
        if (value == null || !value.equals(claimValue)) {
            return OAuth2TokenValidatorResult.failure(error);
        }

        return OAuth2TokenValidatorResult.success();
    }
}
