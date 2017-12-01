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
package org.estatio.module.lease.fixtures.invoicing.personas;

import org.joda.time.LocalDate;

import org.apache.isis.core.commons.ensure.Ensure;

import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;

import org.estatio.module.base.fixtures.security.apptenancy.enums.ApplicationTenancy_enum;
import org.estatio.module.currency.fixtures.enums.Currency_enum;
import org.estatio.module.invoice.dom.PaymentMethod;
import org.estatio.module.lease.dom.Lease;
import org.estatio.module.lease.dom.LeaseItemType;
import org.estatio.module.lease.dom.invoicing.InvoiceForLease;
import org.estatio.module.lease.fixtures.invoice.InvoiceAbstract;
import org.estatio.module.lease.fixtures.lease.enums.Lease_enum;
import org.estatio.module.lease.fixtures.leaseitems.discount.personas.LeaseItemAndLeaseTermForDiscountForOxfMiracl005Gb;
import org.estatio.module.party.fixtures.organisation.enums.OrganisationAndComms_enum;

import static org.hamcrest.CoreMatchers.is;
import static org.incode.module.base.integtests.VT.ldix;

public class InvoiceForLeaseItemTypeOfDiscountOneQuarterForOxfMiracle005 extends InvoiceAbstract {

    private static final Lease_enum lease_d = Lease_enum.OxfMiracl005Gb;
    private static final OrganisationAndComms_enum seller_d = OrganisationAndComms_enum.HelloWorldGb;
    private static final OrganisationAndComms_enum buyer_d = OrganisationAndComms_enum.MiracleGb;
    private static final ApplicationTenancy_enum applicationTenancy_d = ApplicationTenancy_enum.GbOxf;

    public static final String PARTY_REF_SELLER = seller_d.getRef();
    public static final String PARTY_REF_BUYER = buyer_d.getRef();
    public static final String LEASE_REF = lease_d.getRef();

    public static final String AT_PATH = applicationTenancy_d.getPath();

    // simply within the lease's start/end date
    public static LocalDate startDateFor(final Lease lease) {
        Ensure.ensureThatArg(lease.getReference(), is(lease_d.getRef()));
        return lease.getStartDate().plusYears(1);
    }

    public InvoiceForLeaseItemTypeOfDiscountOneQuarterForOxfMiracle005() {
        this(null, null);
    }

    public InvoiceForLeaseItemTypeOfDiscountOneQuarterForOxfMiracle005(final String friendlyName, final String localName) {
        super(friendlyName, localName);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, OrganisationAndComms_enum.HelloWorldGb.toBuilderScript());
        executionContext.executeChild(this, new LeaseItemAndLeaseTermForDiscountForOxfMiracl005Gb());

        // exec
        final ApplicationTenancy applicationTenancy = applicationTenancy_d.findUsing(serviceRegistry);
        final Lease lease = lease_d.findUsing(serviceRegistry);
        final LocalDate invoiceStartDate = startDateFor(lease);

        final InvoiceForLease invoice = createInvoiceAndNumerator(
                applicationTenancy,
                lease,
                PARTY_REF_SELLER,
                PARTY_REF_BUYER,
                PaymentMethod.DIRECT_DEBIT,
                Currency_enum.EUR.getReference(),
                invoiceStartDate,
                executionContext);

        createInvoiceItemsForTermsOfFirstLeaseItemOfType(
                invoice, LeaseItemType.RENT_DISCOUNT_FIXED,
                invoiceStartDate, ldix(invoiceStartDate, invoiceStartDate.plusMonths(3)),
                executionContext);

    }
}
