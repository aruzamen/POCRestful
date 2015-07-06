package DAL.Public;

import DAL.POCContext;

public class DALFactory {
    public static Context getContext() {
        return new POCContext();
    }
        
}
