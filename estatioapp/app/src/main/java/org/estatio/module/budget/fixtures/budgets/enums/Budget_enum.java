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

package org.estatio.module.budget.fixtures.budgets.enums;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.PersonaWithBuilderScript;
import org.apache.isis.applib.fixturescripts.PersonaWithFinder;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import org.estatio.module.asset.dom.Property;
import org.estatio.module.asset.fixtures.property.enums.PropertyAndUnitsAndOwnerAndManager_enum;
import org.estatio.module.budget.dom.budget.Budget;
import org.estatio.module.budget.dom.budget.BudgetRepository;
import org.estatio.module.budget.fixtures.budgets.builders.BudgetBuilder;
import org.estatio.module.charge.fixtures.charges.enums.Charge_enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import static org.incode.module.base.integtests.VT.bd;
import static org.incode.module.base.integtests.VT.ld;

@AllArgsConstructor
@Getter
@Accessors(chain = true)
public enum Budget_enum implements PersonaWithBuilderScript<Budget, BudgetBuilder>, PersonaWithFinder<Budget> {

    OxfBudget2015(
            PropertyAndUnitsAndOwnerAndManager_enum.OxfGb, ld(2015, 1, 1),
            Charge_enum.GbIncomingCharge1, bd("30000.55"),
            Charge_enum.GbIncomingCharge2, bd("40000.35")
    ),
    OxfBudget2016(
            PropertyAndUnitsAndOwnerAndManager_enum.OxfGb, ld(2016, 1, 1),
            Charge_enum.GbIncomingCharge1, bd("30500.99"),
            Charge_enum.GbIncomingCharge2, bd("40600.01")
    ),
    ;

    private final PropertyAndUnitsAndOwnerAndManager_enum property_d;
    private final LocalDate startDate;
    private final Charge_enum charge1_d;
    private final BigDecimal value1;
    private final Charge_enum charge2_d;
    private final BigDecimal value2;

    @Override
    public BudgetBuilder toBuilderScript() {
        return new BudgetBuilder()
                .setPrereq((f,ec) -> f.setProperty(f.objectFor(property_d, ec)))
                .setStartDate(startDate)
                .setPrereq((f,ec) -> f.setCharge1(f.objectFor(charge1_d, ec)))
                .setValue1(value1)
                .setPrereq((f,ec) -> f.setCharge2(f.objectFor(charge2_d, ec)))
                .setValue2(value2)
                ;
    }

    @Override
    public Budget findUsing(final ServiceRegistry2 serviceRegistry) {
        final Property property = property_d.findUsing(serviceRegistry);
        final BudgetRepository budgetRepository = serviceRegistry.lookupService(BudgetRepository.class);

        return budgetRepository.findByPropertyAndStartDate(property, startDate);
    }
}
