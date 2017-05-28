package com.vico.license.service;

import com.vico.license.pojo.DatatableModel;
import com.vico.license.pojo.Hospital;

import java.util.List;

public interface HospitalService {

    /**
     * @return
     * @param:
     * @return: List<Hospital>
     * @Title: showAllHospitals
     * @Description: 查
     */
     List<Hospital> showAllHospitals();

     String selectHospitalName(int hospitalNumber);

     Hospital showOneHospital(int hospitalNumber);


    /**
     * @param hospital,hospitalNumber
     * @return: int
     * @Description: 增, 删, 改
     */
     int addHospital(Hospital hospital);

     int deleteHospital(int hospitalNumber);

     int updateHospital(Hospital hospital);

     DatatableModel getHospitalByPage(Integer draw, Integer start, Integer length);
}
