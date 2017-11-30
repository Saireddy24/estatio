package org.estatio.module.party.app.services;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import org.estatio.module.party.dom.Organisation;

public class ChamberOfCommerceCodeLookUpServiceTest {

    @Test
    public void getChamberOfCommerceCodeCandidates() throws Exception {

        // given
        ChamberOfCommerceCodeLookUpService service = new ChamberOfCommerceCodeLookUpService(){
            @Override
            List<OrganisationNameNumberViewModel> findCandidatesForFrance(final String name){
                return Arrays.asList(
                        new OrganisationNameNumberViewModel(),
                        new OrganisationNameNumberViewModel()
                );
            }
        };
        Organisation organisation = new Organisation(){
            @Override
            public String getAtPath(){
                return "/FRA";
            }
        };
        organisation.setName("Company");

        // when
        List<OrganisationNameNumberViewModel> result = service.getChamberOfCommerceCodeCandidates(organisation);

        // then
        Assertions.assertThat(result.size()).isEqualTo(2);

    }

}