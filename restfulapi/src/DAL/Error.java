package DAL;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Error {

	private String error;

	public Error() {
	}
	
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
