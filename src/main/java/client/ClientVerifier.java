package client;

import data.Post;
import data.Subject;
import data.User;
import data.Users;
import event.*;


import util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

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

    public void addSubject(Subject subject ) throws Exception
    {
        AddSubject addSubject = new AddSubject();
        addSubject.setSubject(subject);

        String response = connection.send(JSONUtil.toJSON(addSubject),"createSubject");

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


    public void deleteSubject(Subject subject) throws Exception
    {
        DeleteSubject deleteSubject = new DeleteSubject();
        deleteSubject.setSubject(subject);

        String response = connection.send(JSONUtil.toJSON(deleteSubject),"deleteSubject");

        System.out.println(response);
    }

    public Users getUsers() throws Exception
    {

        String response = connection.send("users");

        Users users = (Users)JSONUtil.fromJSON(response,Users.class);

        System.out.println(response);

        return users;
    }


    public void getUser(GetUser user) throws Exception
    {

        String response = connection.send(JSONUtil.toJSON(user),"user");

        System.out.println(response);
    }


    public void post(AddPost post) throws Exception
    {

        String response = connection.send(JSONUtil.toJSON(post),"post");

        System.out.println(response);
    }

    public void post(DeletePost post) throws Exception
    {

        String response = connection.send(JSONUtil.toJSON(post),"deletepost");

        System.out.println(response);
    }


    public void retrieve(RetrievePost post) throws Exception
    {

        String response = connection.send(JSONUtil.toJSON(post),"retrieve");

        System.out.println(response);
    }









    public static void main(String[] args) throws Exception {



        ClientVerifier verifier = new ClientVerifier();

      /*  for (int i=0;i<26;i++)
            verifier.addUser(String.valueOf('A'+i));

        for (int i=0;i<26;i++)
            verifier.deleteUser(String.valueOf('A'+i)); */

      User user1 = new User("User1191");
      User user2 = new User("User2191");


        verifier.addUser(user1);
        verifier.addUser(user2);


        Users users = verifier.getUsers();
        users.getUsers().stream().forEach(u->{

            GetUser getUser = new GetUser();
            getUser.setUserId(u);
            try {
                verifier.getUser(getUser);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        FollowUser followUser = new FollowUser();
        followUser.setSelf(user1);
        followUser.setTarget(user2);

        verifier.followUser(followUser);


    /*    UnFollowUser unFollowUser = new UnFollowUser();
        unFollowUser.setSelf(user1);
        unFollowUser.setTarget(user2);

        verifier.unFollowUser(unFollowUser); */


        for (int i=0;i<10;i++) {
            Post post = new Post();
            post.setId(UUID.randomUUID().toString());
            post.setMessage("New Post " + System.nanoTime());
            post.setPoster(user2);

            AddPost addPost = new AddPost();
            addPost.setPost(post);
            verifier.post(addPost);
        }

        RetrievePost retrievePost = new RetrievePost();
        retrievePost.setUser(user1);
        verifier.retrieve(retrievePost);


        Subject subject = new Subject("politics");
        verifier.addSubject(subject);

        Subject subject1 = new Subject("technology");
        verifier.addSubject(subject1);




       /* DeletePost deletePost = new DeletePost();
        deletePost.setPost(post);
        verifier.post(deletePost);*/



    }







}
