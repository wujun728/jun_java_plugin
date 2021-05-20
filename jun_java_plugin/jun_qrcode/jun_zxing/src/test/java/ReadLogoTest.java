import org.junit.Test;

import com.absir.zxing.support.BufferedImageLuminanceSource;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

 
public class ReadLogoTest {

	@Test
	public void test() throws NotFoundException, ChecksumException, FormatException {
		LuminanceSource luminanceSource = new BufferedImageLuminanceSource(null);
		QRCodeReader qrCodeReader = new QRCodeReader();
		qrCodeReader.decode(new BinaryBitmap(new HybridBinarizer(luminanceSource)));
	}
}
