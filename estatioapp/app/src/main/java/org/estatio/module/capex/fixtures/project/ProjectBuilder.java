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
package org.estatio.module.capex.fixtures.project;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.BuilderScriptAbstract;

import org.estatio.module.capex.dom.project.Project;
import org.estatio.module.capex.dom.project.ProjectRepository;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@EqualsAndHashCode(of={"reference"}, callSuper = false)
@ToString(of={"reference"})
@Accessors(chain = true)
public final class ProjectBuilder extends BuilderScriptAbstract<Project, ProjectBuilder> {

    @Getter @Setter
    private String reference;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private LocalDate startDate;
    @Getter @Setter
    private LocalDate endDate;
    @Getter @Setter
    private BigDecimal estimatedCost;
    @Getter @Setter
    private String atPath;
    @Getter @Setter
    private Project parent;

    @Getter
    private Project object;

    @Override
    protected void execute(final ExecutionContext ec) {

        checkParam("reference", ec, String.class);
        checkParam("name", ec, String.class);

        object = projectRepository.create(reference, name, startDate, endDate, atPath, parent);
        ec.addResult(this, reference, object);
    }


    @Inject
    protected ProjectRepository projectRepository;

}
