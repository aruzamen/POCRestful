/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POCServer.Test;

import DAL.UserRolesHelper;
import Model.Roles;
import Model.User;
import Model.UserRoles;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Walter Torricos
 */
public class UserRolesHelperTest {
    Session session;
    Transaction transaction;
    SQLQuery query;
    UserRoles userRol;
    @Before
    public void setUp() {
        session = mock(Session.class);
        transaction = mock(Transaction.class);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.getTransaction()).thenReturn(transaction);
        query = mock(SQLQuery.class);
        when(session.createSQLQuery(anyString())).thenReturn(query);
        when(session.createQuery(anyString())).thenReturn(query);
        userRol = new UserRoles(1, new Roles(1), new User(1,"juan","perez",1));
    }
    
    @Test
    public void get_WithUserResourcesInDB_ReturnsAListOfUserResources() {
        List<UserRoles> userRoles = new ArrayList<>();
        userRoles.add(userRol);
        UserRolesHelper userRolesHelper = new UserRolesHelper(session);
        when(query.list()).thenReturn(userRoles);
        List<UserRoles> expected = userRoles;
        
        List<UserRoles> actual = userRolesHelper.get();
        
        verify(query).list();
        assertEquals(expected.get(0), actual.get(0));
    }
}
