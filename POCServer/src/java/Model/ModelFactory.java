package Model;

import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ModelFactory {

    public static User getUser(JSONObject jsonUser) throws JSONException {        
        User user = null;
        int id = jsonUser.getInt("id");
        String name = jsonUser.getString("name");
        String lastname = jsonUser.getString("lastname");
        int age = jsonUser.getInt("age");
        JSONArray jsonRoles = jsonUser.getJSONArray("roles");
        Set<UserRoles> userRoles = new HashSet<>();
        user = new User(id, name, lastname, age);
        for(int i = 0; i<jsonRoles.length(); i++) {
            JSONObject currentRol = jsonRoles.getJSONObject(i);
            int rolId = currentRol.getInt("id");
            String rolDesc = currentRol.getString("description");
            String rolName = currentRol.getString("name");            
            Roles rol = new Roles(rolId, rolName, rolDesc, null);
            
            int fakeUserRolesId = 0;
            UserRoles ur = new UserRoles(fakeUserRolesId, rol, user);
            userRoles.add(ur);
        }
        user.setUserRoleses(userRoles);
        return user;
    }

    public static Roles getRol(JSONObject jsonRol) throws JSONException {         
        int id = jsonRol.getInt("id");
        String name = jsonRol.getString("name");
        String description = jsonRol.getString("description");        
        Roles rol = new Roles(id, name, description, null);
        return rol;
    }
    
}
