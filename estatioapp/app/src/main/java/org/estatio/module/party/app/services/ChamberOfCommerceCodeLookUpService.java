package org.estatio.module.party.app.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.estatio.module.party.app.services.siren.Siren;
import org.estatio.module.party.dom.Organisation;

@DomainService(
        objectType = "org.estatio.module.party.app.services.ChamberOfCommerceCodeLookUpService",
        nature = NatureOfService.DOMAIN)
public class ChamberOfCommerceCodeLookUpService {

    public List<OrganisationNameNumberViewModel> getChamberOfCommerceCodeCandidates(final Organisation organisation) {
        return getChamberOfCommerceCodeCandidates(organisation.getName(), organisation.getAtPath());
    }

    public List<OrganisationNameNumberViewModel> getChamberOfCommerceCodeCandidates(final String name, final String atPath) {

        switch (atPath){
        case "/FRA":
//            return findCandidatesForFrance(name);
            return findCandidatesForFranceFake(name); // TODO: remove after developement

        default:
            return Collections.emptyList();
        }

    }

    List<OrganisationNameNumberViewModel> findCandidatesForFranceFake(final String name){
        List<OrganisationNameNumberViewModel> result = new ArrayList<>();
        result.add(new OrganisationNameNumberViewModel("ORG1", "123456789"));
        result.add(new OrganisationNameNumberViewModel("ORG2", "234234234"));
        result.add(new OrganisationNameNumberViewModel("ORG3", "345456567"));
        return result;
    }

    List<OrganisationNameNumberViewModel> findCandidatesForFrance(final String name){
        // TODO: improve SIREN to return name of company (l1_normalisee) alongside chamberOfCommerceCode to reduce number of api calls
        List<OrganisationNameNumberViewModel> result = new ArrayList<>();
        Siren siren = new Siren();
        List<String> resultsForCode = siren.getChamberOfCommerceCodes(name);
        for (String code : resultsForCode){
            String companyName = siren.getCompanyName(code);
            result.add(new OrganisationNameNumberViewModel(companyName, code));
        }
        return result;
    }

}
