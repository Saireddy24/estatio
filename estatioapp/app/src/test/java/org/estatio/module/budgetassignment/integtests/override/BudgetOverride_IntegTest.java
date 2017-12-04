package org.estatio.module.budgetassignment.integtests.override;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.estatio.module.budget.dom.budgetcalculation.BudgetCalculationType;
import org.estatio.module.budgetassignment.dom.override.BudgetOverrideForFixed;
import org.estatio.module.budgetassignment.dom.override.BudgetOverrideRepository;
import org.estatio.module.budgetassignment.dom.override.BudgetOverrideValueRepository;
import org.estatio.module.budgetassignment.integtests.BudgetAssignmentModuleIntegTestAbstract;
import org.estatio.module.charge.dom.Charge;
import org.estatio.module.charge.dom.ChargeRepository;
import org.estatio.module.charge.fixtures.charges.enums.Charge_enum;
import org.estatio.module.lease.dom.Lease;
import org.estatio.module.lease.dom.LeaseRepository;
import org.estatio.module.lease.fixtures.lease.enums.Lease_enum;

import static org.assertj.core.api.Assertions.assertThat;

public class BudgetOverride_IntegTest extends BudgetAssignmentModuleIntegTestAbstract {

    @Inject
    BudgetOverrideRepository budgetOverrideRepository;

    @Inject
    BudgetOverrideValueRepository budgetOverrideValueRepository;

    @Inject
    LeaseRepository leaseRepository;

    @Inject
    ChargeRepository chargeRepository;

    @Before
    public void setupData() {
        runFixtureScript(new FixtureScript() {
            @Override
            protected void execute(final ExecutionContext executionContext) {
                executionContext.executeChild(this, Lease_enum.OxfTopModel001Gb.builder());
            }
        });
    }

    BudgetOverrideForFixed budgetOverrideForFixed;

    @Test
    public void findOrCreateValuesTest(){

        // given
        Lease leaseTopModel = Lease_enum.OxfTopModel001Gb.findUsing(serviceRegistry);
        Charge invoiceCharge = Charge_enum.GbServiceCharge.findUsing(serviceRegistry);
        BigDecimal overrideValue = new BigDecimal("1234.56");
        String reason = "Some reason";
        budgetOverrideForFixed = wrap(budgetOverrideRepository).newBudgetOverrideForFixed(overrideValue, leaseTopModel, null, null, invoiceCharge, null, null, reason);
        assertThat(budgetOverrideValueRepository.allBudgetOverrideValues().size()).isEqualTo(0);

        // when
        budgetOverrideForFixed.findOrCreateValues(new LocalDate(2015, 01,01));

        // then
        assertThat(budgetOverrideForFixed.getValues().size()).isEqualTo(2);
        assertThat(budgetOverrideForFixed.getValues().first().getType()).isEqualTo(BudgetCalculationType.BUDGETED);
        assertThat(budgetOverrideForFixed.getValues().last().getType()).isEqualTo(BudgetCalculationType.ACTUAL);

        // and when again
        budgetOverrideForFixed.findOrCreateValues(new LocalDate(2015, 01,01));

        // then still
        assertThat(budgetOverrideForFixed.getValues().size()).isEqualTo(2);

    }



}
