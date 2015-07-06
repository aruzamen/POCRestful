package DAL;

import DAL.Public.Context;
import Model.Roles;
import Model.User;
import Model.UserRoles;
import java.util.List;

public class POCContext implements Context {    
    
    //user
    @Override
    public List<User> getUsers() {
        UserHelper userHelper = new UserHelper();
        return userHelper.getUsers();
    }

    @Override
    public User getUserById(int id) {
        UserHelper userHelper = new UserHelper();
        User user = userHelper.getUserById(id);
        return user;
    }

    @Override
    public void add(User user) {
        UserHelper userHelper = new UserHelper();
        userHelper.add(user);
    }

    @Override
    public void update(User user) {
        UserHelper userHelper = new UserHelper();
        userHelper.update(user);
    }
    
    @Override
    public int deleteUserById(int id) {
        UserHelper userHelper = new UserHelper();
        return userHelper.deleteUser(id);
    }
    
    //userroles
    @Override
    public List<UserRoles> getUserRoles() {
        UserRolesHelper userRolesrHelper = new UserRolesHelper();
        return userRolesrHelper.get();
    }
    
    
    //roles
    @Override
    public List<Roles> getRoles() {
        RolesHelper rolesHelper = new RolesHelper();
        return rolesHelper.getRoles();
    }
    
    @Override
    public User getRolesById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void update(Roles roles) {
        RolesHelper rolesHelper = new RolesHelper();
        rolesHelper.update(roles);
    }
    
    @Override
    public void add(Roles roles) {
        RolesHelper rolesHelper = new RolesHelper();
        rolesHelper.add(roles);
    }

    @Override
    public int deleteRolesById(int id) {
        RolesHelper rolesHelper = new RolesHelper();
        return rolesHelper.deleteById(id);
    }
}
