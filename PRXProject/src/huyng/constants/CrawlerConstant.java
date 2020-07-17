package huyng.constants;

public class CrawlerConstant {
    //PHU CANH
    public static final String PHU_CANH_DOMAIN = "https://www.phucanh.vn";

    public static final String PHU_CANH_CATEGORIES_START_SIGNAL = "<div class=\"box-cat\">";
    public static final String PHU_CANH_CATEGORIES_TAG = "div";

    public static final String PHU_CANH_PAGESIZE_START_SIGNAL = "<div class=\"paging\">";
    public static final String PHU_CANH_PAGESIZE_TAG = "div";

    public static final String PHU_CANH_EACH_PAGE_START_SIGNAL = "<div class=\"category-pro-list\">";
    public static final String PHU_CANH_EACH_PAGE_TAG = "div";

    public static final String PHU_CANH_DETAIL_START_SIGNAL = "<div class=\"tbl-technical nd\"";
    public static final String PHU_CANH_DETAIL_TAG = "div";

    //KimLongCenter
    public static final String KIM_LONG_DOMAIN = "https://kimlongcenter.com";
    public static final String KIM_LONG_BRAND_START_SIGNAL = "<ul id=\"cate_hori\" class=\"show-menu\">";
    public static final String KIM_LONG_BRAND_TAG = "ul";

    public static final String KIM_LONG_PAGE_SIZE_START_SIGNAL = "<ul class=\"pagination\"";
    public static final String KIM_LONG_PAGE_SIZE_TAG = "ul";

    public static final String KIM_LONG_EACH_PAGE_START_SIGNAL = "<div class=\"list-product row\">";
    public static final String KIM_LONG_EACH_PAGE_TAG = "div";

    public static final String KIM_LONG_DETAIL_START_SIGNAL = "<table class=\"tbl-info-product\">";
    public static final String KIM_LONG_DETAIL_TAG = "table";

    public static final String DUPLICATE_SRC_KL = "src=\"data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==\"";

    //CPU
    public static final String INTEL_DOMAIN = "https://www.intel.com";
    public static final String INTEL_ALL_PRODUCT = "https://www.intel.com/content/www/us/en/products/processors/core/view-all.html";

    public static final String INTEL_PAGAING_START_SIGNAL = "<span class=\"paging-info\">";
    public static final String INTEL_PAGAING_TAG = "span";

    public static final String INTEL_EACH_PAGE_START_SIGNAL = "<div class=\"container-wrap card-listings\">";
    public static final String INTEL_EACH_PAGE_TAG = "div";

    public static final String INTEL_PRODUCT_DETAIL_START_SIGNAL = "<h3 class=\"tech-heading\">Performance</h3>";
    public static final String INTEL_PRODUCT_DETAIL_END_SIGNAL = "<div class=\"tech-section\">";

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
}
