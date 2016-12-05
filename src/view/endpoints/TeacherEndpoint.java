package view.endpoints;

import com.google.gson.Gson;
import logic.AdminController;
import logic.StudentController;
import logic.TeacherController;
import shared.CourseDTO;
import shared.ReviewDTO;
import shared.UserDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by Kasper on 19/10/2016.
 */
@Path("/api/teacher")
public class TeacherEndpoint extends UserEndpoint {

    @GET
    @Consumes("applications/json")
    @Path("/courseParticipants/{courseId}")
    public Response courseParticipants(@PathParam("courseId") int courseId) {
        System.out.println("Calling courseParticipants");
        Gson gson = new Gson();

        TeacherController teacherController = new TeacherController();

        int courseParticipants = teacherController.getCourseParticipants(courseId);

        if (courseParticipants >= 0) {
            return successResponse(200, courseParticipants);
        } else {
            return errorResponse(404, "Failed. Couldn't get lectures.");
        }
    }



}
