import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu {

	public static void go(Scanner s) { // throws ClassNotFoundException, IOException {
		Boolean quit = false, validInput;
		String memberName, interestName, fileName, otherChoice;
		int gradeYear, interestScore, choice = 0;
		// Interest interest;
		Member member;
		MemberSet members = new MemberSet(new InterestMap());
		FileHandler fh = new FileHandler();
		String userin;
	
		while (!quit) {
	
			try {
				
				System.out.println("---------------------------------------------------------");
				System.out.println("|                       CSC MATCH                       |");
				System.out.println("|                        HOPPER                         |");
				System.out.println("|                 ABEL,ADAM,RANE,RASHAUN                |");
				System.out.println("---------------------------------------------------------");
				System.out.println("What would you like to do (pick a number)?\n 1. Load members\n "
						+ "2. Save members\n 3. List all members\n 4. Add a member\n "
						+ "5. Remove a member\n 6. List member\n 7. Add an interest to a member\n " + "8. Quit\n");
	
				choice = s.nextInt();
	
			} catch (InputMismatchException e) {
				System.out.println("That is not a valid choice. Please choose a valid integer.");
				s.nextLine();
				continue;
			}
			// Consume \n "new line"
			s.nextLine();
	
			switch (choice) {
			case 1: // Load members
				System.out.println("Enter the file name: ");
	
				try {
					fileName = s.nextLine();
	
					fh.filename(fileName);
					members = fh.load(fileName);
					System.out.println("File loaded successfully.");
				} catch (IOException | ClassNotFoundException e) {
					System.out.println(e.getLocalizedMessage());
				}
	
				break;
			case 2: // Save members
				if (!members.isEmpty()) {
					System.out.println("Enter file name: ");
					try {
						fileName = s.nextLine();
						fh.save(fileName, members);
						System.out.println("File saved.");
					} catch (IOException e) {
						System.out.println(e);
					}
				} else {
					System.out.println("There are no members to save.");
				}
				break;
			case 3: // Print all members
				if (members.isEmpty()) {
					System.out.println("There are no members to list.");
				} else {
					System.out.println(members.toString());
				}
				
				break;
			case 4: // Add new member
				memberName = null;
				validInput = false;
				while (!validInput) {
					System.out.println("Enter new member's name: ");
					memberName = s.nextLine();
					if (memberName.equals("")) {
						System.out.println("Invalid input.");
					} else {
						validInput = true;
					}
				}
	
				// Check if member exists
				if (members.hasMemberByName(memberName)) {
					System.out.println("This member exists, do you wish to overwrite them? [Y/N] ");
					otherChoice = s.nextLine();
	
					// ask user if they want to replace the member and delete then member from
					// the MemberMap if they do
					if (otherChoice.toLowerCase() == "y") {
						members.removeMember(members.getMemberByName(memberName));
					}
				}
	
				// Add the member if they don't exist
				if (!members.hasMemberByName(memberName)) {
					// Get the member's year
					validInput = false;
					while (!validInput) {
						System.out.println("What grade level is this member: ");
						gradeYear = s.nextInt();
						// Consume \n
						s.nextLine();
						if (gradeYear > 5 || gradeYear < 1) {
							System.out.println("Please enter a valid number for the grade level.");
						} else {
							validInput = true;
	
							// Add member to MemberMap
							members.addMember(new Member(memberName, gradeYear, members));
	
							System.out.println("A new member has been added. Would you like to save changes? [Y/N]");
							userin = s.nextLine();
	
							if (userin.equalsIgnoreCase("Y")) {
								System.out.println("Enter file name: ");
								try {
									fileName = s.nextLine();
									fh.save(fileName, members);
									System.out.println("File saved.");
								} catch (IOException e) {
									System.out.println(e);
								}
							}
						}
					}
	
					break;
				}
			case 5: // Remove member
				memberName = null;
				if (!members.isEmpty()) {
					System.out.println("Enter name of member to remove: ");
					memberName = s.nextLine();
					// Verify member exists
					if (!members.hasMemberByName(memberName)) {
						if(memberName.equals("")) {
							System.out.println("You must enter a valid name.");
						} else {
						
						System.out.println(memberName + " is not a member.");
						}
						
					} else {
	
						member = members.getMemberByName(memberName);
						if (member == null)
							System.out.println("Nope");
						else
							System.out.println(member.getName());
						// Remove member
						members.removeMember(member);
						System.out.println("You have removed a member. Would you like to save changes? [Y/N]");
						userin = s.nextLine();
	
						if (userin.equalsIgnoreCase("Y")) {
							System.out.println("Enter file name: ");
							try {
								fileName = s.nextLine();
								fh.save(fileName, members);
								System.out.println("File saved.");
							} catch (IOException e) {
								System.out.println(e);
							}
						}
	
					}
				} else {
					System.out.println("There are no members to remove.");
				}
				break;
			case 6: // Print single members
				if (!members.isEmpty()) {
					System.out.println("Enter name of member to list: ");
					memberName = s.nextLine();
					if (members.hasMemberByName(memberName)) {
						member = members.getMemberByName(memberName);
						System.out.println(member.toString());
					} else {
						if(memberName.equals("")) {
							System.out.println("You must enter a valid name.");
						} else {
						
						System.out.println("No such member.");
						}
						
					}
				} else {
					System.out.println("There are no members to list.");
				}
				break;
			case 7: // Add an interest to the member
				System.out.println("Enter member name: ");
				memberName = s.nextLine();
				// TODO - add error handling
				if (members.hasMemberByName(memberName)) {
					member = members.getMemberByName(memberName);
					System.out.println("What is the interest? ");
					interestName = s.nextLine();
					validInput = false;
	
					while (!validInput) {
						System.out.println("What is the interest level? ");
						interestScore = s.nextInt();
						// consume \n
						s.nextLine();
						if (interestScore > 10 || interestScore < 0) {
							System.out.println("Please enter a valid number for the interest level.");
						} else {
							validInput = true;
						}
	
						// add interest to member
						member.addInterest(interestScore, interestName);
	
						System.out.println("You have entered an interest. Would you like to save changes? [Y/N]");
						userin = s.nextLine();
	
						if (userin.equalsIgnoreCase("Y")) {
							System.out.println("Enter file name: ");
							try {
								fileName = s.nextLine();
								fh.save(fileName, members);
								System.out.println("File saved.");
							} catch (IOException e) {
								System.out.println(e);
							}
						}
					}
				} else {
					if(memberName.equals("")) {
						System.out.println("You must enter a valid name.");
					} else {
					
					System.out.println("This member does not exist.");
					}
				}
	
				break;
			case 8:
				if (!members.isEmpty()) {
					System.out.println("Before you quit, would you like to save? [Y/N]");
					userin = s.nextLine();
		
					if (userin.equalsIgnoreCase("Y")) {
						System.out.println("Enter file name: ");
						try {
							fileName = s.nextLine();
							fh.save(fileName, members);
							System.out.println("File saved.");
						} catch (IOException e) {
							System.out.println(e);
						}
					}
				}
				quit = true;
				break;
	
			default:
				System.out.println("Invalid choice, please pick again or type 8 to quit.");
			}
	
			
	
			
	
		}
		System.out.println("Goodbye.");
		quit();
	
	}

	private static void quit() {
		System.exit(0);

	}

}