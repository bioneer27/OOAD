package hanto.studentxjjrbk.common;

import hanto.common.HantoException;
import hanto.common.HantoPiece;

public class HantoPieceFactory {
	public static HantoStrategicPiece makePiece(HantoPiece unit) throws HantoException {
		switch(unit.getType()) {
		case SPARROW:
			return new HantoStrategicPiece(unit, new HantoSparrowStrategy());
		case BUTTERFLY:
			// do the stuff regarding making sure that you can create a butterfly here
			return new HantoStrategicPiece(unit, new HantoButterflyStrategy());
	    default:
			throw new HantoException("The requested piece type is unsupported");
		}
	}
}
