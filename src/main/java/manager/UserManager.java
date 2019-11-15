package manager;

import data.Subject;
import data.User;
import graph.DAG;
import persistence.DynamoDBManager;
import persistence.UserRecord;

import java.util.*;

public class UserManager {



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
    }

    public void addFollower(User self , User userToFollow)
    {
        int selfIndex = userids.get(self.getId());
        int userToFollowIndex = userids.get(userToFollow.getId());

        follows.addEdge(selfIndex,userToFollowIndex);
        followers.addEdge(userToFollowIndex,selfIndex);


    }

    public void deleteFollower(User self , User userToFollow)
    {
        int selfIndex = userids.get(self.getId());
        int userToFollowIndex = userids.get(userToFollow.getId());

        follows.removeEdge(selfIndex,userToFollowIndex);
        followers.removeEdge(userToFollowIndex,selfIndex);


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
