package Resources.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String lastname;
    private int age;    
    private List<Rol> roles;
    
    public User(Model.User user) {
        this.id = user.getId();
        this.age = user.getAge();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.roles = new ArrayList<>();
        if(user.getUserRoleses() != null) {
            for(Iterator iterator = user.getUserRoleses().iterator(); iterator.hasNext(); ) {
                Rol rol = new Rol((Model.UserRoles)iterator.next());
                roles.add(rol);
            }
        }
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
