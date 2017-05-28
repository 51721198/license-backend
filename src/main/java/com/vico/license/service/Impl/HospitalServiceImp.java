package com.vico.license.service.Impl;

import com.vico.license.dao.HospitalDao;
import com.vico.license.pojo.DatatableModel;
import com.vico.license.pojo.Hospital;
import com.vico.license.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class HospitalServiceImp implements HospitalService {

    @Autowired
    private HospitalDao hospitaldao;

    @Override
    public List<Hospital> showAllHospitals() {

        List<Hospital> list = hospitaldao.showAll();
        return list;
    }

    @Override
    public int addHospital(Hospital hospital) {
        int i = 0;
        try {
            i = hospitaldao.insertHospital(hospital);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return i;
    }

    @Override
    public int deleteHospital(int hospitalnumber) {

        int i = 0;
        try {
            i = hospitaldao.deleteByPrimaryKey(hospitalnumber);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return i;
    }

    @Override
    public String selectHospitalName(int hospitalNumber) {

        String hospitalName = null;

        if (hospitaldao.selectByPrimaryKey(hospitalNumber) == null) {
            return "无此医院信息";
        }

        hospitalName = hospitaldao.selectByPrimaryKey(hospitalNumber).getHospitalName();

        return hospitalName;
    }

    @Override
    public int updateHospital(Hospital hospital) {
        int i = 0;
        try {
            i = hospitaldao.updateByPrimaryKey(hospital);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return i;
    }

    @Override
    public Hospital showOneHospital(int hospitalNumber) {

        return hospitaldao.selectByPrimaryKey(hospitalNumber);
    }

    @Override
    public DatatableModel getHospitalByPage(Integer draw, Integer start, Integer length) {
        DatatableModel model = new DatatableModel();
        Integer recordsTotal = 0;
        Integer recordsFiltered = 0;
        try {
            model.setDraw(draw);
            List<Hospital> list = new ArrayList<>();
            list = hospitaldao.selectAllHospitalsByPage(start, length);
            recordsTotal = hospitaldao.selectCountHospitals();
            recordsFiltered = recordsTotal;
            model.setData(list);
            model.setRecordsFiltered(recordsFiltered);
            model.setRecordsTotal(recordsTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
}
