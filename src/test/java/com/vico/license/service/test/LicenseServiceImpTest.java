package com.vico.license.service.test;

import com.vico.license.service.LicenseService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class) // = extends SpringJUnit4ClassRunner
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class LicenseServiceImpTest {


    private static final Logger logger = Logger.getLogger(LicenseServiceImpTest.class);
    @Autowired
    private LicenseService lsi;

//	@Test
//	public void testcreateSourceCode() {
//		System.out.println("*****************"+lsi.createSourceCode("2017-08-28",21));
//	}

    //警告：该测试将向数据库中插入不规范数据，慎用！！！！！！！！！！！
//	@Test
//		public void testMybatis() {
//		
//			LicenseDetail ldetail = new LicenseDetail();
//			ldetail.setCreateDay("2016-05-28");
//			ldetail.setEncryptedNumber("asdhhas");
//			ldetail.setExpiredDate("2017-07-15");
//			ldetail.setExpiredFlag(1);
//			ldetail.setHospitalNumber(23);
//			//ldetail.setSerialNumberId(1);
//			ldetail.setSourceNumber("asdsadsaaaaaadsa");
//			ldetail.setValidDays(100);
//			
//			System.out.println(ldetail);
//			lsi.saveCode(ldetail);
//			//logger.info(JSON.toJSONStringWithDateFormat("add "+i, "yyyy-MM-dd HH:mm:ss"));
//		}

    //	@Test
//	public void testModifyLicenseState(){
//		lsi.modifyLicenseState(101);
//	}
//	@Test
//	public void showAllCodesTest(){
//		logger.info("*****************测试信息********************"+lsi.listAllCodes());
//	}
    @Test
    public void selectkeyTest() {
        System.out.println(lsi.getLatestRSAKey());
    }


}
