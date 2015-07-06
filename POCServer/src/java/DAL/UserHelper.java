package DAL;

import Model.Roles;
import Model.User;
import Model.UserRoles;
import Util.HibernateUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;

public class UserHelper {
    protected Session session = null;

    public UserHelper() {
        if(session == null){
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        }        
    }
    
    public UserHelper(Session session) {
        this.session = session;
    }
    
    public List<User> getUsers() {
        List<User> users = new ArrayList();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {            
            Query q = session.createQuery("FROM User");
            users = (List<User>)q.list();
            for(User user : users) {
                this.initializeLazyFields(user);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        return users;
    }

    public void add(User user) {        
        org.hibernate.Transaction tx = session.beginTransaction();
        try{            
            session.save(user);
            session.getTransaction().commit();
        }catch(Exception e) {
            session.getTransaction().rollback();
        }
    }
    
    public void update(User user) {        
        org.hibernate.Transaction tx = session.beginTransaction();
        try{
            session.update(user);
            this.addOrTakeRoles(user);
            session.getTransaction().commit();
        }catch(Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public User getUserById(int id) {
        User user = null;
        org.hibernate.Transaction tx = session.beginTransaction();
        try {            
            user =  (User) session.get(User.class, id);
            this.initializeLazyFields(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        return user;
    }

    public int deleteUser(int idToDelete) {        
        org.hibernate.Transaction tx = session.beginTransaction();
        int deletedUserId = -1;
        try {
            User userToDelete = (User) session.get(User.class, idToDelete);
            Set<UserRoles> userRolesToDelete = userToDelete.getUserRoleses();
            for(Iterator iterator = userRolesToDelete.iterator(); iterator.hasNext(); ) {
                UserRoles ur = (UserRoles)iterator.next();
                session.delete(ur);
            }
            session.delete(userToDelete);
            tx.commit();
            deletedUserId = idToDelete;
        } catch (Exception e) {
            tx.rollback();
        }
        return deletedUserId;
    }

    private void initializeLazyFields(User user) {        
        Set<UserRoles> userRoles = user.getUserRoleses();
        for(UserRoles ur : userRoles) {
            int id = ur.getId();
            String name = ur.getRoles().getName();
            String desc = ur.getRoles().getDescription();
        }
    }

    private void addOrTakeRoles(User updatedUser) {        
        Query q = session.createQuery("FROM UserRoles where id_user = :id");
        q.setParameter("id", updatedUser.getId());
        List<UserRoles> actualUserRoles = (List<UserRoles>)q.list();
        Map<String, Roles> updatedRoles = getUpdatedRoles(updatedUser);        
        for(Iterator iterator = actualUserRoles.iterator(); iterator.hasNext(); ) {
            UserRoles ur = (UserRoles)iterator.next();
            Roles rol = ur.getRoles();
            if(updatedRoles.containsKey(rol.getName())) {
                updatedRoles.remove(rol.getName());
            } else {
                session.delete(ur);
            }
        }
        for (Map.Entry<String, Roles> entry : updatedRoles.entrySet())
        {
            UserRoles ur = new UserRoles(0, (Roles)entry.getValue(), updatedUser);
            session.save(ur);
        }
    }

    private Map<String, Roles> getUpdatedRoles(User updatedUser) {
        Map<String, Roles> updatedRoles = new HashMap<>();
        Set<UserRoles> userRoles = updatedUser.getUserRoleses();
        for(Iterator iterator = userRoles.iterator(); iterator.hasNext(); ) {
            UserRoles ur = (UserRoles)iterator.next();
            Roles rol = (Roles)ur.getRoles();
            updatedRoles.put(rol.getName(), rol);
        }
        return updatedRoles;
    }
}
