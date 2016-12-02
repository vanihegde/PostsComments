package com.tinroof.postcomments;


// Comment represents an item in the comments document
public class Comment {
	Long PostID;
	Long ID;
	String Name;
	String Email;
	String Body;
	@Override
	public String toString() {
		return "[PostID=" + PostID + ", ID=" + ID + ", Name=" + Name + ", Email=" + Email + ", Body=" + Body
				+ "]\n";
	}
}
