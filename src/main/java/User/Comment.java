package Comment;

import java.util.Date;

public class Comment {
	private String commentText;
	private Date commentDate;
	
	public Comment(String productName, double price, String productSize, String productColor, String userID, String commentText, Date commentDate) {
		super();
		this.commentText = commentText;
		this.commentDate = commentDate;
	}
	
	public String getUserID() {
		return "Masukkan User ID: ";
	}
	
	public String addComment(String productId) {
	    return "Komentar berhasil ditambahkan ke produk dengan ID: " + productId;
	}

	@Override
	public String toString() {
		return "Comment [commentText=" + commentText + ", commentDate=" + commentDate + "]";
	}
	
	
}
