package com.tinroof.postcomments;

import java.util.ArrayList;

// HashMapEntry represents the object that gets indexed in the hashmap.
// This object will be indexed out of the post ID.
// This will contain both the post associated to the post ID and all the comments associated
// to the post ID.
public class HashMapEntry {
	Post post;
	ArrayList<Comment> comments;

	@Override
	public String toString() {
		return "Post: " + post + "\n Comments: " + comments;
	}
}
