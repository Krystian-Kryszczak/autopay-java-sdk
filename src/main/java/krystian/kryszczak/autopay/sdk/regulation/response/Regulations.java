package krystian.kryszczak.autopay.sdk.regulation.response;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.jetbrains.annotations.NotNull;

@XmlRootElement
@JsonClassDescription
public record Regulations(@NotNull Regulation[] regulation) {}
