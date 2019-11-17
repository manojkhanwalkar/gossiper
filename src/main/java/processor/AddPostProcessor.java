package processor;

import event.AddPost;
import event.Event;
import manager.PostManager;
import manager.UserManager;

public class AddPostProcessor implements Processor{

    UserManager userManager = UserManager.getInstance();

    PostManager postManager = PostManager.getInstance();
    public void process(Event event)
    {
        AddPost addPost = (AddPost)event;

        postManager.addPost(addPost.getPost());

        userManager.queuePost(addPost.getPost());



    }
}
