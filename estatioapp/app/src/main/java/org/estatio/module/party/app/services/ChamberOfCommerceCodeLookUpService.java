package org.estatio.module.party.app.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.estatio.module.party.app.services.siren.SirenResult;
import org.estatio.module.party.app.services.siren.SirenService;
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
            return findCandidatesForFranceByName(name);
//            return findCandidatesForFranceFake(name); // TODO: remove after developement

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
              return findCandidateForFranceByCode(code);
//            return findCandidatesForFranceFake(code).get(0); // TODO: remove after developement

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
        List<OrganisationNameNumberViewModel> result = new ArrayList<>();
        SirenService sirenService = new SirenService();
        List<SirenResult> sirenResults = sirenService.getChamberOfCommerceCodes(name);
        for (SirenResult sirenResult : sirenResults){
            String companyName = sirenResult.getCompanyName();
            String cocc = sirenResult.getChamberOfCommerceCode();
            result.add(new OrganisationNameNumberViewModel(companyName, cocc));
        }
        return result;
    }

    OrganisationNameNumberViewModel findCandidateForFranceByCode(final String code){
        SirenService sirenService = new SirenService();
        String companyName = sirenService.getCompanyName(code).getCompanyName();
        return new OrganisationNameNumberViewModel(companyName, code);
    }

}
