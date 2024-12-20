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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * This class is used to load the necessary payloads from payload files for integration tests.
 */
public class PayloadGenerator {

    private static final String PAYLOAD_LOCATION = "payloads/";
    private static JsonParser parser = new JsonParser();

    public static JsonObject getJsonPayload(String fileName, String method)
            throws FileNotFoundException {
        URL url = PayloadGenerator.class.getClassLoader().getResource(PAYLOAD_LOCATION + fileName);
        JsonObject jsonObject = parser.parse(new FileReader(url.getPath())).getAsJsonObject();
        return jsonObject.get(method).getAsJsonObject();
    }

    public static String getJsonPayloadToString(String fileName) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(PAYLOAD_LOCATION + fileName);
        FileInputStream fisTargetFile = new FileInputStream(new File(url.getPath()));
        String returnString = IOUtils.toString(fisTargetFile, Constants.UTF8);
        return returnString;
    }

    /**
     * Create a Json Array from a specific method in the file
     * @param fileName Name of the file
     * @param method Method name
     * @return Json Arry created from the specific method in the file
     * @throws FileNotFoundException File Not found exception
     */
    public static JsonArray getJsonArray(String fileName, String method)
            throws FileNotFoundException {
        URL url = PayloadGenerator.class.getClassLoader().getResource(PAYLOAD_LOCATION + fileName);
        JsonObject jsonObject = parser.parse(new FileReader(url.getPath())).getAsJsonObject();
        return jsonObject.get(method).getAsJsonArray();
    }
}
