package krystian.kryszczak.autopay.sdk.itn.response;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@JsonClassDescription
@XmlRootElement
@XmlType(propOrder = { "orderID", "confirmation" })
public record TransactionConfirmed(
    @NotNull @JacksonXmlCData String orderID,
    @NotNull @JacksonXmlCData String confirmation
) implements Serializable {}
