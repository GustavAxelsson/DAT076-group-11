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
import static com.nimbusds.jose.JOSEObjectType.JWT;

public class TokenGenerator {

    private final static String CONFIGURATION_FILE = "jwtenizr-config.json";

    public static String generateToken(String username, Set<String> roles) throws Exception { ;
        JSONArray jsonRoles = new JSONArray();
        jsonRoles.addAll(roles);
        JWSSigner signer = new RSASSASigner(getPrivateKey());

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("webshop-company")
                .claim("groups", jsonRoles)
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

    static String readToken(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        return signedJWT.getJWTClaimsSet().toString();
    }

    public static PrivateKey readPrivateKey(String privateKey) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(privateKey);
        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decodedKey));
    }

    public static PrivateKey getPrivateKey() throws Exception {
        JSONParser parser = new JSONParser();
        //CypherService.class.getResourceAsStream("/privateKey.pem");

        InputStream stream = TokenGenerator.class.getResourceAsStream("/jwtenizr-config.json");
        Object obj = parser.parse(stream);
        JSONObject jsonObject = (JSONObject) obj;
        return readPrivateKey((String) jsonObject.get("privateKey"));
    }


    public static void main(String[] args) throws Exception {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        String token = generateToken("linus", roles);
        String command = "curl -i -H'Authorization: Bearer "+ token + "' http://localhost:8080/database-2.0/api/products/list-all-products";
        FileWriter fileWriter = new FileWriter("test-token.jwt");
        fileWriter.write(command);
        fileWriter.close();

        System.out.println(readToken(token));

    }
}

