package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import Database.DBConnection;

public class Comment {
	private String commentId;
	private String commentText;
	private String commentDate;
	private String userId;
	private String productId;
	
	
	public Comment(String commentId, String commentText, String commentDate, String userId, String productId) {
		super();
		this.commentId = commentId;
		this.commentText = commentText;
		this.commentDate = commentDate;
		this.userId = userId;
		this.productId = productId;
	}
	

	public String getCommentId() {
		return commentId;
	}

	public String getUserId() {
		return userId;
	}

	public String getProductId() {
		return productId;
	}

	@Override
	public String toString() {
		return "Comments :" + this.commentText
				+ "\nTanggal : " + this.commentDate;
	}
	
	
}
