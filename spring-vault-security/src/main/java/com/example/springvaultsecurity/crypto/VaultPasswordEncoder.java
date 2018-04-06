package com.example.springvaultsecurity.crypto;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.security.VaultBytesEncryptor;

public class VaultPasswordEncoder implements PasswordEncoder {

    private final VaultBytesEncryptor encryptor;

    public VaultPasswordEncoder(VaultTransitOperations vaultTransitOperations) {
        this.encryptor = new VaultBytesEncryptor(vaultTransitOperations, "password-encoder");
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return new String(Hex.encode(encryptor.encrypt(Utf8.encode(rawPassword))));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null || rawPassword.length() == 0) {
            return false;
        }

        if (encodedPassword == null || encodedPassword.length() == 0) {
            return false;
        }

        String decodedPassword = Utf8.decode(encryptor.decrypt(Hex.decode(encodedPassword)));
        return decodedPassword.equals(rawPassword.toString());
    }
}
