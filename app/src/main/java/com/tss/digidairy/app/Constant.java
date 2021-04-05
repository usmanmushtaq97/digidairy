package com.tss.digidairy.app;

public class Constant {
    public static String MAINBASEUrl = "http://digidairy.brisconsports.com/"; //Admin panel url
    public final static int SPLASH_TIME  = 1500;
    public static String BaseUrl = MAINBASEUrl+"api-reset-php/";
    public static String PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=";
    public static String FAQ_URL = BaseUrl + "get-faqs.php";
    public static String SliderUrl = BaseUrl + "slider-images.php";
    public static final  String CategoryUrl = BaseUrl + "get-categories.php";
    public static String Get_RazorPay_OrderId = BaseUrl + "create-razorpay-order.php";
    public static String SubcategoryUrl = BaseUrl + "get-subcategories-by-category-id.php?id=";
    public static String GET_PRODUCT_BY_SUB_CATE = BaseUrl + "get-products-by-subcategory-id.php?id=";
    public static String FeaturedProductUrl = BaseUrl + "sections.php";
    public static String GET_SECTION_URL = BaseUrl + "sections.php";
    public static String GET_ADDRESS_URL = BaseUrl + "user-addresses.php";
    public static String RegisterUrl = BaseUrl + "user-registration.php";
}
