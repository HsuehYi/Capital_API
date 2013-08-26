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
		result[0] = new String(stock.m_caStockNo, "Big5").trim(); // �Ѳ��N��
		result[1] = new String(stock.m_caName, "Big5").trim(); // �Ѳ��W��
		result[2] = (stock.m_nBid / Dot) + ""; // �R��
		result[3] = stock.m_nBc + ""; // �R�q
		result[4] = (stock.m_nAsk / Dot) + ""; // ���
		result[5] = stock.m_nAc + ""; // ��q
		result[6] = (stock.m_nClose / Dot) + ""; // �����
		result[7] = (stock.m_nClose / Dot) - (stock.m_nRef / Dot) + ""; // ���^
		result[8] = ((stock.m_nClose / Dot) / (stock.m_nRef / Dot)) - 1  + ""; // ���^�T
		result[9] = stock.m_nTickQty + ""; // ��q
		result[10] = stock.m_nTQty + ""; // �`�q
		result[11] = (stock.m_nHigh / Dot) + ""; // �̰���
		result[12] = (stock.m_nLow / Dot) + ""; // �̧C��
		result[13] = (stock.m_nRef / Dot) + ""; // �Q��
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
}
