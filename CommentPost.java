package socialmedia;

public class CommentPost extends Post{
    /* Attributes */
        private int parentPostID;
        private String parentPostType;

    /* Constructor */
        public CommentPost(int accountID, String accountHandle, String contents, int parentPostID, String parentPostType) throws InvalidPostException, AccountIDNotRecognisedException{
            /* initialises a comment */
            super(accountID, accountHandle, contents);
            this.parentPostID = parentPostID;
        }
    
    /* Methods */
        public int getParentPostID(){
            /* returns the id of the parent post */
            return parentPostID;
        }

        public String getParentPostType(){
            /* returns the type of the parent post */
            return parentPostType;
        }
    
    
}