package processor;

import event.Event;
import event.FollowUser;
import event.UnFollowUser;
import manager.UserManager;

public class UnFollowUserProcessor {

    UserManager userManager = UserManager.getInstance();

    public void process(Event event)
    {
        UnFollowUser UnFollowUser = (UnFollowUser) event;


        userManager.deleteFollower(UnFollowUser.getSelf(),UnFollowUser.getTarget());

    }
}
