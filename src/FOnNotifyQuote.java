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
		result[0] = new String(stock.m_caStockNo, "Big5").trim(); // 布腹
		result[1] = new String(stock.m_caName, "Big5").trim(); // 布嘿
		result[2] = (stock.m_nBid / Dot) + ""; // 禦基
		result[3] = stock.m_nBc + ""; // 禦秖
		result[4] = (stock.m_nAsk / Dot) + ""; // 芥基
		result[5] = stock.m_nAc + ""; // 芥秖
		result[6] = (stock.m_nClose / Dot) + ""; // Θユ基
		result[7] = (stock.m_nClose / Dot) - (stock.m_nRef / Dot) + ""; // 害禴
		result[8] = ((stock.m_nClose / Dot) / (stock.m_nRef / Dot)) - 1  + ""; // 害禴碩
		result[9] = stock.m_nTickQty + ""; // 虫秖
		result[10] = stock.m_nTQty + ""; // 羆秖
		result[11] = (stock.m_nHigh / Dot) + ""; // 程蔼基
		result[12] = (stock.m_nLow / Dot) + ""; // 程基
		result[13] = (stock.m_nRef / Dot) + ""; // 琎Μ
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
}
