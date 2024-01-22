package socialmedia;

import java.util.ArrayList;

public class Account {
	static ArrayList<Integer> accountIDs = new ArrayList<Integer>();
	static ArrayList<String> handles = new ArrayList<String>();
	
        /* attributes */
		private int ID;
		private String handle;
		private String description;

	/* constructors */
		public Account(String handle){
			/* initialises an account with given handle */
			int id = createAccountID();
			this.ID = id;
			accountIDs.add(id);		//adds the account's id to the list of currently used ids
			this.handle = handle;
		}

		public Account(String handle, String description){
			/* initialises an account with a given handle and description */
			this.ID = createAccountID();
			this.handle = handle;
			this.description = description;
		}

	/* methods */
		public int createAccountID(){
			/* creates and returns a unique random 5-digit account id */
			double accountID = (Math.floor(Math.random() * 90000) + 10000);		//generates a number between 10000 and 99999
			int intAccountID = (int) accountID;					//converts number to an integer
			for (int id : accountIDs){
				if (intAccountID == id){					//iterates through the current ids and checks if the generated id already exists
					intAccountID = createAccountID();			//if id already exists, retry
				}
			}
			return intAccountID;
		}

		public int getID(){
			/* returns the account's id */
			return (this.ID);
		}
	
		public static ArrayList<Integer> getAccountIDs(){
			/* returns the list of currently used accountIDs */
			return (accountIDs);
		}

		public String getHandle(){
			/* returns the account's id */
			return (this.handle);
		}

		public String getDescription(){
			/* returns the account's id */
			return (this.description);
		}

		public void setHandle(String handle){
			/* sets the account's handle to the given value */
			this.handle = handle;
		}

		public void setDescription(String description){
			/* sets the account's description to the given value */
			this.description = description;
		}
	
		public void removeID(int id){
			/* removes the account's id from the lists of current ids */
			for (int i=0; i<accountIDs.size(); i++) {
				if (accountIDs.get(i) == id){			//iterates through the list of current ids and checks if each element is the id being searched for
					accountIDs.remove(i);
				}
			}
		}
	
		public void removeHandle(String handle){
			/* removes the account's handle from the lists of current handles */
			for (int i=0; i<handles.size(); i++) {
				if (handles.get(i) == handle){			//iterates through the list of current handles and checks if each element is the handle being searched for
					handles.remove(i);
				}
			}
		}
}