/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.estatio.dom.communicationchannel;

import org.junit.Test;

import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;

import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwner;
import org.incode.module.communications.dom.impl.commchannel.PostalAddress;

import org.incode.module.base.dom.testing.AbstractBeanPropertiesTest;
import org.incode.module.base.dom.testing.PojoTester;
import org.incode.module.country.dom.impl.Country;
import org.incode.module.country.dom.impl.State;

public class PostalAddressTest {

    public static class BeanProperties extends AbstractBeanPropertiesTest {

        @Test
        public void test() {
            newPojoTester()
                    .withFixture(pojos(Country.class))
                    .withFixture(pojos(State.class))
                    .withFixture(pojos(CommunicationChannelOwner.class, CommunicationChannelOwnerForTesting.class))
                    .withFixture(pojos(ApplicationTenancy.class))
                    .exercise(new PostalAddress(), PojoTester.FilterSet.excluding("owner"));
        }
    }
}