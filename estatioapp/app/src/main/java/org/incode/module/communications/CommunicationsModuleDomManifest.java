package org.incode.module.communications;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.module.communications.dom.CommunicationsModule;

/**
 * Used by <code>isis-maven-plugin</code> (build-time validation of the module) and also by module-level integration tests.
 */
public class CommunicationsModuleDomManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            CommunicationsModule.class
    );

    public CommunicationsModuleDomManifest() {
        super(BUILDER);
    }

}
