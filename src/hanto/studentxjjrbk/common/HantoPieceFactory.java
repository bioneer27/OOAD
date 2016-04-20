/**
 * @author rbkillea
 */
package hanto.studentxjjrbk.common;

import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;

/**
 * A factory for creating HantoPiece objects.
 */
public class HantoPieceFactory {
	private int blueSparrows, redSparrows, blueButterflies, redButterflies, blueCrabs, redCrabs;
	private HantoGameID gid;


	public HantoPieceFactory(HantoGameID gid, int sparrows, int butterflies, int crabs) {
		blueSparrows = sparrows;
		redSparrows = sparrows;
		blueButterflies = butterflies;
		redButterflies = butterflies;
		blueCrabs = crabs;
		redCrabs = crabs;
		this.gid = gid;
	}

	/**
	 * Make piece.
	 *
	 * @param unit
	 *            the unit
	 * @return the hanto piece impl or null if it can't be done
	 * @throws HantoException
	 *             the hanto exception
	 */
	public HantoPieceImpl makePiece(HantoPiece unit, boolean creation) throws HantoException {
		if(!creation) {
			if((unit.getColor().equals(HantoPlayerColor.RED) && redButterflies != 0) ||
					(unit.getColor().equals(HantoPlayerColor.BLUE) && blueButterflies != 0)) {
				return null;
			}
		}
		switch(unit.getType()) {
		case SPARROW:
			if(creation) {
				if(unit.getColor().equals(HantoPlayerColor.RED) && redSparrows == 0) {
					return null;
				} else if(unit.getColor().equals(HantoPlayerColor.BLUE) && blueSparrows == 0) {
					return null;
				}
				if(unit.getColor().equals(HantoPlayerColor.RED)) {
					redSparrows--;
				} else {
					blueSparrows--;
				}
			}
			if(gid == HantoGameID.GAMMA_HANTO) {
				return new HantoPieceImpl(unit, new HantoWalkStrategy(1));
			} else if(gid == HantoGameID.DELTA_HANTO) {
				return new HantoPieceImpl(unit, new HantoFlyStrategy());
			}
		case BUTTERFLY:
			if(creation) {
				if(unit.getColor().equals(HantoPlayerColor.RED) && redButterflies == 0) {
					return null;
				} else if(unit.getColor().equals(HantoPlayerColor.BLUE) && blueButterflies == 0) {
					return null;
				}
				if(unit.getColor().equals(HantoPlayerColor.RED)) {
					redButterflies--;
				} else if(unit.getColor().equals(HantoPlayerColor.BLUE)) {
					blueButterflies--;
				}
			}
			return new HantoPieceImpl(unit, new HantoWalkStrategy(1));
		case CRAB:
			if(creation) {
				if(unit.getColor().equals(HantoPlayerColor.RED) && redCrabs == 0) {
					return null;
				} else if(unit.getColor().equals(HantoPlayerColor.BLUE) && blueCrabs == 0) {
					return null;
				}
				if(unit.getColor().equals(HantoPlayerColor.RED)) {
					redCrabs--;
				} else if(unit.getColor().equals(HantoPlayerColor.BLUE)) {
					blueCrabs--;
				}
			}
			return new HantoPieceImpl(unit, new HantoWalkStrategy(3));
		default:
			throw new HantoException("The requested piece type is unsupported");
		}
	}
}
