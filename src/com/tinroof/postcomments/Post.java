package com.tinroof.postcomments;

// Post represents an item in the posts document
public class Post {
	public Long UserID;
	public Long  ID;
	public String Title;
	public String Body;
	@Override
	public String toString() {
		return "[UserID=" + UserID + ", ID=" + ID + ", Title=" + Title + ", Body=" + Body + "]\n";
	}
}