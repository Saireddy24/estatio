package org.estatio.module.party.app.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.isis.applib.annotation.DomainObject;

import org.incode.module.base.dom.utils.TitleBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DomainObject(objectType = "org.estatio.module.party.app.services.OrganisationNameNumberViewModel")
@XmlRootElement(name = "organisationNameNumberViewModel")
@XmlType(
        propOrder = {
                "organisationName",
                "chamberOfCommerceCode"
        }
)
@XmlAccessorType(XmlAccessType.FIELD)
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationNameNumberViewModel {

    public String title(){
        return TitleBuilder.start()
                .withName(getOrganisationName())
                .withName(getChamberOfCommerceCode())
                .toString();
    }

    private String organisationName;

    private String chamberOfCommerceCode;

}
