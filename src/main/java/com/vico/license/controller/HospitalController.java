package com.vico.license.controller;

import com.vico.license.enums.ProcessResultEnum;
import com.vico.license.pojo.DatatableModel;
import com.vico.license.pojo.Hospital;
import com.vico.license.pojo.LicenseDetail;
import com.vico.license.pojo.ProcessResult;
import com.vico.license.service.HospitalService;
import com.vico.license.service.LicenseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author Liu.Dun
 * @医院信息系统控制器
 */
@RestController
@RequestMapping(value = "hospitalController")
public class HospitalController {

    private static final Logger logger = Logger.getLogger(HospitalController.class);
    @Autowired
    private HospitalService hospitalservice;

    @Autowired
    private LicenseService licenseservice;

    @Autowired
    private ProcessResult processResult;

    @RequestMapping(value = "showhospital")
    public ProcessResult showAllHospital() {
        logger.info("show all hospitals");
        try {
            List<Hospital> list = hospitalservice.showAllHospitals();
            processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_SUCCESS);
            processResult.setResultdesc(ProcessResultEnum.SELECT_SUCCESS);
            processResult.setResultobject(list);
        } catch (Exception e) {
            logger.error(ProcessResultEnum.SELECT_ERROR + ProcessResultEnum.getClassPath());
        }
        return processResult;
    }

    @RequestMapping(value = "showHospitalByPage", method = RequestMethod.POST)
    public DatatableModel showAllHospitalByPage(HttpServletRequest request) {
        Integer draw = 1;
        Integer length = 0;
        Integer start = 0;
        DatatableModel result = null;
        logger.info("show all hospitals");
        try {
            if (request != null) {
                draw = (Integer.parseInt(request.getParameter("draw")));
                length = Integer.parseInt(request.getParameter("length"));
                start = Integer.parseInt(request.getParameter("start"));
                result = hospitalservice.getHospitalByPage(draw, start, length);
            }
        } catch (Exception e) {
            logger.error(ProcessResultEnum.SELECT_ERROR + ProcessResultEnum.getClassPath());
        }
        return result;
    }

    /**
     * @return
     * @由医院编号获取医院信息
     */
    @RequestMapping(value = "showone")
    public ProcessResult selectOneHospital(@PathParam("hospitalNumber") String hospitalNumber) {
        try {
            if (hospitalNumber != "") {
                Hospital hospital = hospitalservice.showOneHospital(Integer.parseInt(hospitalNumber));
                processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_SUCCESS);
                processResult.setResultdesc(ProcessResultEnum.SELECT_SUCCESS);
                processResult.setResultobject(hospital);
            } else {
                processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_ERROR);
                processResult.setResultdesc(ProcessResultEnum.SELECT_ERROR);
            }
        } catch (Exception e) {
            logger.error(ProcessResultEnum.SELECT_ERROR + ProcessResultEnum.getClassPath());
        }
        return processResult;
    }

    /**
     * @return: void
     * @Title: deleteHospital
     * @Description: 删除医院，有关联序列号信息的医院禁止删除
     */
    @RequestMapping(value = "deletehospital")
    public ProcessResult deleteHospital(@PathParam("hospitalNumber") String hospitalNumber) {
        try {
            List<LicenseDetail> list = licenseservice.selectByhospitalNumber(Integer.parseInt(hospitalNumber));
            if (list.isEmpty()) {
                int i = hospitalservice.deleteHospital(Integer.parseInt(hospitalNumber));
                //返回值为1说明删除操作执行成功
                if (i == 1) {
                    processResult.setResult(ProcessResultEnum.SUCCESS);
                } else {
                    processResult.setResult(ProcessResultEnum.DEL_FAIL);
                }
            } else {
                processResult.setResult(ProcessResultEnum.DEL_FAIL);
                processResult.setResultmessage("有关联序列号信息,删除失败");
            }
        } catch (Exception e) {
            logger.error(ProcessResultEnum.DEL_FAIL + ProcessResultEnum.getClassPath());
        }
        return processResult;
    }

    /**
     * @param hospital
     * @return
     * @param:
     * @return: String
     * @Title: addHospital
     * @Description: 根据hospitalNumber的值是否存在判断是添加还是修改
     */
    @RequestMapping(value = "addhospital")
    public ModelAndView addHospital(@Valid Hospital hospital, Errors error) {
        /**
         * 后台非空判断,假如输入的医院编号和名称为空,则返回原页面
         */
        if (error.hasErrors()) {
            ModelAndView mv = new ModelAndView("redirect:/bounceController/toaddhospital");
            return mv;
        }
        try {
            if (hospital.getHospitalNumber() == null) {
                int i = hospitalservice.addHospital(hospital);
                if (i == 1) {
                    processResult.setResult(ProcessResultEnum.SUCCESS);
                } else {
                    processResult.setResult(ProcessResultEnum.INS_FAIL);
                }
            } else {
                int i = hospitalservice.updateHospital(hospital);
                if (i == 1) {
                    processResult.setResult(ProcessResultEnum.SUCCESS);
                } else {
                    processResult.setResult(ProcessResultEnum.UPD_FAIL);
                }
            }
        } catch (Exception e) {
            logger.error(ProcessResultEnum.MODIFY_ERROR + ProcessResultEnum.getClassPath());
        }
        ModelAndView mv = new ModelAndView("redirect:/bounceController/toshowallhospital");
        return mv;
    }

}
