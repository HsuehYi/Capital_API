import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;


public class FOnNotifyQuote implements StdCallCallback{
	final SKQuoteLib skquotelib = (SKQuoteLib) Native.loadLibrary(
			"SKQuoteLib", SKQuoteLib.class);
	public void callback(short Market, short Index) {
		int Status;
		SKQuoteLib.Stock stock = new SKQuoteLib.Stock();
		Status = skquotelib.SKQuoteLib_GetStockByIndex(Market, Index, stock);
		if (Status == 0) {
			String[] str = translation(stock);
			System.out.println(Arrays.toString(str));
		}
	}
	
	private String[] translation(SKQuoteLib.Stock stock) {
		double Dot = Math.pow(10, stock.m_sDecimal);
		String[] result = new String[14];
		try {
		result[0] = new String(stock.m_caStockNo, "Big5").trim(); // 股票代號
		result[1] = new String(stock.m_caName, "Big5").trim(); // 股票名稱
		result[2] = (stock.m_nBid / Dot) + ""; // 買價
		result[3] = stock.m_nBc + ""; // 買量
		result[4] = (stock.m_nAsk / Dot) + ""; // 賣價
		result[5] = stock.m_nAc + ""; // 賣量
		result[6] = (stock.m_nClose / Dot) + ""; // 成交價
		result[7] = (stock.m_nClose / Dot) - (stock.m_nRef / Dot) + ""; // 漲跌
		result[8] = ((stock.m_nClose / Dot) / (stock.m_nRef / Dot)) - 1  + ""; // 漲跌幅
		result[9] = stock.m_nTickQty + ""; // 單量
		result[10] = stock.m_nTQty + ""; // 總量
		result[11] = (stock.m_nHigh / Dot) + ""; // 最高價
		result[12] = (stock.m_nLow / Dot) + ""; // 最低價
		result[13] = (stock.m_nRef / Dot) + ""; // 昨收
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
}
