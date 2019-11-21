package manager;

import data.Subject;
import data.Subjects;
import data.User;
import graph.DAG;
import persistence.DynamoDBManager;
import persistence.SubjectRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectManager {

    static class SubjectManagerHolder
    {
        static SubjectManager instance = new SubjectManager();
    }


    public static SubjectManager getInstance()
    {
        return SubjectManager.SubjectManagerHolder.instance;
    }

    private SubjectManager()
    {

    }

    Map<String,Integer> subjectids = new HashMap<>();

    List<String> subjectidList = new ArrayList<>();

    DAG followers = new DAG(100);

    DynamoDBManager manager = new DynamoDBManager();


    public void addSubject(Subject subject)
    {
        if (!subjectids.containsKey(subject.getId()))
        {

            int subjectNum = subjectidList.size();
            subjectidList.add(subject.getId());
            subjectids.put(subject.getId(), subjectNum);



              SubjectRecord subjectRecord = new SubjectRecord();
              subjectRecord.setSubjectId(subject.getId());
              subjectRecord.setName(subject.getName());

            manager.putSubject(subjectRecord);


            // persist the subject in database
        }
    }

    public void deleteSubject(Subject subject)
    {
        if (subjectids.containsKey(subject.getId()))
        {
            subjectids.remove(subject.getId());
            subjectidList.remove(subject.getId());

            // delete user in database

            SubjectRecord subjectRecord = manager.getSubject(subject.getId());
            manager.removeSubject(subjectRecord);


            // let the user stay in the DAG as the next restart will remove it .


        }
    }

    //TODO - implement getsubject
 /*   public UserInfo getUser(String userId) {

        UserRecord userRecord = manager.getUser(userId);
        UserInfo userInfo = new UserInfo();
        userInfo.setFollowedBy(userRecord.getFollowedBy());
        userInfo.setFollows(userRecord.getFollows());
        userInfo.setName(userRecord.getName());
        userInfo.setUserId(userRecord.getUserId());

        return userInfo;

    } */



    public void addFollower(int selfIndex , Subject subjectToFollow)
    {

        int subjectToFollowIndex = subjectids.get(subjectToFollow.getId());


        followers.addEdge(subjectToFollowIndex,selfIndex);


    }


    public Subjects getSubjects()
    {
        Subjects subjects = new Subjects();
        subjects.setSubjects((ArrayList)subjectidList);
        return subjects;
    }




}
