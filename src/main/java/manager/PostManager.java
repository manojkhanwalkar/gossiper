package manager;

import data.Post;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class PostManager {



    static class PostManagerHolder
    {
        static PostManager instance = new PostManager();
    }


    public static PostManager getInstance()
    {
        return PostManagerHolder.instance;
    }

    private PostManager()
    {

    }


    //TODO - implement a TTL cache that cleans up older posts

    /** Upper bound on posts held in memory; the oldest are evicted first. */
    static final int MAX_POSTS = 100_000;

    Map<String,Post> posts = new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Post> eldest) {
            return size() > MAX_POSTS;
        }
    };

    public void addPost(Post post)
    {
        posts.put(post.getId(),post);
    }

    public Post getPost(String postId)
    {
        return posts.get(postId);
    }

    public void deletePost(Post post) {

        posts.remove(post.getId());
    }

    public boolean isExists(String postId)
    {
        return posts.containsKey(postId);
    }

}
