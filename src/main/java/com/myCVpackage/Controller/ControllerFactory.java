package com.myCVpackage.Controller;

import com.myCVpackage.data.Repository.RepoFactory;

/**
 * Created by buresina on 21/10/2016.
 */
public class ControllerFactory {
    private ControllerFactory(){}

    public static IController createController(Class tClass) {
        return new GenericController(RepoFactory.createRepo(tClass));
    }
}
