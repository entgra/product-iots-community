/*
 * Copyright (c) 2018-2023, Entgra (Pvt) Ltd. (http://entgra.io) All Rights Reserved.
 *
 * Entgra (Pvt) Ltd. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.entgra.community.iots.integration.test.common;

import org.apache.commons.net.util.Base64;
import org.json.JSONObject;
import org.wso2.carbon.automation.test.utils.http.client.HttpResponse;

/**
 * This Util class holds method necessary to get an OAuth token.
 */
public class OAuthUtil {


    public static String getScopes(String backendHTTPURL, String backendHTTPSURL) throws Exception {
        return getOAuthTokenPair(backendHTTPURL, backendHTTPSURL).get(Constants.SCOPE).toString();
    }

    public static String getOAuthToken(String backendHTTPURL, String backendHTTPSURL) throws Exception {
        return getOAuthTokenPair(backendHTTPURL, backendHTTPSURL).get(Constants.OAUTH_ACCESS_TOKEN).toString();
    }

    public static JSONObject getOAuthTokenPair(String backendHTTPURL, String backendHTTPSURL) throws Exception {
        String AuthString = "Basic YWRtaW46YWRtaW4=";
        RestClient client = new RestClient(backendHTTPURL, Constants.APPLICATION_JSON, AuthString);
        HttpResponse oAuthData = client.post(Constants.APIApplicationRegistration.API_APP_REGISTRATION_ENDPOINT,
                                             Constants.APIApplicationRegistration.API_APP_REGISTRATION_PAYLOAD);
        JSONObject jsonObj = new JSONObject(oAuthData.getData());
        String clientId = jsonObj.get(Constants.OAUTH_CLIENT_ID).toString();
        String clientSecret = jsonObj.get(Constants.OAUTH_CLIENT_SECRET).toString();
        byte[] bytesEncoded = Base64.encodeBase64((clientId + ":" + clientSecret).getBytes());
        String basicAuthString = "Basic " + new String(bytesEncoded);
        //Initiate a RestClient to get OAuth token
        client = new RestClient(backendHTTPSURL, Constants.APPLICATION_URL_ENCODED, basicAuthString);
        oAuthData = client.post(Constants.APIApplicationRegistration.TOKEN_ENDPOINT,
                                Constants.APIApplicationRegistration.OAUTH_TOKEN_PAYLOAD);
        jsonObj = new JSONObject(oAuthData.getData());
        return jsonObj;
    }

    /**
     * To get the oauth token pair for the given auth string which is encoded in base64 format.
     * @param authString encoded auth string
     * @param backendHTTPURL backend http URL
     * @param backendHTTPSURL backend https URL
     * @return a JSON object which consist of oauth token pair
     * @throws Exception Exception
     */
    public static String getOAuthTokenPair(String authString, String backendHTTPURL, String backendHTTPSURL,
            String username, String password) throws Exception {
        RestClient client = new RestClient(backendHTTPURL, Constants.APPLICATION_JSON, "Basic " + authString);
        HttpResponse oAuthData = client.post(Constants.APIApplicationRegistration.API_APP_REGISTRATION_ENDPOINT,
                Constants.APIApplicationRegistration.API_APP_REGISTRATION_PAYLOAD);
        JSONObject jsonObj = new JSONObject(oAuthData.getData());
        String clientId = jsonObj.get(Constants.OAUTH_CLIENT_ID).toString();
        String clientSecret = jsonObj.get(Constants.OAUTH_CLIENT_SECRET).toString();
        byte[] bytesEncoded = Base64.encodeBase64((clientId + ":" + clientSecret).getBytes());
        String basicAuthString = "Basic " + new String(bytesEncoded);
        //Initiate a RestClient to get OAuth token
        client = new RestClient(backendHTTPSURL, Constants.APPLICATION_URL_ENCODED, basicAuthString);
        oAuthData = client.post(Constants.APIApplicationRegistration.TOKEN_ENDPOINT,
                "username=" + username + "&password=" + password + Constants.APIApplicationRegistration.MULTI_TENANT_OAUTH_TOKEN_PAYLOAD);
        jsonObj = new JSONObject(oAuthData.getData());
        return jsonObj.get(Constants.OAUTH_ACCESS_TOKEN).toString();

    }
}
