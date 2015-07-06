package DAL;

import java.util.List;

import model.Category;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CategoryManager {

	Session session = null;

	public CategoryManager(Session currentSession) {
		session = currentSession;
	}
	
	public List<Category> getAll() {
		Query query = session.createQuery("from Category");
		List<Category> list = query.list();
		return list;
	}
	
	public Category getById(int categoryId) {
		Category category = (Category)session.get(Category.class, categoryId);
		return category;
	}

	public Category updateCategory(int categoryId, Category category) {
		category.setId(categoryId);
		session.beginTransaction();
		session.saveOrUpdate(category);
		session.getTransaction().commit();
		return category;
	}

	public Category removeCategory(int categoryId) {
		Category category = (Category)session.get(Category.class, categoryId);
		session.beginTransaction();
		session.delete(category);
		session.getTransaction().commit();
		return category;
	}
	
	public Category createCategory(Category category) {
		session.beginTransaction();
		session.persist(category);
		session.getTransaction().commit();
		return category;
	}
	
	@Override
	protected void finalize() throws Throwable {
		session.close();
		super.finalize();
	}
}