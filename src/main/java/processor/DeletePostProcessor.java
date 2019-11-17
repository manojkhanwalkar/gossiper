package processor;

import event.AddPost;
import event.DeletePost;
import event.Event;
import manager.PostManager;
import manager.UserManager;

public class DeletePostProcessor {

   // UserManager userManager = UserManager.getInstance();

    PostManager postManager = PostManager.getInstance();
    public void process(Event event)
    {
        DeletePost deletePost = (DeletePost)event;

        postManager.deletePost(deletePost.getPost());

      //  userManager.queuePost(addPost.getPost());


        // Dont delete from user queue , on retrieval , just ignore the posts that have been deleted.



    }
}
