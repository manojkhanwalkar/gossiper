package services;

import com.codahale.metrics.annotation.Timed;
import util.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static util.ClaimValidator.convert;
import static util.ClaimValidator.isValidClaim;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class GossiperResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    String certFile = "/home/manoj/IdeaProjects/KryptIdEcoSysDemo/src/main/resources/bureaucert.pem";
    String privateFile = "/home/manoj/IdeaProjects/KryptIdEcoSysDemo/src/main/resources/bureaukey.der";

    String rsaCertFile = "/home/manoj/IdeaProjects/KryptIdEcoSysDemo/src/main/resources/bureaursacertificate.pem";
    String rsaPrivateFile = "/home/manoj/IdeaProjects/KryptIdEcoSysDemo/src/main/resources/bureaursakey.der";


    PublicKey publicKey;
    PrivateKey privateKey;
    X509Certificate certificate;

    KeyExchangeManager keyExchangeManager;


    RSAKeyHolder rsaKeyHolder;


    public GossiperResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();


        publicKey =  CryptUtil.getPublicKeyFromCertFile(certFile,"EC");
        certificate = CertUtil.getCertificate(certFile);
        privateKey =  CryptUtil.getPrivateKeyDerFromFile(privateFile,"EC");
        keyExchangeManager = new KeyExchangeManager(publicKey,privateKey,certificate);

        rsaKeyHolder = new RSAKeyHolder(rsaCertFile,rsaPrivateFile);
    }


    @POST
    @Timed
    @Path("/keyexchange")
    @Produces(MediaType.APPLICATION_JSON)
    public KeyExchange exchange(KeyExchange request) {


        return keyExchangeManager.processExchange(request);


    }


    @GET
    @Timed
    @Path("/claimkey")
    @Produces(MediaType.APPLICATION_JSON)
    public String exchange() {


        return rsaKeyHolder.getPublicKeyStr();

    }


    Random random = new Random();


    @POST
    @Timed
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public EncryptedMessage verify(EncryptedMessage request) {

        keyExchangeManager.verify(request);


        String requestStr = keyExchangeManager.decryptRequest(request);


               // Encrypted Verified claim to be processed here .
        EncryptedVerifiedClaim encryptedVerifiedClaim = (EncryptedVerifiedClaim) JSONUtil.fromJSON(requestStr,EncryptedVerifiedClaim.class);

        VerifiedClaim claim = convert(encryptedVerifiedClaim,rsaKeyHolder.getPrivateKey());

        isValidClaim(claim);

        System.out.println(claim);




   /*     VerifiedClaim claim = (VerifiedClaim) JSONUtil.fromJSON(requestStr,VerifiedClaim.class);

        isValidClaim(claim);

        System.out.println(claim);*/
      //  System.out.println(requestStr);

        CreditScore creditScore = new CreditScore();
        creditScore.setScore(random.nextInt(300) + 500);

        String responseStr = JSONUtil.toJSON(creditScore);
        EncryptedMessage response =  keyExchangeManager.encryptResponse(responseStr,request);
        keyExchangeManager.sign(response);
        return response;


    }









}
