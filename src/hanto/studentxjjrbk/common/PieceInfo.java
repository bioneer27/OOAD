package hanto.studentxjjrbk.common;

import hanto.common.HantoCoordinate;

public interface PieceInfo {

	boolean containsPiece(HantoCoordinate hantoCoordinate);

	int numPieces();

	HantoPieceImpl getPiece(HantoCoordinate source);

}
