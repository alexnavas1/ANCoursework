package controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Stats/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Stats{
    @GET
    @Path("get/{UserID}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String GetStats(@PathParam("UserID") Integer UserID) {
        System.out.println("Invoked Stats.Statsget() with UserID " + UserID);
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT StatID, UserID, Health, Stamina, Score, ProgressID FROM Stats WHERE UserID = ?");
            ps.setInt(1, UserID);
            ResultSet results = ps.executeQuery();
            JSONObject response = new JSONObject();
            if (results.next()== true) {
                response.put("UserID", results.getInt(2));
                response.put("StatID", results.getInt(1));
                response.put("Health", results.getInt(3));
                response.put("Stamina", results.getInt(4));
                response.put("Score", results.getInt(5));
                response.put("ProgressID", results.getInt(6));
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
}
