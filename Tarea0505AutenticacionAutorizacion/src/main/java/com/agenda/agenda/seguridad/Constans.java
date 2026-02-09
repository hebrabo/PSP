package com.agenda.agenda.seguridad;

import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

/*
 * Esta clase nos sirve para declarar como constantes
 * aquellas variables que necesitaremos en las siguientes clases a desarrollar.
 */
public class Constans {
    // Spring Security
    public static final String LOGIN_URL = "/login";
    public static final String HEADER_AUTHORIZACION_KEY = "token";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    public static final String USER = "aitor";
    public static final String PASS = "1234";
    // JWT
    public static final String SUPER_SECRET_KEY =
            "ZnJhc2VzbGFyZ2FzcGFyYWNvbG9jYXJjb21vY2xhdmVlbnVucHJvamVjdG9kZWVtZXBsb3BhcmFqd3Rjb25zcHJpbmdzZWN1cml0eQ==bWlwcnVlYmFkZWVqbXBsb3BhcmFiYXNlNjQ=";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000; //10 day

    public static Key getSigningKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Claves para cifrado de contraseñas (AES)
    // Clave secreta para AES (16, 24 o 32 bytes)
    public static final String SECRET_KEY = "1234567890123456"; // 16 caracteres
    // Vector de inicialización (16 bytes)
    public static final String INIT_VECTOR = "1234567890123456"; // 16 caracteres
}
