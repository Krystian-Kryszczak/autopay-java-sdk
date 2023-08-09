package krystian.kryszczak.bm.sdk.regulation.response;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import org.jetbrains.annotations.NotNull;

@JsonClassDescription
public record Regulations(@NotNull Regulation[] regulation) {}
