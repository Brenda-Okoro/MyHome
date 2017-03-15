package com.example.brenda.myhome.models;

import com.google.gson.JsonObject;

/**
 * Created by brenda on 3/15/17.
 */
public class Token {
        private String token;
        private String refresh_token;
        private String token_type;
        private String scope;
        private String expiry;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private String status;

        public Token(JsonObject jsonObject) {
            setExpiry(jsonObject.get("data").getAsJsonObject().get("expires_in").getAsString());
            setRefresh_token(jsonObject.get("data").getAsJsonObject().get("refresh_token").getAsString());
            setScope(jsonObject.get("data").getAsJsonObject().get("scope").isJsonNull() ? "" : jsonObject.get("data").getAsJsonObject().get("scope").getAsString());
            setToken(jsonObject.get("data").getAsJsonObject().get("access_token").isJsonNull() ?"": jsonObject.get("data").getAsJsonObject().get("access_token").getAsString());
            setToken_type(jsonObject.get("data").getAsJsonObject().get("token_type").getAsString());
            setStatus(jsonObject.get("status").getAsString());
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }
    }
