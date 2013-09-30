package baron.dino.animalxingqr;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * This class houses bytes whose meaning I do know know yet.
 * All of these bytes seem to be constant per character,
 * so the current method is to merely open an old QR
 * @author maplebaconburgr
 *
 */
public class AnimalXingDefaults {
	public static ErrorCorrectionLevel defautErrorCorrectionLevel = ErrorCorrectionLevel.M;
	public static int defaultQRWidth = 93;
	public static int defaultQRHeight = 93;
	
	private static AnimalXingDefaults instance = null;
	
	private byte[] _postPatternNameBytes;  //   2 bytes 0x02a
	private byte[] _postPersonNameBytes;   //   2 bytes 0x040
	private byte[] _prePaletteBytes;       //   2 bytes 0x056
	private byte _postPaletteByte;         //   1 byte  0x067
	private byte _prePatternByte;          //   1 byte  0x068
	
	private String _personName;            //  20 bytes 0x02c
	private String _townName;              //  18 bytes 0x042

	public static void setInstance(AnimalXingDefaults instance)
	{
		AnimalXingDefaults.instance = instance;
	}
	
	public static AnimalXingDefaults getInstance()
	{
		return instance;
	}
	
	public AnimalXingDefaults(AnimalXingQR qr) {
		this._postPatternNameBytes = qr.getPostPatternNameBytes();
		this._postPersonNameBytes = qr.getPostPersonNameBytes();
		this._prePaletteBytes = qr.getPrePaletteBytes();
		this._postPaletteByte = qr.getPostPaletteByte();
		this._prePatternByte = qr.getPrePatternByte();
		
		this._personName = qr.getPersonName();
		this._townName = qr.getTownName();
	}
	
	public void loadIntoAnimalXingQR(AnimalXingQR qr) {
		qr.setPostPatternNameBytes(_postPatternNameBytes);
		qr.setPostPersonNameBytes(_postPersonNameBytes);
		qr.setPrePaletteBytes(_prePaletteBytes);
		qr.setPostPaletteByte(_postPaletteByte);
		qr.setPrePatternByte(_prePatternByte);
		
		qr.setPersonName(_personName);
		qr.setTownName(_townName);
	}

}
