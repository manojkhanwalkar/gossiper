package processor;

import event.DeleteUser;
import event.Event;
import event.FollowUser;
import manager.UserManager;

public class FollowUserProcessor {

    UserManager userManager = UserManager.getInstance();

    public void process(Event event)
    {
        FollowUser followUser = (FollowUser) event;


        userManager.addFollower(followUser.getSelf(),followUser.getTarget());

    }
}
