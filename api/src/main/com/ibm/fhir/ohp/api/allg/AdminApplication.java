package com.ibm.fhir.ohp.api.allg;

import java.io.File;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("")
public class AdminApplication extends Application {
    public AdminApplication() {
        System.out.println("API started");
    }
}