/**
 * This class represents a player in One Night Ultimate Werewolf.
 */
package werewolf;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	
	private int ID;
	private static int lastAssignedID = 0;
	private Role role, origRole;
	private String origRoleStr;
	
	/**
	 * The constructor 
	 */
	public Player() {
		lastAssignedID++;
		ID = lastAssignedID;
		origRoleStr = "";
	}
	
	/**
	 * Assigns a role to the player
	 * @param r the role to assign to player 
	 */
	public void assignRole(Role r) {
		if(origRoleStr.equals("")) { //set originalRole only the first time of getting assigned a role (at the beginning of game)
			origRoleStr = r.getRoleStr();
			origRole = r;
		}
		role = r;
	}
	
//	public ArrayList<Integer> wakeUp(int numOfPlayers, Scanner in) {
//		
//		return origRole.wakeUp(numOfPlayers, ID, in);
//	}
	
	/**
	 * Gets ID number of player
	 * @return ID
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Gets role of the player
	 * @return role of the player
	 */
	public Role getRole() {
		return role;
	}
	
	/**
	 * Gets string name of role of the player
	 * @return string name of role of the player
	 */
	public String getRoleStr() {
		return role.getRoleStr();
	}
	
	/**
	 * Gets string name of the player's original role
	 * @return string name of player's original role
	 */
	public String getOrigRoleStr() {
		return origRoleStr;
	}
	
	/**
	 * Gets file name of ImageIcon of player's role
	 * @return string of file name of ImageIcon of player's role
	 */
	public String getImage() {
		return role.imageFile();
	}
}
