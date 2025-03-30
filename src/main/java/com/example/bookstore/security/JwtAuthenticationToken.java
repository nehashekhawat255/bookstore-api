package com.example.bookstore.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final UserDetails userDetails;

    public JwtAuthenticationToken(UserDetails userDetails) {

        super(userDetails.getAuthorities());
        this.userDetails = userDetails;
        setAuthenticated(true);  // Mark as authenticated
    }

    @Override
    public Object getCredentials() {
        return null;  // JWTs don’t require credentials (passwords) after validation
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }
}
