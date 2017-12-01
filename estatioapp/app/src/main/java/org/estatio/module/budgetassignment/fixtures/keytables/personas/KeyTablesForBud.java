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

package org.estatio.module.budgetassignment.fixtures.keytables.personas;

import org.joda.time.LocalDate;

import org.estatio.module.asset.dom.Property;
import org.estatio.module.asset.fixtures.property.enums.Property_enum;
import org.estatio.module.budget.dom.budget.Budget;
import org.estatio.module.budget.dom.keytable.FoundationValueType;
import org.estatio.module.budget.dom.keytable.KeyValueMethod;
import org.estatio.module.budget.fixtures.keytables.KeyTableAbstract;
import org.estatio.module.budgetassignment.fixtures.budget.personas.BudgetForBud;

public class KeyTablesForBud extends KeyTableAbstract {

    public static final String NAME_BY_AREA = "table1";
    public static final String NAME_BY_COUNT = "table2";
    public static final FoundationValueType BUDGET_FOUNDATION_VALUE_TYPE = FoundationValueType.AREA;
    public static final FoundationValueType BUDGET_FOUNDATION_VALUE_TYPE2 = FoundationValueType.COUNT;
    public static final KeyValueMethod BUDGET_KEY_VALUE_METHOD = KeyValueMethod.PROMILLE;
    public static final LocalDate START_DATE = new LocalDate(2015, 1, 1);
    public static final int NUMBER_OF_DIGITS = 6;

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new BudgetForBud());

        // exec
        Property property = Property_enum.BudNl.findUsing(serviceRegistry);
        Budget budget = budgetRepository.findByPropertyAndStartDate(property, START_DATE);

        createKeyTable(budget, NAME_BY_AREA, BUDGET_FOUNDATION_VALUE_TYPE, BUDGET_KEY_VALUE_METHOD, NUMBER_OF_DIGITS, executionContext);
        createKeyTable(budget, NAME_BY_COUNT, BUDGET_FOUNDATION_VALUE_TYPE2, BUDGET_KEY_VALUE_METHOD, NUMBER_OF_DIGITS, executionContext);
    }
}
