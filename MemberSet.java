import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class MemberSet implements Serializable {
    private Set<Member> memberSet = new LinkedHashSet<Member>();
    private InterestMap<String, Member[]> interestMap;

    public MemberSet(InterestMap<String, Member[]> interestMap) {
        this.interestMap = interestMap;
    }

    // TODO - add error handling
    public void addMember(Member member) {
        System.out.println("Adding " + member.getName() + ".");
        memberSet.add(member);
    }

    // TODO - add error handling
    public void removeMember(Member member) {
        Set<String> interests = member.getAllInterests();
        Set<Member> matches;

        for (String interest: interests) {
            matches = interestMap.getMembersWithInterest(interest);
            for(Member m: matches) {
                m.removeMatch(member);
            }
        }
        memberSet.remove(member);
    }

    public Set getAllMembers() {
        return memberSet;
    }
    
    public boolean isEmpty() {
    	return memberSet.isEmpty();
    }

    // Looks for a Member in the Member set using their name and
    // returns the Member. Returns null if Member does not exist
    public Member getMemberByName(String name) {
        Member member = null;

        for(Member myMember: memberSet) {
            if(name.equals(myMember.getName())) member = myMember;
        }

        return member;
    }

    // Checks if Member is part of MemberSet
    public Boolean hasMemberByName(String  name) {
    	Boolean hasMember = false;

        for(Member member: memberSet) {
            if (name.equalsIgnoreCase(member.getName())) {
                hasMember = true;
            }
        }

        return hasMember;
    }

    public InterestMap getInterestMap() {
        return interestMap;
    }

    @Override
    public String toString() {
        StringBuilder members = new StringBuilder();
        for(Member member: memberSet) {
            members.append("\n");
            members.append(member.toString());
        }

        //System.out.println(members);
        return members.toString();
    }

}