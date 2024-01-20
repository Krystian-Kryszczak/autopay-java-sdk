package krystian.kryszczak.autopay.demo.serialization;

import io.micronaut.serde.annotation.SerdeImport;
import krystian.kryszczak.autopay.sdk.payway.response.Gateway;
import krystian.kryszczak.autopay.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.autopay.sdk.regulation.response.Regulation;
import krystian.kryszczak.autopay.sdk.regulation.response.RegulationListResponse;

@SerdeImport(RegulationListResponse.class)
@SerdeImport(Regulation.class)
@SerdeImport(PaywayListResponse.class)
@SerdeImport(Gateway.class)
final class SerializationIntrospection {}
