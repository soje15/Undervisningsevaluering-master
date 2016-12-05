package logic;

import service.DBWrapper;
import shared.Logging;
import shared.ReviewDTO;
import shared.StudentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentController extends UserController {

    private StudentDTO currentStudent;

    public StudentController() {
        super();
    }


    public static void main(String[] args) {

        StudentController controller = new StudentController();

        controller.getReviews(2);
        //controller.addReview(new ReviewDTO(1, 1, 1, "1", true));
    }


    public void loadStudent(StudentDTO currentStudent) {
        this.currentStudent = currentStudent;
    }

    //Metode til at indsætte et review i databasen
    public boolean addReview(ReviewDTO review) {
        System.out.println("Attempting to add review");
        boolean isAdded = true;

        try {
            Map<String, String> values = new HashMap();

            values.put("user_id", String.valueOf(review.getUserId()));
            values.put("lecture_id", String.valueOf(review.getLectureId()));
            values.put("rating", String.valueOf(review.getRating()));
            values.put("comment", review.getComment());
            values.put("is_deleted", "0");

            DBWrapper.insertIntoRecords("review", values);
            return isAdded;

        } catch (SQLException e) {
            e.printStackTrace();
            isAdded = false;
        }
        return isAdded;
    }


    public ArrayList<ReviewDTO> getReviews(int userId) {

        ArrayList<ReviewDTO> reviews = new ArrayList<ReviewDTO>();

        try {
            Map<String, String> params = new HashMap();
            params.put("user_id", String.valueOf(userId));
            params.put("is_deleted", "0");
            String[] attributes = {"id", "user_id", "lecture_id", "rating", "comment"};

            ResultSet rs = DBWrapper.getRecords("review", attributes, params, null, 0);

            while (rs.next()) {
                ReviewDTO review = new ReviewDTO();
                review.setId(rs.getInt("id"));
                review.setUserId(rs.getInt("user_id"));
                review.setLectureId(rs.getInt("lecture_id"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));

                reviews.add(review);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Logging.log(e,2,"Kunne ikke hente getReviews");
        }
        return reviews;
    }


    public boolean softDeleteReview(int userId, int reviewId) {
        boolean isSoftDeleted = true;

        try {
            Map<String, String> isDeleted = new HashMap();

            isDeleted.put("is_deleted", "1");

            Map<String, String> params = new HashMap();
            params.put("id", String.valueOf(reviewId));
            params.put("user_id", String.valueOf(userId));

            DBWrapper.updateRecords("review", isDeleted, params);
            return isSoftDeleted;

        } catch (SQLException e) {
            e.printStackTrace();
            isSoftDeleted = false;
        }
        return isSoftDeleted;
    }


    public boolean deleteReviewComment(int userId, int reviewId) {
        boolean commentDeleted = true;

        try {
            Map<String, String> deleteComment = new HashMap();

            deleteComment.put("comment", "");

            Map<String, String> params = new HashMap();
            params.put("id", String.valueOf(reviewId));
            params.put("user_id", String.valueOf(userId));

            DBWrapper.updateRecords("review", deleteComment, params);
            return commentDeleted;

        } catch (SQLException e) {
            e.printStackTrace();
            commentDeleted = false;
        }
        return commentDeleted;
    }
}