package socialmedia;

public class EndorsePost extends Post {
    /* Attributes */
        private int parentPostID;

    /* Constructor */
        public EndorsePost(int accountID, String accountHandle, String contents, int parentPostID) throws InvalidPostException, AccountIDNotRecognisedException{
            /* initiliases an endorsement */
            super(accountID, accountHandle, contents);
            this.parentPostID = parentPostID;
        }

    /* Methods */
        public int getParentPostID(){
            /* returns the id of the parent post */
            return parentPostID;
        }

}