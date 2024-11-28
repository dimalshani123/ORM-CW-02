package lk.ijse.dao.custom;

import java.util.List;

public interface CrudDAO<T> extends SuperDAO {

    public boolean save(T entity);

    public List<T> getAll();
    public String getCurrentID();
    public boolean update(T entity);
    public boolean delete(String id);
    public  T search(String id);
}
