package com.myCVpackage.Controller;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by buresina on 21/10/2016.
 */
public class GenericController<T> implements IController<T> {

    private IController<T> repository;

    public GenericController(IController<T> repository) {
        this.repository = repository;
    }
    public T findById(int id) throws Exception{
        return repository.findById(id);
    }

    public List<T> findAll() throws Exception {
        return repository.findAll();
    }

    public List<T> findByColumn(String column, String property) throws Exception{
        return repository.findByColumn(column, property);
    }

    public void save(T entity) throws Exception{
        repository.save(entity);
    }
}
