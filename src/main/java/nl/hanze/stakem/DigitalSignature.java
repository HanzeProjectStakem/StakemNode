package nl.hanze.stakem;

import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DigitalSignature {

    /**
     * This method validates a digital signature.
     *
     * @param obj The JsonObject containing the transaction data and the digital signature.
     * @return true if the digital signature is valid, false otherwise.
     * @throws NoSuchAlgorithmException if the algorithm is not available.
     * @throws InvalidKeySpecException  if the key specification is invalid.
     * @throws InvalidKeyException      if the key is invalid.
     * @throws SignatureException       if the signature is invalid.
     */
    public boolean isValid(JsonObject obj) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        Signature ecdsaVerify = Signature.getInstance(obj.get("algorithm").getAsString());
        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(obj.get("senderAddress").getAsString()));
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        ecdsaVerify.initVerify(publicKey);
        ecdsaVerify.update(obj.get("amount").getAsString().getBytes(StandardCharsets.UTF_8));

        return ecdsaVerify.verify(Base64.getDecoder().decode(obj.get("signature").getAsString()));
    }
}
