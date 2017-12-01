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
package org.estatio.module.lease.fixtures.bankaccount.personas;

import org.estatio.module.financial.fixtures.FinancialAccountTransactionAbstract;
import org.estatio.module.party.fixtures.organisation.enums.OrganisationAndComms_enum;

import static org.incode.module.base.integtests.VT.bd;
import static org.incode.module.base.integtests.VT.ld;

public class FinancialAccountTransactionForTopModel extends FinancialAccountTransactionAbstract {

    public FinancialAccountTransactionForTopModel() {
        this(null, null);
    }

    public FinancialAccountTransactionForTopModel(String friendlyName, String localName) {
        super(friendlyName, localName);
    }

    @Override
    protected void execute(ExecutionContext ec) {

        createFinancialAccountTransaction(
                OrganisationAndComms_enum.TopModelGb.getRef(), ld(2014, 7, 1), bd(1000), ec);
        createFinancialAccountTransaction(
                OrganisationAndComms_enum.TopModelGb.getRef(), ld(2014, 7, 2), bd(2000), ec);
    }
}