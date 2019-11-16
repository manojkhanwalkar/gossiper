package client;

import data.User;
import event.AddUser;

import event.DeleteUser;
import event.FollowUser;
import event.UnFollowUser;
import processor.FollowUserProcessor;
import util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClientVerifier {

    public static final String GossiperServiceUrl = "https://172.17.0.1:8480/";


    Connection connection;


    public ClientVerifier()
    {
        connection = new Connection(GossiperServiceUrl);

    }

    public void addUser(User user ) throws Exception
    {
        AddUser addUser = new AddUser();
        addUser.setUser(user);

        String response = connection.send(JSONUtil.toJSON(addUser),"create");

        System.out.println(response);
    }


    public void followUser(FollowUser followUser ) throws Exception
    {


        String response = connection.send(JSONUtil.toJSON(followUser),"follow");

        System.out.println(response);
    }

    public void unFollowUser(UnFollowUser followUser ) throws Exception
    {


        String response = connection.send(JSONUtil.toJSON(followUser),"unfollow");

        System.out.println(response);
    }

    public void deleteUser(User user) throws Exception
    {
        DeleteUser deleteUser = new DeleteUser();
        deleteUser.setUser(user);

        String response = connection.send(JSONUtil.toJSON(deleteUser),"delete");

        System.out.println(response);
    }







    public static void main(String[] args) throws Exception {



        ClientVerifier verifier = new ClientVerifier();

      /*  for (int i=0;i<26;i++)
            verifier.addUser(String.valueOf('A'+i));

        for (int i=0;i<26;i++)
            verifier.deleteUser(String.valueOf('A'+i)); */

      User user1 = new User("User11");
      User user2 = new User("User21");


        verifier.addUser(user1);
        verifier.addUser(user2);

        FollowUser followUser = new FollowUser();
        followUser.setSelf(user1);
        followUser.setTarget(user2);

        verifier.followUser(followUser);


        UnFollowUser unFollowUser = new UnFollowUser();
        unFollowUser.setSelf(user1);
        unFollowUser.setTarget(user2);

        verifier.unFollowUser(unFollowUser);





    }







}
