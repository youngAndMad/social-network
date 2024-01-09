package socialapp.authservice.common.utils;

import com.nimbusds.jose.jwk.RSAKey;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import static socialapp.authservice.common.utils.AppConstants.KEY_PAIR_ALGORITHM;
import static socialapp.authservice.common.utils.AppConstants.KEY_PAIR_SIZE;

public class KeyManager {

    public RSAKey generateRsa() {
        var keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

    }

    public KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance(KEY_PAIR_ALGORITHM);
            keyPairGenerator.initialize(KEY_PAIR_SIZE);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
}
