package DAL;

import Model.Roles;
import Util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class RolesHelper {
    
    protected Session session = null;

    public RolesHelper() {
        if(session == null){
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
    }

    public RolesHelper(Session session) {
        this.session = session;
    }
    
    
    
    public List<Roles> getRoles() {
        List<Roles> roles = new ArrayList();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {            
            Query q = session.createQuery("FROM Roles");
            roles = (List<Roles>)q.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        return roles;
    }

    public void update(Roles roles) {
        org.hibernate.Transaction tx = session.beginTransaction();
        try{
            session.update(roles);
            session.getTransaction().commit();
        }catch(Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void add(Roles roles) {
        org.hibernate.Transaction tx = session.beginTransaction();
        try{            
            session.save(roles);
            session.getTransaction().commit();
        }catch(Exception e) {
            session.getTransaction().rollback();
        }
    }

    public int deleteById(int idToDelete) {
        org.hibernate.Transaction tx = session.beginTransaction();
        int deletedUserId = -1;
        try {
            Roles rolesToDelete = (Roles) session.get(Roles.class, idToDelete);            
            session.delete(rolesToDelete);
            tx.commit();
            deletedUserId = idToDelete;
        } catch (Exception e) {
            tx.rollback();
        }
        return deletedUserId;
    }        
}
