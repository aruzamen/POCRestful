package POCServer.Test;

import DAL.UserHelper;
import Model.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserHelperTest {
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
    public void getUserById_WithAValidId_ReturnsTheUser() {                
        User expected = new User(0,"pepe","perez",5);
        when(session.get(User.class, 0)).thenReturn(expected);
        
        UserHelper userHelper = new UserHelper(session);
        User actual = userHelper.getUserById(0);
                
        assertEquals(actual, expected);
    }
    
    @Test
    public void getUser_ReturnsAllTheUsersInTheDataBase() {        
        List<User> users = new ArrayList<>();
        users.add(new User(0,"pepe","perez",5));
        when(query.list()).thenReturn(users);
        
        UserHelper userHelper = new UserHelper(session);
        List<User> actual = userHelper.getUsers();
        
        List<User> expected = new ArrayList<>();
        expected.add(new User(0,"pepe","perez",5));
        
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(0).getLastname(), actual.get(0).getLastname());
        assertEquals(expected.get(0).getAge(), actual.get(0).getAge());
    }
    
    @Test
    public void add_WithAValidUser_AddsTheUserToTheDataBase() {
        UserHelper userHelper = new UserHelper(session);
        User toSave = new User(1, "pepe", "rodriguez", 6);
        
        userHelper.add(toSave);
        
        verify(session).save(toSave);
    }
    
    @Test
    public void update_WithAValidUser_UpdatesTheUserInTheDataBase() {
        UserHelper userHelper = new UserHelper(session);
        User toUpdate = new User(1, "pepe", "rodriguez", 6);
        
        userHelper.update(toUpdate);
        
        verify(session).update(toUpdate);
    }
    
    @Test
    public void delete_WithAValidId_DeletesTheUserFromTheDataBase() {
        UserHelper userHelper = new UserHelper(session);        
        int idToDelete = 1;        
        User userToDelete = new User(1, "pepe", "rodriguez", 6);
        when(session.get(User.class, idToDelete)).thenReturn(userToDelete);
        
        userHelper.deleteUser(idToDelete);
        
        verify(session).delete(userToDelete);
    }    
}
