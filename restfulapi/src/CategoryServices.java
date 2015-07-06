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

import model.Category;
import model.Employee;
import DAL.CategoryManager;
import DAL.EmployeeManager;
import DAL.HibernateUtil;

@Path("/category")
public class CategoryServices {

	private CategoryManager categoryManager;
	private EmployeeManager employeeManager;

	public CategoryServices() {
		HibernateUtil hibernateUtil = HibernateUtil.getInstance();		
		SessionFactory factory = hibernateUtil.getSessionFactory();
		categoryManager = new CategoryManager(factory.openSession());
		employeeManager = new EmployeeManager(factory.openSession());
	}

	@GET
	@Produces("application/json")
	public List<Category> getAll() {
		return categoryManager.getAll();
	}

	@GET
	@Path("{categoryId}")
	@Produces("application/json")
	public Category getById(@PathParam("categoryId") int categoryId) {
		return categoryManager.getById(categoryId);
	}

	@PUT
	@Path("{categoryId}")
	@Produces("application/json")
	public Category updateEmployee(@PathParam("categoryId") int categoryId, Category category) {
		return categoryManager.updateCategory(categoryId, category);
	}

	@DELETE
	@Path("{categoryId}")
	@Produces("application/json")
	public Category removeEmployee(@PathParam("categoryId") int categoryId) {
		return categoryManager.removeCategory(categoryId);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Category createGroup(Category group) throws Exception {
		return categoryManager.createCategory(group);
	}

	@POST @Path("{categoryId}/employee")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Employee createEmployee(@PathParam("categoryId") int categoryId, Employee employee) throws Exception {
		if (employee.getFirstName() == null) {
			throw new Exception("FisrtName of employee should be provided");
		}
		if (employee.getLastName() == null) {
			throw new Exception("LastName of employee should be provided");
		}
		Category category = categoryManager.getById(categoryId); 
		employee.setCategory(category);
		return employeeManager.createEmployee(employee);
	}
}