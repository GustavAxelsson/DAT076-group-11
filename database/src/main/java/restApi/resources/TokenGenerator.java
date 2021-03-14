package restApi.resources;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.io.FileWriter;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
// cred to  adam bien http://jwtenizr.sh/ for insperation.
// https://connect2id.com/products/nimbus-jose-jwt/examples/jwt-with-rsa-signature
// https://github.com/tuxtor/microjwt-provider/blob/ea29528dc0eb607c021f12601f91a5293690ac63/src/main/java/com/nabenik/jwt/controller/TokenProviderResource.java
import static com.nimbusds.jose.JOSEObjectType.JWT;

public class TokenGenerator {

    public static String generateToken(
            String username,
            Set<String> roles,
            long userId) throws Exception { ;
        JSONArray jsonRoles = new JSONArray();
        jsonRoles.addAll(roles);
        JWSSigner signer = new RSASSASigner(getPrivateKey());

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("webshop-company")
                .claim("groups", jsonRoles)
                .claim("userId", userId)
                .jwtID("42")
                .issueTime(new Date())
                .expirationTime(new Date(new Date().getTime() + 60 * 10000))
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID("jwt.key").type(JWT).build(),
                claimsSet);

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    public static PrivateKey getPrivateKey() throws Exception {
        JSONParser parser = new JSONParser();
        InputStream stream = TokenGenerator.class.getResourceAsStream("/jwtenizr-config.json");
        Object obj = parser.parse(stream);
        JSONObject jsonObject = (JSONObject) obj;
        return readPrivateKey((String) jsonObject.get("privateKey"));
    }

    public static PrivateKey readPrivateKey(String privateKey) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(privateKey);
        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decodedKey));
    }
}

