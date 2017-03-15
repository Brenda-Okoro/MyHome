package com.example.brenda.myhome.models;

import com.google.gson.JsonObject;

/**
 * Created by brenda on 3/15/17.
 */
public class AuthorizationCode {
    String status;
    String code;
    public AuthorizationCode(JsonObject jsonObject){
        setCode(jsonObject.get("data").getAsJsonObject().get("code").getAsString());
        setStatus(jsonObject.get("status").getAsString());
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
