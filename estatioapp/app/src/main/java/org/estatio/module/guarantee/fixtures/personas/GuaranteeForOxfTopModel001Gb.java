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
package org.estatio.module.guarantee.fixtures.personas;

import javax.inject.Inject;

import org.estatio.module.agreement.dom.role.AgreementRoleTypeRepository;
import org.estatio.module.guarantee.dom.Guarantee;
import org.estatio.module.guarantee.dom.GuaranteeAgreementRoleTypeEnum;
import org.estatio.module.guarantee.dom.GuaranteeType;
import org.estatio.module.guarantee.fixtures.GuaranteeAbstract;
import org.estatio.module.lease.dom.Lease;
import org.estatio.module.lease.dom.LeaseRepository;
import org.estatio.module.lease.fixtures.lease.enums.Lease_enum;
import org.estatio.module.party.dom.PartyRepository;
import org.estatio.module.party.fixtures.organisation.enums.OrganisationAndComms_enum;

import static org.incode.module.base.integtests.VT.bd;
import static org.incode.module.base.integtests.VT.ld;

public class GuaranteeForOxfTopModel001Gb extends GuaranteeAbstract {

    public static final String LEASE_REFERENCE = Lease_enum.OxfTopModel001Gb.getRef();
    public static final String REFERENCE = LEASE_REFERENCE + "-D";
    public static final String PARTY_REF_BANK = OrganisationAndComms_enum.DagoBankGb.getRef();

    @Override
    protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, Lease_enum.OxfTopModel001Gb.builder());
        executionContext.executeChild(this, OrganisationAndComms_enum.DagoBankGb.builder());

        createGuaranteeForOxfTopModel001(executionContext);
    }

    private void createGuaranteeForOxfTopModel001(final ExecutionContext executionContext) {

        final Lease lease = leaseRepository.findLeaseByReference(LEASE_REFERENCE);

        final Guarantee guarantee = newGuarantee(
                lease,
                REFERENCE,
                REFERENCE,
                GuaranteeType.BANK_GUARANTEE,
                ld(2014, 1, 1),
                ld(2015, 1, 1),
                "Description",
                bd(50000),
                executionContext);
        guarantee.createRole(
                agreementRoleTypeRepository.find(GuaranteeAgreementRoleTypeEnum.BANK),
                partyRepository.findPartyByReference(PARTY_REF_BANK),
                null,
                null);
    }

    @Inject
    AgreementRoleTypeRepository agreementRoleTypeRepository;

    @Inject
    PartyRepository partyRepository;

    @Inject
    LeaseRepository leaseRepository;
}
