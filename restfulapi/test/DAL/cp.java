package DAL;

public class cp {

}/*
package DAL;

import model.Employee;

import org.hibernate.Session;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class CategoryManagerMockTest {

	EmployeeManager employeeManager;
	
	public CategoryManagerMockTest() {
		//HibernateUtil hibernateUtil = mock(HibernateUtil.class);		
		//SessionFactory factory = mock(SessionFactory.class);
		
		Session session = mock(Session.class);
		Employee employee = new Employee(); 
		when(session.get()).thenReturn();
		employeeManager = new EmployeeManager(session);

		
		//EmployeeManager.setLocal(hibernateUtil, factory);
	}
	
	public void initTest() {
		
		employeeManager.getById(1);
	}
}
*/