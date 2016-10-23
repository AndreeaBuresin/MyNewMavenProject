package com.myCVpackage.Controller;

import java.util.List;

/**
 * Created by buresina on 21/10/2016.
 */
public interface IController <T> {

    T findById(int id) throws Exception;

    List<T> findAll() throws Exception;

    List<T> findByColumn(String column, String property) throws Exception;

    void save(T entity) throws Exception;

}
