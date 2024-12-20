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

package io.entgra.community.iots.integration.test.jmeter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;
import org.wso2.carbon.automation.engine.exceptions.AutomationFrameworkException;
import org.wso2.carbon.automation.extensions.jmeter.JMeterTest;
import org.wso2.carbon.automation.extensions.jmeter.JMeterTestManager;

import java.io.File;
import java.net.URL;

/**
 * JMeter Test cases for Device Management APIs.
 */
public class AndroidDeviceManagementAPIJmeterTestCase {
    private static Log log = LogFactory.getLog(AndroidDeviceManagementAPIJmeterTestCase.class);

    @Test(description = "Testing the android device management functionalities")
    public void permutationTest() throws AutomationFrameworkException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(
                "jmeter-scripts" + File.separator + "AndroidDeviceManagementAPI.jmx");
        JMeterTest script = new JMeterTest(new File(url.getPath()));
        JMeterTestManager manager = new JMeterTestManager();
        log.info("Running permutation test using jmeter scripts");
        manager.runTest(script);
    }
}
