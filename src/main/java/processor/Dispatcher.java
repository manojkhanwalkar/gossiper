package processor;

import data.*;
import event.*;
import manager.PostManager;
import manager.SubjectManager;
import manager.UserManager;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

public class Dispatcher {

    /** Longest accepted user/subject/post id or name. */
    public static final int MAX_ID_LENGTH = 256;

    /** Longest accepted post message. */
    public static final int MAX_MESSAGE_LENGTH = 10_000;

    /**
     * The managers are process-wide singletons backed by non-thread-safe collections
     * (HashMap, ArrayList, DAG). Jersey serves requests concurrently, so every
     * dispatch runs under this one lock to keep the in-memory indexes consistent.
     */
    private static final Object LOCK = new Object();

    UserManager userManager = UserManager.getInstance();

    PostManager postManager = PostManager.getInstance();

    SubjectManager subjectManager = SubjectManager.getInstance();

    public Users dispatch()
    {
        synchronized (LOCK) {
            return userManager.getUsers();
        }
    }


    public Subjects retrieveSubjects()
    {
        synchronized (LOCK) {
            return subjectManager.getSubjects();
        }
    }

    public void dispatch(AddPost event)
    {
        require(event, "request");
        Post post = event.getPost();
        require(post, "post");
        requireId(post.getId(), "post.id");
        requireText(post.getMessage(), "post.message", MAX_MESSAGE_LENGTH);
        require(post.getPoster(), "post.poster");
        require(post.getSubject(), "post.subject");

        synchronized (LOCK) {
            requireKnownUser(post.getPoster().getId());
            requireKnownSubject(post.getSubject().getId());

            postManager.addPost(post);

            userManager.queuePost(post);
        }
    }

    public Posts dispatch(RetrievePost event)
    {
        require(event, "request");
        require(event.getUser(), "user");

        synchronized (LOCK) {
            requireKnownUser(event.getUser().getId());
            return userManager.getPostsForUser(event.getUser().getId());
        }
    }

    public UserInfo dispatch(GetUser event)
    {
        require(event, "request");

        synchronized (LOCK) {
            requireKnownUser(event.getUserId());
            return userManager.getUser(event.getUserId());
        }
    }



    public SubjectInfo dispatch(GetSubject subject)
    {
        require(subject, "request");

        synchronized (LOCK) {
            requireKnownSubject(subject.getSubjectId());
            return subjectManager.getSubject(subject.getSubjectId());
        }
    }

    public void dispatch(AddUser event)
    {
        require(event, "request");
        User user = event.getUser();
        require(user, "user");
        requireId(user.getId(), "user.id");
        requireText(user.getName(), "user.name", MAX_ID_LENGTH);

        synchronized (LOCK) {
            userManager.addUser(user);
        }
    }

    public void dispatch(DeletePost event)
    {
        require(event, "request");
        require(event.getPost(), "post");
        requireId(event.getPost().getId(), "post.id");

        synchronized (LOCK) {
            postManager.deletePost(event.getPost());
        }

    }

    public void dispatch(DeleteUser event)
    {
        require(event, "request");
        require(event.getUser(), "user");
        requireId(event.getUser().getId(), "user.id");

        synchronized (LOCK) {
            userManager.deleteUser(event.getUser());
        }
    }

    public void dispatch(FollowSubject event)
    {
        // add user to subject relationship in user manager
        // add subject to user relationship in subject manager
        require(event, "request");
        require(event.getUser(), "user");
        require(event.getSubject(), "subject");

        synchronized (LOCK) {
            requireKnownUser(event.getUser().getId());
            requireKnownSubject(event.getSubject().getId());

            Integer subjectIndex = subjectManager.getSubjectIndex(event.getSubject().getId());
            Integer userIndex = userManager.getUserIndex(event.getUser());

            userManager.addUserAsSubjectFollower(event.getUser(), subjectIndex, event.getSubject());
            subjectManager.addFollower(userIndex, event.getSubject(), event.getUser());
        }
    }

    public void dispatch(FollowUser event)
    {
        require(event, "request");
        require(event.getSelf(), "self");
        require(event.getTarget(), "target");

        synchronized (LOCK) {
            requireKnownUser(event.getSelf().getId());
            requireKnownUser(event.getTarget().getId());

            userManager.addFollower(event.getSelf(), event.getTarget());
        }

    }

    public void dispatch(UnFollowSubject event)
    {
        require(event, "request");
        require(event.getUser(), "user");
        require(event.getSubject(), "subject");

        synchronized (LOCK) {
            requireKnownUser(event.getUser().getId());
            requireKnownSubject(event.getSubject().getId());

            Integer subjectIndex = subjectManager.getSubjectIndex(event.getSubject().getId());
            Integer userIndex = userManager.getUserIndex(event.getUser());

            userManager.deleteUserAsSubjectFollower(event.getUser(), subjectIndex, event.getSubject());
            subjectManager.deleteFollower(userIndex, event.getSubject(), event.getUser());
        }

    }

    public void dispatch(UnFollowUser event)
    {
        require(event, "request");
        require(event.getSelf(), "self");
        require(event.getTarget(), "target");

        synchronized (LOCK) {
            requireKnownUser(event.getSelf().getId());
            requireKnownUser(event.getTarget().getId());

            userManager.deleteFollower(event.getSelf(), event.getTarget());
        }
    }


    public void dispatch(AddSubject event)
    {
        require(event, "request");
        Subject subject = event.getSubject();
        require(subject, "subject");
        requireId(subject.getId(), "subject.id");
        requireText(subject.getName(), "subject.name", MAX_ID_LENGTH);

        synchronized (LOCK) {
            subjectManager.addSubject(subject);
        }
    }


    public void dispatch(DeleteSubject event)
    {
        require(event, "request");
        require(event.getSubject(), "subject");
        requireId(event.getSubject().getId(), "subject.id");

        synchronized (LOCK) {
            subjectManager.deleteSubject(event.getSubject());
        }
    }


    private static void require(Object value, String field)
    {
        if (value == null) {
            throw new BadRequestException(field + " is required");
        }
    }

    private static void requireId(String value, String field)
    {
        requireText(value, field, MAX_ID_LENGTH);
    }

    private static void requireText(String value, String field, int maxLength)
    {
        if (value == null || value.isEmpty()) {
            throw new BadRequestException(field + " is required");
        }
        if (value.length() > maxLength) {
            throw new BadRequestException(field + " is longer than " + maxLength + " characters");
        }
    }

    private void requireKnownUser(String userId)
    {
        requireId(userId, "user id");
        if (userManager.getUserIndex(userId) == null) {
            throw new NotFoundException("unknown user");
        }
    }

    private void requireKnownSubject(String subjectId)
    {
        requireId(subjectId, "subject id");
        if (subjectManager.getSubjectIndex(subjectId) == null) {
            throw new NotFoundException("unknown subject");
        }
    }

}
