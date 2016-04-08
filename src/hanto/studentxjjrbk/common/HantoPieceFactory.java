package hanto.studentxjjrbk.common;

import hanto.common.HantoException;
import hanto.common.HantoPiece;

public class HantoPieceFactory {
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
