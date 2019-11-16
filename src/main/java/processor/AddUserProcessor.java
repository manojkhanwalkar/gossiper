package processor;


import data.User;
import event.AddPost;
import event.AddUser;
import event.Event;
import manager.UserManager;

public class AddUserProcessor implements Processor{

    UserManager userManager = UserManager.getInstance();

    public void process(Event event)
    {
        AddUser addUser = (AddUser)event;



        userManager.addUser(addUser.getUser());

    }
}
