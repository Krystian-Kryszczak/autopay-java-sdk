package krystian.kryszczak.autopay.sdk.regulation.response;

import jakarta.xml.bind.annotation.XmlRootElement;
import org.jetbrains.annotations.NotNull;

@XmlRootElement
public record Regulations(@NotNull Regulation[] regulation) {}
