package com.tinroof.postcomments;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PostCommentsMain {

	public static void main(String[] args) {

		// Just for this particular URL, openStream() is resulting in IOException
		// with 403 code. It is working for other URLs. Hence in the interest of time,
		// I chose to save the contents of the two URLs in local JSON files and continued
		// with further tasks.
		
		// Please note that the code to download the file is present below and it is commented.

		/*
		 * URL postURL = null; try { postURL = new
		 * URL("https://jsonplaceholder.typicode.com/comments"); } catch
		 * (MalformedURLException e1) { e1.printStackTrace(); }
		 * 
		 * InputStream is = null; try { is = postURL.openStream(); } catch
		 * (IOException e) { e.printStackTrace(); } Scanner outScanner = new
		 * Scanner(is); if (outScanner.hasNextLine()) {
		 * System.out.println(outScanner.nextLine()); } outScanner.close();
		 */

		// Map to hold the object that is linked to the post ID.
		HashMap<Long, HashMapEntry> map = new HashMap<Long, HashMapEntry>();

		// Parse to parse both posts and comments
		JSONParser parser = new JSONParser();

		try {
			//
			// Parse the posts and create an entry in the map for each post identifier.
			//
			
			Object postsObj = parser.parse(new FileReader("posts.json"));
			JSONArray postsArray = (JSONArray) postsObj;
			Iterator<JSONObject> postsIterator = postsArray.iterator();
			while (postsIterator.hasNext()) {
				JSONObject post = (JSONObject) postsIterator.next();
				HashMapEntry entry = new HashMapEntry();
				
				// Create a Post object and put it in the HashMap indexed by the post ID.
				Post postEntry = new Post();
				postEntry.Body = (String) post.get("body");
				postEntry.ID = (Long) post.get("id");
				postEntry.UserID = (Long) post.get("userId");
				postEntry.Title = (String) post.get("title");
				entry.post = postEntry;
				map.put(postEntry.ID, entry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Iterate through the HashMap. For each post identifier in the HashMap,
		// iterate through the comments and see if the post ID matches. If a match
		// is found, place it in an arraylist. When all the comments are found, update
		// the HashMap entry with the comments along with the Post object. This way
		// when user provides the post ID, we can simply look up the HashMap without any
		// additional processing.
		
		// This also means that the preprocessing of data is expensive. But when the processed
		// data is referred, it will be extremely fast.
		
		// Also note that preprocessing of data can be optimized. The code here is written
		// only in the interest of time and limited scope.
		
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry item = (Map.Entry) it.next();
			Long idToMatch = (Long) item.getKey();
			HashMapEntry entry = (HashMapEntry) item.getValue();

			entry.comments = new ArrayList<Comment>();
			try {
				Object commentsObj = parser.parse(new FileReader("comments.json"));
				JSONArray commentsArray = (JSONArray) commentsObj;

				Iterator<JSONObject> commentsIterator = commentsArray.iterator();
				while (commentsIterator.hasNext()) {
					JSONObject comment = (JSONObject) commentsIterator.next();
					Long postID = (Long) comment.get("postId");
					if (postID == idToMatch) {
						Comment commentObj = new Comment();
						commentObj.Body = (String) comment.get("body");
						commentObj.PostID = postID;
						commentObj.Email = (String) comment.get("email");
						commentObj.Name = (String) comment.get("name");
						commentObj.ID = (Long) comment.get("id");
						entry.comments.add(commentObj);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			map.put(idToMatch, entry);
		}

		System.out.println("Welcome!");
		System.out.print("Enter the Post Identifier: ");

		Scanner scanner = new Scanner(System.in);
		int postId = scanner.nextInt();
		scanner.close();

		Long mapKey = new Long(postId);

		HashMapEntry entry = map.get(mapKey);
		System.out.println(entry);
	}
}
