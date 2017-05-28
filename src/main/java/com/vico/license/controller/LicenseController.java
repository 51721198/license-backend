package com.vico.license.controller;

import com.vico.license.aop.NeedCheck;
import com.vico.license.enums.ProcessResultEnum;
import com.vico.license.pojo.DatatableModel;
import com.vico.license.pojo.LicenseDetail;
import com.vico.license.pojo.ProcessResult;
import com.vico.license.pojo.RSAKey;
import com.vico.license.service.LicenseService;
import com.vico.license.util.ClassPathResourceURI;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "licenseController")

/**
 *
 * @ClassName: LicenseController
 * @Description: 序列号管理系统控制器
 * @author: Liu.Dun
 * @date: 2016年6月27日 下午8:40:05
 */
public class LicenseController {

    private static final Logger logger = Logger.getLogger(LicenseController.class);

    @Autowired
    private LicenseService licenseService;

    @Autowired
    private Environment env;

    @RequestMapping(value = "createcode", method = RequestMethod.GET)
    public ProcessResult sourceCode(@PathParam("hosnumber") String hosnumber, @PathParam("duedate") String duedate) {
        Map<String, String> codeMap = null;
        ProcessResult processResult = new ProcessResult();
        try {
            if (hosnumber != null && duedate != null) {
                codeMap = licenseService.createSourceCode(duedate, Integer.parseInt(hosnumber));
                processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_SUCCESS);
                processResult.setResultdesc(ProcessResultEnum.CREATE_SUCCESS);
                processResult.setResultobject(codeMap);
            }
        } catch (Exception e) {
            logger.error(ProcessResultEnum.CREATE_ERROR + ProcessResultEnum.getClassPath());
        }
        return processResult;
    }

    @RequestMapping(value = "encryptcode")
    public ProcessResult encryptCode(@PathParam("sourcecode") String sourcecode) {
        ProcessResult processResult = new ProcessResult();
        try {
            RSAKey rsakey = licenseService.getLatestRSAKey();

            String encryptcode = licenseService.createEncryptCode(sourcecode, rsakey.getPublicKey());

            processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_SUCCESS);
            processResult.setResultdesc(ProcessResultEnum.CREATE_SUCCESS);
            processResult.setResultmessage(encryptcode);
            processResult.setResultobject(rsakey.getKeyId());
        } catch (Exception e) {
            logger.error(ProcessResultEnum.CREATE_ERROR + ProcessResultEnum.getClassPath());
        }
        return processResult;
    }

    /**
     * @param:
     * @return: 所有序列号+序列号对应医院名称
     * @Title: showAllCodes
     * @Description: 获取所有序列号，并且获取序列号对应的医院名称
     */
    @NeedCheck("Hello world API")  //有这个注解的方法必须进行AOP拦截
    @RequestMapping(value = "showallcodes")
    public ProcessResult showAllCodes() {
        ProcessResult processResult = new ProcessResult();
        System.out.println("controller方法执行！拦截这个方法!!!!!!!!!!!!!");

        try {
            List<LicenseDetail> list = licenseService.listAllCodes();
            processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_SUCCESS);
            processResult.setResultdesc(ProcessResultEnum.SELECT_SUCCESS);
            processResult.setResultobject(list);
        } catch (Exception e) {
            logger.error(ProcessResultEnum.SELECT_ERROR + ProcessResultEnum.getClassPath());
        }
        return processResult;
    }

    @NeedCheck("")
    @RequestMapping(value = "showallcodesByPage", method = RequestMethod.POST)
    public DatatableModel showAllCodesByPage(HttpServletRequest request) {
        Integer draw = 1;
        Integer length = 0;
        Integer start = 0;
        DatatableModel result = null;
        try {
            if (request != null) {
                draw = (Integer.parseInt(request.getParameter("draw")));
                length = Integer.parseInt(request.getParameter("length"));
                start = Integer.parseInt(request.getParameter("start"));
                result = licenseService.getLicenseByPage(draw, start, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param:
     * @return: ProcessResult
     * @Title: deleteCode
     * @Description: 删除序列号，如果序列号未过期则禁止删除
     */
    @RequestMapping(value = "deletecode")
    public ProcessResult deleteCode(@PathParam("serialNumberId") String serialNumberId) {
        ProcessResult processResult = new ProcessResult();

        try {
            LicenseDetail licensedetail = licenseService.listOneCode(Integer.parseInt(serialNumberId));

            int lastdays = licenseService.countEndDate(licensedetail.getExpiredDate());
            if (lastdays >= 0) {
                processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_FAIL);
                processResult.setResultdesc(ProcessResultEnum.DELETE_FAIL);
                processResult.setResultmessage("序列号并未过期,删除失败！");
            } else {
                int i = licenseService.deleteCode(Integer.parseInt(serialNumberId));
                if (i == 1) {
                    processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_SUCCESS);
                    processResult.setResultdesc(ProcessResultEnum.DELETE_SUCCESS);
                } else {
                    processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_FAIL);
                    processResult.setResultdesc(ProcessResultEnum.DELETE_FAIL);
                }
            }
        } catch (Exception e) {
            logger.error(ProcessResultEnum.SELECT_ERROR + ProcessResultEnum.getClassPath());
        }
        return processResult;
    }

    /**
     * @param serialNumberId
     * @return
     * @throws IOException
     * @param:
     * @return: ProcessResult
     * @Title: useLicense
     * @Description: 使用序列号
     */
    @RequestMapping(value = "uselicense")
    public void useLicense(@PathParam("serialNumberId") String serialNumberId, HttpServletResponse response) {
        ProcessResult processResult = new ProcessResult();
        String path = ClassPathResourceURI.getResourceURI("/").getPath();
        boolean creatsucess = false;
        String nameofzip = "license.zip";
        creatsucess = licenseService.createZIPFile(Integer.parseInt(serialNumberId));
        if (!creatsucess) {
            logger.error("生成ZIP文件失败!");
            return;
        }

        try {
            FileInputStream inputStream1 = new FileInputStream(path + nameofzip);
            response.addHeader("Content-disposition", "attachment;filename=license.zip");
            response.setContentType("zip/plain");
            IOUtils.copy(inputStream1, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            logger.error(e);
        }

        try {
            int i = licenseService.modifyLicenseState(Integer.parseInt(serialNumberId));
            if (i == 1) {
                processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_SUCCESS);
                processResult.setResultdesc(ProcessResultEnum.MODIFY_SUCCESS);
            } else {
                processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_FAIL);
                processResult.setResultdesc(ProcessResultEnum.MODIFY_FAIL);
            }
        } catch (Exception e) {
            logger.error(ProcessResultEnum.MODIFY_ERROR + ProcessResultEnum.getClassPath());
        }
    }

    @RequestMapping(value = "uselicense/{serialNumberId}")
    public void useLicenseAngu(@PathVariable("serialNumberId") String serialNumberId, HttpServletResponse response) {
        ProcessResult processResult = new ProcessResult();
        String path = ClassPathResourceURI.getResourceURI("/").getPath();
        boolean creatsucess = false;
        String nameofzip = "license.zip";
        creatsucess = licenseService.createZIPFile(Integer.parseInt(serialNumberId));
        if (!creatsucess) {
            logger.error("生成ZIP文件失败!");
            return;
        }

        try {
            FileInputStream inputStream1 = new FileInputStream(path + nameofzip);
            response.addHeader("Content-disposition", "attachment;filename=license.zip");
            response.setContentType("zip/plain");
            IOUtils.copy(inputStream1, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            logger.error(e);
        }

        try {
            int i = licenseService.modifyLicenseState(Integer.parseInt(serialNumberId));
            if (i == 1) {
                processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_SUCCESS);
                processResult.setResultdesc(ProcessResultEnum.MODIFY_SUCCESS);
            } else {
                processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_FAIL);
                processResult.setResultdesc(ProcessResultEnum.MODIFY_FAIL);
            }
        } catch (Exception e) {
            logger.error(ProcessResultEnum.MODIFY_ERROR + ProcessResultEnum.getClassPath());
        }
    }

    /**
     * @param licensedetail
     * @return
     * @param:
     * @return: ModelAndView
     * @Title: saveCode
     * @Description: 保存序列号对象, 该对象包含原始序列号, 加密序列号等信息,
     * 后端非空判断采用javax.validation,防止前端js非空判断失效
     */
    @RequestMapping(value = "savecode", method = RequestMethod.POST)
    public ProcessResult saveCode(@RequestBody @Valid LicenseDetail licensedetail, BindingResult bindingResult) {
        ProcessResult processResult = new ProcessResult();
        /**
         * 非空判断,假如传入信息出现了空值,则返回生成序列号页面
         */
        if(bindingResult.hasFieldErrors()){
            logger.error("序列号参数绑定异常:"+bindingResult.getFieldError().getDefaultMessage());
            processResult.setResultdesc("参数绑定失败");
            return processResult;
        }

        try {
            int i = licenseService.saveCode(licensedetail);
           if (i != 1){
               logger.warn("序列号保存失败"+licensedetail.toString());
           }
        } catch (Exception e) {
            logger.warn("序列号保存失败"+licensedetail.toString());
            logger.error(ProcessResultEnum.INSERT_ERROR + ProcessResultEnum.getClassPath());
            processResult.setResultdesc(ProcessResultEnum.CREATE_FAIL);
            return processResult;
        }
        processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_SUCCESS);
        processResult.setResultdesc(ProcessResultEnum.INSERT_SUCCESS);
        return processResult;
    }

    @RequestMapping(value = "createkeypair")
    public ProcessResult createKeyPair() {
        ProcessResult processResult = new ProcessResult();
        int i = 0;
        RSAKey rsaKey = new RSAKey();
        i = licenseService.createKeyPair(rsaKey);
        if (i == 1) {
            processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_SUCCESS);
            processResult.setResultdesc(ProcessResultEnum.INSERT_SUCCESS);
        } else {
            processResult.setResultcode(ProcessResultEnum.RETURN_RESULT_FAIL);
            processResult.setResultdesc(ProcessResultEnum.INSERT_FAIL);
        }
        return null;
    }
}
