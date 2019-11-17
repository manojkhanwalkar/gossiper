package processor;


import data.UserInfo;
import event.AddUser;
import event.Event;
import event.GetUser;
import manager.UserManager;

public class GetUserProcessor {

    UserManager userManager = UserManager.getInstance();

    public UserInfo process(Event event)
    {
        GetUser getUser = (GetUser)event;



       return  userManager.getUser(getUser.getUserId());

    }
}
