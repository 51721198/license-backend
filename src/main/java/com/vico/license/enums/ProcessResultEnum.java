package com.vico.license.enums;

public enum ProcessResultEnum {

    SUCCESS(0,"操作成功"),
    INS_FAIL(101,"删除失败"),
    UPD_FAIL(202,"修改失败"),
    DEL_FAIL(303,"删除失败");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ProcessResultEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    /**
     * 操作成功，返回结果1
     */
    public static final Integer RETURN_RESULT_SUCCESS = 1;

    /**
     * 操作失败，返回结果0
     */
    public static final Integer RETURN_RESULT_FAIL = 0;

    /**
     * 操作异常，返回结果-1
     */
    public static final Integer RETURN_RESULT_ERROR = -1;

    /**
     * 添加操作的三种操作结果
     */
    public static final String INSERT_SUCCESS = "添加成功！";
    public static final String INSERT_FAIL = "添加失败！";
    public static final String INSERT_ERROR = "添加异常！";

    /**
     * 删除操作的三种操作结果
     */
    public static final String DELETE_SUCCESS = "删除成功！";
    public static final String DELETE_FAIL = "删除失败！";
    public static final String DELETE_ERROR = "删除异常！";

    /**
     * 修改错做的三种操作结果
     */
    public static final String MODIFY_SUCCESS = "修改成功！";
    public static final String MODIFY_FAIL = "修改失败！";
    public static final String MODIFY_ERROR = "修改异常！";

    /**
     * 查询操作的三种操作结果
     */
    public static final String SELECT_SUCCESS = "查询成功！";
    public static final String SELECT_FAIL = "查询失败！";
    public static final String SELECT_ERROR = "查询异常！";

    /**
     * 序列号生成加密操作的三种结果
     */
    public static final String CREATE_SUCCESS = "生成成功！";
    public static final String CREATE_FAIL = "生成失败！";
    public static final String CREATE_ERROR = "生成异常！";

    /**
     * 获取异常的类名+方法
     *
     * @return 类名+类方法
     */
    public static final String getClassPath() {
        String currentMethod = "";
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        if (stack.length > 1) {
            for (int i = 1; i < 2; i++) {
                StackTraceElement ste = stack[i];
                currentMethod = currentMethod + ste.getClassName() + "." + ste.getMethodName() + "(...);";
            }
        } else {
        }
        return currentMethod;
    }
}


