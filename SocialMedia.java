package socialmedia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * SocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {
	/* creates ArrayLists that holds all of the objects that are to be created */
	ArrayList<Account> accounts = new ArrayList<>();
	ArrayList<Post> posts = new ArrayList<>();
	ArrayList<Post> allPosts = new ArrayList<>();
	ArrayList<EndorsePost> allEndorsements = new ArrayList<>();
	ArrayList<CommentPost> allComments = new ArrayList<>();

	
	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		/* creates an account with a given handle which has no description and returns its id, if handle is not allowed then an error is thrown */
		accounts.add(new Account(handle));		//adds Account objects straight into the ArrayList
		int i = accounts.size() - 1;			//finds the object in the ArrayList to return its ID
		int id = accounts.get(i).getID();
		return id;
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		/* creates an account with a given handle and description and returns its id, if handle is not allowed then an error is thrown */
		accounts.add(new Account(handle, description));			//adds Account objects straight into the ArrayList
		int i = accounts.size() - 1;					//finds the object in the ArrayList to return its ID
		int id = accounts.get(i).getID();
		return id;
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		/* removes an account with a given id, if account does not exist then an error is thrown */
				
		for (int i = 0; i<allPosts.size(); i++){			//searches through the ArrayList in order to find all posts with the correct account id
			if (allPosts.get(i).getAccountID() == id){
				allPosts.remove(i);				//removes the post from the ArrayList once found
				break;
			}
		}

		for (int i = 0; i<accounts.size(); i++){			//searches through the ArrayList in order to find the correct account
			if (accounts.get(i).getID() == id){
				String handle = accounts.get(i).getHandle();	//removes the account's id and handle from the list of current ids and handles
				accounts.get(i).removeID(id);
				accounts.get(i).removeHandle(handle);
				accounts.remove(i);				//removes the account from the ArrayList once found
				break;
			}
		}
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		/* removes an account with a given handle, if account does not exist then an error is thrown */
				
		for (int i = 0; i<allPosts.size(); i++){			//searches through the ArrayList in order to find all posts with the correct account handle
			if (allPosts.get(i).getAccountHandle() == handle){
				allPosts.remove(i);				//removes the post from the ArrayList once found
				break;
			}
		}

		for (int i = 0; i<accounts.size(); i++){			//searches through the ArrayList in order to find the correct account
			if (accounts.get(i).getHandle() == handle){
				int id = accounts.get(i).getID();		//removes the account's id and handle from the list of current ids and handles
				accounts.get(i).removeID(id);
				accounts.get(i).removeHandle(handle);
				accounts.remove(i);				//removes the account from the ArrayList once found
				break;
			}
		}

	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle) throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		/* takes an account with a given handle and updates the handle to the new handle (also given), if either handle is invalid then an error is thrown */
		for (int i = 0; i<accounts.size(); i++){			//seaches through the ArrayList in order to find the correct account
			if (accounts.get(i).getHandle() == oldHandle){
				accounts.get(i).setHandle(newHandle);		//once the specified account has been found, the method 'setHandle' is called to change the handle attribute
			}
		}

	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		/* takes an account with a given handle and updates the description (also given), if handle is invalid then an error is thrown */
		for (int i = 0; i<accounts.size(); i++){			//searches through the ArrayList in order to find the correct account
			if (accounts.get(i).getHandle() == handle){
				accounts.get(i).setDescription(description);	//once the specified account has been found, the method 'setDescription' is called to change the description attribute
			}
		}

	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		/* compiles and returns a list of facts about the account with given handle, if handle is invalid then an error is thrown */
		int noOfPosts = 0;		//initialises all the local variables that will be used
		int noOfEndorsements = 0;
		String ID = "";
		String description = "";

		for (int i = 0; i<accounts.size(); i++){		//searches for the account details of the desired account and saves them in their respective variables
			if (accounts.get(i).getHandle() == handle){
				ID = Integer.toString(accounts.get(i).getID());
				description = accounts.get(i).getDescription();
			}
		}

		for (int i = 0; i<posts.size(); i++){			//searches for posts made by the account
			if (posts.get(i).getAccountHandle() == handle){
				++noOfPosts;				//increments the number of posts that the user has made
				noOfEndorsements += posts.get(i).getNoOfEndorsements();		//for each post, the number of endorsements is added to a total sum
			}
		}
		
		String information = String.format("ID: %s \nHandle: %s \nDescription: %s \nPost Count: %i \nEndorse Count: %i", ID, handle, description, noOfPosts, noOfEndorsements);

		return information;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		/* creates a new post with given author's account handle and post contents and returns the post id, if handle or contents is invalid then an error is thrown */
		for (int i = 0; i<accounts.size(); i++){		//searches for the accountID of the user looking to make the post
			if (accounts.get(i).getHandle() == handle){
				int accountID = accounts.get(i).getID();	//adds post object straight into an ArrayList
				posts.add(new Post(accountID, handle, message));
				break;
			}
		}
				
		int j = posts.size() - 1;				//finds the postID of the post to return it
		allPosts.add(posts.get(j));
		int postID = posts.get(j).getID();
		return postID;
	}

	@Override
	public int endorsePost(String handle, int id) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		/* creates an endorsement with  given author's account handle and post id to add endorsement to and returns the endorsement id, if handle or post id is invalid or the post id is an endorsement then an error is thrown */
		int accountID = 0;			//initialises the variables
		String message = "";
		Post post;
		int endorsementID = 0;

		for (int i = 0; i<accounts.size(); i++){		//searches for the accountID of the user looking to endorse the post
			if (accounts.get(i).getHandle() == handle){
				accountID = accounts.get(i).getID();
				break;
			}
		}

		for (int i = 0; i<posts.size(); i++){				//searches for the post to be endorsed
			if (posts.get(i).getID() == id){
				String type = allPosts.get(i).getClass().getName();		//gets the type of post to be commented on e.g comment
				if (type != "EndorsePost"){
					message = posts.get(i).getContents();		//gets the contents of the post being endorsed
					post = posts.get(i);				//puts the post object into a temporary variable
					String contents = String.format("EP@%s: %s", handle, message);	//puts the information into a formatted string
					allEndorsements.add(new EndorsePost(accountID, handle, contents, id));	//adds endorsement object straight into the correct ArrayList
					int j = allEndorsements.size() - 1;
					endorsementID = allEndorsements.get(j).getID();
					post.addEndorsement(allEndorsements.get(j));	//puts that endorsement into an ArrayList in the post object					
				}else{
					throw new NotActionablePostException("Cannot comment on an endorsement");	//prevents user from endorsing an endorsement
				}
			}
		}
		return endorsementID;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		/* creates a comment with  given author's account handle, post id to add comment to, and comment contents and returns the comment id, if handle or post id is invalid or the post id is an endorsement then an error is thrown */
		int accountID = 0;		//initialises the variables
		Post post;
		int commentID = 0;

		for (int i = 0; i<accounts.size(); i++){		//searches for the accountID of the user looking to comment
			if (accounts.get(i).getHandle() == handle){
				accountID = accounts.get(i).getID();
				break;
			}
		}
	
		for (int i = 0; i<allPosts.size(); i++){			//searches for the post to be commented
			if(allPosts.get(i).getID() == id){
				String type = allPosts.get(i).getClass().getName();		//gets the type of post to be commented on e.g comment
				if (type != "EndorsePost"){
					post = allPosts.get(i);					//puts the post object into a temporary variable
					allComments.add(new CommentPost(accountID, handle, message, id, type));		//adds comment object straight into the ArrayList
					int j = allComments.size() - 1;			//gets the id of the comment to return it
					commentID = allComments.get(j).getID();
					allPosts.add(allComments.get(j));
					post.addComment(allComments.get(j));		//adds comment to ArrayList in object that stores all of its comments
					return commentID;
				}else{
					throw new NotActionablePostException("Cannot comment on an endorsement");	//prevents user from commenting on an endorsement
				}
			}
		}
		return commentID;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		/* deletes post and all child comments/endorsements with given post id, if id is invalid then an error is throwns */
		
		int IDtoDelete = 0;
		
		for (int i = 0; i<allPosts.size(); i++){		//searches for the post id in all ArrayLists and if it is found then it is removed from it
			if (allPosts.get(i).getID() == id){
				allPosts.remove(i);
				break;
			}
		}
		for (int i = 0; i<posts.size(); i++){			//iterates through all posts to try and locate the post to delete
			if (posts.get(i).getID() == id){		//if the post to delete is found then remove all child comments too
				ArrayList<CommentPost> comments = allComments.get(i).getComments();
				for(int j = 0; j<comments.size(); j++){
					IDtoDelete = comments.get(i).getID();
					deletePost(IDtoDelete);			//recursively delete comments aka "remove the comments on the comments"
				}
				posts.remove(i);
				break;
			}
		}
		for (int i = 0; i<allComments.size(); i++){		//iterates through all comments to try and locate the post to delete
			if (allComments.get(i).getID() == id){		//if the post to delete is found then remove all child comments too
				ArrayList<CommentPost> comments = allComments.get(i).getComments();
				for(int j = 0; j<comments.size(); j++){
					IDtoDelete = comments.get(i).getID();
					deletePost(IDtoDelete);						//recursively delete comments aka "remove the comments on the comments"
				}			
				allComments.remove(i);
				break;
			}
		}
		for (int i = 0; i<allEndorsements.size(); i++){		//iterates through all comments to try and locate the post to delete
			if (allEndorsements.get(i).getID() == id){		//if the post to delete is found then remove
				allEndorsements.remove(i);
				break;
			}
		}

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		/* compiles and returns a list of facts about the post with id, if id is invalid then an error is thrown */
		for (int i = 0; i<posts.size(); i++){
			if (posts.get(i).getID() == id){				//searches for the post in the ArrayList 
				String postContents = posts.get(i).getContents();	//gets all the relevent detailss of the post
				String account = posts.get(i).getAccountHandle();
				int noOfComments = posts.get(i).getNoOfComments();
				int noOfEndorsements = posts.get(i).getNoOfEndorsements();
				
				String post = String.format("ID: %i\nAccount: %s\nNo. Endorsements: %i | No. Comments: %i\n%s", id, account, noOfEndorsements, noOfComments, postContents);	//puts all the details into a formatted string
				
				return post;	//returns the information
			}
		}
		return null;
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException {
		/* compiles and returns the thread of comments on a post with given id, if id is invalid then an error is thrown */
		StringBuilder postChildrenDetails = new StringBuilder();		//creates a stringbuilder object
		for (int i = 0; i<posts.size(); i++){
			if (posts.get(i).getID() == id){				//finds the post to be shown
				postChildrenDetails.append(showIndividualPost(id));	//calls 'showIndividualPost' to add the posts information to the stringbuilder object
				postChildrenDetails.append("\n");
				ArrayList<CommentPost> comments = posts.get(i).getComments();	//puts all of the comments of a post into a temporary ArrayList
				for (int j = 0; j<comments.size();j++){
					postChildrenDetails.append(showPostChildrenDetails(comments.get(i).getID()));	//for each of the comments the process is repeated
				}
				return postChildrenDetails;		//returns the stringbuilder
			}
		}
		return null;
	}

	@Override
	public int getNumberOfAccounts() {
		/* returns the number of accounts in the ArrayList */
		return accounts.size();
	}

	@Override
	public int getTotalOriginalPosts() {
		/* returns the number of posts in the ArrayList */
		return posts.size();
	}

	@Override
	public int getTotalEndorsmentPosts() {
		/* returns the number of endorsements in the ArrayList */
		return allEndorsements.size();
	}

	@Override
	public int getTotalCommentPosts() {
		/* returns the number of comments in the ArrayList */
		return allComments.size();
	}

	@Override
	public int getMostEndorsedPost() {
		/* finds the most endorsed post in the system and returns its id */
		int postID = 0;			//initialises the variables
		int mostEndorsements = 0;	
		for (int i = 0; i<posts.size(); i++){		//searches through all the posts in the ArrayList
			if (mostEndorsements < posts.get(i).getNoOfEndorsements()){	//compares the number of endorsements for each post
				mostEndorsements = posts.get(i).getNoOfEndorsements();	//if the number of endorsements is greater than the current highest number of endorsements, the two variables are updated
				postID = posts.get(i).getID();
			}
		}
		return postID;
	}

	@Override
	public int getMostEndorsedAccount() {
		/* finds the most endorsed account in the system and returns its id */
		int mostEndorsedAccount = 0;	//initialises the variables
		int noOfEndorsements = 0;
		int temp = 0; 
		for (int i = 0; i<accounts.size(); i++){	//iterates through all the accounts
			for(int j = 0; j<posts.size(); j++){	//searches through all of the posts to see if the current account has made any posts
				if (posts.get(j).getAccountID() == accounts.get(i).getID()){
					temp += posts.get(j).getNoOfEndorsements();	//if the account has made posts, the number of endorsements is added to the variable 'temp'
				}
			}
			if (temp > noOfEndorsements){		//once the for loop has been exited, the value of 'temp' is compared with the current highest number of endorsements
				noOfEndorsements = temp;	//if 'temp' is higher, the variables are updated accordingly
				mostEndorsedAccount = accounts.get(i).getID();
			}

		}
		return mostEndorsedAccount;
	}

	@Override
	public void erasePlatform() {
		/* empties this platform of its contents and resets all internal counters.  */
		accounts.clear();
		posts.clear();
		allPosts.clear();
		allEndorsements.clear();
		allComments.clear();

		Account.accountIDs.clear();
		Account.handles.clear();
	}

	@Override
	public void savePlatform(String filename) throws IOException {
		/* saves the platform into a given filename, if IO goes wrong then an errror is thrown */
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
		out.writeObject(this);
		out.close();
	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		/* loads platform from a given string name, if name is invalid then an error is thrown */

		//gonna be so honest and say idk how to do this fr

		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream ois = new ObjectInputStream(fis);

		Object SocialMedia = (Object) ois.readObject();

		ois.close();
	}

}