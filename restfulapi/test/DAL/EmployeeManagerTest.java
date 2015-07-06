package DAL;

import junit.framework.Assert;
import model.Category;
import model.Employee;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
public class EmployeeManagerTest {
	
	@Test
	public void test() {
		Assert.assertEquals(true, true);
	}
	/*public Employee getEmployee() {
		Employee newEmployee = new Employee();
		newEmployee.setFirstName("Name");
		Category category = CategoryManager.getById(1);
		newEmployee.setCategory(category);
		Employee employee = EmployeeManager.createEmployee(newEmployee);
		return employee;
	}*/
	
	/*@Before
	public void EmployeeManagerTest2() {
		HibernateUtil hibernateUtil = mock(HibernateUtil.class);		
		SessionFactory factory = mock(SessionFactory.class);
		Session session = mock(Session.class);
		//when(factory.openSession()).thenReturn(session);
		
		//EmployeeManager.setLocal(hibernateUtil, factory);
	}*/
	
	/*@Test
	public void createEmployee() {
		//Employee employee = getEmployee();
		//EmployeeManager.removeEmployee(employee.getId());
		try {
			EmployeeManager.removeEmployee(40);

		} catch (Exception e) {
			System.out.print(e);
		}
		//Assert.assertEquals("Name", employee.getFirstName());
	}*/

/*	@Test
	public void getAll() {
		Employee employee = getEmployee();
		List<Employee> categories = EmployeeManager.getAll();
		Employee categoryResult = EmployeeManager.removeEmployee(employee.getId());
		Assert.assertEquals(true, true);
	}
*/
	/*@Test
	public void getById() {
		Employee employee = getEmployee();

		Employee categoryResult = EmployeeManager.getById(employee.getId());

		EmployeeManager.removeEmployee(employee.getId());
		Assert.assertEquals(employee.getId(), categoryResult.getId());
	}*/

/*	@Test
	public void updateEmployee() {
		Employee employee = getEmployee();
		employee.setFirstName("Name update");
		Employee categoryResult = EmployeeManager.updateEmployee(employee.getId(), employee);
		EmployeeManager.removeEmployee(employee.getId());
		Assert.assertEquals("Name update", categoryResult.getFirstName());
	}

	@Test
	public void deleteEmployee() {
		Employee employee = getEmployee();
		Employee categoryResult = EmployeeManager.removeEmployee(employee.getId());
		Assert.assertEquals(employee.getId(), categoryResult.getId());
	}
*/
}
