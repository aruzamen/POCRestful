package Resources.Model;

import Model.Roles;
import Model.UserRoles;

public class Rol {    
    private int id;
    private String name;
    private String description;

    public Rol(UserRoles userRoles) {
        this.id = userRoles.getRoles().getId();
        this.name = userRoles.getRoles().getName();
        this.description = userRoles.getRoles().getDescription();
    }
    
    public Rol(Roles roles) {
        this.id = roles.getId();
        this.name = roles.getName();
        this.description = roles.getDescription();
    }

    public Rol(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
