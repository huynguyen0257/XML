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

    //CPN
    public static final String PAGE_CPN = "https://cpn.vn";

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

    //XSLT
    public static final String PA_XSL_PATH = "src/huyng/trAxResource/PALaptop.xsl";
    public static final String KL_XSL_PATH = "src/huyng/trAxResource/KLLaptop.xsl";

    //XSD
    public static final String XSD_PATH = "src/huyng/jaxb";

    //File
    public static final String ERROR_PHUCANH = "src/huyng/crawler/PhucAnh.txt";
    public static final String ERROR_KIMLONG = "src/huyng/crawler/KimLong.txt";
}
