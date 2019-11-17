package processor;


import data.Users;
import event.AddUser;
import event.Event;
import manager.UserManager;

public class GetUsersProcessor {

    UserManager userManager = UserManager.getInstance();

    public Users process()
    {


        return userManager.getUsers();

    }
}
