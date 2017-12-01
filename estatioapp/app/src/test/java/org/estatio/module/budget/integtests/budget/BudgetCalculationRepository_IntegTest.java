package org.estatio.module.budget.integtests.budget;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.estatio.module.asset.dom.Property;
import org.estatio.module.asset.dom.PropertyRepository;
import org.estatio.module.asset.fixtures.property.enums.Property_enum;
import org.estatio.module.budget.dom.budget.Budget;
import org.estatio.module.budget.dom.budget.BudgetRepository;
import org.estatio.module.budget.dom.budgetcalculation.BudgetCalculation;
import org.estatio.module.budget.dom.budgetcalculation.BudgetCalculationRepository;
import org.estatio.module.budget.dom.budgetcalculation.BudgetCalculationService;
import org.estatio.module.budget.dom.budgetcalculation.BudgetCalculationType;
import org.estatio.module.budget.dom.keyitem.KeyItem;
import org.estatio.module.budget.dom.partioning.PartitionItem;
import org.estatio.module.budget.dom.partioning.PartitionItemRepository;
import org.estatio.module.budget.fixtures.budgets.enums.Budget_enum;
import org.estatio.module.budget.fixtures.partitioning.personas.PartitioningAndItemsForOxfBudget2015;
import org.estatio.module.budget.integtests.BudgetModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class BudgetCalculationRepository_IntegTest extends BudgetModuleIntegTestAbstract {

    @Inject
    BudgetCalculationRepository budgetCalculationRepository;

    @Inject
    PropertyRepository propertyRepository;

    @Inject
    BudgetRepository budgetRepository;

    @Inject
    PartitionItemRepository partitionItemRepository;

    @Inject
    BudgetCalculationService budgetCalculationService;

    @Before
    public void setupData() {
        runFixtureScript(new FixtureScript() {
            @Override
            protected void execute(final ExecutionContext executionContext) {
                executionContext.executeChild(this, new PartitioningAndItemsForOxfBudget2015());
            }
        });
    }

    public static class FindUnique extends BudgetCalculationRepository_IntegTest {

        @Test
        public void happyCase() throws Exception {
            // given
            PartitionItem partitionItem = partitionItemRepository.allPartitionItems().get(0);
            KeyItem keyItem = partitionItem.getKeyTable().getItems().first();
            BudgetCalculation newBudgetCalculation = budgetCalculationRepository.createBudgetCalculation(partitionItem, keyItem, BigDecimal.ZERO, BudgetCalculationType.BUDGETED);

            // when
            BudgetCalculation budgetCalculation = budgetCalculationRepository.findUnique(partitionItem, keyItem, BudgetCalculationType.BUDGETED);

            // then
            assertThat(budgetCalculation).isEqualTo(newBudgetCalculation);

        }

    }

    public static class FindByBudgetAndUnitAndInvoiceChargeAndType extends BudgetCalculationRepository_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            Property property = Property_enum.OxfGb.findUsing(serviceRegistry);
            Budget budget = budgetRepository.findByPropertyAndStartDate(property,
                    Budget_enum.OxfBudget2015.getStartDate());
            PartitionItem partitionItem = budget.getItems().first().getPartitionItems().get(0);
            budgetCalculationService.calculatePersistedCalculations(budget);

            // when
            List<BudgetCalculation> budgetCalculations = budgetCalculationRepository.findByBudgetAndUnitAndInvoiceChargeAndType(budget, property.getUnits().first(), partitionItem.getCharge(), BudgetCalculationType.BUDGETED);

            // then
            assertThat(budgetCalculations.size()).isEqualTo(3);

        }
    }

    public static class FindByBudgetAndUnitAndInvoiceChargeAndInomingChargeAndType extends
            BudgetCalculationRepository_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            Property property = Property_enum.OxfGb.findUsing(serviceRegistry);
            Budget budget = budgetRepository.findByPropertyAndStartDate(property,
                    Budget_enum.OxfBudget2015.getStartDate());
            PartitionItem partitionItem = budget.getItems().first().getPartitionItems().get(0);
            budgetCalculationService.calculatePersistedCalculations(budget);

            // when
            List<BudgetCalculation> budgetCalculations = budgetCalculationRepository.findByBudgetAndUnitAndInvoiceChargeAndIncomingChargeAndType(budget, property.getUnits().first(), partitionItem.getCharge(), partitionItem.getBudgetItem().getCharge(), BudgetCalculationType.BUDGETED);

            // then
            assertThat(budgetCalculations.size()).isEqualTo(1);

        }
    }

}
