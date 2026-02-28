package com.techouts.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        // configure multipart file upload location and limits
        registration.setMultipartConfig(
                new MultipartConfigElement(
                        "C:/uploads",          // location for temporary storage, must exist
                        5 * 1024 * 1024,       // max file size = 5MB
                        10 * 1024 * 1024,      // max request size = 10MB
                        0                      // file size threshold after which files are written to disk
                )
        );
    }

}
