package huyng.constants;

public class CrawlerConstant {
    public static final int THREAD_WAIT_CHECK = 15*1000;
    public static final int THREAD_WAIT_CALL = 5*1000;
    //XSLT
    public static final String PA_XSL_PATH = "WEB-INF/trAxResource/PALaptop.xsl";
    public static final String KL_XSL_PATH = "WEB-INF/trAxResource/KLLaptop.xsl";
    public static final String INTEL_XSL_PATH = "WEB-INF/trAxResource/IntelProcessor.xsl";
    public static final String AMD_XSL_PATH = "WEB-INF/trAxResource/AMDProcessor.xsl";

    //XSD
    public static final String XSD_PATH = "WEB-INF/jaxbResource";

    //File
    public static final String ERROR_PHUCANH = "WEB-INF/errorFile/PhucAnhError.txt";
    public static final String ERROR_KIMLONG = "WEB-INF/errorFile/KimLongError.txt";
    public static final String ERROR_INTEL = "WEB-INF/errorFile/IntelError.txt";

    //XMLConstant
    public static final String KL_PAGE_CONSTANT_XML = "WEB-INF/xmlConstant/KLCrawlerConstant.xml";
    public static final String PA_PAGE_CONSTANT_XML = "WEB-INF/xmlConstant/PACrawlerConstant.xml";
    public static final String INTEL_PAGE_CONSTANT_XML = "WEB-INF/xmlConstant/IntelCrawlerConstant.xml";
}
