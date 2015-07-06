package Resources;

import DAL.Public.DALFactory;
import Model.UserRoles;
import Util.JSONConverter;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

@Path("userroles")
public class UserRoleResource {

    @Context
    private UriInfo context;
    private final DAL.Public.Context dalContext;
    
    public UserRoleResource() {
        this.dalContext = DALFactory.getContext();
    }

    public UserRoleResource(DAL.Public.Context dalContext) {
        this.dalContext = dalContext;
    }        
    
    @GET
    @Produces("application/json")
    public String get() {
        List<UserRoles> userRoles = this.dalContext.getUserRoles();        
        StringBuilder sb = new StringBuilder("[");
        String prefix = "";
        for(UserRoles ur : userRoles) {
            sb.append(prefix);
            prefix = ",";
            sb.append(JSONConverter.toJSON(ur).toString());
        }
        sb.append("]");
        return sb.toString();
    }

    @PUT
    @Consumes("application/json")
    public Response put(String content) {
        throw new UnsupportedOperationException();
    }
    
    @POST
    @Consumes("application/json")
    public Response post(String content) {
        throw new UnsupportedOperationException();
    }
    
    @DELETE
    public Response delete(int id) {
        throw new UnsupportedOperationException();
    }
}
