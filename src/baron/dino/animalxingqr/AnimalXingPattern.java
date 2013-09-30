package baron.dino.animalxingqr;

public class AnimalXingPattern {

	private byte[][] colourCodes = new byte[32][32]; // [y][x] NOTE: X MUST BE OF EVEN LENGTH
	
	public AnimalXingPattern(byte[] pattern){
		if(pattern.length < colourCodes.length * colourCodes[0].length / 2){
			throw new UnsupportedOperationException("Pattern does not have enough bytes");
		}
		
		int positionInByteArray = 0;
		
		for(int y = 0; y < colourCodes.length; y++){
			for(int x = 0; x < colourCodes[y].length; x+=2){
				byte b = pattern[positionInByteArray++]; // Get next number to load
				colourCodes[y][x] = (byte) ((b>>4)&0xf); // High 4 bits should be read first
				colourCodes[y][x+1] = (byte)(b&0xf); // Low 4 bits should be read next
			}
		}
	}
	
	public AnimalXingPattern(byte[][] pattern){
		if( pattern == null || pattern[0] == null || pattern[0].length%2 != 0 ){
			throw new UnsupportedOperationException("Invalid pattern (possibly due to odd length of arrays)");
		} else {
			colourCodes = pattern;
		}
	}

	public AnimalXingPattern() {
		// I don't think anything needs to go here
	}

	public byte[] getBytes() {
		byte[] result = new byte[colourCodes.length * colourCodes[0].length / 2]; // Length is half of width * height
		int positionInByteArray = 0;
		
		for(int y = 0; y < colourCodes.length; y++){
			for(int x = 0; x < colourCodes[y].length; x+=2){
				byte highBits = (byte) ((colourCodes[y][x+1]<<4)&0xf0);
				byte lowBits = (byte)(colourCodes[y][x]&0x0f);
				result[positionInByteArray++] = (byte) (highBits | lowBits); // Next position in array is the two elements in x mixed together
			}
		}
		
		return result;
	}
	
	public byte[][] getColourCodes(){
		return colourCodes;
	}

}
