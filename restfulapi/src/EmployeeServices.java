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

/**
 * Provide a category service.
 */
@Path("/employee")
public class EmployeeServices
{
    /** Constants */
    private EmployeeManager employeeManager;

    /**
     * Initializes database connection.
     */
    public EmployeeServices() {
        HibernateUtil hibernateUtil = HibernateUtil.getInstance();
        SessionFactory factory = hibernateUtil.getSessionFactory();
        employeeManager = new EmployeeManager(factory.openSession());
    }

    /**
     * Gets all employees.
     * @return list of employees 
     */
    @GET
    @Produces("application/json")
    public List<Employee> getAll()
    {
        List<Employee> employess = employeeManager.getAll(); 
        return employess;
    }

    /**
     * Finds an employee by ID.
     * @param employeeId unique identifier of employee
     * @return employee object
     */
    @GET
    @Path("{employeeId}")
    @Produces("application/json")
    public Employee getById(@PathParam("employeeId") int employeeId)
    {
        return employeeManager.getById(employeeId);
    }

    /**
     * Updates a employee.
     * @param employeeId unique identifier of employee
     * @param employee object with changes
     * @return updated employee object 
     */
    @PUT
    @Path("{employeeId}")
    @Produces("application/json")
    public Employee updateEmployee(@PathParam("employeeId") int employeeId, Employee employee)
    {
        return employeeManager.updateEmployee(employeeId, employee);
    }

    /**
     * Removes a employee by ID.
     * @param employeeId unique identifier of employee
     * @return removed employee object
     */
    @DELETE
    @Path("{employeeId}")
    @Produces("application/json")
    public Employee removeEmployee(@PathParam("employeeId") int employeeId)
    {
        return employeeManager.removeEmployee(employeeId);
    }

    /**
     * Creates a new employee.
     * @param employee object to be saved
     * @return employee object
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    public Employee createEmployee(Employee employee) throws Exception
    {
        return employeeManager.createEmployee(employee);
    }
}