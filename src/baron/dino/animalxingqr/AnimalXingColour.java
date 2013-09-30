package baron.dino.animalxingqr;

import java.io.Serializable;

import org.apache.commons.imaging.color.ColorCieLab;
import org.apache.commons.imaging.color.ColorConversions;
import org.apache.commons.imaging.color.ColorXyz;

/**
 * This class simply holds a possible colour in Animal Crossing
 * @author maplebaconburgr
 *
 */
public class AnimalXingColour implements Serializable{

	private static final long serialVersionUID = -6476897893247697404L;

	private byte colourCode;
	private ColorCieLab colour;
	
	/**
	 * Returns rgb value of colour
	 * Note that this does a lot of computations
	 * @return
	 */
	public int getRgbValue(){
		ColorXyz xyz = ColorConversions.convertCIELabtoXYZ(colour);
		return ColorConversions.convertXYZtoRGB(xyz);
	}
	
	public AnimalXingColour(byte code, int rgb){
		this(code, ColorConversions.convertXYZtoCIELab(ColorConversions.convertRGBtoXYZ(rgb)));
	}
	
	public AnimalXingColour(byte code, double l, double a, double b){
		this(code, new ColorCieLab(l, a, b));
	}
	
	public AnimalXingColour(byte code, ColorCieLab labColour) {
		this.colourCode = code;
		this.colour = labColour;
	}

	public ColorCieLab getColour() {
		return colour;
	}

	public void setColour(ColorCieLab colour) {
		this.colour = colour;
	}

	public byte getColourCode() {
		return colourCode;
	}

	public void setColourCode(byte colourCode) {
		this.colourCode = colourCode;
	}
	

}
