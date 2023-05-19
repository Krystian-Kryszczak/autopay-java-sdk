package krystian.kryszczak.bluemedia.demo.serialization;

import io.micronaut.serde.annotation.SerdeImport;
import krystian.kryszczak.bm.sdk.payway.response.Gateway;
import krystian.kryszczak.bm.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.bm.sdk.regulation.response.Regulation;
import krystian.kryszczak.bm.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.bm.sdk.regulation.response.Regulations;

@SerdeImport(RegulationListResponse.class)
@SerdeImport(Regulations.class)
@SerdeImport(Regulation.class)

@SerdeImport(PaywayListResponse.class)
@SerdeImport(Gateway.class)
final class SerializationIntrospection {}
