/**
 * @author rbkillea
 */
package hanto.studentxjjrbk.common;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.studentxjjrbk.gamma.GammaHantoGame;

/**
 * The Class HantoWalkStrategy.
 */
public class HantoFlyStrategy implements HantoPieceStrategy {
	
	private static PieceInfo game;
	
	private HantoCoordinateImpl source, dest;
	
	/* (non-Javadoc)
	 * @see hanto.studentxjjrbk.common.HantoPieceStrategy#canMove(hanto.common.HantoCoordinate, hanto.common.HantoCoordinate, hanto.common.HantoPlayerColor)
	 */
	@Override
	public boolean canMove(HantoCoordinateImpl source, HantoCoordinateImpl destination, HantoPlayerColor player)
			throws HantoException {
		if(!game.containsPiece(source) || !game.getPiece(source).getColor().equals(player) || game.containsPiece(destination)) {
			return false;
		}
		this.source = new HantoCoordinateImpl(source);
		this.dest = new HantoCoordinateImpl(destination);
		return remainsConnected();
	}
	
	public static void setGame(PieceInfo toSet) {
		game = toSet;
	}
	
	/**
	 * Remains connected.
	 *
	 * @return true, if successful
	 */
	private boolean remainsConnected() {
		for(HantoCoordinate h : HantoAdjacencies.getHantoAdjacencies()) {
			HantoCoordinateImpl tmp = new HantoCoordinateImpl(dest, h);
			if(game.containsPiece(tmp) && !tmp.equals(source)) {
				return !disconnects();
			}
		}
		return false;
	}
	
	/**
	 * Disconnects.
	 *
	 * @return true, if successful
	 */
	private boolean disconnects() {
		// do breadth first search on the grid, ignoring the source coordinates and treating the dest as existing
		List<HantoCoordinateImpl> alreadyVisited = new ArrayList<HantoCoordinateImpl>();
		List<HantoCoordinateImpl> frontier = new ArrayList<HantoCoordinateImpl>();
		frontier.add(source);
		while(!frontier.isEmpty()) {
			HantoCoordinateImpl expanding = frontier.remove(0);
			for(HantoCoordinateImpl h : HantoAdjacencies.getHantoAdjacencies()) {
				HantoCoordinateImpl tmp = new HantoCoordinateImpl(expanding, h);
				if(containsPiece(tmp) && !alreadyVisited.contains(tmp) && !frontier.contains(tmp)) {
					frontier.add(tmp);
				}
			}
			alreadyVisited.add(expanding);
		}
		return alreadyVisited.size() != game.numPieces() + 1;
	}
	
	/**
	 * Contains piece.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	private boolean containsPiece(HantoCoordinateImpl piece) {
		if(piece.equals(source)) {
			return false;
		} else if(piece.equals(dest)) {
			return true;
		} else {
			return game.containsPiece(piece);
		}
	}
}
