package baron.dino.animalxingqr;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

import org.apache.commons.imaging.color.ColorCieLab;

/**
 * Contains the 15-colour palette used by Animal Crossing
 * For it to be useful, you need to set possibleColours to a mapping between colours and CIELab colours
 * @author maplebaconburgr
 *
 */
public class AnimalXingPalette {

	public static TreeMap<Byte, AnimalXingColour> possibleColours = new TreeMap<Byte, AnimalXingColour>();
	public static boolean initialized = false;

	byte[] _paletteSlots = new byte[15];
	AnimalXingColour[] colours = new AnimalXingColour[15];

	public AnimalXingPalette(byte[] palette) {
		_paletteSlots = Arrays.copyOf(palette, _paletteSlots.length);
		for(int i = 0; i<_paletteSlots.length; i++)
		{
			if(possibleColours != null && possibleColours.containsKey(new Byte(_paletteSlots[i]))) // can identify colour
			{
				colours[i] = possibleColours.get(new Byte(_paletteSlots[i]));
			} else { // mystery colour
				colours[i] = null;
			}
		}
		
	}

	public AnimalXingPalette() {
		// I don't think there needs to be anything here
	}

	/**
	 * Read serialised TreeMap<Byte, AnimalXingColour> from Input Stream
	 * Trying to make all the possible exceptions handled nicely
	 * We don't handle errors, since they are the fault of the caller
	 * @param inputStream
	 * @throws IOException
	 * @throws ClassNotFoundException, ClassCastException 
	 */
	
	public static void initColours(InputStream inputStream) throws IOException, ClassNotFoundException, ClassCastException
	{
		Exception recievedException = null;
		ObjectInputStream ois= null;
		try
		{
			ois = new ObjectInputStream(inputStream);
			Object readObject = ois.readObject();
			if(readObject instanceof TreeMap){
				possibleColours = (TreeMap<Byte, AnimalXingColour>) readObject;
			} else {
				throw new ClassCastException("Expected TreeMap but got " + readObject.getClass().getName());
			}
		} catch (Exception ex) {
			recievedException = ex;
		} finally {
			if(ois != null){
				ois.close();
			}
			
			if(recievedException != null)
			{
				try {
					throw recievedException;
				} catch (Exception e) {}
			}
		}

		initialized = true;
	}
	
	/**
	 * Finds the closest colour in a collection of colours to a given lab value
	 * TODO: This should implement a voronoi diagram.
	 * Right now we are getting O(mn) time where we could hit O(mlogm + nlogm)
	 * @param colours is a set of lab colours
	 * @return
	 */
	public void createFromLabValues(ColorCieLab[] labColours)
	{
		if(possibleColours == null || possibleColours.size() < 1)
		{
			throw new UnsupportedOperationException("Trying to match colours to an empty set of possibilities");
		}
		
		LinkedList<AnimalXingColour> chosenColours = new LinkedList<AnimalXingColour>();
		
		for(ColorCieLab colour : labColours)
		{
			AnimalXingColour bestColour = possibleColours.firstEntry().getValue(); // default to first
			double bestDistance = Double.MAX_VALUE; // Max out best distance
			for(AnimalXingColour testColour : possibleColours.values()){
				double testDistance = testColour.getColour().getDistance(colour);
				if(testDistance <= bestDistance){
					bestDistance = testDistance;
					bestColour = testColour;
				}
			}
			
			chosenColours.add(bestColour);
		}
		
		colours = chosenColours.toArray(colours);
		_paletteSlots = new byte[colours.length];
		for(int i = 0; i<colours.length;i++)
		{
			_paletteSlots[i] = colours[i].getColourCode();
		}

	}

	public byte[] getBytes() {
		return _paletteSlots;
	}

	public AnimalXingColour[] getColours() {
		return colours;
	}

	public AnimalXingColour getColour(int i) {
		return colours[i];
	}
}
