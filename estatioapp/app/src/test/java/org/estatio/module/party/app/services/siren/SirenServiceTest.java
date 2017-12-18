/**
 * Copyright 2015-2016 Eurocommercial Properties NV
 * <p>
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.estatio.module.party.app.services.siren;

import java.util.List;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SirenServiceTest {
    public static String COMPANY_QUERY = "APPLE FRANCE";
    public static String COMPANY_NAME = "APPLE";
    public static String COMPANY_CODE = "322120916";

    @Test
    public void company_query_should_return_company_code() {
        // given
        SirenService sirenService = new SirenService();

        // when
        List<SirenResult> results = sirenService.getChamberOfCommerceCodes(COMPANY_QUERY);

        // then
        assertThat(results.size()).isEqualTo(1);
        SirenResult result = results.get(0);
        assertThat(result.getChamberOfCommerceCode()).isEqualTo(COMPANY_CODE);
    }

    @Test
    public void company_code_should_return_company_name() {
        // given
        SirenService sirenService = new SirenService();

        // when
        SirenResult result = sirenService.getCompanyName(COMPANY_CODE);

        // then
        assertThat(result.getCompanyName()).isEqualTo(COMPANY_NAME);
    }

    @Test
    public void full_circle() {
        // given
        SirenService sirenService = new SirenService();

        // when
        List<SirenResult> codeResults = sirenService.getChamberOfCommerceCodes(COMPANY_QUERY);

        // then
        assertThat(codeResults.size()).isEqualTo(1);
        SirenResult codeResult = codeResults.get(0);
        assertThat(codeResult.getChamberOfCommerceCode()).isEqualTo(COMPANY_CODE);

        // when
        SirenResult companyNameResult = sirenService.getCompanyName(codeResult.getChamberOfCommerceCode());

        // then
        assertThat(companyNameResult.getCompanyName()).isEqualTo(COMPANY_NAME);
    }
}
