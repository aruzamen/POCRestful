package DAL;

import java.util.ArrayList;
import java.util.List;
import model.Employee;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Handles all employee resource.
 */
public class EmployeeManager
{
    /** Database session */
    Session session = null;

    /**
     * Constructor.
     * @param  currentSession  database connection
     */
    public EmployeeManager(Session currentSession)
    {
        if (currentSession == null)
        {
            throw new BadRequestException("There is not access to Database");
        }
        session = currentSession;
    }

    /**
     * Gets all employees.
     * @return list of employees 
     */
    public List<Employee> getAll()
    {
        Query query = session.createQuery("from Employee");
        List<Employee> list = query.list(); 
        for (Employee emp : list)
        {
            Hibernate.initialize(emp.getCategory());
        }
        return list;
    }

    /**
     * Finds an employee by ID.
     * @param employeeId unique identifier of employee
     * @return employee object
     */
    public Employee getById(int employeeId)
    {
        Employee employee = (Employee)session.get(Employee.class, employeeId);
        if (employee == null)
        {
            throw new BadRequestException("Employee object does not exist");
        }
        Hibernate.initialize(employee.getCategory());
        return employee;
    }

    /**
     * Updates a employee.
     * @param employeeId unique identifier of employee
     * @param employee object with changes
     * @return updated employee object 
     */
    public Employee updateEmployee(int employeeId, Employee employee)
    {
        employee.setId(employeeId);
        session.beginTransaction();
        session.saveOrUpdate(employee);
        session.getTransaction().commit();
        return employee;
    }

    /**
     * Removes a employee by ID.
     * @param employeeId unique identifier of employee
     * @return removed employee object
     */
    public Employee removeEmployee(int employeeId)
    {
        Employee removeEmployee = (Employee)session.get(Employee.class, employeeId);
        if (removeEmployee == null)
        {
            throw new BadRequestException("Employee object does not exist");
        }
        Hibernate.initialize(removeEmployee.getCategory());
        session.beginTransaction();
        session.delete(removeEmployee);
        session.getTransaction().commit();
        return removeEmployee;
    }

    /**
     * Creates a new employee.
     * @param employee object to be saved
     * @return employee object
     */
    public Employee createEmployee(Employee employee)
    {
        if (employee.getFirstName() == null)
        {
            throw new BadRequestException("FirstName of employee should be provided");
        }
        if (employee.getLastName() == null)
        {
            throw new BadRequestException("LastName of employee should be provided");
        }
        session.beginTransaction();
        session.persist(employee);
        session.getTransaction().commit();
        return employee;
    }

    /**
     * Finds employees by category.
     * @param categoryId unique identifier of category
     * @return list of employees
     */
    public List<Employee> findEmployees(int categoryId)
    {
        Query query = session.createQuery("from Employee where categoryId = :categoryId ");
        query.setParameter("categoryId", categoryId);
        List<Employee> list = query.list();
        if (list.size() == 0)
        {
            return new ArrayList<Employee>();
        }
        for (Employee emp : list)
        {
            Hibernate.initialize(emp.getCategory());
        }
        return list;
    }

    @Override
    protected void finalize() throws Throwable
    {
        session.close();
        super.finalize();
    }
}
