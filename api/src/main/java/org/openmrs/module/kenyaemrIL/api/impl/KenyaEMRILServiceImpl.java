/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 * <p>
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * <p>
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.kenyaemrIL.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.core.util.PresortedSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.openmrs.*;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemrIL.api.ILMessageType;
import org.openmrs.module.kenyaemrIL.api.KenyaEMRILService;
import org.openmrs.module.kenyaemrIL.api.db.KenyaEMRILDAO;
import org.openmrs.module.kenyaemrIL.il.*;
import org.openmrs.module.kenyaemrIL.il.appointment.APPOINTMENT_INFORMATION;
import org.openmrs.module.kenyaemrIL.il.appointment.AppointmentMessage;
import org.openmrs.module.kenyaemrIL.il.appointment.PLACER_APPOINTMENT_NUMBER;
import org.openmrs.module.kenyaemrIL.il.observation.OBSERVATION_RESULT;
import org.openmrs.module.kenyaemrIL.il.observation.ObservationMessage;
import org.openmrs.module.kenyaemrIL.il.observation.VIRAL_LOAD_RESULT;
import org.openmrs.module.kenyaemrIL.il.pharmacy.ILPharmacyDispense;
import org.openmrs.module.kenyaemrIL.il.pharmacy.ILPharmacyOrder;
import org.openmrs.module.kenyaemrIL.il.utils.MessageHeaderSingleton;
import org.openmrs.module.kenyaemrIL.il.viralload.ViralLoadMessage;
import org.openmrs.module.kenyaemrIL.util.ILUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.openmrs.module.kenyaemrIL.api.ILPatientRegistration.conceptService;

/**
 * It is a default implementation of {@link KenyaEMRILService}.
 */
public class KenyaEMRILServiceImpl extends BaseOpenmrsService implements KenyaEMRILService {

    protected final Log log = LogFactory.getLog(this.getClass());

    private List<PatientIdentifierType> allPatientIdentifierTypes;

    private Map<String, PatientIdentifierType> identifiersMap = new HashMap<>();

    private KenyaEMRILDAO dao;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    PatientService patientService;

    @Autowired
    private KenyaEmrService kenyaEmrService;

    @Autowired
    private IdentifierSourceService idgenService;

    /**
     * @param dao the dao to set
     */
    public void setDao(KenyaEMRILDAO dao) {
        this.dao = dao;
    }

    /**
     * @return the dao
     */
    public KenyaEMRILDAO getDao() {
        return dao;
    }

    @Override
    public List<ILMessage> getPersonList(boolean status) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public List<ILMessage> getAddPersonList(boolean status) {
        throw new NotYetImplementedException("Not Yet Implemented");

    }

