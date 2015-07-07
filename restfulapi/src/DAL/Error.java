package DAL;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class used on Bad Request Exception.
 */
@XmlRootElement
public class Error {

    private String error;

    /**
     * Empty constructor.
     */
    public Error() {
    }
    
    /**
     * Constructor.
     * @param errorMessage message to send on a failed request
     */
    public Error(String errorMessage) {
        setError(errorMessage);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
