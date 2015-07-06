package DAL.Public;

import Model.Roles;
import Model.User;
import Model.UserRoles;
import java.util.List;

public interface Context {
    //users
    List<User> getUsers();
    User getUserById(int id);
    void add(User user);
    void update(User user);
    int deleteUserById(int id);
    //userrole
    List<UserRoles> getUserRoles();
    //roles
    public List<Roles> getRoles();
    User getRolesById(int id);
    void add(Roles roles);
    void update(Roles roles);
    int deleteRolesById(int id);
}
