package Resources;

import DAL.Public.DALFactory;
import Model.ModelFactory;
import Resources.Model.Rol;
import Util.JSONConverter;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

@Path("roles")
public class RolesResource {

    @Context
    private UriInfo context;
    private final DAL.Public.Context dalContext;
    
    public RolesResource() {
        this.dalContext = DALFactory.getContext();
    }
    
    public RolesResource(DAL.Public.Context context) {
        this.dalContext = context;
    }
    
    @GET
    @Produces("application/json")
    public String get() {
        try{
            List<Model.Roles> modelRoles = dalContext.getRoles();
            StringBuilder sb = new StringBuilder("[");
            String prefix = "";
            for(Model.Roles modelRol : modelRoles) {
                Rol rol = new Rol(modelRol);
                sb.append(prefix);                
                prefix = ",";
                sb.append(  JSONConverter.toJSON(rol).toString() );
            }
            sb.append("]");
            return sb.toString();            
        } catch(Exception e) {
            return "{}";
        }
    }

    @PUT
    @Produces("application/json")
    public Response put(String content) {
        try {
            JSONObject jsonRol = new JSONObject(content);
            Model.Roles rol = ModelFactory.getRol(jsonRol);
            dalContext.update(rol);
            return Response.status(Response.Status.OK)                    
                    .entity(content)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)                    
                    .entity("{}")
                    .build();
        }
    }
    
    @POST
    @Consumes("application/json")
    public Response post(String content) {
        try{
            JSONObject jsonRol = new JSONObject(content);
            Model.Roles rol = ModelFactory.getRol(jsonRol);
            dalContext.add(rol);
            Rol responseRol = new Rol(rol);
            String response = JSONConverter.toJSON(responseRol).toString();
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
    
    @DELETE
    @Path("/{id}")
    public Response deleteRoles(@PathParam("id")int id) {
        int deletedId = dalContext.deleteRolesById(id);
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
