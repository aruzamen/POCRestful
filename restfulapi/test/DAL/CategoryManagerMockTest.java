package DAL;


import javax.transaction.TransactionalException;

import junit.framework.Assert;
import model.Category;
import model.Employee;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoryManagerMockTest {

	CategoryManager categoryManager;
	Category categoryTemplate;
	Session session;
	
	@Before
	public void CategoryManagerMockTest1() {
		session = mock(Session.class);
		categoryManager = new CategoryManager(session);
		
		// Creating a new category
		Category newCategory = new Category(); 
		newCategory.setName("next generation");
		categoryTemplate = newCategory;
	}
	
	@Test
	public void findCategory() {
		when(session.get(Category.class, 1)).thenReturn(categoryTemplate);
		Category category = categoryManager.getById(1);
		assertEquals(category.getName(), "next generation");
	}
	
	@Test
	public void createCategory() {
		Transaction transaction = mock(TransactionLocal.class); 
		when(session.getTransaction()).thenReturn(transaction);
		Category category = categoryManager.createCategory(categoryTemplate);
		verify(session).persist(categoryTemplate);
	}
}
