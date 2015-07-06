package DAL;

import java.util.List;
import model.Employee;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

public class EmployeeManager {

	Session session = null;

	public EmployeeManager(Session currentSession) {
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
		Hibernate.initialize(removeEmployee.getCategory());
		session.beginTransaction();
		session.delete(removeEmployee);
		session.getTransaction().commit();
		return removeEmployee;
	}

	public Employee createEmployee(Employee employee) {
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
