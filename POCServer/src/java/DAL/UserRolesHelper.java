package DAL;

import Model.Roles;
import Model.User;
import Model.UserRoles;
import Util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserRolesHelper {
    protected Session session = null;

    public UserRolesHelper() {
        if(session == null){
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
    }

    public UserRolesHelper(Session session) {
        this.session = session;
    }

    public List<UserRoles> get() {
        List<UserRoles> userRoles = null;
        Transaction transaction = this.session.beginTransaction();
        try {
            Query query = session.createQuery("FROM UserRoles");
            userRoles = (List<UserRoles>)query.list();
            for(UserRoles ur : userRoles) {                
                int userId = ur.getUser().getId();
                String userName = ur.getUser().getName();
                String lastName = ur.getUser().getLastname();
                int age = ur.getUser().getAge();
                ur.setUser(new User(userId, userName, lastName, age, null));
                
                int rolId = ur.getRoles().getId();
                String rolName = ur.getRoles().getName();
                String rolDescription = ur.getRoles().getDescription();
                ur.setRoles(new Roles(rolId, rolName, rolDescription, null));                
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return userRoles;
    }
    
    public void post(UserRoles userRole) {
        
    }
}
