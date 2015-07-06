import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.hibernate.SessionFactory;
import DAL.EmployeeManager;
import DAL.HibernateUtil;
import model.Employee;

@Path("/employee")
public class EmployeeServices {

	private EmployeeManager employeeManager;

	public EmployeeServices() {
		HibernateUtil hibernateUtil = HibernateUtil.getInstance();		
		SessionFactory factory = hibernateUtil.getSessionFactory();
		employeeManager = new EmployeeManager(factory.openSession());
	}

	@GET
	@Produces("application/json")
	public List<Employee> getAll() {
		List<Employee> employess = employeeManager.getAll(); 
		return employess;
	}

	@GET
	@Path("{employeeId}")
	@Produces("application/json")
	public Employee getById(@PathParam("employeeId") int employeeId) {
		return employeeManager.getById(employeeId);
	}

	@PUT
	@Path("{employeeId}")
	@Produces("application/json")
	public Employee updateEmployee(@PathParam("employeeId") int employeeId, Employee employee) {
		return employeeManager.updateEmployee(employeeId, employee);
	}

	@DELETE
	@Path("{employeeId}")
	@Produces("application/json")
	public Employee removeEmployee(@PathParam("employeeId") int employeeId) {
		return employeeManager.removeEmployee(employeeId);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Employee createEmployee(Employee employee) throws Exception {
		return employeeManager.createEmployee(employee);
	}
}