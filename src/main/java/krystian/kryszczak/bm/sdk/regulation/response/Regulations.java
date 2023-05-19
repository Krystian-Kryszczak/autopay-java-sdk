package krystian.kryszczak.bm.sdk.regulation.response;

import com.fasterxml.jackson.annotation.JsonClassDescription;

@JsonClassDescription
public record Regulations(Regulation[] regulations) {}
