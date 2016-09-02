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
package org.estatio.app.integration.documents;

import javax.inject.Inject;

import org.incode.module.documents.dom.docs.Document;
import org.incode.module.documents.dom.docs.DocumentRepository;
import org.incode.module.documents.dom.docs.DocumentTemplate;
import org.incode.module.documents.dom.rendering.Renderer;

public class RendererForSvg implements Renderer {

    @Override
    public Document render(
            final DocumentTemplate documentTemplate, final Object dataModel, final String documentName) {
        throw new RuntimeException("Not yet implemented");
    }

    @Inject
    private DocumentRepository documentRepository;

}