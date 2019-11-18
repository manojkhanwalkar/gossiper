package processor;

import data.Posts;
import data.UserInfo;
import event.AddPost;
import event.Event;
import event.RetrievePost;
import manager.PostManager;
import manager.UserManager;

public class RetrievePostProcessor {

    UserManager userManager = UserManager.getInstance();

    PostManager postManager = PostManager.getInstance();
    public Posts process(Event event)
    {
        RetrievePost retrievePost = (RetrievePost)event;

       // postManager.addPost(addPost.getPost());

        return userManager.getPostsForUser(retrievePost.getUser().getId());





    }
}
