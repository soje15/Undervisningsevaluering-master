package logic;

import service.DBWrapper;
import shared.ReviewDTO;
import shared.StudentDTO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StudentController extends UserController {

    private StudentDTO currentStudent;

    public StudentController() {
        super();
    }

    /*
    public static void main(String[] args) {

        StudentController controller = new StudentController();
        controller.addReview(new ReviewDTO(1, 1, 1, "1", true));
    }
*/

    public void loadStudent(StudentDTO currentStudent) {
        this.currentStudent = currentStudent;
    }

    //Metode til at indsætte et review i databasen
    public boolean addReview(ReviewDTO review) {
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
}