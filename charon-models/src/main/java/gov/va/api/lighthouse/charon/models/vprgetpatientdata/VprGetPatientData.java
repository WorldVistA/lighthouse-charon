package gov.va.api.lighthouse.charon.models.vprgetpatientdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.models.TypeSafeRpc;
import gov.va.api.lighthouse.charon.models.TypeSafeRpcRequest;
import gov.va.api.lighthouse.charon.models.TypeSafeRpcResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;

/** Helper class for invoking the VprGetPatientData rpc safely. */
@NoArgsConstructor(staticName = "create")
public class VprGetPatientData
    implements TypeSafeRpc<VprGetPatientData.Request, VprGetPatientData.Response> {
  public static final String RPC_NAME = "VPR GET PATIENT DATA";

  private static final String DEFAULT_RPC_CONTEXT = "VPR APPLICATION PROXY";

  /** All known VprGetPatientData domains. */
  public enum Domains {
    appointments,
    consults,
    demographics,
    documents,
    education,
    exams,
    factors,
    flags,
    immunizations,
    insurance,
    labs,
    meds,
    observations,
    problems,
    procedures,
    reactions,
    reminders,
    skinTests,
    visits,
    vitals
  }

  /**
   * Start and stop are currently supported as fileman date strings. Later a macro will be used to
   * change an ISO 8601 into a fileman date for the target VistA's correct timezone.
   */
  @Builder
  public static class Request implements TypeSafeRpcRequest {
    private Optional<String> context;

    @NonNull private PatientId dfn;

    private Set<Domains> type;

    private Optional<String> start;

    private Optional<String> stop;

    private Optional<String> max;

    private Optional<String> id;

    private Map<String, String> filter;

    /** Build RpcDetails out of the request. */
    @Override
    public RpcDetails asDetails() {
      return RpcDetails.builder()
          .context(context().orElse(DEFAULT_RPC_CONTEXT))
          .name(RPC_NAME)
          .parameters(
              List.of(
                  RpcDetails.Parameter.builder().string(dfn.toString()).build(),
                  RpcDetails.Parameter.builder()
                      .string(
                          type().stream()
                              .filter(Objects::nonNull)
                              .map(Enum::name)
                              .collect(Collectors.joining(";")))
                      .build(),
                  RpcDetails.Parameter.builder().string(start().orElse("")).build(),
                  RpcDetails.Parameter.builder().string(stop().orElse("")).build(),
                  RpcDetails.Parameter.builder().string(max().orElse("")).build(),
                  RpcDetails.Parameter.builder().string(id().orElse("")).build(),
                  RpcDetails.Parameter.builder().namedArray(filter()).build()))
          .build();
    }

    /** Lazy Initializer. */
    @SuppressWarnings("OptionalAssignedToNull")
    Optional<String> context() {
      if (context == null) {
        context = Optional.empty();
      }
      return context;
    }

    /** Lazy getter. */
    Map<String, String> filter() {
      if (filter == null) {
        filter = new HashMap<>();
      }
      return filter;
    }

    /** Lazy getter. */
    @SuppressWarnings("OptionalAssignedToNull")
    Optional<String> id() {
      if (id == null) {
        id = Optional.empty();
      }
      return id;
    }

    /** Lazy getter. */
    @SuppressWarnings("OptionalAssignedToNull")
    Optional<String> max() {
      if (max == null) {
        max = Optional.empty();
      }
      return max;
    }

    /** Lazy getter. */
    @SuppressWarnings("OptionalAssignedToNull")
    Optional<String> start() {
      if (start == null) {
        start = Optional.empty();
      }
      return start;
    }

    /** Lazy getter. */
    @SuppressWarnings("OptionalAssignedToNull")
    Optional<String> stop() {
      if (stop == null) {
        stop = Optional.empty();
      }
      return stop;
    }

    /** Lazy getter. */
    Set<Domains> type() {
      if (type == null) {
        type = new HashSet<>();
      }
      return type;
    }

    /** Patient ID model. */
    @Value
    public static class PatientId {
      String dfn;
      String icn;

      /** You must specify dfn or icn, or both. */
      @Builder
      public PatientId(String dfn, String icn) {
        this.dfn = dfn;
        this.icn = icn;
        if (dfn == null && icn == null) {
          throw new IllegalArgumentException("At least one of DFN or ICN must be specified.");
        }
      }

      public static PatientId forDfn(String dfn) {
        return new PatientId(dfn, null);
      }

      public static PatientId forIcn(String icn) {
        return new PatientId(null, icn);
      }

      @Override
      public String toString() {
        StringBuilder s = new StringBuilder();
        if (dfn != null) {
          s.append(dfn);
        }
        if (icn != null) {
          s.append(';').append(icn);
        }
        return s.toString();
      }
    }
  }

  /** Type Safe Response for VprGetPatientData. */
  @Data
  @Builder
  public static class Response implements TypeSafeRpcResponse {
    private Map<String, Results> resultsByStation;

    /** Results model of the response. */
    @AllArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @JacksonXmlRootElement(localName = "results")
    public static class Results {
      @JacksonXmlProperty(isAttribute = true)
      private String version;

      @JacksonXmlProperty(isAttribute = true)
      private String timeZone;

      @JacksonXmlProperty private Appointments appointments;

      @JacksonXmlProperty private Labs labs;

      @JacksonXmlProperty private Meds meds;

      @JacksonXmlProperty private Vitals vitals;

      @JacksonXmlProperty private Problems problems;

      @JacksonXmlProperty private Visits visits;

      /** Get a stream of appointments for a patient. */
      @JsonIgnore
      public Stream<Appointments.Appointment> appointmentStream() {
        if (appointments() == null) {
          return Stream.empty();
        }
        return appointments().appointmentResults().stream();
      }

      /** Get a stream of labs for a patient. */
      @JsonIgnore
      public Stream<Labs.Lab> labStream() {
        if (labs() == null) {
          return Stream.empty();
        }
        return labs().labResults().stream();
      }

      /** Get a stream of meds for a patient. */
      @JsonIgnore
      public Stream<Meds.Med> medStream() {
        if (meds() == null) {
          return Stream.empty();
        }
        return meds().medResults().stream();
      }

      /** Get a stream of problems for a patient. */
      @JsonIgnore
      public Stream<Problems.Problem> problemStream() {
        if (problems() == null) {
          return Stream.empty();
        }
        return problems().problemResults().stream();
      }

      /** Get a stream of visits for a patient. */
      @JsonIgnore
      public Stream<Visits.Visit> visitsStream() {
        if (visits() == null) {
          return Stream.empty();
        }
        return visits().visitResults().stream();
      }

      /** Get a stream of vitals for a patient. */
      @JsonIgnore
      public Stream<Vitals.Vital> vitalStream() {
        if (vitals() == null) {
          return Stream.empty();
        }
        return vitals().vitalResults().stream();
      }
    }
  }
}
