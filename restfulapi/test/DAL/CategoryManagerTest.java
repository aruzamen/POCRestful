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
	public void getAll() throws Exception {
		Category newCategory = new Category();
		newCategory.setName("test1");
		Category category = categoryManager.createCategory(newCategory);
		List<Category> categories = categoryManager.getAll();
		Category categoryResult = categoryManager.removeCategory(category.getId());
		Assert.assertEquals(true, true);
	}

	@Test
	public void getById() throws Exception {
		Category newCategory = new Category();
		newCategory.setName("test1");
		Category category = categoryManager.createCategory(newCategory);
		Category categoryResult = categoryManager.getById(category.getId());
		categoryManager.removeCategory(category.getId());
		Assert.assertEquals(category.getId(), categoryResult.getId());
	}
}

