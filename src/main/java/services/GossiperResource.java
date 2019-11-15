package services;

import com.codahale.metrics.annotation.Timed;
import event.AddUser;


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

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class GossiperResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;




    public GossiperResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();



    }


    @POST
    @Timed
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public String exchange(AddUser request) {


       // return keyExchangeManager.processExchange(request);

        return null;


    }


 /*   @GET
    @Timed
    @Path("/claimkey")
    @Produces(MediaType.APPLICATION_JSON)
    public String exchange() {


        return rsaKeyHolder.getPublicKeyStr();

    }*/











}
