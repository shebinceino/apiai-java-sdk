package ai.api.test.compatibility;

/***********************************************************************************************************************
 *
 * API.AI Java SDK - client-side libraries for API.AI
 * =================================================
 *
 * Copyright (C) 2015 by Speaktoit, Inc. (https://www.speaktoit.com)
 * https://www.api.ai
 *
 * *********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************/

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIContext;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

import static org.junit.Assert.assertEquals;

/**
 * Test for backward compatibility to the first protocol version ("20150415")
 */
public class V20150415ProtocolTest {
    private static final String PROTOCOL_VERSION = "20150415";

    @Test
    public void legacyContextsWithoutParametersTest() throws AIServiceException {
        final AIConfiguration config = new AIConfiguration(
                "3485a96fb27744db83e78b8c4bc9e7b7",
                AIConfiguration.SupportedLanguages.English);

        config.setProtocolVersion(PROTOCOL_VERSION);

        final AIDataService aiDataService = new AIDataService(config);

        final AIContext weatherContext = new AIContext("weather");
        weatherContext.setParameters(Collections.singletonMap("location", "London"));

        final List<AIContext> contexts = Collections.singletonList(weatherContext);

        final AIRequest aiRequest = new AIRequest();
        aiRequest.setQuery("and for tomorrow");
        aiRequest.setContexts(contexts);

        final AIResponse aiResponse = aiDataService.request(aiRequest);

        // Old protocol doesn't support parameters, so response will not contains city name
        assertEquals("Weather in for tomorrow", aiResponse.getResult().getFulfillment().getSpeech());

    }
}
