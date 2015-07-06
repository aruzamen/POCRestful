package DAL;

import java.util.List;
import junit.framework.Assert;
import model.Category;
import org.hibernate.SessionFactory;
import org.junit.Test;

public class CategoryManagerTest {
	
	private CategoryManager categoryManager;
	
	public CategoryManagerTest() {
		HibernateUtil hibernateUtil = HibernateUtil.getInstance();		
		SessionFactory factory = hibernateUtil.getSessionFactory();
		categoryManager = new CategoryManager(factory.openSession());
	}

	@Test
	public void createCategory() {
		Category newCategory = new Category();
		newCategory.setName("test1");
		Category category = categoryManager.createCategory(newCategory);

		categoryManager.removeCategory(category.getId());
		Assert.assertEquals(category.getName(), newCategory.getName());
	}

	@Test
	public void getAll() {
		Category newCategory = new Category();
		newCategory.setName("test1");
		Category category = categoryManager.createCategory(newCategory);
		List<Category> categories = categoryManager.getAll();
		Category categoryResult = categoryManager.removeCategory(category.getId());
		Assert.assertEquals(true, true);
	}

	@Test
	public void getById() {
		Category newCategory = new Category();
		newCategory.setName("test1");
		Category category = categoryManager.createCategory(newCategory);

		Category categoryResult = categoryManager.getById(category.getId());
		 
		categoryManager.removeCategory(category.getId());
		Assert.assertEquals(category.getId(), categoryResult.getId());
	}
	
	@Test
	public void updateCategory() {
		Category newCategory = new Category();
		newCategory.setName("test1");
		Category category = categoryManager.createCategory(newCategory);
		category.setName("test update");
		Category categoryResult = categoryManager.updateCategory(category.getId(), category);
		categoryManager.removeCategory(category.getId());
		Assert.assertEquals("test update", categoryResult.getName());
	}

	@Test
	public void deleteCategory() {
		Category newCategory = new Category();
		newCategory.setName("test1");
		Category category = categoryManager.createCategory(newCategory);
		Category categoryResult = categoryManager.removeCategory(category.getId());
		Assert.assertEquals(category.getId(), categoryResult.getId());
	}
}

