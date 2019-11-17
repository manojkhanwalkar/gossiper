package services;

import com.codahale.metrics.annotation.Timed;
import data.UserInfo;
import data.Users;
import event.*;
import processor.Dispatcher;


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



    Dispatcher dispatcher = new Dispatcher();


    public GossiperResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();



    }


    @POST
    @Timed
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public String createUser(AddUser request) {


        dispatcher.dispatch(request);

       // return keyExchangeManager.processExchange(request);

        return "User added";


    }

    @POST
    @Timed
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(DeleteUser request) {


        dispatcher.dispatch(request);

        // return keyExchangeManager.processExchange(request);

        return "User deleted";


    }


    @POST
    @Timed
    @Path("/follow")
    @Produces(MediaType.APPLICATION_JSON)
    public String follow(FollowUser request) {


        dispatcher.dispatch(request);

        // return keyExchangeManager.processExchange(request);

        return "Follower added";


    }

    @POST
    @Timed
    @Path("/unfollow")
    @Produces(MediaType.APPLICATION_JSON)
    public String unfollow(UnFollowUser request) {


        dispatcher.dispatch(request);

        // return keyExchangeManager.processExchange(request);

        return "Follower removed";


    }

    @GET
    @Timed
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Users users() {


        return dispatcher.dispatch();




    }


    @POST
    @Timed
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfo user(GetUser user) {


        return dispatcher.dispatch(user);



    }

    @POST
    @Timed
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    public String post(AddPost post) {


         dispatcher.dispatch(post);

         return "posted";



    }


    @POST
    @Timed
    @Path("/deletepost")
    @Produces(MediaType.APPLICATION_JSON)
    public String post(DeletePost post) {


        dispatcher.dispatch(post);

        return "post deleted";



    }



 /*   @GET
    @Timed
    @Path("/claimkey")
    @Produces(MediaType.APPLICATION_JSON)
    public String exchange() {


        return rsaKeyHolder.getPublicKeyStr();

    }*/











}
