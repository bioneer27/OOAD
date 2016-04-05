package hanto.studentxjjrbk.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class HantoStrategicPiece extends HantoPieceImpl {
	private HantoPieceStrategy strategy;
	public HantoStrategicPiece(HantoPlayerColor color, HantoPieceType type, HantoPieceStrategy strategy) {
		super(color, type);
		this.strategy = strategy;
	}
	
	public HantoStrategicPiece(HantoPiece impl, HantoPieceStrategy strategy) {
		super(impl.getColor(), impl.getType());
		this.strategy = strategy;
	}

	void move(HantoCoordinate source, HantoCoordinate destination) throws HantoException {
		strategy.move(source, destination);
	}
}
