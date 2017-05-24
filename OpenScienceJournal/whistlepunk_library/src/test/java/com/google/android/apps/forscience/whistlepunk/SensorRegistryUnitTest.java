/*
 *  Copyright 2016 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.google.android.apps.forscience.whistlepunk;

import com.google.android.apps.forscience.whistlepunk.api.scalarinput.RecordingUsageTracker;
import com.google.android.apps.forscience.whistlepunk.api.scalarinput.ScalarInputDiscoverer;
import com.google.android.apps.forscience.whistlepunk.api.scalarinput.ScalarInputSpec;
import com.google.android.apps.forscience.whistlepunk.devicemanager.ConnectableSensor;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SensorRegistryUnitTest {
    @Test
    public void addApiSensorsOnlyOnce() {
        SensorRegistry registry = new SensorRegistry();

        List<ConnectableSensor> sensors = Lists.newArrayList(
                new ConnectableSensor.Connector().connected(
                        new ScalarInputSpec("name", "serviceId", "address", null, null, "devId"),
                        "id"));

        assertEquals(Lists.newArrayList("id"),
                registry.updateExternalSensors(sensors, getProviders()));
        assertEquals(Lists.newArrayList(), registry.updateExternalSensors(sensors, getProviders()));
    }

    public Map<String,ExternalSensorProvider> getProviders() {
        Map<String, ExternalSensorProvider> providers = new HashMap<>();

        providers.put(ScalarInputSpec.TYPE,
                new ScalarInputDiscoverer(null, null, MoreExecutors.directExecutor(),
                        new MockScheduler(), 100, new RecordingUsageTracker()).getProvider());
        return providers;
    }
}