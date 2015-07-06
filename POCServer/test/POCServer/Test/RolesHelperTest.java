package POCServer.Test;

import DAL.RolesHelper;
import Model.Roles;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RolesHelperTest {
    Session session;
    Transaction transaction;
    SQLQuery query;
    
    @Before
    public void setUp() {
        session = mock(Session.class);
        transaction = mock(Transaction.class);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.getTransaction()).thenReturn(transaction);
        query = mock(SQLQuery.class);
        when(session.createSQLQuery(anyString())).thenReturn(query);
        when(session.createQuery(anyString())).thenReturn(query);
    }

    @Test
    public void getRoles_ReturnsAllTheRolessInTheDataBase() {        
        Roles rol = new Roles(1, "admin", "admin desc", null);
        List<Roles> roles = new ArrayList<>();
        roles.add(rol);
        when(query.list()).thenReturn(roles);
        
        RolesHelper rolesHelper = new RolesHelper(session);
        List<Roles> actual = rolesHelper.getRoles();
        
        List<Roles> expected = new ArrayList<>();
        expected.add(rol);
        
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(0).getDescription(), actual.get(0).getDescription());        
    }
    
    @Test
    public void add_WithAValidRoles_AddsTheRolesToTheDataBase() {
        RolesHelper rolesHelper = new RolesHelper(session);
        Roles toSave = new Roles(1, "admin", "admin desc", null);
        
        rolesHelper.add(toSave);
        
        verify(session).save(toSave);
    }
    
    @Test
    public void update_WithAValidRoles_UpdatesTheRolesInTheDataBase() {
        RolesHelper rolesHelper = new RolesHelper(session);
        Roles toUpdate = new Roles(1, "admin", "admin desc", null);
        
        rolesHelper.update(toUpdate);
        
        verify(session).update(toUpdate);
    }
    
    @Test
    public void delete_WithAValidId_DeletesTheRolesFromTheDataBase() {
        RolesHelper rolesHelper = new RolesHelper(session);        
        int idToDelete = 1;        
        Roles rolToDelete = new Roles(1, "admin", "admin desc", null);
        when(session.get(Roles.class, idToDelete)).thenReturn(rolToDelete);
        
        rolesHelper.deleteById(idToDelete);
        
        verify(session).delete(rolToDelete);
    }
}
