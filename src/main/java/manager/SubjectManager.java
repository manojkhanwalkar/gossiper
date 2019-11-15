package manager;

import data.Subject;
import data.User;
import graph.DAG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectManager {

    Map<String,Integer> subjectids = new HashMap<>();

    List<String> subjectidList = new ArrayList<>();

    DAG followers = new DAG(100);


    public void addSubject(Subject subject)
    {
        if (!subjectids.containsKey(subject.getId()))
        {

            int subjectNum = subjectidList.size();
            subjectidList.add(subject.getId());
            subjectids.put(subject.getId(), subjectNum);

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

            // let the user stay in the DAG as the next restart will remove it .


        }
    }

    public void addFollower(int selfIndex , Subject subjectToFollow)
    {

        int subjectToFollowIndex = subjectids.get(subjectToFollow.getId());


        followers.addEdge(subjectToFollowIndex,selfIndex);


    }




}
