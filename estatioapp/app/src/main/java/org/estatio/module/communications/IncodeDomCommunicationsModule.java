/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package org.estatio.module.communications;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.module.communications.dom.CommunicationsModule;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannel;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwnerLink;
import org.incode.module.communications.dom.impl.comms.CommChannelRole;
import org.incode.module.communications.dom.impl.comms.Communication;
import org.incode.module.communications.dom.impl.paperclips.PaperclipForCommunication;
import org.incode.module.fixturesupport.dom.scripts.TeardownFixtureAbstract;

import org.estatio.module.country.IncodeDomCountryModule;
import org.estatio.module.document.IncodeDomDocumentModule;

/**
 * This is a "proxy" for the corresponding module defined in the Incode Platform,
 * which we intend to move up into the Estatio codebase
 */
@XmlRootElement(name = "module")
public final class IncodeDomCommunicationsModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new IncodeDomCountryModule(),
                new IncodeDomDocumentModule()
        );
    }


    @Override
    public Set<Class<?>> getAdditionalModules() {
        return Sets.newHashSet(CommunicationsModule.class);
    }


    @Override
    public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract() {
            @Override
            protected void execute(final FixtureScript.ExecutionContext executionContext) {

                deleteFrom(PaperclipForCommunication.class);
                deleteFrom(CommChannelRole.class); // ie communication correspondent
                deleteFrom(Communication.class);

                deleteFrom(CommunicationChannelOwnerLink.class);
                deleteFrom(CommunicationChannel.class);
            }
        };
    }



}
