package com.forlost.sunflower.model.httpModel;

import com.forlost.sunflower.model.BaseModel;
import com.google.gson.JsonElement;

public class DtdBaseResponseModel extends BaseModel {
    private int code;
    private String msg;
    private Long time;
    private JsonElement data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public JsonElement getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "DtdApiModel{\n" +//
                "\tcode=" + code + "\n" +//
                "\tmsg='" + msg + "\'\n" +//
                "\ttime='" + time + "\'\n" +//
                "\tdata=" + data + "\n" +//
                '}';
    }
}
