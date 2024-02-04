package krystian.kryszczak.autopay.sdk.itn.request;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import jakarta.xml.bind.annotation.XmlRootElement;
import krystian.kryszczak.autopay.sdk.itn.Itn;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@JsonClassDescription
@XmlRootElement
public record Transactions(@NotNull Itn[] transaction) implements Serializable {}
