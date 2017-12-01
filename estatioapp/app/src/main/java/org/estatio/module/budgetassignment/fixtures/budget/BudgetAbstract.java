/*
 * Copyright 2015 Yodo Int. Projects and Consultancy
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.estatio.module.budgetassignment.fixtures.budget;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.estatio.module.asset.dom.Property;
import org.estatio.module.asset.dom.PropertyRepository;
import org.estatio.module.budget.dom.budget.Budget;
import org.estatio.module.budget.dom.budget.BudgetRepository;
import org.estatio.module.budget.dom.budgetcalculation.BudgetCalculationType;
import org.estatio.module.budget.dom.budgetitem.BudgetItem;
import org.estatio.module.budget.dom.budgetitem.BudgetItemRepository;
import org.estatio.module.budget.dom.budgetitem.BudgetItemValue;
import org.estatio.module.budget.dom.budgetitem.BudgetItemValueRepository;
import org.estatio.module.budget.dom.partioning.PartitioningRepository;
import org.estatio.module.charge.dom.Charge;
import org.estatio.module.charge.dom.ChargeRepository;

public abstract class BudgetAbstract extends FixtureScript {


    protected Budget createBudget(
            final Property property,
            final LocalDate startDate,
            final LocalDate endDate,
            final ExecutionContext fixtureResults){
        Budget budget = budgetRepository.newBudget(property, startDate, endDate);
        return fixtureResults.addResult(this, budget);
    }

    protected BudgetItem createBudgetItem(
            final Budget budget,
            final Charge charge
    ){
        return budgetItemRepository.newBudgetItem(budget, charge);
    }

    protected BudgetItemValue createBudgetItemValue(
            final BudgetItem item,
            final BigDecimal value,
            final LocalDate date,
            final BudgetCalculationType type){
        return budgetItemValueRepository.newBudgetItemValue(item, value, date, type);
    }

    @Inject
    protected BudgetRepository budgetRepository;

    @Inject
    protected BudgetItemRepository budgetItemRepository;

    @Inject
    protected PropertyRepository propertyRepository;

    @Inject
    protected ChargeRepository chargeRepository;

    @Inject
    protected BudgetItemValueRepository budgetItemValueRepository;

    @Inject
    protected PartitioningRepository partitioningRepository;

}
