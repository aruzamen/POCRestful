package DAL;

import java.util.List;
import model.Employee;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

public class EmployeeManager {

    Session session = null;

    public EmployeeManager(Session currentSession) {
        if (currentSession == null) {
            throw new BadRequestException("There is not access to Database");
        }
        session = currentSession;
    }

    public List<Employee> getAll() {
        Query query = session.createQuery("from Employee");
        List<Employee> list = query.list(); 
        for (Employee emp : list) {
            Hibernate.initialize(emp.getCategory());
        }
        return list;
    }

    public Employee getById(int employeeId) {
        Employee employee = (Employee)session.get(Employee.class, employeeId);
        if (employee == null) {
            throw new BadRequestException("Employee object does not exist");
        }
        Hibernate.initialize(employee.getCategory());
        return employee;
    }

    public Employee updateEmployee(int employeeId, Employee employee) {
        employee.setId(employeeId);
        session.beginTransaction();
        session.saveOrUpdate(employee);
        session.getTransaction().commit();
        return employee;
    }

    public Employee removeEmployee(int employeeId) {
        Employee removeEmployee = (Employee)session.get(Employee.class, employeeId);
        if (removeEmployee == null) {
            throw new BadRequestException("Employee object does not exist");
        }
        Hibernate.initialize(removeEmployee.getCategory());
        session.beginTransaction();
        session.delete(removeEmployee);
        session.getTransaction().commit();
        return removeEmployee;
    }

    public Employee createEmployee(Employee employee) {
        if (employee.getFirstName() == null) {
            throw new BadRequestException("FirstName of employee should be provided");
        }
        if (employee.getLastName() == null) {
            throw new BadRequestException("LastName of employee should be provided");
        }
        session.beginTransaction();
        session.persist(employee);
        session.getTransaction().commit();
        return employee;
    }

    @Override
    protected void finalize() throws Throwable {
        session.close();
        super.finalize();
    }
}
