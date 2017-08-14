package com.btcdata.constants;

public class URLConstant {

	/**  okex合约行情  */
	public static final String OKEX_URL = "https://www.okex.com/api/v1/future_ticker.do?symbol={0}&contract_type={1}";
	
	/**  btc  */
	public static final String OKEX_SYMBOL_BTC = "btc_usd";
	/**  ltc  */
	public static final String OKEX_SYMBOL_LTC = "ltc_usd";
	/**  this_week:当周   */
	public static final String OKEX_TYPE_THIS_WEEK = "this_week";
	/**  next_week:下周   */
	public static final String OKEX_TYPE_NEXT_WEEK = "next_week";
	/**  quarter:季度   */
	public static final String OKEX_TYPE_QUARTER = "quarter";
	
	
	/////////// ---------------------------------------------------------------  //////////////////////
	//https://www.bitmex.com/api/v1/instrument?filter=%7B%22symbol%22%3A%20%22XBTUSD%22%7D&count=1&reverse=false
	//https://www.bitmex.com/api/v1/instrument?filter={"symbol": "XBTUSD"}&count=1&reverse=false
	/** bitmex 合约信息  已urlencode编码 filter参数值 */
	public static final String BITMEX_URL = "https://www.bitmex.com/api/v1/instrument?filter=%7B%22symbol%22%3A%20%22{0}%22%7D&count=1&reverse=false";
	
	/**  XBTUSD  */
	public static final String BITMEX_SYMBOL_XBTUSD = "XBTUSD";
	/**  XBTU17  */
	public static final String BITMEX_SYMBOL_XBTU17 = "XBTU17";
	/**  LTCU17  */
	public static final String BITMEX_SYMBOL_LTC = "LTCU17";
	
	
}
