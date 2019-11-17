package processor;

import data.UserInfo;
import data.Users;
import event.*;

public class Dispatcher {

    AddUserProcessor addUserProcessor = new AddUserProcessor();
    DeleteUserProcessor deleteUserProcessor = new DeleteUserProcessor();
    FollowUserProcessor followUserProcessor = new FollowUserProcessor();
    UnFollowUserProcessor unFollowUserProcessor = new UnFollowUserProcessor();
    GetUsersProcessor getUsersProcessor = new GetUsersProcessor();
    GetUserProcessor getUserProcessor = new GetUserProcessor();
    AddPostProcessor addPostProcessor = new AddPostProcessor();
    DeletePostProcessor deletePostProcessor = new DeletePostProcessor();

    public Users dispatch()
    {
        return getUsersProcessor.process();
    }

    public void dispatch(AddPost event)
    {
        addPostProcessor.process(event);
    }

    public UserInfo dispatch(GetUser event)
    {
        return getUserProcessor.process(event);
    }

    public void dispatch(AddUser event)
    {
        addUserProcessor.process(event);
    }

    public void dispatch(DeletePost event)
    {
        deletePostProcessor.process(event);
    }

    public void dispatch(DeleteUser event)
    {
        deleteUserProcessor.process(event);
    }

    public void dispatch(FollowSubject event)
    {
    }

    public void dispatch(FollowUser event)
    {
        followUserProcessor.process(event);
    }

    public void dispatch(UnFollowSubject event)
    {

    }

    public void dispatch(UnFollowUser event)
    {
        unFollowUserProcessor.process(event);
    }




}
