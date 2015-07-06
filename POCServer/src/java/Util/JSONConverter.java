package Util;

import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONConverter {
    public static JSONObject toJSON(Object obj) {
        JSONObject jsonObject = new JSONObject();
        Class objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for(Field field : fields) {
            addJSONObjectFieldValue(field, obj, jsonObject);
        }        
        return jsonObject;
    }

    private static void addJSONObjectFieldValue(Field field, Object obj, JSONObject jsonObject) {
        field.setAccessible(true);
        if( isInnerClass(field) ) {
            return;
        }
        else if( isPrimitiveOrString(field) ){
            addKeyValueToJSONObj(obj, field, jsonObject);
        }
        else if( isCollection(field) ){
            addCollectionValueToJsonObj(field, obj, jsonObject);
        }
        else { //is a complex object
            addKeyValueFromComplexObject(field, obj, jsonObject);                
        }
    }
    
    private static boolean isCollection(Field field) {
        Class<?> c = field.getType();
        return c.isArray() || c.equals(java.util.Set.class) || c.equals(java.util.List.class);
    }

    private static boolean isInnerClass(Field field) {        
        String name = field.getName();
        return name.equals("this$0");
    }

    private static boolean isPrimitiveOrString(Field field) {
        return field.getType().isPrimitive() || field.getType().equals(String.class);
    }
    
    private static void addKeyValueToJSONObj(Object owner, Field field, JSONObject jsonObject) {        
        String key = field.getName();
        try{                
            String value = field.get(owner).toString();
            jsonObject.put(key, value);
        } catch(Exception e) {
            try {
                jsonObject.put(key, "null");
            } catch(Exception ex) {
                
            }
        }
    }
    
    private static void addKeyValueToJSONObj(Field field, JSONObject jsonValue, JSONObject jsonObject) {        
        String key = field.getName();
        try{
            jsonObject.put(key, jsonValue);
        } catch(Exception e) {
            try {
                jsonObject.put(key, "null");
            } catch(Exception ex) {
                
            }
        }
    }
    
    private static void addKeyValueFromComplexObject(Field field, Object obj, JSONObject jsonObject) {
        try {
            Object complexObj = field.get(obj);
            JSONObject complexJsonValue = toJSON(complexObj);
            addKeyValueToJSONObj(field, complexJsonValue, jsonObject);
        } catch (Exception e) {
            addKeyValueToJSONObj(obj, field, jsonObject);
        }
    }    

    private static void addCollectionValueToJsonObj(Field field, Object obj, JSONObject jsonObject) {
        if( isList(field) ) {
            addListValuesToJSONObj(field, obj, jsonObject);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }        
    }

    private static boolean isList(Field field) {
        Class<?> c = field.getType();
        return c.equals(java.util.List.class);
    }

    private static void addListValuesToJSONObj(Field field, Object obj, JSONObject jsonObject) {
        JSONArray jsonArray = new JSONArray();        
        try {
            List<Object> objs = (List<Object>)field.get(obj);
            for(Object current : objs ) {
                JSONObject currentJSON = toJSON(current);
                jsonArray.put(currentJSON);
            }
            jsonObject.put(field.getName(), jsonArray);
        } catch (Exception e) {
        }
    }
}
