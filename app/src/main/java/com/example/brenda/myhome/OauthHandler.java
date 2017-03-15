package com.example.brenda.myhome;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.brenda.myhome.helpers.OauthHelper;
import com.example.brenda.myhome.interfaces.CompleteOperations;
import com.example.brenda.myhome.models.AuthorizationCode;
import com.example.brenda.myhome.models.Token;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

/**
 * Created by brenda on 3/15/17.
 */
public class OauthHandler {
        OauthHelper oauthHelper;
        Context context;
        Token token;
        AuthorizationCode authorizationCode;

        public OauthHandler(Context context) {
            this.context = context;
        }

        public void getAuthCode(final ProgressDialog progressDialog, final CompleteOperations completeOperations) {
            oauthHelper = new OauthHelper(context);
            oauthHelper.AuthorizationCode(
                    new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (result != null) {
                                authorizationCode = new AuthorizationCode(result);
                                if (!authorizationCode.getStatus().equalsIgnoreCase("ERROR")) {
                                    completeOperations.onComplete(authorizationCode.getCode());
                                }
                            } else {
                                Toast.makeText(context, "Authorization failed", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }

                        }
                    });
        }

}

