package com.shuangyang.flutter_yunmou;

public class YunmouToFlutterEntity {
    private String code;
    private String Data;
    private long callBackFuncId;

    public YunmouToFlutterEntity(String code, String data) {
        this.code = code;
        Data = data;
    }

    public long getCallBackFuncId() {
        return callBackFuncId;
    }

    public void setCallBackFuncId(long callBackFuncId) {
        this.callBackFuncId = callBackFuncId;
    }
}
