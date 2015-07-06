package Resources;

import DAL.Public.DALFactory;
import Model.ModelFactory;
import Resources.Model.User;
import Util.JSONConverter;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

@Path("users")
public class UserResource {

    @Context
    private UriInfo context;
    private final DAL.Public.Context dalContext;
    
    public UserResource() {
        this.dalContext = DALFactory.getContext();
    }
    
    public UserResource(DAL.Public.Context dalContext) {
        this.dalContext = dalContext;
    }
    
    @GET
    @Produces("application/json")
    public String getUsers() {
        try{
            List<Model.User> modelUsers = dalContext.getUsers();
            StringBuilder sb = new StringBuilder("[");
            String prefix = "";
            for(Model.User modelUser : modelUsers) {
                User user = new User(modelUser);
                sb.append(prefix);                
                prefix = ",";
                sb.append(  JSONConverter.toJSON(user).toString() );
            }
            sb.append("]");
            return sb.toString();            
        } catch(Exception e) {
            return "{}";
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public String getUserById(@PathParam("id") int id) {        
        Model.User modelUser = dalContext.getUserById(id);        
        User user = null;
        String response = "null";
        if(modelUser != null) {
            user = new User(modelUser);
            response = JSONConverter
                                .toJSON(user)
                                .toString();
        }
        return response;
    }
    
    @POST
    @Consumes("application/json")
    public Response postUser(String content) {
        try{
            JSONObject jsonUser = new JSONObject(content);
            Model.User user = ModelFactory.getUser(jsonUser);
            dalContext.add(user);
            User responseUser = new User(user);
            String response = JSONConverter.toJSON(responseUser).toString();
            return Response.status(Response.Status.OK)                    
                    .entity(response)
                    .build();
        }
        catch(Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{}")
                    .build();
        }
    }
    
    @PUT
    @Consumes("application/json")
    public Response putUser(String content) {
        try {
            JSONObject jsonUser = new JSONObject(content);
            Model.User user = ModelFactory.getUser(jsonUser);
            dalContext.update(user);
            return Response.status(Response.Status.OK)                    
                    .entity(content)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)                    
                    .entity("{}")
                    .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id")int id) {
        int deletedId = dalContext.deleteUserById(id);
        if(deletedId >= 0){
            return Response.status(Response.Status.OK)
                           .entity(deletedId)
                           .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(deletedId)
                           .build();
        }
        
    }
}
