package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

public interface InsuranceVerificationProcessor {
  String FILE_NUMBER = "355.33";

  String DATE_ENTERED = "#.01";

  String ENTERED_BY = "#.02";

  String SOURCE_OF_INFORMATION = "#.03";

  String STATUS = "#.04";

  String OVERRIDE_FRESHNESS_FLAG = "#.13";

  String SERVICE_DATE = "#.18";

  String INSURANCE_COMPANY_NAME = "#20.01";

  String PHONE_NUMBER = "#20.02";

  String BILLING_PHONE_NUMBER = "#20.03";

  String PRECERTIFICATION_PHONE_NUMBER = "#20.04";

  String REIMBURSE = "#20.05";

  String STREET_ADDRESS_LINE_1 = "#21.01";

  String STREET_ADDRESS_LINE_2 = "#21.02";

  String STREET_ADDRESS_LINE_3 = "#21.03";

  String CITY = "#21.04";

  String STATE = "#21.05";

  String ZIP_CODE = "#21.06";

  String IS_THIS_A_GROUP_POLICY = "#40.01";

  String UTILIZATION_REVIEW_REQUIRED = "#40.04";

  String PRECERTIFICATION_REQUIRED = "#40.05";

  String AMBULATORY_CARE_CERTIFICATION = "#40.06";

  String EXCLUDE_PREEXISTING_CONDITION = "#40.07";

  String BENEFITS_ASSIGNABLE = "#40.08";

  String TYPE_OF_PLAN = "#40.09";

  String BANKING_IDENTIFICATION_NUMBER = "#40.1";

  String PROCESSOR_CONTROL_NUMBER_PCN = "#40.11";

  String PATIENT_NAME = "#60.01";

  String EFFECTIVE_DATE = "#60.02";

  String EXPIRATION_DATE = "#60.03";

  String WHOSE_INSURANCE = "#60.05";

  String INSUREDS_DOB = "#60.08";

  String INSUREDS_SSN = "#60.09";

  String PRIMARY_CARE_PROVIDER = "#60.1";

  String PRIMARY_PROVIDER_PHONE = "#60.11";

  String COORDINATION_OF_BENEFITS = "#60.12";

  String INSUREDS_SEX = "#60.13";

  String PT_RELATIONSHIP_HIPAA = "#60.14";

  String PHARMACY_RELATIONSHIP_CODE = "#60.15";

  String PHARMACY_PERSON_CODE = "#60.16";

  String PATIENT_ID = "#62.01";

  String SUBSCRIBER_ADDRESS_LINE_1 = "#62.02";

  String SUBSCRIBER_ADDRESS_LINE_2 = "#62.03";

  String SUBSCRIBER_ADDRESS_CITY = "#62.04";

  String SUBSCRIBER_ADDRESS_STATE = "#62.05";

  String SUBSCRIBER_ADDRESS_ZIP = "#62.06";

  String SUBSCRIBER_ADDRESS_COUNTRY = "#62.07";

  String SUBSCRIBER_ADDRESS_SUBDIVISION = "#62.08";

  String SUBSCRIBER_PHONE = "#62.09";

  String INQ_SERVICE_TYPE_CODE_1 = "#80.01";

  String GROUP_NAME = "#90.01";

  String GROUP_NUMBER = "#90.02";

  String SUBSCRIBER_ID = "#90.03";

  String NAME_OF_INSURED = "#91.01";
}
