package com.vico.license.dao;

import com.vico.license.pojo.Hospital;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: HospitalDao
 * @Description: 医院信息Dao层
 * @author: Liu.Dun
 * @date: 2016年6月27日 下午8:45:17
 */
@Component
public interface HospitalDao {
    int deleteByPrimaryKey(Integer hospitalNumber);

    int insertHospital(Hospital hospital);

    Hospital selectByPrimaryKey(Integer hospitalNumber);

    int updateByPrimaryKey(Hospital hospital);

    List<Hospital> showAll();

    List<Hospital> selectAllHospitalsByPage(@Param("start") Integer start, @Param("length") Integer length);

    Integer selectCountHospitals();
}
