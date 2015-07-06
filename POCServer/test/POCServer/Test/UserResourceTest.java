package POCServer.Test;

import Model.User;
import Resources.UserResource;
import Util.JSONConverter;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Walter Torricos
 */
public class UserResourceTest {
    DAL.Public.Context context;
    Resources.Model.User testUser;
    User modelUser;
    
    @Before
    public void setUp() {
        modelUser = new User(1, "juan", "rojas", 10);
        testUser = new Resources.Model.User(modelUser);
        context = mock(DAL.Public.Context.class);
    }
    
    @Test
    public void getUsers_WithOneUserInTheDB_ReturnsAValidCollectionOfUsersInJSONFormat() {        
        List<User> users = new ArrayList<>();
        users.add(modelUser);
        when(context.getUsers()).thenReturn(users);
        JSONObject jsonUser = JSONConverter.toJSON(testUser);
        String expected = String.format("[%s]", jsonUser.toString());
        
        UserResource userResource = new UserResource(context);
        String actual = userResource.getUsers();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void getUserById_WithAValidId_ReturnsTheUserInJSONFormat() {        
        when(context.getUserById(anyInt())).thenReturn(modelUser);
        
        String expected = JSONConverter.toJSON(testUser).toString();
        
        UserResource userResource = new UserResource(context);
        String actual = userResource.getUserById(0);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void getUserById_WithANonValidId_ReturnsNullString() {        
        when(context.getUserById(anyInt())).thenReturn(null);
        
        String expected = "null";
        
        UserResource userResource = new UserResource(context);
        String actual = userResource.getUserById(0);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void postUser_WithAValidUser_ReturnsThePostedUserInJSONFormat() {
        JSONObject jsonUser = JSONConverter.toJSON(testUser);
        UserResource userResource = new UserResource(context);
        String expected = jsonUser.toString();
        
        Response response = userResource.postUser(expected);
        
        verify(context).add(any(User.class));
        assertEquals(expected, (String)response.getEntity());
    }
    
    @Test
    public void postUser_WithANonValidUser_ReturnsAnEmptyJSONObject() {        
        UserResource userResource = new UserResource(context);
        String expected = "{}";
        
        Response response = userResource.postUser("");
        
        verify(context, never()).add(any(User.class));
        assertEquals(expected, (String)response.getEntity());
    }
    
    @Test
    public void putUser_WithAValidUser_UpdatesTheUserAndReturnsTheUpdatedUser() {
        JSONObject jsonUser = JSONConverter.toJSON(testUser);
        UserResource userResource = new UserResource(context);
        String expected = jsonUser.toString();
        
        Response response = userResource.putUser(expected);
        
        verify(context).update(any(User.class));
        assertEquals(expected, (String)response.getEntity());
    }
    
    @Test
    public void putUser_WithANonValidUser_ReturnsAnEmptyJSONObject() {        
        UserResource userResource = new UserResource(context);
        String expected = "{}";
        
        Response response = userResource.putUser(expected);
        
        verify(context, never()).update(any(User.class));
        assertEquals(expected, (String)response.getEntity());
    }
    
    @Test
    public void deleteUser_WithAValidId_DeletesTheUserAndReturnsTheDeletedUserId() {
        UserResource userResource = new UserResource(context);
        when(context.deleteUserById(anyInt())).thenReturn(1);
        int expected = 1;
        
        Response response = userResource.deleteUser(1);
        
        verify(context).deleteUserById(1);
        assertEquals(expected, (int)response.getEntity());
    }
    
    @Test
    public void deleteUser_WithAnInValidId_ReturnsMinusOneAndStatusBadRequest() {
        UserResource userResource = new UserResource(context);
        when(context.deleteUserById(anyInt())).thenReturn(-1);
        int expected = -1;
        
        Response response = userResource.deleteUser(1);
        
        verify(context).deleteUserById(1);
        assertEquals(expected, (int)response.getEntity());
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }
}
