package manager;

import data.Subject;
import data.User;
import graph.DAG;
import persistence.DynamoDBManager;
import persistence.UserRecord;

import java.util.*;

public class UserManager {



    static class UserManagerHolder
    {
        static UserManager instance = new UserManager();
    }


    public static UserManager getInstance()
    {
        return UserManagerHolder.instance;
    }

    private UserManager()
    {

    }






    Map<String,Integer> userids = new HashMap<>();

    List<String> useridList = new ArrayList<>();

    DAG followers = new DAG(100);
    DAG follows = new DAG(100);
    DAG followsSubject = new DAG(100);

    DynamoDBManager manager = new DynamoDBManager();


    public void addUser(User user)
    {
        if (!userids.containsKey(user.getId()))
        {

            int userNum = useridList.size();
            useridList.add(user.getId());
            userids.put(user.getId(), userNum);

            UserRecord userRecord = new UserRecord();
            userRecord.setUserId(user.getId());
            userRecord.setName(user.getName());
            manager.putUser(userRecord);

            // persist the user in database
        }
        else
        {
            System.out.println("User already exists " + user);
        }
    }

    public void deleteUser(User user)
    {

        if (userids.containsKey(user.getId()))
        {
            userids.remove(user.getId());
            useridList.remove(user.getId());

            UserRecord userRecord = manager.getUser(user.getId());
            manager.removeUser(userRecord);

            // delete user in database

            // let the user stay in the DAG as the next restart will remove it .


        }
        else
        {
            System.out.println("User not found " + user);
        }
    }

    public void addFollower(User self , User userToFollow)
    {
        int selfIndex = userids.get(self.getId());
        int userToFollowIndex = userids.get(userToFollow.getId());

        follows.addEdge(selfIndex,userToFollowIndex);
        followers.addEdge(userToFollowIndex,selfIndex);

        UserRecord userRecord = manager.getUser(self.getId());
        if (!userRecord.getFollows().contains(userToFollow.getId()))
        {
            userRecord.getFollows().add(userToFollow.getId());
            manager.putUser(userRecord);

            userRecord = manager.getUser(userToFollow.getId());
            userRecord.getFollowedBy().add(self.getId());
            manager.putUser(userRecord);
        }
        else
        {
            System.out.println("Follower already exists " + self + "  " + userToFollow);
        }



    }

    public void deleteFollower(User self , User userToFollow)
    {
        Integer selfIndex = userids.get(self.getId());
        if (selfIndex==null)
        {
            System.out.println("Relationship not found " + self + "  "+ userToFollow);
            return;
        }
        int userToFollowIndex = userids.get(userToFollow.getId());

        follows.removeEdge(selfIndex,userToFollowIndex);
        followers.removeEdge(userToFollowIndex,selfIndex);

        UserRecord userRecord = manager.getUser(self.getId());
        userRecord.getFollows().remove(userToFollow.getId());
        manager.putUser(userRecord);

        userRecord = manager.getUser(userToFollow.getId());
        userRecord.getFollowedBy().remove(self.getId());
        manager.putUser(userRecord);



    }

    public void addUserAsSubjectFollower(User self , Integer subjectIndex)
    {
        int selfIndex = userids.get(self.getId());
        followsSubject.addEdge(selfIndex,subjectIndex);

    }

    public void deleteUserAsSubjectFollower(User self , Integer subjectIndex)
    {
        int selfIndex = userids.get(self.getId());
        followsSubject.removeEdge(selfIndex,subjectIndex);

    }


}
