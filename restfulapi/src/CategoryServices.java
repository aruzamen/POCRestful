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
import DAL.BadRequestException;
import DAL.CategoryManager;
import DAL.EmployeeManager;
import DAL.HibernateUtil;

/**
 * Provide a category service.
 */
@Path("/category")
public class CategoryServices
{
    /** Constants */
    private CategoryManager categoryManager;
    private EmployeeManager employeeManager;

    /**
     * Initializes database connection.
     */
    public CategoryServices()
    {
        HibernateUtil hibernateUtil = HibernateUtil.getInstance();
        SessionFactory factory = hibernateUtil.getSessionFactory();
        categoryManager = new CategoryManager(factory.openSession());
        employeeManager = new EmployeeManager(factory.openSession());
    }

    /**
     * Gets all categories. 
     * @return list of categories 
     */
    @GET
    @Produces("application/json")
    public List<Category> getAll()
    {
        return categoryManager.getAll();
    }

    /**
     * Gets a category by ID.
     * @param categoryId unique identifier of category
     * @return category object
     */
    @GET
    @Path("{categoryId}")
    @Produces("application/json")
    public Category getById(@PathParam("categoryId") int categoryId)
    {
        return categoryManager.getById(categoryId);
    }

    /**
     * Updates a category. 
     * @param categoryId unique identifier of category
     * @param category object with changes
     * @return updated category
     */
    @PUT
    @Path("{categoryId}")
    @Produces("application/json")
    public Category updateCategory(@PathParam("categoryId") int categoryId, Category category)
    {
        return categoryManager.updateCategory(categoryId, category);
    }

    /**
     * Removes a category by ID.
     * @param categoryId unique identifier of category
     * @return removed category object
     */
    @DELETE
    @Path("{categoryId}")
    @Produces("application/json")
    public Category removeCategory(@PathParam("categoryId") int categoryId)
    {
        return categoryManager.removeCategory(categoryId);
    }

    /**
    * Creates a new category.
    * @param category object to be saved
    * @return category object
    */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    public Category createCategory(Category category)
    {
        return categoryManager.createCategory(category);
    }

    /**
     * Creates a new employee.
     * @param categoryId unique identifier of category
     * @param employee object to be saved
     * @return employee object
     */
    @POST @Path("{categoryId}/employee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    public Employee createEmployee(@PathParam("categoryId") int categoryId, Employee employee)
    {
        Category category = categoryManager.getById(categoryId);
        employee.setCategory(category);
        return employeeManager.createEmployee(employee);
    }

    /**
     * Gets a list of employee by category
     * @param categoryId unique identifier of category
     * @return a list of employees
     */
    @GET @Path("{categoryId}/employee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    public List<Employee> findEmployee(@PathParam("categoryId") int categoryId)
    {
        Category category = categoryManager.getById(categoryId);
        return employeeManager.findEmployees(categoryId);
    }
}