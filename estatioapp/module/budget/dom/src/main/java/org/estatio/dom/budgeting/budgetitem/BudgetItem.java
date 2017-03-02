/*
 *
 *  Copyright 2012-2015 Eurocommercial Properties NV
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
package org.estatio.dom.budgeting.budgetitem;

import java.math.BigDecimal;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;

import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;

import org.incode.module.base.dom.utils.TitleBuilder;

import org.estatio.dom.UdoDomainObject2;
import org.estatio.dom.apptenancy.WithApplicationTenancyProperty;
import org.estatio.dom.budgeting.api.PartitionItemCreator;
import org.estatio.dom.budgeting.budget.Budget;
import org.estatio.dom.budgeting.budgetcalculation.BudgetCalculationType;
import org.estatio.dom.budgeting.keytable.KeyTable;
import org.estatio.dom.budgeting.partioning.PartitionItem;
import org.estatio.dom.budgeting.partioning.PartitionItemRepository;
import org.estatio.dom.charge.Charge;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE
        ,schema = "dbo" // Isis' ObjectSpecId inferred from @DomainObject#objectType
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Queries({
        @Query(
                name = "findByBudget", language = "JDOQL",
                value = "SELECT " +
                        "FROM org.estatio.dom.budgeting.budgetitem.BudgetItem " +
                        "WHERE budget == :budget "),
        @Query(
                name = "findByBudgetAndCharge", language = "JDOQL",
                value = "SELECT " +
                        "FROM org.estatio.dom.budgeting.budgetitem.BudgetItem " +
                        "WHERE budget == :budget && charge == :charge")
})
@Unique(name = "BudgetItem_budget_charge_UNQ", members = { "budget", "charge" })
@DomainObject(
        objectType = "org.estatio.dom.budgeting.budgetitem.BudgetItem"
)
public class BudgetItem extends UdoDomainObject2<BudgetItem>
        implements WithApplicationTenancyProperty, PartitionItemCreator {

    public BudgetItem() {
        super("budget, charge");
    }

    public String title() {
        return TitleBuilder.start()
                .withParent(getBudget())
                .withName(getCharge().getReference())
                .toString();
    }

    @Column(name="budgetId", allowsNull = "false")
    @PropertyLayout(hidden = Where.PARENTED_TABLES)
    @Getter @Setter
    private Budget budget;

    @Persistent(mappedBy = "budgetItem", dependentElement = "true")
    @CollectionLayout(hidden = Where.EVERYWHERE)
    @Getter @Setter
    private SortedSet<BudgetItemValue> values = new TreeSet<>();

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public BudgetItem newValue(final BigDecimal value, final LocalDate date){
        return newValue(value, date, BudgetCalculationType.ACTUAL);
    }

    public LocalDate default1NewValue(final BigDecimal value, final LocalDate date){
        return getBudget().getStartDate();
    }

    public String validateNewValue(final BigDecimal value, final LocalDate date){
        return budgetItemValueRepository.validateNewBudgetItemValue(this, value, date, BudgetCalculationType.ACTUAL);
    }

    public String disableNewValue(){
        return budgetItemValueRepository.findByBudgetItemAndType(this, BudgetCalculationType.ACTUAL).size()>0 ? "Audited value already entered" : null;
    }

    @Programmatic
    public BudgetItem newValue(final BigDecimal value, final LocalDate date, final BudgetCalculationType type){
        budgetItemValueRepository.newBudgetItemValue(this, value, date, type);
        return this;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public BigDecimal getBudgetedValue() {
        if (budgetItemValueRepository.findByBudgetItemAndType(this, BudgetCalculationType.BUDGETED).size() > 0) {
            return budgetItemValueRepository.findByBudgetItemAndType(this, BudgetCalculationType.BUDGETED).get(0).getValue().setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public BigDecimal getAuditedValue(){
        if (budgetItemValueRepository.findByBudgetItemAndType(this, BudgetCalculationType.ACTUAL).size() > 0) {
            return budgetItemValueRepository.findByBudgetItemAndType(this, BudgetCalculationType.ACTUAL).get(0).getValue();
        } else {
            return null;
        }
    }

    @Column(name="chargeId", allowsNull = "false")
    @Getter @Setter
    private Charge charge;

    @Override
    @PropertyLayout(hidden = Where.EVERYWHERE)
    public ApplicationTenancy getApplicationTenancy() {
        return getBudget().getApplicationTenancy();
    }

    @Programmatic
    public BudgetItem updateOrCreateBudgetItemValue(final BigDecimal value, final LocalDate date, final BudgetCalculationType type){
        if (value!=null) {
            budgetItemValueRepository.updateOrCreateBudgetItemValue(value, this, date, type);
        }
        return this;
    }

    @Programmatic
    public PartitionItem updateOrCreatePartitionItem(final Charge invoiceCharge, final KeyTable keyTable, final BigDecimal percentage){
        return partitionItemRepository.updateOrCreatePartitionItem(getBudget().getPartitioningForBudgeting(), getCharge(), invoiceCharge, keyTable, percentage);
    }

    @Inject
    private BudgetItemRepository budgetItemRepository;

    @Inject
    private PartitionItemRepository partitionItemRepository;

    private BudgetItemValueRepository budgetItemValueRepository;

}