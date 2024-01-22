package socialmedia;

import java.util.ArrayList;


public class Post{

    /* attributes */
        private int ID;
        private int accountID;
        private String accountHandle;
        private String contents;
        private static int numberOfPosts = 0;

        private ArrayList<CommentPost> comments = new ArrayList<>();
        private ArrayList<EndorsePost> endorsements = new ArrayList<>();

    /* constructors */
        public Post(int accountID, String accountHandle, String contents) throws InvalidPostException, AccountIDNotRecognisedException{
            /* initialises a post with given author id, author handle and post contents */
            this.ID = ++numberOfPosts;                  //sets the post id to be incremental from the last post
            if(validateAccountID(accountID)){           //checks that the given author id exists as an account
                this.accountID = accountID;
            }else{
                throw new AccountIDNotRecognisedException("AccountID is invalid");
            }
            this.accountHandle = accountHandle;
            if(contents.length() <= 100){               //ensures the given post contents contains up to 100 characters
                this.contents = contents;
            }else{
                throw new InvalidPostException("Post contains too many characters");
            }

        }

    /* methods */
        public int getID(){
            /* returns the post id */
            return this.ID;
        }

        public int getAccountID(){
            /* returns the post author's account id */
            return this.accountID;
        }

        public String getAccountHandle(){
            /* returns the post author's accout handle */
            return this.accountHandle;
        }

        public String getContents(){
            /* returns the post contents */
            return this.contents;
        }

        public void setContents(String contents){
            /* sets the post contents */
            this.contents = contents;
        }

        public boolean validateAccountID(int accountID){
            /* check if accountID exists in accounts id arraylist */

            ArrayList<Integer> accountIDs = Account.getAccountIDs();
            for (int id : accountIDs) {
                if (id == accountID){               //iterates through the current ids and checks if the id exists
                    return true;                    //if id exists, true is returned
                }
            }
            return false;                           //if id does not exist, false is returned
        }

        public void addComment(CommentPost Post){
            /* adds a comment to this post */
            comments.add(Post);
        }

        public void addEndorsement(EndorsePost Post){
            /* adds an endorsement to this post */
            endorsements.add(Post);
        }

        public int getNoOfComments(){
            /* returns the number of comments on this post */
            return comments.size();
        }

        public int getNoOfEndorsements(){
            /* returns the number of endorsements on this post */
            return endorsements.size();
        }

        public ArrayList<CommentPost> getComments(){
            /* returns comments on this post */
            return comments;
        }
}