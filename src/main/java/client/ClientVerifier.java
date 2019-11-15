package client;

import event.AddUser;

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

    public void addUser() throws Exception
    {
        AddUser addUser = new AddUser();
        addUser.setName("A");

        String response = connection.send(JSONUtil.toJSON(addUser),"create");

        System.out.println(response);
    }





    public static void main(String[] args) throws Exception {



        ClientVerifier verifier = new ClientVerifier();

        verifier.addUser();




    }







}
