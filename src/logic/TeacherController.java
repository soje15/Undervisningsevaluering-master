package logic;

import service.DBWrapper;
import shared.LectureDTO;
import shared.ReviewDTO;
import shared.TeacherDTO;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Controllerklasse der håndterer metoder/funktioner specifikker for Underviser-brugeren.
 * Arver fra UserController.
 */
public class TeacherController extends UserController {

    public static void main(String[] args) {
        TeacherController ctr = new TeacherController();

        //Virker
        System.out.println("Course participants on course ID 3 : " + ctr.getCourseParticipants(3));


        //Virker ikke
        System.out.println(ctr.calculateReviewParticipation(6));


        //virker ikke
        System.out.println(ctr.calculateLectureAverage(135));


        //Virker ikke
        System.out.println(ctr.calculateAverageRatingOnCourse("BALJO1001U_LA_E16"));
    }


    private TeacherDTO currentTeacher;

    public TeacherController() {
        super();
    }

    public void loadTeacher(TeacherDTO currentTeacher) {
        this.currentTeacher = currentTeacher;
    }


    /*
        Skal vise gennemsnitlig deltagelse for en lecture, udregnet som antal reviews/antal tilmeldte på kurset.

        Find courseID ud fra lectureID
        Query courseattendant og find samtlige instanser af det courseID
        Udregn gennemsnit

     */

    /**  VIRKER
     * Gennemsøger tabellen course_attendant for brugere med et bestemt course_id.
     * @param courseId id'et på det kursus man ønsker samlet antal deltagere for.
     * @return det samlede antal der er tilmeldt kurset.
     */
    public int getCourseParticipants(int courseId) {

        //Forbered MySQL statement
        String table = "course_attendant";
        Map<String, String> whereStmt = new HashMap<String, String>();
        whereStmt.put("course_id", String.valueOf(courseId));
        CachedRowSet rs = null;
        int courseAttendants = 0;

        //Query courseattendant og find samtlige instanser af det courseID
        try {
            rs = DBWrapper.getRecords(table, null, whereStmt, null, 0);
            courseAttendants = rs.size();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseAttendants;

    }

    /**
     * Udregner hvor mange som har deltaget i evaluering af lektionen ud fra samlet antal tilmeldte studerende på kurset.
     * @param lectureId ID'et på lektionen.
     * @return Mængden af studerende som har deltaget i evalueringen, udregnet som en procentsats.
     */
    public double calculateReviewParticipation(int lectureId) {

        String table = "lecture";
        String[] attributes = new String[]{"course_id"};
        Map<String, String> whereStmt = new HashMap<String, String>();
        whereStmt.put("id", String.valueOf(lectureId));
        double reviewParticipation = 0;

        try {
            //Find courseID ud fra lectureID
            CachedRowSet rs = DBWrapper.getRecords(table, attributes, whereStmt, null, 0);
            rs.next();

            String courseIdString = rs.getString("course_id");

            //Find autogenerede databaseid for course ud fra courseIdString
            table = "course";
            whereStmt = new HashMap<String, String>();
            attributes = new String[]{"id"};
            whereStmt.put("name", "BALJO1001U_LA_E16");

            rs = DBWrapper.getRecords(table, attributes, whereStmt, null, 0);

            ArrayList<ReviewDTO> reviews = getReviews(lectureId);
            int number = 0;

            while (rs.next()) {
                ReviewDTO review = new ReviewDTO();
                number += rs.getInt("id");

                reviews.add(review);
            }
            System.out.println("number" + number);
            //rs.next();
            //int courseId = rs.getInt("id");


            int courseAttendants = getCourseParticipants(number);


            //Find mængde af reviews for den givne lektion



            int reviewsOnLecture = reviews.size();
            System.out.println("review size" + reviews.size());

            //Caster til double i tilfælde af kommatal
            reviewParticipation = (double) reviewsOnLecture / (double) courseAttendants;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ArithmeticException e){
            e.printStackTrace();
        }

        return reviewParticipation;

    }

    /**
     * Udregner en lektions gennemsnitige rating ud fra rating givet i dets reviews.
     * @param lectureId ID'et på lektionen.
     * @return Returnerer den gennemsnitlige rating for lektionen som en Integer.
     */
    public double calculateLectureAverage(int lectureId) {
        ArrayList<ReviewDTO> reviews = getReviews(lectureId);
        int total = 0;

        for (ReviewDTO review : reviews) {
            total += review.getRating();
        }

        double average = (double) total / (double)reviews.size();
        return average;
    }


    /**
     * Udregner den gennemsnitlige rating for et givent kursus ud fra dets lektioner.
     * @param courseId ID'et på kurset
     * @return Returnerer den gennemsnitlige rating for kurset som en Integer.
     */
    public int calculateAverageRatingOnCourse(String courseId) {
        ArrayList<LectureDTO> lecturesInCourse = getLectures(courseId);
        int total = 0;

        for (LectureDTO lecture : lecturesInCourse){
            total += calculateLectureAverage(lecture.getId());
        }

        int average = total / lecturesInCourse.size();

        return average;

    }
}