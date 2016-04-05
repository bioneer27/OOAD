package hanto.studentxjjrbk.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;

public interface HantoPieceStrategy {
	void move(HantoCoordinate source, HantoCoordinate destination) throws HantoException;
}
