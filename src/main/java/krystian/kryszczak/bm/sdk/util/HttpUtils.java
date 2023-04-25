package krystian.kryszczak.bm.sdk.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class HttpUtils {
    public static String convertMapToFormUrlencoded(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return result.toString();
    }
}
