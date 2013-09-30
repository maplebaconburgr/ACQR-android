package baron.dino.animalxingqr;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class AnimalXingQR
{
	public final Charset _charset = Charset.forName("UTF-16LE");
	
	private String _patternName;                                                             //  42 bytes 0x000
	private byte[] _postPatternNameBytes;                                                    //   2 bytes 0x02a
	private String _personName;                                                              //  20 bytes 0x02c
	private byte[] _postPersonNameBytes;                                                     //   2 bytes 0x040
	private String _townName;                                                                //  18 bytes 0x042
	private final byte[] _postTownNameConstant = {0x1, 0x0};                                 //   2 bytes 0x054
	private byte[] _prePaletteBytes;                                                         //   2 bytes 0x056
	private AnimalXingPalette _palette;                                                      //  15 bytes 0x058
	private byte _postPaletteByte;                                                           //   1 byte  0x067
	private byte _prePatternByte;                                                            //   1 byte  0x068
	private final byte[] _prePatternConstant = {0x9, 0x0, 0x0};                              //   3 bytes 0x069
	private AnimalXingPattern _pattern;                                                      // 512 bytes 0x06c
	//final private byte[] _postPatternConstant = {0xe, (byte) 0xc1, 0x1e, (byte) 0xc1, 0x10}; //   5 bytes 0x26c
																			                 // ---------------
                                                                                             // 625-5=620 bytes 0x271
	public AnimalXingQR()  
	{
		// Create objects needed
		_palette = new AnimalXingPalette();
		_pattern = new AnimalXingPattern();
	}
	
	public AnimalXingQR(byte[] bytes)
	{
		this();
		setPatternName(Arrays.copyOfRange(bytes, 0x000, 0x02a));
		setPostPatternNameBytes(Arrays.copyOfRange(bytes, 0x02a, 0x02c));
		setPersonName(Arrays.copyOfRange(bytes, 0x02c, 0x040));
		setPostPersonNameBytes(Arrays.copyOfRange(bytes, 0x040, 0x042));
		setTownName(Arrays.copyOfRange(bytes, 0x042, 0x054));
		// Do something with _postTownNameConstant?
		setPrePaletteBytes(Arrays.copyOfRange(bytes, 0x056, 0x058));
		setPalette(Arrays.copyOfRange(bytes, 0x058, 0x067));
		setPostPaletteByte(bytes[0x067]);
		setPrePatternByte(bytes[0x068]);
		// Do something with _prePatternConstant?
		setPattern(Arrays.copyOfRange(bytes, 0x06c, 0x26c));
		// Do something with _postPatternConstant?
	}
	
	public byte[] getBytes()
	{
		byte[] bytes = ArrayUtils.EMPTY_BYTE_ARRAY;
		bytes = ArrayUtils.addAll(bytes, getPatternNameBytes());
		bytes = ArrayUtils.addAll(bytes, getPostPatternNameBytes());
		bytes = ArrayUtils.addAll(bytes, getPersonNameBytes());
		bytes = ArrayUtils.addAll(bytes, getPostPersonNameBytes());
		bytes = ArrayUtils.addAll(bytes, getTownNameBytes());
		bytes = ArrayUtils.addAll(bytes, getPostTownNameConstant());
		bytes = ArrayUtils.addAll(bytes, getPrePaletteBytes());
		bytes = ArrayUtils.addAll(bytes, getPaletteBytes());
		bytes = ArrayUtils.addAll(bytes, getPostPaletteBytes());
		bytes = ArrayUtils.addAll(bytes, getPrePatternBytes());
		bytes = ArrayUtils.addAll(bytes, getPrePatternConstant());
		bytes = ArrayUtils.addAll(bytes, getPatternBytes());
		// unused bytes = ArrayUtils.addAll(bytes, getPostPatternConstant());
		return bytes;
	}
	
	public void setPatternName(byte[] patternName) {
		this._patternName = new String(patternName, _charset);
	}
	
	public void setPatternName(String patternName) {
		this._patternName = patternName;
	}
	
	public String getPatternName() {
		return _patternName;
	}
	
	public byte[] getPatternNameBytes() {
		byte[] patternNameBytes = this._patternName.getBytes(_charset);
		patternNameBytes = Arrays.copyOf(patternNameBytes, 42); // Extend or truncate to needed length
		return patternNameBytes;
	}
	
	public void setPostPatternNameBytes(byte[] postPatternNameBytes)
	{
		this._postPatternNameBytes = postPatternNameBytes;
	}
	
	public byte[] getPostPatternNameBytes() {
		return _postPatternNameBytes;
	}
	
	public void setPersonName(byte[] personName) {
		this._personName = new String(personName, _charset);
	}
	
	public void setPersonName(String personName) {
		this._personName = personName;
	}
	
	public String getPersonName()
	{
		return _personName;
	}
	
	public byte[] getPersonNameBytes()
	{
		byte[] personNameBytes = this._personName.getBytes(_charset);
		personNameBytes = Arrays.copyOf(personNameBytes, 20); // Extend or truncate to needed length
		return personNameBytes;
	}
	
	public void setPostPersonNameBytes(byte[] postPersonNameBytes) {
		this._postPersonNameBytes = postPersonNameBytes;
	}
	
	public byte[] getPostPersonNameBytes() {
		return _postPersonNameBytes;
	}
	
	public void setTownName(byte[] townName) {
		this._townName = new String(townName, _charset);
	}
	
	public void setTownName(String townName) {
		this._townName = townName;
	}
	
	public String getTownName() {
		return _townName;
	}
	
	public byte[] getTownNameBytes() {
		byte[] townNameBytes = this._townName.getBytes(_charset);
		townNameBytes = Arrays.copyOf(townNameBytes, 18); // Extend or truncate to needed length
		return townNameBytes;
	}
	
	public byte[] getPostTownNameConstant() {
		return _postTownNameConstant;
	}
	
	public void setPrePaletteBytes(byte[] prePaletteBytes) {
		this._prePaletteBytes = prePaletteBytes;
	}
	
	public byte[] getPrePaletteBytes() {
		return _prePaletteBytes;
	}
	
	public void setPalette(byte[] palette) {
		this._palette = new AnimalXingPalette(palette);
	}
	
	public void setPalette(AnimalXingPalette palette) {
		this._palette = palette;
	}
	
	public AnimalXingPalette getPalette() {
		return _palette;
	}
	
	public byte[] getPaletteBytes() {
		return _palette.getBytes();
	}
	
	public byte getPostPaletteByte() {
		return _postPaletteByte;
	}
	
	public byte[] getPostPaletteBytes() {
		byte[] bytes = {_postPaletteByte};
		return bytes;
	}

	public void setPostPaletteByte(byte postPaletteByte) {
		this._postPaletteByte = postPaletteByte;
	}

	public byte getPrePatternByte() {
		return _prePatternByte;
	}
	
	public byte[] getPrePatternBytes() {
		byte[] bytes = {_prePatternByte};
		return bytes;
	}

	public void setPrePatternByte(byte prePatternByte) {
		this._prePatternByte = prePatternByte;
	}

	public byte[] getPrePatternConstant() {
		return _prePatternConstant;
	}

	public AnimalXingPattern getPattern() {
		return _pattern;
	}
	
	public byte[] getPatternBytes() {
		return _pattern.getBytes();
	}

	public void setPattern(byte[] pattern){
		this._pattern = new AnimalXingPattern(pattern);
	}
	
	public void setPattern(AnimalXingPattern pattern) {
		this._pattern = pattern;
	}

//	public byte[] getPostPatternConstant() {
//		return _postPatternConstant;
//	}
	
}
