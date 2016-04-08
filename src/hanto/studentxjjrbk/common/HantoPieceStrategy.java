package hanto.studentxjjrbk.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;

public interface HantoPieceStrategy {
	boolean canMove(HantoCoordinate source, HantoCoordinate destination, HantoPlayerColor player) throws HantoException;
}
