package POCServer.Test;

import Util.JSONConverter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class JSONConveterTest {
    
    public class FakeEntity{
        public FakeEntity(int id, String name) {
            this.id = id;
            this.name = name;
        }
        private final int id;
        private final String name;
    }
    
    @Test
    public void toJSON_WithAValidEntity_ConvertsCorrectlyToAJSONObject() throws JSONException {
        JSONObject expected = new JSONObject();
        int id = 1;
        String name = "name";
        expected.put("id", id+"");
        expected.put("name", name);
               
        FakeEntity entity = new FakeEntity(id, name); 
        JSONObject actual = JSONConverter.toJSON(entity);
                
        assertEquals(expected.toString(), actual.toString());
    }
    
    public class ComplexFakeEntity {
        private final int id;
        private final FakeEntity fakeEntity;

        public ComplexFakeEntity(int id, FakeEntity fakeEntity) {
            this.id = id;
            this.fakeEntity = fakeEntity;
        } 
    }
    
    @Test
    public void toJSON_WithAValidComplexEntity_ConvertsCorrectlyToAJSONObjectIncludingTheNestedObjectsAreConverted() throws JSONException {
        JSONObject innerExpected = new JSONObject();
        int innerId = 1;
        String innerName = "inner";
        innerExpected.put("id", innerId+"");
        innerExpected.put("name", innerName);        
        int id = 1;
        JSONObject expected = new JSONObject();
        expected.put("id", id+"");
        expected.put("fakeEntity", innerExpected);
        FakeEntity entity = new FakeEntity(innerId, innerName);
        ComplexFakeEntity complexEntity = new ComplexFakeEntity(id, entity);
        
        JSONObject actual = JSONConverter.toJSON(complexEntity);
                
        assertEquals(expected.toString(), actual.toString());
    }
    
    public class ComplexFakeEntityWithInnerList {
        private final int id;
        private final FakeEntity fakeEntity;
        private final List<FakeEntity> fakeEntities;
        
        public ComplexFakeEntityWithInnerList(int id, FakeEntity fakeEntity, List<FakeEntity> fakes) {
            this.id = id;
            this.fakeEntity = fakeEntity;
            this.fakeEntities = fakes;
        } 
    }
    
    @Test
    public void toJSON_WithAnEntityThatHasAList_ConvertsCorrectlyToAJSONObjectIncludingTheListIsConverted() throws JSONException {
        JSONObject innerExpected = new JSONObject();
        int innerId = 1;
        String innerName = "inner";
        innerExpected.put("id", innerId+"");
        innerExpected.put("name", innerName);        
        int id = 1;
        JSONObject expected = new JSONObject();
        expected.put("id", id+"");
        expected.put("fakeEntity", innerExpected);
        JSONArray expectedLst = new JSONArray();
        expectedLst.put(innerExpected);
        expected.put("fakeEntities", expectedLst);
        
        FakeEntity entity = new FakeEntity(innerId, innerName);
        List<FakeEntity> list = new ArrayList<>();
        list.add(entity);
        ComplexFakeEntityWithInnerList complexEntity = new ComplexFakeEntityWithInnerList(id, entity, list);
        
        JSONObject actual = JSONConverter.toJSON(complexEntity);
                
        assertEquals(expected.toString(), actual.toString());
    }
}
