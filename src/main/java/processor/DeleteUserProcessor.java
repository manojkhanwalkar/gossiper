package processor;

import data.User;
import event.AddUser;
import event.DeleteUser;
import event.Event;
import manager.UserManager;

public class DeleteUserProcessor {

    UserManager userManager = UserManager.getInstance();

    public void process(Event event)
    {
        DeleteUser deleteUser = (DeleteUser)event;


        userManager.deleteUser(deleteUser.getUser());

    }
}