    @Override
    public List<ILMessage> getUpdatePersonList(boolean status) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public boolean sendUpdateRequest(ILMessage ilMessage) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public boolean sendAddPersonRequest(ILMessage ilMessage) {
        boolean isSuccessful;
        //Message Header
        MESSAGE_HEADER messageHeader = MessageHeaderSingleton.getMessageHeaderInstance("ADT^A04");
        ilMessage.setMessage_header(messageHeader);
        ILPerson ilPerson = ilMessage.extractILRegistration();
        KenyaEMRILMessage kenyaEMRILMessage = new KenyaEMRILMessage();
        try {
            String messageString = mapper.writeValueAsString(ilPerson);
            kenyaEMRILMessage.setHl7Type("ADT^A04");
            kenyaEMRILMessage.setMessage(messageString);
            kenyaEMRILMessage.setDescription("");
            kenyaEMRILMessage.setName("");
            kenyaEMRILMessage.setMessageType(ILMessageType.OUTBOUND.getValue());
            KenyaEMRILMessage savedInstance = saveKenyaEMRILMessage(kenyaEMRILMessage);
            if (savedInstance != null) {
                isSuccessful = true;
            } else {
                isSuccessful = false;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            isSuccessful = false;
        }
        return isSuccessful;
    }


    @Override
    public List<ILPharmacyOrder> fetchAllPharmacyOrders() {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public List<ILPharmacyOrder> fetchPharmacyOrders(boolean processed) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public ILPharmacyOrder createPharmacyOrder(ILPharmacyOrder ilPharmacyOrder) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public ILPharmacyOrder updatePharmacyOrder(ILPharmacyOrder ilPharmacyOrder) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public boolean deletePharmacyOrder(ILPharmacyOrder ilPharmacyOrder) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public List<ILPharmacyDispense> fetchAllPharmacyDispenses() {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public List<ILPharmacyDispense> fetchPharmacyDispenses(boolean processed) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public ILPharmacyDispense createPharmacyDispense(ILPharmacyDispense ilPharmacyDispense) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public ILPharmacyDispense updatePharmacyDispense(ILPharmacyDispense ilPharmacyDispense) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public boolean deletePharmacyDispense(ILPharmacyDispense ilPharmacyDispense) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public KenyaEMRILMessage getKenyaEMRILMessageByUuid(String uniqueId) {
        KenyaEMRILMessage kenyaEMRILMessage = this.dao.getKenyaEMRILMessageByUuid(uniqueId);
        System.out.println("What is it htat was returned: " + kenyaEMRILMessage);
        return kenyaEMRILMessage;
    }

    @Override
    public KenyaEMRILMessage saveKenyaEMRILMessage(KenyaEMRILMessage ilMessage) {
        return this.dao.createKenyaEMRILMessage(ilMessage);
    }

    @Override
    public List<KenyaEMRILMessage> getKenyaEMRILInboxes(Boolean includeRetired) {
        return this.dao.getKenyaEMRILInboxes(includeRetired);
    }

    @Override
    public List<KenyaEMRILMessage> getKenyaEMRILOutboxes(Boolean includeRetired) {
        return this.dao.getKenyaEMRILOutboxes(includeRetired);
    }

    @Override
    public void deleteKenyaEMRILMessage(KenyaEMRILMessage kenyaEMRILMessage) {
        this.dao.deleteKenyaEMRILMessage(kenyaEMRILMessage);
    }

    @Override
    public List<KenyaEMRILMessage> getAllKenyaEMRILMessages(Boolean includeAll) {
        return this.dao.getAllKenyaEMRILMessages(includeAll);
    }

    @Override
    public boolean processCreatePatientRequest(ILMessage ilMessage) {
        boolean successful = false;
        Patient patient = patientService.savePatient(wrapIlPerson(ilMessage));
        if (patient != null) {
            successful = true;
        }
        return successful;
    }

    @Override
    public boolean processUpdatePatientRequest(ILMessage ilMessage) {
        Patient patient = null;
        String cccNumber = null;
//        1. Fetch the person to update using the CCC number
        for (INTERNAL_PATIENT_ID internalPatientId : ilMessage.getPatient_identification().getInternal_patient_id()) {
            if (internalPatientId.getIdentifier_type().equalsIgnoreCase("CCC_NUMBER")) {
                cccNumber = internalPatientId.getId();
                break;
            }
        }
        if (cccNumber == null) {
//            no patient with the given ccc number, proceed to create a new patient with the received details

            return processCreatePatientRequest(ilMessage);
        } else {
//            fetch the patient
            List<Patient> patients = Context.getPatientService().getPatients(null, cccNumber, allPatientIdentifierTypes, true);
            if (patients.size() > 0) {
                patient = patients.get(0);
                patient = updatePatientDetails(patient, wrapIlPerson(ilMessage));
            }

            Patient cPatient = patientService.savePatient(patient);
            if (cPatient != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    private Patient updatePatientDetails(Patient oldPatientToUpdate, Patient newPatientDetails) {
//    Update patient identification - names and identifiers
        //Update person address
        PersonAddress nPersonAddress = newPatientDetails.getPersonAddress();
        PersonAddress oPersonAddress = oldPatientToUpdate.getPersonAddress();

        oPersonAddress.setAddress1(nPersonAddress.getAddress1() != null ? nPersonAddress.getAddress1() : oPersonAddress.getAddress1());
        oPersonAddress.setAddress2(nPersonAddress.getAddress2() != null ? nPersonAddress.getAddress2() : oPersonAddress.getAddress2());
        oPersonAddress.setAddress3(nPersonAddress.getAddress3() != null ? nPersonAddress.getAddress3() : oPersonAddress.getAddress3());
        oPersonAddress.setAddress4(nPersonAddress.getAddress4() != null ? nPersonAddress.getAddress4() : oPersonAddress.getAddress4());
        oPersonAddress.setAddress5(nPersonAddress.getAddress5() != null ? nPersonAddress.getAddress5() : oPersonAddress.getAddress5());
        oPersonAddress.setCityVillage(nPersonAddress.getCityVillage() != null ? nPersonAddress.getCityVillage() : oPersonAddress.getCityVillage());
        oPersonAddress.setCountyDistrict(nPersonAddress.getCountyDistrict() != null ? nPersonAddress.getCountyDistrict() : oPersonAddress.getCountyDistrict());
        oPersonAddress.setStateProvince(nPersonAddress.getStateProvince() != null ? nPersonAddress.getStateProvince() : oPersonAddress.getStateProvince());
        oPersonAddress.setCountry(nPersonAddress.getCountry() != null ? nPersonAddress.getCountry() : oPersonAddress.getCountry());
        oPersonAddress.setPostalCode(nPersonAddress.getPostalCode() != null ? nPersonAddress.getPostalCode() : oPersonAddress.getPostalCode());

        Set<PersonAddress> addresses = new PresortedSet();
        addresses.add(oPersonAddress);
        oldPatientToUpdate.setAddresses(addresses);

        //Update person name
        PersonName nPersonName = newPatientDetails.getPersonName();
        PersonName oPersonName = oldPatientToUpdate.getPersonName();

        oPersonName.setGivenName(nPersonName.getGivenName() != null ? nPersonName.getGivenName() : oPersonName.getGivenName());
        oPersonName.setMiddleName(nPersonName.getMiddleName() != null ? nPersonName.getMiddleName() : oPersonName.getMiddleName());
        oPersonName.setFamilyName(nPersonName.getFamilyName() != null ? nPersonName.getFamilyName() : oPersonName.getFamilyName());

        Set<PersonName> names = new PresortedSet();
        names.add(oPersonName);
        oldPatientToUpdate.setNames(names);

//    Update identifiers
        Set<PatientIdentifier> oldIds = oldPatientToUpdate.getIdentifiers();
        for (PatientIdentifier patientIdentifier : newPatientDetails.getIdentifiers()) {
            PatientIdentifierType identifierType = patientIdentifier.getIdentifierType();
            PatientIdentifier fetchedId = oldPatientToUpdate.getPatientIdentifier(identifierType);
            if (fetchedId != null) {
                fetchedId.setIdentifier(patientIdentifier.getIdentifier());
                fetchedId.setPreferred(patientIdentifier.getPreferred());
            } else {
                fetchedId = new PatientIdentifier();
                fetchedId.setPreferred(patientIdentifier.getPreferred());
                fetchedId.setIdentifier(patientIdentifier.getIdentifier());
                fetchedId.setIdentifierType(identifierType);
            }
            oldIds.add(fetchedId);
        }
        oldPatientToUpdate.setIdentifiers(oldIds);
//Update person attributes

        Set<PersonAttribute> personAttributes = newPatientDetails.getAttributes();
        for (PersonAttribute personAttribute : personAttributes) {
            if (personAttribute.getValue() != null) {
                PersonAttributeType attributeType = personAttribute.getAttributeType();
                PersonAttribute attribute = oldPatientToUpdate.getAttribute(attributeType);
                if (attribute != null) {
                    attribute.setValue(personAttribute.getValue());
                } else {
                    attribute = new PersonAttribute();
                    attribute.setValue(personAttribute.getValue());
                    attribute.setAttributeType(attributeType);
                }
                oldPatientToUpdate.addAttribute(attribute);
            }
        }

        //Update gender
        oldPatientToUpdate.setGender(newPatientDetails.getGender() != null ? newPatientDetails.getGender() : oldPatientToUpdate.getGender());
        // Update birthdate
        oldPatientToUpdate.setBirthdate(newPatientDetails.getBirthdate() != null ? newPatientDetails.getBirthdate() : oldPatientToUpdate.getBirthdate());
        // Update birthdate Estimated
        oldPatientToUpdate.setBirthdateEstimated(newPatientDetails.getBirthdateEstimated() != null ? newPatientDetails.getBirthdateEstimated() : oldPatientToUpdate.getBirthdateEstimated());
        // Update if dead
        oldPatientToUpdate.setDead(newPatientDetails.getDead() != null ? newPatientDetails.getDead() : oldPatientToUpdate.getDead());
        // Update if death date
        oldPatientToUpdate.setDeathDate(newPatientDetails.getDeathDate() != null ? newPatientDetails.getDeathDate() : oldPatientToUpdate.getDeathDate());
// Update patient attributes

        return oldPatientToUpdate;
    }

    @Override
    public boolean processPharmacyOrder(ILMessage ilMessage) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public boolean processPharmacyDispense(ILMessage ilMessage) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public boolean processAppointmentSchedule(ILMessage ilMessage) {
        boolean success = false;
        String cccNumber = null;
//        1. Fetch the person to update using the CCC number
        for (INTERNAL_PATIENT_ID internalPatientId : ilMessage.getPatient_identification().getInternal_patient_id()) {
            if (internalPatientId.getIdentifier_type().equalsIgnoreCase("CCC_NUMBER")) {
                cccNumber = internalPatientId.getId();
                break;
            }
        }
        if (cccNumber == null) {
//            no patient with the given ccc number, proceed to create a new patient with the received details
            success = false;
        } else {
//            fetch the patient
            List<Patient> patients = Context.getPatientService().getPatients(null, cccNumber, allPatientIdentifierTypes, true);
            Patient patient;
            if (patients.size() > 0) {
                patient = patients.get(0);

                //Save the appointment
                AppointmentMessage appointmentMessage = ilMessage.extractAppointmentMessage();
                APPOINTMENT_INFORMATION[] appointmentInformation = appointmentMessage.getAppointment_information();
                Encounter lastFollowUpEncounter = ILUtils.lastEncounter(patient, Context.getEncounterService().getEncounterTypeByUuid("a0034eee-1940-4e35-847f-97537a35d05e"));   //last greencard followup form
                Integer patientTCAConcept = 5096;
                Integer patientTCAReasonConcept = 160288;
                for (APPOINTMENT_INFORMATION appInfo : appointmentInformation) {
                    PLACER_APPOINTMENT_NUMBER placerAppointmentNumber = appInfo.getPlacer_appointment_number();
                    String appointmentStatus = appInfo.getAppointment_status();
                    String appointmentReason = appInfo.getAppointment_reason();
                    String action_code = appInfo.getAction_code();
                    String appointment_note = appInfo.getAppointment_note();
                    String appointmentDate = appInfo.getAppointment_date();
                    String placerAppointmentNumberNumber = placerAppointmentNumber.getNumber();
                    String entity = placerAppointmentNumber.getEntity();

                    Obs appointmentObs = new Obs();
                    appointmentObs.setComment(placerAppointmentNumberNumber + "" + appointment_note + "" + appointmentStatus + "" + appointmentReason);
                    appointmentObs.setConcept(Context.getConceptService().getConcept(patientTCAConcept));
                    appointmentObs.setValueText(appointmentDate);
                    appointmentObs.setObsDatetime(new Date());
                    lastFollowUpEncounter.addObs(appointmentObs);
                }
                Context.getEncounterService().saveEncounter(lastFollowUpEncounter);
            }
        }
        return success;
    }
    @Override
    public boolean processLabOrder(ILMessage ilMessage) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }
      //TODO: Lab orders not yet implemented
    @Override
    public boolean processObservationResult(ILMessage ilMessage) {
        boolean success = false;
        String cccNumber = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        1. Fetch the person to update using the CCC number
     for (INTERNAL_PATIENT_ID internalPatientId : ilMessage.getPatient_identification().getInternal_patient_id()) {
               if (internalPatientId.getIdentifier_type().equalsIgnoreCase("CCC_NUMBER")) {
                   cccNumber = internalPatientId.getId();
                   break;
               }
           }
           if (cccNumber == null) {
//            no patient with the given ccc number, proceed to create a new patient with the received details
               success = false;
           } else {
//            fetch the patient
               List<Patient> patients = Context.getPatientService().getPatients(null, cccNumber, allPatientIdentifierTypes, true);
               Patient patient;
               if (patients.size() > 0) {
                   patient = patients.get(0);

                   //Save the observation
                   ObservationMessage observationMessage = ilMessage.extractORUMessage();
                   OBSERVATION_RESULT[] observationInformation = observationMessage.getObservation_result();
                   //GEt the encounters to append ORUs
                   Encounter hivGreencardEncounter = ILUtils.lastEncounter(patient, Context.getEncounterService().getEncounterTypeByUuid("a0034eee-1940-4e35-847f-97537a35d05e"));   //last greencard followup
                   Encounter hivEnrollmentEncounter = ILUtils.lastEncounter(patient, Context.getEncounterService().getEncounterTypeByUuid("de78a6be-bfc5-4634-adc3-5f1a280455cc"));  //hiv enrollment
                   Encounter drugOrderEncounter = ILUtils.lastEncounter(patient, Context.getEncounterService().getEncounterTypeByUuid("7df67b83-1b84-4fe2-b1b7-794b4e9bfcc3"));  //last drug order
                   Encounter mchMotherEncounter = ILUtils.lastEncounter(patient, Context.getEncounterService().getEncounterTypeByUuid("3ee036d8-7c13-4393-b5d6-036f2fe45126"));  //mch mother enrollment
                   Encounter labResultEncounter = ILUtils.lastEncounter(patient, Context.getEncounterService().getEncounterTypeByUuid("17a381d1-7e29-406a-b782-aa903b963c28"));  //lab results
                   //Obs for consideration   - not complete list
                   Integer HeightConcept = 5090;
                   Integer WeightConcept = 5089;
                   Integer HivDiagnosisDateConcept = 160554;
                   Integer HivCareInitiationDateConcept = 160555;
                   Integer ARTInitiationDateConcept = 159599;
                   Integer IspregnantConcept = 5272;
                   Integer EDDConcept = 5596;
                   Integer DateOfDeliveryConcept = 5599;
                   Integer ARVConcept = 1085;    //TODO: Implement incoming regimen
                   Integer SmokerConcept = 155600;
                   Integer AlcoholUseConcept = 159449;
                   Integer CTXStartConcept = 162229;
                   Integer TestsOrderedConcept = 1271;
                   Integer CD4Concept = 5497;
                   Integer CD4PercentConcept = 730;
                   Integer TBdiagnosisDateConcept = 1662;
                   Integer TBTreatmentStartDateConcept = 1113;
                   Integer TBTreatmentCompleteDateConcept = 164384;
                   Integer WhoStageConcept = 5356;
                   Integer PedWhoStage1Concept = 1220;
                   Integer PedWhoStage2Concept = 1221;
                   Integer PedWhoStage3Concept = 1222;
                   Integer PedWhoStage4Concept = 1223;
                   Integer AdultWhoStage1Concept = 1204;
                   Integer AdultWhoStage2Concept = 1205;
                   Integer AdultWhoStage3Concept = 1206;
                   Integer AdultWhoStage4Concept = 1207;
                   Integer YesConcept = 1065;
                   Integer NoConcept = 1066;

                   for (OBSERVATION_RESULT obsInfo : observationInformation) {

                       String observationSubId = obsInfo.getObservation_sub_id();
                       String observationCodingSystem = obsInfo.getCoding_system();
                       String observationValueType = obsInfo.getValue_type();
                       String observationUnits = obsInfo.getUnits();
                       String observationResultStatus = obsInfo.getObservation_result_status();
                       String observationAbnormalFlags = obsInfo.getAbnormal_flags();
                       Obs observationObs = new Obs();
                       //observationObs.setComment(placerAppointmentNumberNumber + "" + appointment_note + "" + appointmentStatus + "" + appointmentReason);


                       if (obsInfo.getObservation_identifier() == "START_HEIGHT") {             //Start Height
                           if (obsInfo.getObservation_value() != null) {
                               observationObs.setValueNumeric(Double.parseDouble(obsInfo.getObservation_value()));
                               observationObs.setConcept(Context.getConceptService().getConcept(HeightConcept));
                               try {
                                   observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               hivEnrollmentEncounter.addObs(observationObs);
                               Context.getEncounterService().saveEncounter(hivEnrollmentEncounter);
                           }
                       }


                       if (obsInfo.getObservation_identifier() == "START_WEIGHT") {             //Start Weight

                           if (obsInfo.getObservation_value() != null) {
                               observationObs.setValueNumeric(Double.parseDouble(obsInfo.getObservation_value()));
                               observationObs.setConcept(Context.getConceptService().getConcept(WeightConcept));
                               try {
                                   observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               hivEnrollmentEncounter.addObs(observationObs);
                               Context.getEncounterService().saveEncounter(hivEnrollmentEncounter);
                           }

                       }
                       if (obsInfo.getObservation_identifier() == "HIV_DIAGNOSIS") {             //HIV Diagnosis date

                           if (obsInfo.getObservation_value() != null) {
                               observationObs.setConcept(Context.getConceptService().getConcept(HivDiagnosisDateConcept));
                               try {
                                   observationObs.setValueDatetime(formatter.parse(obsInfo.getObservation_value()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               try {
                                   observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }

                               hivEnrollmentEncounter.addObs(observationObs);
                               Context.getEncounterService().saveEncounter(hivEnrollmentEncounter);
                           }
                       }
                       if (obsInfo.getObservation_identifier() == "HIV_CARE_INITIATION") {             // hiv care initiation date  HivCareInitiationDateConcept
                           if (obsInfo.getObservation_value() != null) {
                               observationObs.setConcept(Context.getConceptService().getConcept(HivCareInitiationDateConcept));
                               try {
                                   observationObs.setValueDatetime(formatter.parse(obsInfo.getObservation_value()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               try {
                                   observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }

                               hivEnrollmentEncounter.addObs(observationObs);
                               Context.getEncounterService().saveEncounter(hivEnrollmentEncounter);
                           }
                       }

                       if (obsInfo.getObservation_identifier() == "IS_PREGNANT") {             // Is pregnant
                           if (obsInfo.getObservation_value() != null && obsInfo.getObservation_value() == "Y") {
                               observationObs.setConcept(Context.getConceptService().getConcept(IspregnantConcept));
                               observationObs.setValueCoded(Context.getConceptService().getConcept(YesConcept));
                               try {
                                   observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               hivGreencardEncounter.addObs(observationObs);
                               Context.getEncounterService().saveEncounter(hivGreencardEncounter);
                           }
                       }

                       if (obsInfo.getObservation_identifier() == "PRENGANT_EDD") {             // EDD
                           if (obsInfo.getObservation_value() != null) {
                               observationObs.setConcept(Context.getConceptService().getConcept(EDDConcept));
                               try {
                                   observationObs.setValueDatetime(formatter.parse(obsInfo.getObservation_value()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               try {
                                   observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               hivGreencardEncounter.addObs(observationObs);
                               Context.getEncounterService().saveEncounter(hivGreencardEncounter);
                           }
                       }
                       if (obsInfo.getObservation_identifier() == "COTRIMOXAZOLE_START") {             // CTX start date
                           if (obsInfo.getObservation_value() != null) {
                               observationObs.setConcept(Context.getConceptService().getConcept(CTXStartConcept));
                               observationObs.setValueCoded(Context.getConceptService().getConcept(YesConcept));
                               try {
                                   observationObs.setValueDatetime(formatter.parse(obsInfo.getObservation_value()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               try {
                                   observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               hivGreencardEncounter.addObs(observationObs);
                               Context.getEncounterService().saveEncounter(hivGreencardEncounter);
                           }
                       }
                       if (obsInfo.getObservation_identifier() == "TB_DIAGNOSIS_DATE") {             // TB diagnosis  date
                           if (obsInfo.getObservation_value() != null) {
                               observationObs.setConcept(Context.getConceptService().getConcept(TBdiagnosisDateConcept));
                               try {
                                   observationObs.setValueDatetime(formatter.parse(obsInfo.getObservation_value()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               try {
                                   observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               hivGreencardEncounter.addObs(observationObs);
                               Context.getEncounterService().saveEncounter(hivGreencardEncounter);
                           }
                       }
                       if (obsInfo.getObservation_identifier() == "TB_TREATMENT_START_DATE") {             // TB treatment start  date
                           if (obsInfo.getObservation_value() != null) {
                               observationObs.setConcept(Context.getConceptService().getConcept(TBTreatmentStartDateConcept));
                               try {
                                   observationObs.setValueDatetime(formatter.parse(obsInfo.getObservation_value()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               try {
                                   observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               hivGreencardEncounter.addObs(observationObs);
                               Context.getEncounterService().saveEncounter(hivGreencardEncounter);
                           }
                       }
                       if (obsInfo.getObservation_identifier() == "TB_TREATMENT_COMPLETE_DATE") {             // TB treatment complete  date
                           if (obsInfo.getObservation_value() != null) {
                               if (obsInfo.getObservation_value() != null) {
                                   observationObs.setConcept(Context.getConceptService().getConcept(TBTreatmentCompleteDateConcept));
                                   try {
                                       observationObs.setValueDatetime(formatter.parse(obsInfo.getObservation_value()));
                                   } catch (ParseException e) {
                                       e.printStackTrace();
                                   }
                                   try {
                                       observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                                   } catch (ParseException e) {
                                       e.printStackTrace();
                                   }
                                   hivGreencardEncounter.addObs(observationObs);
                                   Context.getEncounterService().saveEncounter(hivGreencardEncounter);
                               }
                           }
                       }
                       if (obsInfo.getObservation_identifier() == "WHO_STAGE") {             // WHO stage
                           if (obsInfo.getObservation_value() != null) {
                               observationObs.setConcept(Context.getConceptService().getConcept(WhoStageConcept));
                               Date dob = patient.getBirthdate();
                               Integer age = calculateAge(dob);
                               if (age >= 14) {
                                   if (obsInfo.getObservation_value() == "1") {
                                       observationObs.setValueCoded(Context.getConceptService().getConcept(AdultWhoStage1Concept));
                                   } else if (obsInfo.getObservation_value() == "2") {
                                       observationObs.setValueCoded(Context.getConceptService().getConcept(AdultWhoStage2Concept));
                                   } else if (obsInfo.getObservation_value() == "3") {
                                       observationObs.setValueCoded(Context.getConceptService().getConcept(AdultWhoStage3Concept));
                                   } else if (obsInfo.getObservation_value() == "4") {
                                       observationObs.setValueCoded(Context.getConceptService().getConcept(AdultWhoStage4Concept));
                                   }
                               } else {
                                   if (obsInfo.getObservation_value() == "1") {
                                       observationObs.setValueCoded(Context.getConceptService().getConcept(PedWhoStage1Concept));
                                   } else if (obsInfo.getObservation_value() == "2") {
                                       observationObs.setValueCoded(Context.getConceptService().getConcept(PedWhoStage2Concept));
                                   } else if (obsInfo.getObservation_value() == "3") {
                                       observationObs.setValueCoded(Context.getConceptService().getConcept(PedWhoStage3Concept));
                                   } else if (obsInfo.getObservation_value() == "4") {
                                       observationObs.setValueCoded(Context.getConceptService().getConcept(PedWhoStage4Concept));
                                   }
                               }
                               try {
                                   observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               hivGreencardEncounter.addObs(observationObs);
                               Context.getEncounterService().saveEncounter(hivGreencardEncounter);
                           }
                       }
                       if (obsInfo.getObservation_identifier() == "ART_START") {             // art start date
                           if (obsInfo.getObservation_value() != null) {
                               observationObs.setConcept(Context.getConceptService().getConcept(ARTInitiationDateConcept));
                               try {
                                   observationObs.setValueDatetime(formatter.parse(obsInfo.getObservation_value()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               try {
                                   observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               hivGreencardEncounter.addObs(observationObs);
                               Context.getEncounterService().saveEncounter(hivGreencardEncounter);
                           }
                       }
                       if (obsInfo.getObservation_identifier() == "PMTCT_INITIATION") {          // pmtct initiation date
                           if (obsInfo.getObservation_value() != null) {
                               if (mchMotherEncounter != null) {
                                   try {
                                       observationObs.setValueDatetime(formatter.parse(obsInfo.getObservation_value()));
                                   } catch (ParseException e) {
                                       e.printStackTrace();
                                   }
                                   observationObs.setObsDatetime(mchMotherEncounter.getEncounterDatetime());
                                   mchMotherEncounter.addObs(observationObs);
                                   Context.getEncounterService().saveEncounter(mchMotherEncounter);
                               }
                           }
                       }
                       if (obsInfo.getObservation_identifier() == "CHILD_BIRTH") {        // delivery date
                           if (obsInfo.getObservation_value() != null) {
                               if (mchMotherEncounter != null) {
                                   observationObs.setConcept(Context.getConceptService().getConcept(DateOfDeliveryConcept));
                                   try {
                                       observationObs.setValueDatetime(formatter.parse(obsInfo.getObservation_value()));
                                   } catch (ParseException e) {
                                       e.printStackTrace();
                                   }
                                   observationObs.setObsDatetime(mchMotherEncounter.getEncounterDatetime());
                                   mchMotherEncounter.addObs(observationObs);
                                   Context.getEncounterService().saveEncounter(mchMotherEncounter);
                               }
                           }
                       }
                       if (obsInfo.getObservation_identifier() == "CD4_COUNT") {             // CD4 Count
                           if (obsInfo.getObservation_value() != null) {
                               observationObs.setConcept(Context.getConceptService().getConcept(CD4Concept));
                               observationObs.setValueNumeric(Double.parseDouble(obsInfo.getObservation_value()));
                               try {
                                   observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               labResultEncounter.addObs(observationObs);
                               Context.getEncounterService().saveEncounter(labResultEncounter);
                           }
                       }
                       if (obsInfo.getObservation_identifier() == "CD4_PERCENT") {             // CD4 Percent
                           if (obsInfo.getObservation_value() != null) {
                               observationObs.setConcept(Context.getConceptService().getConcept(CD4PercentConcept));
                               observationObs.setValueNumeric(Double.parseDouble(obsInfo.getObservation_value()));
                               try {
                                   observationObs.setObsDatetime(formatter.parse(obsInfo.getObservation_datetime()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               labResultEncounter.addObs(observationObs);
                               Context.getEncounterService().saveEncounter(labResultEncounter);
                           }
                       }
                   }

               }
           }
            return success;
      }
    @Override
    public boolean processViralLoad(ILMessage ilMessage) {
        boolean success = false;
        String cccNumber = null;
//        1. Fetch the person to update using the CCC number
        for (INTERNAL_PATIENT_ID internalPatientId : ilMessage.getPatient_identification().getInternal_patient_id()) {
            if (internalPatientId.getIdentifier_type().equalsIgnoreCase("CCC_NUMBER")) {
                cccNumber = internalPatientId.getId();
                break;
            }
        }
        if (cccNumber == null) {
//            no patient with the given ccc number, proceed to create a new patient with the received details
            success = false;
        } else {
//            fetch the patient
            List<Patient> patients = Context.getPatientService().getPatients(null, cccNumber, allPatientIdentifierTypes, true);
            Patient patient;
            if (patients.size() > 0) {
                patient = patients.get(0);

                //Save the viral load results
                ViralLoadMessage viralLoadMessage = ilMessage.extractViralLoadMessage();
                VIRAL_LOAD_RESULT [] viralLoadResult = viralLoadMessage.getViral_load_result();
                Encounter lastLabResultsEncounter = ILUtils.lastEncounter(patient, Context.getEncounterService().getEncounterTypeByUuid("17a381d1-7e29-406a-b782-aa903b963c28"));   //last lab results followup form
                Integer vLConcept = 856;
                Integer LDLQuestionConcept = 1305;
                Integer LDLAnswerConcept = 1302;
                Integer ARVConcept = 1085;

                for (VIRAL_LOAD_RESULT labInfo : viralLoadResult) {
                    String dateSampleCollected = labInfo.getDate_sample_collected();
                    String dateSampleTested = labInfo.getDate_sample_tested();
                    String vlResult = labInfo.getVl_result();
                    String sampleType = labInfo.getSample_type();
                    String sampleRejection = labInfo.getSample_rejection();
                    String justification = labInfo.getJustification();
                    String regimen = labInfo.getRegimen();
                    String labTested = labInfo.getLab_tested_in();

                    Obs labsObs = new Obs();
                    labsObs.setComment(dateSampleCollected + "" + dateSampleTested + "" + sampleType + "" + sampleRejection+ "" + justification + "" + regimen + "" + labTested);
                   //Check to
                    if(vlResult != null ) {
                        String vl = vlResult.replaceAll("\\D+", "");
                        if (vl !="") {
                            labsObs.setConcept(Context.getConceptService().getConcept(vLConcept));       //add viral load concept
                            double viralResultNumeric = Double.parseDouble(vlResult);
                            labsObs.setValueNumeric(viralResultNumeric);
                        } else {
                            labsObs.setConcept(Context.getConceptService().getConcept(LDLQuestionConcept));       //add ldl concept
                            labsObs.setValueText(vlResult);
                        }
                    }

                    labsObs.setObsDatetime(new Date());
                    lastLabResultsEncounter.addObs(labsObs);
                }
                Context.getEncounterService().saveEncounter(lastLabResultsEncounter);
            }
        }

        return success;
    }

    @Override
    public boolean process731Adx(ILMessage ilMessage) {
        throw new NotYetImplementedException("Not Yet Implemented");
    }

    @Override
    public boolean logAppointmentSchedule(ILMessage ilMessage) {
        boolean isSuccessful;
        //Message Header
        MESSAGE_HEADER messageHeader = MessageHeaderSingleton.getMessageHeaderInstance("SIU^S12");
        ilMessage.setMessage_header(messageHeader);
        KenyaEMRILMessage kenyaEMRILMessage = new KenyaEMRILMessage();
        try {
            AppointmentMessage appointmentMessage = ilMessage.extractAppointmentMessage();
            String messageString = mapper.writeValueAsString(appointmentMessage);
            kenyaEMRILMessage.setHl7Type("SIU^S12");
            kenyaEMRILMessage.setMessage(messageString);
            kenyaEMRILMessage.setDescription("");
            kenyaEMRILMessage.setName("");
            kenyaEMRILMessage.setMessageType(ILMessageType.OUTBOUND.getValue());
            KenyaEMRILMessage savedInstance = saveKenyaEMRILMessage(kenyaEMRILMessage);
            if (savedInstance != null) {
                isSuccessful = true;
            } else {
                isSuccessful = false;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            isSuccessful = false;
        }
        return isSuccessful;
    }

    @Override
    public boolean logViralLoad(ILMessage ilMessage) {
        boolean isSuccessful;
        //Message Header
        MESSAGE_HEADER messageHeader = MessageHeaderSingleton.getMessageHeaderInstance("ORU^VL");
        ilMessage.setMessage_header(messageHeader);
        KenyaEMRILMessage kenyaEMRILMessage = new KenyaEMRILMessage();
        try {
            ViralLoadMessage viralLoadMessage = ilMessage.extractViralLoadMessage();
            String messageString = mapper.writeValueAsString(viralLoadMessage);
            kenyaEMRILMessage.setHl7Type("ORU^VL");
            kenyaEMRILMessage.setMessage(messageString);
            kenyaEMRILMessage.setDescription("");
            kenyaEMRILMessage.setName("");
            kenyaEMRILMessage.setMessageType(ILMessageType.OUTBOUND.getValue());
            KenyaEMRILMessage savedInstance = saveKenyaEMRILMessage(kenyaEMRILMessage);
            if (savedInstance != null) {
                isSuccessful = true;
            } else {
                isSuccessful = false;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            isSuccessful = false;
        }
        return isSuccessful;
    }

    @Override
    public boolean logORUs(ILMessage ilMessage) {
        boolean isSuccessful;
        //Message Header
        MESSAGE_HEADER messageHeader = MessageHeaderSingleton.getMessageHeaderInstance("ORU^R01");
        ilMessage.setMessage_header(messageHeader);
        KenyaEMRILMessage kenyaEMRILMessage = new KenyaEMRILMessage();
        try {
            ObservationMessage observationMessage = ilMessage.extractORUMessage();
            String messageString = mapper.writeValueAsString(observationMessage);
            kenyaEMRILMessage.setHl7Type("ORU^R01");
            kenyaEMRILMessage.setMessage(messageString);
            kenyaEMRILMessage.setDescription("");
            kenyaEMRILMessage.setName("");
            kenyaEMRILMessage.setMessageType(ILMessageType.OUTBOUND.getValue());
            KenyaEMRILMessage savedInstance = saveKenyaEMRILMessage(kenyaEMRILMessage);
            if (savedInstance != null) {
                isSuccessful = true;
            } else {
                isSuccessful = false;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            isSuccessful = false;
        }
        return isSuccessful;
    }

    private Patient wrapIlPerson(ILMessage ilPerson) {

        Patient patient = new Patient();
        Location defaultLocation = kenyaEmrService.getDefaultLocation();

        allPatientIdentifierTypes = Context.getPatientService().getAllPatientIdentifierTypes();
        for (PatientIdentifierType identiferType : allPatientIdentifierTypes) {
            identifiersMap.put(identiferType.getName(), identiferType);
        }
//        Process Patient Identification details
        PATIENT_IDENTIFICATION patientIdentification = ilPerson.getPatient_identification();


//        Process the general stuff here


//        Set the patient date of birth
        String stringDateOfBirth = patientIdentification.getDate_of_birth();
        if (stringDateOfBirth != null) {
            Date dateOfBirth = null;
            DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            try {
                dateOfBirth = formatter.parse(stringDateOfBirth);
                patient.setBirthdate(dateOfBirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

//        Set the date of birth precision
        String dateOfBirthPrecision = patientIdentification.getDate_of_birth_precision();
        if (dateOfBirthPrecision != null) {
            patient.setBirthdateEstimated(dateOfBirthPrecision.equalsIgnoreCase("estimated") ? true : false);
        }


//        Process the marital status
        String maritalStatus = patientIdentification.getMarital_status();
        PersonAttribute maritalAttribute = new PersonAttribute();
        PersonAttributeType maritalAttributeType = Context.getPersonService().getPersonAttributeTypeByUuid("8d871f2a-c2cc-11de-8d13-0010c6dffd0f");
        if (maritalAttributeType != null) {
            maritalAttribute.setAttributeType(maritalAttributeType);
            maritalAttribute.setValue(maritalStatus);
            patient.addAttribute(maritalAttribute);
        }

//        Process phone number as an attribute
        String phoneNumber = patientIdentification.getPhone_number();
        if (phoneNumber != null) {
            PersonAttribute phoneAttribute = new PersonAttribute();
            PersonAttributeType phoneAttributeType = Context.getPersonService().getPersonAttributeTypeByUuid("b2c38640-2603-4629-aebd-3b54f33f1e3a");
            phoneAttribute.setAttributeType(phoneAttributeType);
            phoneAttribute.setValue(phoneNumber);
            patient.addAttribute(phoneAttribute);
        }


//        Set the gender
        if (patientIdentification.getSex() != null) {
            patient.setGender(patientIdentification.getSex().toUpperCase());
        }

//        Patient name processing
        PATIENT_NAME patientName = patientIdentification.getPatient_name();
//        Set<PersonName> names = new HashSet<>();
        if (patientName != null) {
            SortedSet<PersonName> names = new PresortedSet();
            PersonName personName = new PersonName();
            personName.setGivenName(patientName.getFirst_name() != null ? patientName.getFirst_name() : "");
            personName.setMiddleName(patientName.getMiddle_name() != null ? patientName.getMiddle_name() : "");
            personName.setFamilyName(patientName.getLast_name() != null ? patientName.getLast_name() : "");
            names.add(personName);
            patient.setNames(names);
        }

        SortedSet<PatientIdentifier> patientIdentifiers = new PresortedSet();
//        Process external patient id if it exists
        EXTERNAL_PATIENT_ID externalPatientId = patientIdentification.getExternal_patient_id();
        if (externalPatientId != null) {
            PatientIdentifier patientIdentifier = new PatientIdentifier();
            PatientIdentifierType idType = processIdentifierType(externalPatientId.getIdentifier_type());
            if (idType != null && patientIdentifier != null) {
                patientIdentifier.setIdentifierType(idType);
                patientIdentifier.setIdentifier(externalPatientId.getId());
                patientIdentifiers.add(patientIdentifier);
            }
        }

//        Process internal patient IDs
        List<INTERNAL_PATIENT_ID> internalPatientIds = patientIdentification.getInternal_patient_id();

//        Must set a preferred Identifier
        if (internalPatientIds.size() > 0) {

            for (INTERNAL_PATIENT_ID internalPatientId : internalPatientIds) {
                PatientIdentifier patientIdentifier = new PatientIdentifier();
                PatientIdentifierType idType = processIdentifierType(internalPatientId.getIdentifier_type());
                int missingOpenMRSId = 0;
                if (idType != null) {
                    patientIdentifier.setIdentifierType(idType);

                    if (internalPatientId.getIdentifier_type().equalsIgnoreCase("CCC_NUMBER")) {
                        patientIdentifier.setPreferred(true);
                        String ccc = internalPatientId.getId();
                        ccc = deleteCharacter(ccc, "-");
                        patientIdentifier.setIdentifier(ccc);
                        patientIdentifiers.add(patientIdentifier);
                        continue;
                    }
                } else {
                    continue;
                }
                patientIdentifier.setIdentifier(internalPatientId.getId());
//            internalPatientId.getAssigning_authority();
            }
//            //Generate openmrsID
            PatientIdentifierType openmrsIdType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.OPENMRS_ID);
            PatientIdentifier openmrsId = patient.getPatientIdentifier(openmrsIdType);
            String generated = Context.getService(IdentifierSourceService.class).generateIdentifier(openmrsIdType, "Registration");
            openmrsId = new PatientIdentifier(generated, openmrsIdType, defaultLocation);
            patientIdentifiers.add(openmrsId);
        }

        patient.setIdentifiers(patientIdentifiers);


////        Process mother name
        MOTHER_NAME motherName = patientIdentification.getMother_name();
        if (motherName != null) {
            PersonAttribute motherNameAttribute = new PersonAttribute();
            PersonAttributeType motherNameAttributeType = Context.getPersonService().getPersonAttributeTypeByUuid("8d871d18-c2cc-11de-8d13-0010c6dffd0f");
            if (motherNameAttributeType != null) {
                motherNameAttribute.setAttributeType(motherNameAttributeType);
                patient.addAttribute(motherNameAttribute);

                String motherNameString = motherName.getFirst_name() + motherName.getMiddle_name() + motherName.getLast_name();
                motherNameAttribute.setValue(motherNameString);
            }
        }
//        Process patient address if exists
        PATIENT_ADDRESS patientAddress = patientIdentification.getPatient_address();
        if (patientAddress != null) {
//        Set<PersonAddress> addresses = new HashSet<>();
            SortedSet<PersonAddress> addresses = new PresortedSet();
            PersonAddress personAddress = new PersonAddress();
            personAddress.setPreferred(true);
            personAddress.setAddress6(patientAddress.getPhysical_address() != null ? patientAddress.getPhysical_address().getWard() : "");
            personAddress.setAddress2(patientAddress.getPhysical_address() != null ? patientAddress.getPhysical_address().getNearest_landmark() : "");
            personAddress.setAddress4(patientAddress.getPhysical_address() != null ? patientAddress.getPhysical_address().getSub_county() : "");
            personAddress.setCityVillage(patientAddress.getPhysical_address() != null ? patientAddress.getPhysical_address().getVillage() : "");
//        personAddress.setCountry();
            personAddress.setCountyDistrict(patientAddress.getPhysical_address() != null ? patientAddress.getPhysical_address().getCounty() : "");
            personAddress.setAddress1(patientAddress.getPostal_address() != null ? patientAddress.getPostal_address() : "");
            addresses.add(personAddress);
            patient.setAddresses(addresses);
        }

//        Process Next of kin details
        NEXT_OF_KIN[] next_of_kin = ilPerson.getNext_of_kin();
        if (next_of_kin.length > 0 && next_of_kin != null) {
//            There is a next of kin thus process it
            NEXT_OF_KIN nok = next_of_kin[0];
//            Process the nok name
            if (nok.getNok_name() != null) {
                PersonAttribute nokNameAttribute = new PersonAttribute();
                PersonAttributeType nokNameAttributeType = Context.getPersonService().getPersonAttributeTypeByUuid("830bef6d-b01f-449d-9f8d-ac0fede8dbd3");
                nokNameAttribute.setAttributeType(nokNameAttributeType);
                nokNameAttribute.setValue(nok.getNok_name().toString());
                patient.addAttribute(nokNameAttribute);
            }

//            Process the nok contact
            if (nok.getPhone_number() != null) {
                PersonAttribute nokContactAttribute = new PersonAttribute();
                PersonAttributeType nokContactAttributeType = Context.getPersonService().getPersonAttributeTypeByUuid("342a1d39-c541-4b29-8818-930916f4c2dc");
                nokContactAttribute.setAttributeType(nokContactAttributeType);
                nokContactAttribute.setValue(nok.getPhone_number());
                patient.addAttribute(nokContactAttribute);
            }

//            Process the nok address
            if (nok.getAddress() != null) {
                PersonAttribute nokAddressAttribute = new PersonAttribute();
                PersonAttributeType nokAddressAttributeType = Context.getPersonService().getPersonAttributeTypeByUuid("d0aa9fd1-2ac5-45d8-9c5e-4317c622c8f5");
                nokAddressAttribute.setAttributeType(nokAddressAttributeType);
                nokAddressAttribute.setValue(nok.getAddress());
                patient.addAttribute(nokAddressAttribute);
            }

//            Process the nok relationship
            if (nok.getRelationship() != null) {
                PersonAttribute nokRealtionshipAttribute = new PersonAttribute();
                PersonAttributeType nokRelationshipAttributeType = Context.getPersonService().getPersonAttributeTypeByUuid("d0aa9fd1-2ac5-45d8-9c5e-4317c622c8f5");
                nokRealtionshipAttribute.setAttributeType(nokRelationshipAttributeType);
                nokRealtionshipAttribute.setValue(nok.getRelationship());
                patient.addAttribute(nokRealtionshipAttribute);
            }
        }


        return patient;
    }

    private PatientIdentifierType processIdentifierType(String identifierType) {
        PatientIdentifierType patientIdentifierType = null;
//        TODO - Process the appropriate openmrs identifier types
//        OpenMRS Identification Number
//        Old Identification Number
        if (identifierType != null) {

            switch (identifierType.toUpperCase()) {
                case "CCC_NUMBER": {
                    patientIdentifierType = identifiersMap.get("Unique Patient Number");
                    break;
                }
                case "HTS_NUMBER": {
                    patientIdentifierType = identifiersMap.get("HTS NUMBER");
                    break;
                }
                case "TB_NUMBER": {
                    patientIdentifierType = identifiersMap.get("TB Treatment Number");
                    break;
                }
                case "ANC_NUMBER": {
                    patientIdentifierType = identifiersMap.get("ANC NUMBER");
                    break;
                }
                case "PMTCT_NUMBER": {
                    patientIdentifierType = identifiersMap.get("PMTCT NUMBER");
                    break;
                }
                case "OPD_NUMBER": {
                    patientIdentifierType = identifiersMap.get("OPD NUMBER");
                    break;
                }
                case "NATIONAL_ID": {
                    patientIdentifierType = identifiersMap.get("National ID");
                    break;
                }
                case "NHIF": {
                    patientIdentifierType = identifiersMap.get("NHIF");
                    break;
                }
                case "HDSS_ID": {
                    patientIdentifierType = identifiersMap.get("HDSS ID");
                    break;
                }
                case "GODS_NUMBER": {
                    patientIdentifierType = identifiersMap.get("MPI GODS NUMBER");
                    break;
                }
                default: {
//                nothing to process, set to null
                    patientIdentifierType = null;
                    break;
                }
            }
        }
        return patientIdentifierType;
    }


    //    TODO - review after discussion on the standard format
    private static String deleteCharacter(String string, String index) {
        return string.replaceAll(index, "");
    }

    public String deleteCharacter(String string, Object... arguments) {
        for (Object argument : arguments) {
            string = string.replaceAll(String.valueOf(argument), "");
        }
        return string;
    }

    static String patientTypeConverter(Concept key) {
        Map<Concept, String> patientTypeList = new HashMap<Concept, String>();
        patientTypeList.put(conceptService.getConcept(164144), "New");
        patientTypeList.put(conceptService.getConcept(160563), "Transfer In");
        patientTypeList.put(conceptService.getConcept(164931), "Transit");
        return patientTypeList.get(key);
    }

    static String patientSourceConverter(Concept key) {
        Map<Concept, String> patientSourceList = new HashMap<Concept, String>();
        patientSourceList.put(conceptService.getConcept(159938), "hbtc");
        patientSourceList.put(conceptService.getConcept(160539), "vct_site");
        patientSourceList.put(conceptService.getConcept(159937), "mch");
        patientSourceList.put(conceptService.getConcept(160536), "ipd_adult");
        patientSourceList.put(conceptService.getConcept(160537), "ipd_child");
        patientSourceList.put(conceptService.getConcept(160541), "tb");
        patientSourceList.put(conceptService.getConcept(160542), "opd");
        patientSourceList.put(conceptService.getConcept(162050), "ccc");
        patientSourceList.put(conceptService.getConcept(160551), "self");
        patientSourceList.put(conceptService.getConcept(5622), "other");
        return patientSourceList.get(key);
    }
    String whoAdultConverter (Concept key) {
        Map<Concept, String> whoSategeList = new HashMap<Concept, String>();
        whoSategeList.put(conceptService.getConcept(1204), "1");
        whoSategeList.put(conceptService.getConcept(1205), "2");
        whoSategeList.put(conceptService.getConcept(1206), "3");
        whoSategeList.put(conceptService.getConcept(1207), "4");
        return whoSategeList.get(key);
    }
    String whoPedConverter (Concept key) {
        Map<Concept, String> whoSategeList = new HashMap<Concept, String>();
        whoSategeList.put(conceptService.getConcept(1220), "1");
        whoSategeList.put(conceptService.getConcept(1221), "2");
        whoSategeList.put(conceptService.getConcept(1222), "3");
        whoSategeList.put(conceptService.getConcept(1223), "4");
        return whoSategeList.get(key);
    }
    public Integer calculateAge(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        Calendar cal2 = Calendar.getInstance();
        int i = 0;
        while (cal1.before(cal2)) {
            cal1.add(Calendar.YEAR, 1);
            i += 1;
        }
        return i;
    }

}
