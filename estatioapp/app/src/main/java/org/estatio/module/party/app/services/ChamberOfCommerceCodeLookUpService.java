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

    public List<OrganisationNameNumberViewModel> getChamberOfCommerceCodeCandidatesByOrganisation(final Organisation organisation) {
        return getChamberOfCommerceCodeCandidatesByOrganisation(organisation.getName(), organisation.getAtPath());
    }

    public List<OrganisationNameNumberViewModel> getChamberOfCommerceCodeCandidatesByOrganisation(final String name, final String atPath) {

        switch (atPath){
        case "/FRA":
//            return findCandidatesForFranceByName(name);
            return findCandidatesForFranceFake(name); // TODO: remove after developement

        default:
            return Collections.emptyList();
        }

    }

    public OrganisationNameNumberViewModel getChamberOfCommerceCodeCandidatesByCode(final Organisation organisation) {
        return getChamberOfCommerceCodeCandidatesByCode(organisation.getChamberOfCommerceCode(), organisation.getAtPath());
    }

    public OrganisationNameNumberViewModel getChamberOfCommerceCodeCandidatesByCode(final String code, final String atPath) {
        switch (atPath){
        case "/FRA":
//              return findCandidateForFranceByCode(code);
            return findCandidatesForFranceFake(code).get(0); // TODO: remove after developement

        default:
            return null;
        }
    }


    List<OrganisationNameNumberViewModel> findCandidatesForFranceFake(final String name){
        List<OrganisationNameNumberViewModel> result = new ArrayList<>();
        result.add(new OrganisationNameNumberViewModel("ORG1", "123456789"));
        result.add(new OrganisationNameNumberViewModel("ORG2", "234234234"));
        result.add(new OrganisationNameNumberViewModel("ORG3", "345456567"));
        return result;
    }

    List<OrganisationNameNumberViewModel> findCandidatesForFranceByName(final String name){
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

    OrganisationNameNumberViewModel findCandidateForFranceByCode(final String code){
        List<OrganisationNameNumberViewModel> result = new ArrayList<>();
        Siren siren = new Siren();
        String resultForCode = siren.getCompanyName(code);
        return new OrganisationNameNumberViewModel(resultForCode, code);
    }

}
