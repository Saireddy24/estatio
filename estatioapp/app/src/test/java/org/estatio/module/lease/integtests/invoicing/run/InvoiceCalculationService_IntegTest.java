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
package org.estatio.module.lease.integtests.invoicing.run;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.estatio.module.invoice.dom.InvoiceRunType;
import org.estatio.module.lease.dom.Lease;
import org.estatio.module.lease.dom.LeaseItemType;
import org.estatio.module.lease.dom.invoicing.InvoiceCalculationParameters;
import org.estatio.module.lease.dom.invoicing.InvoiceCalculationService;
import org.estatio.module.lease.dom.invoicing.InvoiceForLeaseRepository;
import org.estatio.module.lease.fixtures.lease.enums.Lease_enum;
import org.estatio.module.lease.fixtures.leaseitems.enums.LeaseItemForRent_enum;
import org.estatio.module.lease.integtests.LeaseModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class InvoiceCalculationService_IntegTest extends LeaseModuleIntegTestAbstract {

    @Inject
    private InvoiceCalculationService invoiceCalculationService;

    @Inject
    private InvoiceForLeaseRepository invoiceRepository;


    private Lease lease;

    @Before
    public void setupData() {
        runFixtureScript(new FixtureScript() {
            @Override
            protected void execute(ExecutionContext ec) {
                ec.executeChild(this, LeaseItemForRent_enum.KalPoison001Nl.builder());
            }
        });

        lease = Lease_enum.KalPoison001Nl.findUsing(serviceRegistry);
    }

    @Test
    public void xxx() throws Exception {
        //Given
        final InvoiceCalculationParameters invoiceCalculationParameters = InvoiceCalculationParameters.builder()
                .lease(lease)
                .leaseItemType(LeaseItemType.RENT)
                .invoiceRunType(InvoiceRunType.NORMAL_RUN)
                .invoiceDate(new LocalDate("2017-01-01"))
                .startDueDate(new LocalDate("2017-01-01"))
                .nextDueDate(new LocalDate("2017-01-02"))
                .build();

        //When
        invoiceCalculationService.calculateAndInvoice(invoiceCalculationParameters);

        //Dan
        assertThat(invoiceRepository.findByLease(lease)).hasSize(1);

    }

}
