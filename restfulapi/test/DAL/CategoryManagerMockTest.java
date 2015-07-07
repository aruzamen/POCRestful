package DAL;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import junit.framework.Assert;
import model.Category;
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
public class CategoryManagerMockTest {

    CategoryManager categoryManager;
    Category categoryTemplate;
    Session session;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void CategoryManagerBefore() {
        session = mock(Session.class);
        categoryManager = new CategoryManager(session);

        // Creating a new category
        Category newCategory = new Category(); 
        newCategory.setName("next generation");
        categoryTemplate = newCategory;
    }

    @Test
    public void findCategory() throws Exception {
        when(session.get(Category.class, 1)).thenReturn(categoryTemplate);
        Category category = categoryManager.getById(1);
        assertEquals(category.getName(), "next generation");
    }
    
    @Test
    public void findCategoryError() {
        thrown.expect(BadRequestException.class);
        categoryManager.getById(-1);
    }

    @Test
    public void findAllCategories() {
        Query query = mock(Query.class);
        List<Category> fakeCategories = new ArrayList<Category>();
        fakeCategories.add(new Category());
        fakeCategories.add(new Category());
        when(query.list()).thenReturn(fakeCategories);
        when(session.createQuery("from Category")).thenReturn(query);   
        List<Category> categories = categoryManager.getAll();
        Assert.assertEquals(2, categories.size());
    }

    @Test
    public void createCategory() throws Exception {
        Transaction transaction = mock(TransactionLocal.class); 
        when(session.getTransaction()).thenReturn(transaction);
        categoryManager.createCategory(categoryTemplate);
        verify(session).persist(categoryTemplate);
    }
    
    @Test
    public void updateCategory() throws Exception {
        Transaction transaction = mock(TransactionLocal.class); 
        when(session.getTransaction()).thenReturn(transaction);
        categoryManager.updateCategory(1, categoryTemplate);
        verify(session).saveOrUpdate(categoryTemplate);
    }
    
    @Test
    public void updateCategoryError() {
        thrown.expect(BadRequestException.class);
        categoryManager.updateCategory(-1, categoryTemplate);
    }

    @Test
    public void deleteCategory() throws Exception {
        Transaction transaction = mock(TransactionLocal.class); 
        when(session.get(Category.class, 1)).thenReturn(categoryTemplate);
        when(session.getTransaction()).thenReturn(transaction);
        categoryManager.removeCategory(1);
        verify(session).get(Category.class, 1);
        verify(session).delete(categoryTemplate);
    }
    
    @Test
    public void deleteCategoryError() throws Exception {
        thrown.expect(BadRequestException.class);
        categoryManager.removeCategory(-1);
    }
}
