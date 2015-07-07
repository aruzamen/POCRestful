package DAL;

import java.util.List;
import model.Category;
import org.hibernate.Query;
import org.hibernate.Session;

public class CategoryManager {

    Session session = null;

    public CategoryManager(Session currentSession) {
        if (currentSession == null) {
            throw new BadRequestException("There is not access to Database");
        }
        session = currentSession;
    }

    public List<Category> getAll() {
        Query query = session.createQuery("from Category");
        List<Category> list = query.list();
        return list;
    }

    public Category getById(int categoryId) {
        validateCategoryId(categoryId);
        Category category = (Category)session.get(Category.class, categoryId);
        if (category == null) {
            throw new BadRequestException("Category object does not exist");
        }
        return category;
    }

    public Category updateCategory(int categoryId, Category category) {
        validateCategoryId(categoryId);
        category.setId(categoryId);
        session.beginTransaction();
        session.saveOrUpdate(category);
        session.getTransaction().commit();
        return category;
    }

    public Category removeCategory(int categoryId) {
        validateCategoryId(categoryId);
        Category category = (Category)session.get(Category.class, categoryId);
        if (category == null) {
            throw new BadRequestException("Category object does not exist");
        }
        session.beginTransaction();
        session.delete(category);
        session.getTransaction().commit();
        return category;
    }

    private void validateCategoryId(int categoryId) {
        if(categoryId < 0) {
            throw new BadRequestException("CategoryId should more than zero");
        }
    }

    public Category createCategory(Category category) {
        if (category.getName().equals("")) {
            throw new BadRequestException("name is empty");
        }
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