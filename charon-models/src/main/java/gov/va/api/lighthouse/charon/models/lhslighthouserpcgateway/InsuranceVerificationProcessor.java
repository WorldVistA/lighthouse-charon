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

  String STREET_ADDRESS_LINE_1 = "#21.01";

  String STREET_ADDRESS_LINE_2 = "#21.02";

  String STREET_ADDRESS_LINE_3 = "#21.03";

  String CITY = "#21.04";

  String STATE = "#21.05";

  String ZIP_CODE = "#21.06";

  String TYPE_OF_PLAN = "#40.09";

  String BANKING_IDENTIFICATION_NUMBER = "#40.1";

  String PROCESSOR_CONTROL_NUMBER_PCN = "#40.11";

  String PATIENT_NAME = "#60.01";

  String WHOSE_INSURANCE = "#60.05";

  String INSUREDS_DOB = "#60.08";

  String PT_RELATIONSIP_HIPAA = "#60.14";

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
