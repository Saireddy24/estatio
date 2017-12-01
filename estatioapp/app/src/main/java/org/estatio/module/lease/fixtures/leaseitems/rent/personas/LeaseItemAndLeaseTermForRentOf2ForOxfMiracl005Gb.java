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
package org.estatio.module.lease.fixtures.leaseitems.rent.personas;

import org.estatio.module.base.fixtures.security.apptenancy.enums.ApplicationTenancy_enum;
import org.estatio.module.index.fixtures.enums.Index_enum;
import org.estatio.module.lease.dom.Lease;
import org.estatio.module.lease.fixtures.LeaseItemAndTermsAbstract;
import org.estatio.module.lease.fixtures.lease.enums.Lease_enum;

import static org.incode.module.base.integtests.VT.bd;
import static org.incode.module.base.integtests.VT.ld;

public class LeaseItemAndLeaseTermForRentOf2ForOxfMiracl005Gb extends LeaseItemAndTermsAbstract {


    @Override
    protected void execute(final ExecutionContext fixtureResults) {
        createLeaseTermsForOxfMiracl005(fixtureResults);
    }

    private void createLeaseTermsForOxfMiracl005(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, Lease_enum.OxfMiracl005Gb.toBuilderScript());

        // exec

        final Lease lease = Lease_enum.OxfMiracl005Gb.findUsing(serviceRegistry);

        createLeaseTermForIndexableRent(
                Lease_enum.OxfMiracl005Gb.getRef(),
                ApplicationTenancy_enum.GbOxfDefault.getPath(),
                lease.getStartDate(), null,
                bd(150000),
                null, null, null,
                Index_enum.IStatFoi.getReference(),
                executionContext);

        createLeaseTermForIndexableRent(
                Lease_enum.OxfMiracl005Gb.getRef(),
                ApplicationTenancy_enum.GbOxfDefault.getPath(),
                ld(2015, 1, 1), null,
                null,
                ld(2013, 11, 1), ld(2014, 12, 1), null,
                Index_enum.IStatFoi.getReference(),
                executionContext);

    }

}
