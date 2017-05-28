package com.vico.license.dao.test;

import com.vico.license.dao.HospitalDao;
import com.vico.license.pojo.Hospital;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) // = extends SpringJUnit4ClassRunner
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class HospitalDaoTest {

    private static final Logger logger = Logger.getLogger(UserDaoTest.class);
    @Autowired
    HospitalDao hospitalDao;

    @Test
    public void testSelectAllHospitalsByPage() {
        List<Hospital> list = hospitalDao.selectAllHospitalsByPage(2, 8);
        for (Hospital hospital : list) {
            logger.info(">>: " + hospital);
        }
    }

    @Test
    public void testSelectCountHospitals() {
        logger.info("***********" + hospitalDao.selectCountHospitals());
    }
}
