package client;

import event.AddUser;

import event.DeleteUser;
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

    public void addUser(String name ) throws Exception
    {
        AddUser addUser = new AddUser();
        addUser.setName(name);

        String response = connection.send(JSONUtil.toJSON(addUser),"create");

        System.out.println(response);
    }


    public void deleteUser(String name) throws Exception
    {
        DeleteUser deleteUser = new DeleteUser();
        deleteUser.setName(name);

        String response = connection.send(JSONUtil.toJSON(deleteUser),"delete");

        System.out.println(response);
    }







    public static void main(String[] args) throws Exception {



        ClientVerifier verifier = new ClientVerifier();

        for (int i=0;i<26;i++)
            verifier.addUser(String.valueOf('A'+i));

        for (int i=0;i<26;i++)
            verifier.deleteUser(String.valueOf('A'+i));



    }







}
