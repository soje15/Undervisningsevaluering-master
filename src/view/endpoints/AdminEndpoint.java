package view.endpoints;

import com.google.gson.Gson;
import logic.AdminController;
import logic.StudentController;
import shared.ReviewDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/api/admin")
public class AdminEndpoint extends UserEndpoint {


    /**
     * Henter reviews med tilknyttet userId.
     * @return
     */
    @GET
    @Consumes("applications/json")
    @Path("/getReviews/")
    public Response getReviews() {
        Gson gson = new Gson();

        AdminController adminController = new AdminController();
        ArrayList<ReviewDTO> reviews = adminController.getReviews2();

        if (!reviews.isEmpty()) {
            System.out.println("returning reviews");
            return successResponse(200, reviews);
        } else {
            return errorResponse(404, "Failed. Couldn't get reviews.");
        }
    }

}
