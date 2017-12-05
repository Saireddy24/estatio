package org.estatio.module.party.app;

import java.util.Collections;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;

import org.incode.module.country.dom.impl.Country;

import org.estatio.module.countryapptenancy.dom.EstatioApplicationTenancyRepositoryForCountry;
import org.estatio.module.party.dom.PartyConstants;
import org.estatio.module.party.dom.PartyRepository;
import org.estatio.module.numerator.dom.Numerator;
import org.estatio.module.numerator.dom.NumeratorRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class OrganisationMenuTest {

    public static class ValidateNewOrganisation extends OrganisationMenuTest {

        @Rule
        public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

        @Mock
        EstatioApplicationTenancyRepositoryForCountry mockEstatioApplicationTenancyRepositoryForCountry;

        @Mock
        NumeratorRepository mockNumeratorRepository;

        @Mock
        PartyRepository mockPartyRepository;

        String reference;
        String name;
        Country country;
        ApplicationTenancy applicationTenancy;

        OrganisationMenu organisationMenu;

        @Before
        public void setUp() throws Exception {
            organisationMenu = new OrganisationMenu();

            organisationMenu.estatioApplicationTenancyRepository = mockEstatioApplicationTenancyRepositoryForCountry;
            organisationMenu.numeratorRepository = mockNumeratorRepository;
            organisationMenu.partyRepository = mockPartyRepository;

            reference = "someReference";
            name = "someName";
            country = new Country();

            applicationTenancy = new ApplicationTenancy();
            context.checking(new Expectations() {{
                allowing(mockEstatioApplicationTenancyRepositoryForCountry).findOrCreateTenancyFor(country);
                will(returnValue(applicationTenancy));
            }});

        }

        @Test
        public void when_using_numerator_for_reference_and_no_numerator_found() throws Exception {

            // given
            context.checking(new Expectations() {{
                allowing(mockNumeratorRepository).findGlobalNumerator(PartyConstants.ORGANISATION_REFERENCE_NUMERATOR_NAME, applicationTenancy);
                will(returnValue(null));

                ignoring(mockPartyRepository);
            }});

            // when
            String reason = organisationMenu.validateNewOrganisation(null, name, country, Collections.emptyList());
            // then
            assertThat(reason).isEqualTo("No numerator found");

            // and when
            reason = organisationMenu.validateNewOrganisation(reference, name, country, Collections.emptyList());
            // then
            assertThat(reason).isNull();
        }

        @Test
        public void when_using_numerator_for_reference_and_numerator_is_found() throws Exception {

            // given
            boolean useNumeratorForReference = true;

            context.checking(new Expectations() {{
                allowing(mockNumeratorRepository).findGlobalNumerator(PartyConstants.ORGANISATION_REFERENCE_NUMERATOR_NAME, applicationTenancy);
                will(returnValue(new Numerator()));

                ignoring(mockPartyRepository);
            }});

            // when
            String reason = organisationMenu.validateNewOrganisation(null, name, country, Collections.emptyList());
            // then
            assertThat(reason).isNull();

            // and when
            reason = organisationMenu.validateNewOrganisation(reference, name, country, Collections.emptyList());
            // then
            assertThat(reason).isEqualTo("Reference must be left empty because a numerator is being used");
        }

    }

}