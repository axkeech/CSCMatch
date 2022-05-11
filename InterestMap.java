/*
 * InterestMap.java
 *
 * This class creates an InterestMap collect that stores a Map of Interest => Members
 */

import java.io.*;
import java.util.*;


public class InterestMap<K,V> implements Serializable
{
    private Map<K,Set<V>> interestMap;

    public InterestMap() {
        interestMap = new HashMap<K, Set<V>>();
    }

    // add a member to an interest
    public void addMemberToInterest(V member, K interest) {
        Set memberInterests = interestMap.get(interest);

        // if the interest doesn't have members
        // create an ArrayList
        if(memberInterests == null) {
            memberInterests = new HashSet<V>();
        }

        // Add member to the list
        memberInterests.add(member);

        // put the interest on the map
        interestMap.put(interest, memberInterests);

        // update member score for reach member in this interest
        //for(Object m: members) ((Member)m).updateMatches((ArrayList<Member>)members);
    }

    // remove member from interest
    public Boolean removeMemberFromInterest(V member, K interest) throws Exception {
        // get the list of members with the interest
        Set<V> members = interestMap.get(interest);
        try {
            // try to remove the member from the list
            members.remove(member);
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        // add updated member list back to interestMap
        interestMap.put(interest, members);

        // update member score for reach member in this interest
        // for(Object m: members) ((Member)m).updateMatches((ArrayList<Member>)members);

        return true;
    }

    // Get members with interest
    public Set getMembersWithInterest(K interest) {
        return ((Set<V>)interestMap.get(interest));
    }

    // Get a Set with all the interests
    public Set<K> getAllInterests() {
        return interestMap.keySet();
    }
}