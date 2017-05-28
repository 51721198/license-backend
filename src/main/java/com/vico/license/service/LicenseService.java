package com.vico.license.service;

import com.vico.license.pojo.DatatableModel;
import com.vico.license.pojo.LicenseDetail;
import com.vico.license.pojo.RSAKey;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface LicenseService {

    public Map<String, String> createSourceCode(String duedate, int hosnumber);

    public List<LicenseDetail> listAllCodes();

    public LicenseDetail listOneCode(int serialNumberId);

    public int countEndDate(Date expiredDate);

    public List<LicenseDetail> selectByhospitalNumber(int hospitalNumber);

    /**
     * @param:
     * @return: void
     * @Title: taskChangeData
     * @Description: spring定时任务，每隔24小时自动修改一次序列号信息
     */
    public void taskChangeData();

    /**
     * @param record
     * @return
     * @param:
     * @return: int
     * @Title: saveCode
     * @Description: 增, 删, 改
     */
    public int saveCode(LicenseDetail record);

    public int modifyLicenseState(int serialNumberId);

    public int deleteCode(int codeID);

    public int createKeyPair(RSAKey rsaKey);

    String createEncryptCode(String code, byte[] publickey);

    RSAKey getLatestRSAKey();

    public boolean createZIPFile(int serialNumberId);

    public DatatableModel getLicenseByPage(Integer draw, Integer start, Integer length);


}
