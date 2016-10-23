package com.myCVpackage.data.Repository;

import com.myCVpackage.Controller.IController;

/**
 * Created by buresina on 21/10/2016.
 */
public class RepoFactory {
    public static IController createRepo(Class tClass){
        return new GenericRepository(tClass);
    }
}
