package processor;

import event.AddPost;
import event.Event;

public class AddPostProcessor implements Processor{


    public void process(Event event)
    {
        AddPost addPost = (AddPost)event;

    }
}
