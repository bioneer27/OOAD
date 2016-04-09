/**
 * @author rbkillea
 */
package hanto.studentxjjrbk.common;

import hanto.common.HantoException;
import hanto.common.HantoPiece;

/**
 * A factory for creating HantoPiece objects.
 */
public class HantoPieceFactory {
	
	/**
	 * Make piece.
	 *
	 * @param unit
	 *            the unit
	 * @return the hanto piece impl
	 * @throws HantoException
	 *             the hanto exception
	 */
	public HantoPieceImpl makePiece(HantoPiece unit) throws HantoException {
		switch(unit.getType()) {
		case SPARROW:
			return new HantoPieceImpl(unit, new HantoSparrowStrategy());
		case BUTTERFLY:
			// do the stuff regarding making sure that you can create a butterfly here
			return new HantoPieceImpl(unit, new HantoButterflyStrategy());
	    default:
			throw new HantoException("The requested piece type is unsupported");
		}
	}
}
