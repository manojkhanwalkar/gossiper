package processor;

import event.*;

public class Dispatcher {

    AddUserProcessor addUserProcessor = new AddUserProcessor();
    DeleteUserProcessor deleteUserProcessor = new DeleteUserProcessor();

    public void dispatch(AddPost event)
    {

    }

    public void dispatch(AddUser event)
    {
        addUserProcessor.process(event);
    }

    public void dispatch(DeletePost event)
    {

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

    }

    public void dispatch(UnFollowSubject event)
    {

    }

    public void dispatch(UnFollowUser event)
    {

    }




}
