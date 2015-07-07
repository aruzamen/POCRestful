package DAL;

import java.util.List;
import model.Category;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Handles all category resource.
 */
public class CategoryManager {

    /** Data base session */
    Session session = null;

    /**
     * Constructor.
     * @param  currentSession  database connection
     */
    public CategoryManager(Session currentSession) {
        if (currentSession == null) {
            throw new BadRequestException("There is not access to Database");
        }
        session = currentSession;
    }

    /**
     * Gets all categories.
     * @return list of categories 
     */
    public List<Category> getAll() {
        Query query = session.createQuery("from Category");
        List<Category> list = query.list();
        return list;
    }

    /**
     * Finds a category by ID.
     * @param categoryId unique identifier of category
     * @return category object
     */
    public Category getById(int categoryId) {
        validateCategoryId(categoryId);
        Category category = (Category)session.get(Category.class, categoryId);
        if (category == null) {
            throw new BadRequestException("Category object does not exist");
        }
        return category;
    }

    /**
     * Updates a category.
     * @param categoryId unique identifier of category
     * @param category object with changes
     * @return updated category object 
     */
    public Category updateCategory(int categoryId, Category category) {
        validateCategoryId(categoryId);
        category.setId(categoryId);
        session.beginTransaction();
        session.saveOrUpdate(category);
        session.getTransaction().commit();
        return category;
    }

    /**
     * Removes a category by ID.
     * @param categoryId unique identifier of category
     * @return removed category object
     */
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

    /**
     * Validates if category Id is more than zero.
     * @param categoryId unique identifier of category
     */
    private void validateCategoryId(int categoryId) {
        if(categoryId < 0) {
            throw new BadRequestException("CategoryId should more than zero");
        }
    }

    /**
     * Creates a new category.
     * @param category object to be saved
     * @return category object
     */
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