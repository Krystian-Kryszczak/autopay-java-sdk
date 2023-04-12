package krystian.kryszczak.bm.sdk.common;

public final class Pattern {
    public static final String PATTERN_PAYWAY = "@<!-- PAYWAY FORM BEGIN -->(.*)<!-- PAYWAY FORM END -->@Usi";
    public static final String PATTERN_XML = "@xml version=\"1.0\" encoding=\"UTF-8\"@";
    public static final String PATTERN_XML_ERROR = "@<error>(.*)</error>@Usi";
    public static final String PATTERN_GENERAL_ERROR = "/error(.*)/si";
}
