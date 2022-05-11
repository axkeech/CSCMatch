/*
 References:
 Sorting a map: https://beginnersbook.com/2014/07/how-to-sort-a-treemap-by-value-in-java/
 */

import java.util.*;
import java.io.Serializable;

public class Member implements Serializable{
	//Instance Variables
	private static int MAX_TOP_MEMBERS = 5;
	private String name;
	private int year;
	private Map<String, Integer> interests;
	private Map<Member, Integer> potentialMatches;
	private MemberSet members;

	//Constructors
	public Member(String name, int year, MemberSet members)
	{
		this.name = name;
		this.year = year;
		this.members = members;
		this.interests = new HashMap<>();
		this.potentialMatches = new HashMap<Member, Integer>();
	}
	
	public void addInterest(int score, String name)
	{
		InterestMap interestMap = members.getInterestMap();
		Set<Member> interestedMembers = potentialMatches.keySet();
		Set<Member> combinedMembers = new HashSet<>();
		
		interests.put(name, score);
		interestMap.addMemberToInterest(this, name);

		combinedMembers.addAll(interestedMembers);
		combinedMembers.addAll (members.getInterestMap().getMembersWithInterest(name));

		for (Member member : combinedMembers) {
			if (member != this) {
				updateMatch(member);
				member.addPotentialMatch(this);
			}
		}
	}
	
	public Set listInterests()
	{
		return (Set<String>)interests.keySet();
	}
	
	public void updateMatch(Member member)
	{
		int score = 0;
		score = calcMatchScore(member);
		potentialMatches.put(member, score);

	}

	public void addPotentialMatch(Member member) {
		potentialMatches.put(member, calcMatchScore(member));
	}

	private int calcMatchScore(Member member) {
		int score = 0, tmp = 0;
		Set<String> matchInterests = member.listInterests();

		System.out.println(member.getName());
		for(String interest: matchInterests) {
			if (interests.containsKey(interest)) {
				tmp = (getMemberInterest(interest) * member.getMemberInterest(interest));
				System.out.println("Adding " + tmp + " for mutual interest in " + interest + ".");
				score += tmp;
			} else {
				tmp = (int)Math.floor((member.getMemberInterest(interest) / 2));
				System.out.println("Adding " + tmp +" for non-mutual interest in " + interest + ".");
				score += tmp;
			}
		}

		return score;
	}

	public List<Member> getTopMatches()
	{
		Map sortedMap = sortByValues(potentialMatches);
		List topMatches = new ArrayList<Member>();
		int count = 0;

		for(Member member: (Set<Member>)sortedMap.keySet()) {
			if (count < MAX_TOP_MEMBERS) {
				topMatches.add(member);
				//System.out.println(member.toString());
				count++;
			}
		}

		return topMatches;
	}

	public void removeMatch(Member member) {
		potentialMatches.remove(member);
	}

	public int getMemberInterest(String interest) {
		return interests.get(interest);
	}

	public Set<String> getAllInterests() {
		return interests.keySet();
	}
	
	//Getters and Setters
	public String getName()
	{
		return name;
	}
	
	public void setName()
	{
		this.name = name;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public void setYear()
	{
		this.year = year;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(String.format("-----------------------------------\n"));
		result.append(String.format("| %-10s| %-20s|\n", "Name", name));
		result.append(String.format("|---------------------------------|\n"));
		result.append(String.format("| %-10s| %-20s|\n", "Year", year));
		result.append(String.format("|---------------------------------|\n"));
		result.append(String.format("|          Interests              |\n"));
		result.append(String.format("|---------------------------------|\n"));

		for(String interest: interests.keySet()) {
			result.append(String.format("| %-19s|  %-10s|\n", interest, interests.get(interest)));
		}
		result.append(String.format("|---------------------------------|\n"));
		result.append(String.format("|            Matches              |\n"));
		result.append(String.format("|---------------------------------|\n"));
		for(Member m: getTopMatches()) {
			result.append(String.format("| %-19s| %-10s |\n", m.getName(), potentialMatches.get(m)));
			//result.append(m.name + " score: " + potentialMatches.get(m));
		}
		result.append(String.format("-----------------------------------\n"));

		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	// https://beginnersbook.com/2014/07/how-to-sort-a-treemap-by-value-in-java/
	public static <K, V extends Comparable<V>> Map<K, V>
	sortByValues(final Map<K, V> map) {
		Comparator<K> valueComparator =
				new Comparator<K>() {
					public int compare(K k1, K k2) {
						int compare =
								map.get(k1).compareTo(map.get(k2));
						if (compare == 0)
							return 1;
						else
							return -compare;
					}
				};
		Map<K, V> sortedByValues =
				new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}
	
}