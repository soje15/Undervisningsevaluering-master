package view.endpoints;

import com.google.gson.Gson;
import logic.StudentController;
import security.Digester;
import shared.ReviewDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Kasper on 19/10/2016.
 */

@Path("/api/student")
public class StudentEndpoint extends UserEndpoint {

    @POST
    @Consumes("application/json")
    @Path("/review")
    public Response addReview(String json) {

        System.out.println(json);

        Gson gson = new Gson();
        ReviewDTO review = new Gson().fromJson(json, ReviewDTO.class);

        StudentController studentCtrl = new StudentController();
        boolean isAdded = studentCtrl.addReview(review);

        if (isAdded) {
            String toJson = Digester.encrypt(gson.toJson(isAdded));
                //String toJson = gson.toJson(isAdded);
            System.out.println(toJson);
            return successResponse(200, toJson);

        } else {
            return errorResponse(404, "Failed. Couldn't get reviews.");
        }
    }

    @PUT
    @Consumes("application/json")
    @Path("/review/")
    public Response deleteReview(String data) {
        System.out.println("Attempting to delete review");
        Gson gson = new Gson();

        ReviewDTO review = gson.fromJson(data, ReviewDTO.class);
        StudentController studentCtrl = new StudentController();

        boolean isDeleted = studentCtrl.softDeleteReview(review.getUserId(), review.getId());

        System.out.println(isDeleted);

        if (isDeleted) {
            String toJson = Digester.encrypt(gson.toJson(isDeleted));
            return successResponse(200, toJson);
        } else {
            System.out.println("failed to delete review");
            return errorResponse(404, "Failed. Couldn't delete the chosen review.");
        }
    }

    @PUT
    @Consumes("application/json")
    @Path("/reviewcomment/")
    public Response deleteComment(String data) {
        System.out.println("Attempting to delete comment");
        Gson gson = new Gson();

        ReviewDTO review = gson.fromJson(data, ReviewDTO.class);
        StudentController studentCtrl = new StudentController();

        boolean commentDeleted = studentCtrl.deleteReviewComment(review.getUserId(), review.getId());

        System.out.println(commentDeleted);

        if (commentDeleted) {
            String toJson = Digester.encrypt(gson.toJson(commentDeleted);
           // String toJson = gson.toJson(commentDeleted);
            return successResponse(200, toJson);
        } else {
            System.out.println("failed to delete review");
            return errorResponse(404, "Failed. Couldn't delete the chosen review.");
        }
    }
}
