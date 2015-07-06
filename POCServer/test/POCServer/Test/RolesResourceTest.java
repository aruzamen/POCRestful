/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POCServer.Test;


import Model.Roles;
import Model.User;
import Resources.Model.Rol;
import Resources.RolesResource;
import Resources.UserResource;
import Util.JSONConverter;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Walter Torricos
 */
public class RolesResourceTest {
    DAL.Public.Context context;
    Model.Roles testRoles;
    Rol testRol;
    @Before
    public void setUp() {
        testRoles = new Roles(1, "admin", "admin desc", null);
        testRol = new Rol(testRoles.getId(), testRoles.getName(), testRoles.getDescription());
        context = mock(DAL.Public.Context.class);
    }

    @Test
    public void get_WithAtLeastOneRolInTheDataBase_ReturnsAListOfRolesInJSONFormat() {
        List<Roles> roles = new ArrayList<>();
        roles.add(testRoles);
        when(context.getRoles()).thenReturn(roles);        
        JSONObject jsonRol = JSONConverter.toJSON(testRol);        
        String expected = String.format("[%s]", jsonRol.toString());
        
        RolesResource rolesResource = new RolesResource(context);
        String actual = rolesResource.get();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void post_WithAValidRol_PostsTheRolToTheDataBase() {
        JSONObject jsonRol = JSONConverter.toJSON(testRol);
        RolesResource rolesResource = new RolesResource(context);
        String expected = jsonRol.toString();
        
        Response response = rolesResource.post(expected);
        
        verify(context).add(any(Roles.class));
        assertEquals(expected, (String)response.getEntity());
    }
    
    @Test
    public void post_WithANonValidRol_ReturnsAnEmptyObjectAndABadRequestState() {        
        RolesResource rolesResource = new RolesResource(context);
        String expected = "{}";
        
        Response response = rolesResource.post("");
        
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
        assertEquals(expected, (String)response.getEntity());
    }
    
    @Test
    public void putRoles_WithAValidRoles_UpdatesTheRolAndReturnsTheUpdatedRol() {
        JSONObject jsonRol = JSONConverter.toJSON(testRol);
        RolesResource rolesResource = new RolesResource(context);
        String expected = jsonRol.toString();
        
        Response response = rolesResource.put(expected);
        
        verify(context).update(any(Roles.class));
        assertEquals(expected, (String)response.getEntity());
    }
    
    @Test
    public void putRoles_WithANonValidRol_ReturnsAnEmptyObjectAndBadRequestState() {        
        RolesResource rolesResource = new RolesResource(context);
        String expected = "{}";
        
        Response response = rolesResource.put("");
        
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
        assertEquals(expected, (String)response.getEntity());
    }
    
    @Test
    public void deleteRol_WithAValidId_DeletesTheRolAndReturnsTheDeletedRolId() {
        RolesResource rolesResource = new RolesResource(context);
        when(context.deleteRolesById(anyInt())).thenReturn(1);
        int expected = 1;
        
        Response response = rolesResource.deleteRoles(1);
        
        verify(context).deleteRolesById(1);
        assertEquals(expected, (int)response.getEntity());
    }
    
    @Test
    public void deleteRol_WithANonValidId_ReturnsMinusOneAndStatusBadRequest() {
        RolesResource rolesResource = new RolesResource(context);
        when(context.deleteRolesById(anyInt())).thenReturn(-1);
        int expected = -1;
        
        Response response = rolesResource.deleteRoles(1);
        
        verify(context).deleteRolesById(1);
        assertEquals(expected, (int)response.getEntity());
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }
}
