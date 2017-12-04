package org.estatio.module.party.app.services;

import org.assertj.core.api.Assertions;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.joda.time.LocalDate;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

import org.estatio.module.party.dom.Organisation;
import org.estatio.module.party.dom.OrganisationPreviousNameRepository;

public class OrganisationNameNumberViewModel_Test {

    @Test
    public void title_works() throws Exception {

        // given
        OrganisationNameNumberViewModel vm = new OrganisationNameNumberViewModel();
        final String organisationName = "Some organisation name";
        final String chamberOfCommerceCode = "123456789";

        // when
        vm.setOrganisationName(organisationName);
        vm.setChamberOfCommerceCode(chamberOfCommerceCode);

        // then
        Assertions.assertThat(vm.title()).isEqualTo("Some organisation name 123456789");

    }

    @Rule public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @Mock ClockService mockClockService;
    @Mock OrganisationPreviousNameRepository mockOrganisationPreviousNameRepository;

    @Test
    public void applyChamberOfCommerceCodeAndNameTo_works() throws Exception {

        // given
        final LocalDate date = new LocalDate(2017, 1, 1);

        OrganisationNameNumberViewModel vm = new OrganisationNameNumberViewModel();
        final String officalName = "Some official organisation";
        final String chamberOfCommerceCode = "123456789";
        vm.setOrganisationName(officalName);
        vm.setChamberOfCommerceCode(chamberOfCommerceCode);
        vm.clockService = mockClockService;

        final String localName = "Some local name";
        Organisation organisation = new Organisation();
        organisation.setName(localName);
        organisation.organisationPreviousNameRepository = mockOrganisationPreviousNameRepository;
        Assertions.assertThat(organisation.getPreviousNames().size()).isEqualTo(0);

        // expect
        context.checking(new Expectations(){{
            allowing(mockClockService).now();
            will(returnValue(date));
            oneOf(mockOrganisationPreviousNameRepository).newOrganisationPreviousName(localName, date);

        }});

        // when
        vm.applyChamberOfCommerceCodeAndNameTo(organisation);

        // then
        Assertions.assertThat(organisation.getName()).isEqualTo(officalName);
        Assertions.assertThat(organisation.getChamberOfCommerceCode()).isEqualTo(chamberOfCommerceCode);
        Assertions.assertThat(organisation.getPreviousNames().size()).isEqualTo(1);

    }

}