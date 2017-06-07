package com.vico.license.util.test;

import com.vico.license.util.ClassPathResourceURI;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassPathResourceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathResourceTest.class);

    @Test
    public void testGetResourceURI() {
        //fail("Not yet implemented");
        LOGGER.info("testGetResourceURI" + ClassPathResourceURI.getResourceURI("/"));

    }

    @Test
    public void testGetWorkspacePath() {
        //fail("Not yet implemented");
        LOGGER.info("testGetWorkspacePath" + ClassPathResourceURI.getProjectPath());
    }

    @Test
    public void testGetProjectPath() {
        //fail("Not yet implemented");
        LOGGER.info("testGetResourceURI" + ClassPathResourceURI.getProjectPath());
    }

    @Test
    public void testGetParentFilePath() {
        //fail("Not yet implemented");
    }

}
