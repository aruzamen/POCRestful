package DAL;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import junit.framework.Assert;
import model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeManagerMockTest
{
    private EmployeeManager employeeManager;
    private Employee employeeTemplate;
    private Session session;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void EmployeeManagerBefore()
    {
        session = mock(Session.class);
        employeeManager = new EmployeeManager(session);

        // Creating a new category
        Employee newEmployee = new Employee();
        newEmployee.setFirstName("lorem");
        newEmployee.setLastName("ipsum");
        employeeTemplate = newEmployee;
    }

    @Test
    public void findEmployee()
    {
        when(session.get(Employee.class, 1)).thenReturn(employeeTemplate);
        Employee category = employeeManager.getById(1);
        assertEquals(category.getFirstName(), "lorem");
    }

    @Test
    public void findAllEmployees()
    {
        Query query = mock(Query.class);
        List<Employee> fakeEmployees = new ArrayList<Employee>();
        fakeEmployees.add(new Employee());
        fakeEmployees.add(new Employee());
        when(query.list()).thenReturn(fakeEmployees);
        when(session.createQuery("from Employee")).thenReturn(query);
        List<Employee> categories = employeeManager.getAll();
        Assert.assertEquals(2, categories.size());
    }

    @Test
    public void createEmployee() throws Exception
    {
        Transaction transaction = mock(TransactionLocal.class);
        when(session.getTransaction()).thenReturn(transaction);
        employeeManager.createEmployee(employeeTemplate);
        verify(session).persist(employeeTemplate);
    }

    @Test
    public void createEmployeeFirstNameError() throws Exception
    {
        thrown.expect(Exception.class);
        employeeManager.createEmployee(new Employee());
        thrown.expectMessage("FisrtName of employee should be provided");
    }

    @Test
    public void createEmployeeLastNameError() throws Exception
    {
        thrown.expect(Exception.class);
        Employee employee = new Employee();
        employee.setFirstName("lorem");
        employeeManager.createEmployee(employee);
        thrown.expectMessage("LastName of employee should be provided");
    }

    @Test
    public void updateEmployee()
    {
        Transaction transaction = mock(TransactionLocal.class);
        when(session.getTransaction()).thenReturn(transaction);
        employeeManager.updateEmployee(1, employeeTemplate);
        verify(session).saveOrUpdate(employeeTemplate);
    }

    @Test
    public void deleteEmployee()
    {
        Transaction transaction = mock(TransactionLocal.class);
        when(session.get(Employee.class, 1)).thenReturn(employeeTemplate);
        when(session.getTransaction()).thenReturn(transaction);
        employeeManager.removeEmployee(1);
        verify(session).get(Employee.class, 1);
        verify(session).delete(employeeTemplate);
    }
}
