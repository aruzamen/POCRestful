/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POCServer.Test;

import DAL.Public.Context;
import Model.Roles;
import Model.User;
import Model.UserRoles;
import Resources.UserRoleResource;
import Util.JSONConverter;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Walter Torricos
 */
public class UserRolesResourceTest {
    
    private UserRoles userRol;
    private Context context;
    
    @Before
    public void setUp() {
        userRol = new UserRoles(1, new Roles(1), new User(1,"juan","perez", 10));
        context = mock(Context.class);
        
    }

     @Test
     public void get_WithUserRolesInDB_ReturnsAllTheUserRoles() {         
        UserRoleResource userRolesResource = new UserRoleResource(context);        
        List<UserRoles> userRoles = new ArrayList<>();
        userRoles.add(userRol);
        when(context.getUserRoles()).thenReturn(userRoles);
        String expected = String.format("[%s]", JSONConverter.toJSON(userRol));
        
        String actual = userRolesResource.get();
        
        verify(context).getUserRoles();
        assertEquals(expected, actual);
     }
}
