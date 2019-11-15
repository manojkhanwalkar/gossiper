package processor;

import event.AddPost;
import event.AddUser;
import event.Event;

public class AddUserProcessor implements Processor{


    public void process(Event event)
    {
        AddUser addUser = (AddUser)event;

    }
}
