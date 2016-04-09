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
public class HantoWalkStrategy implements HantoPieceStrategy{
	
	/** The dest y. */
	private int sourceX, sourceY, destX, destY;
	
	/* (non-Javadoc)
	 * @see hanto.studentxjjrbk.common.HantoPieceStrategy#canMove(hanto.common.HantoCoordinate, hanto.common.HantoCoordinate, hanto.common.HantoPlayerColor)
	 */
	@Override
	public boolean canMove(HantoCoordinate source, HantoCoordinate destination, HantoPlayerColor player)
			throws HantoException {
		if(!GammaHantoGame.containsPiece(source) || GammaHantoGame.getPiece(source).getColor() != player || GammaHantoGame.containsPiece(destination)) {
			return false;
		}
		sourceX = source.getX();
		sourceY = source.getY();
		destX = destination.getX();
		destY = destination.getY();
		int deltaX = destX - sourceX;
		int deltaY = destY - sourceY;
		if(deltaX == 1 && (deltaY == 0 || deltaY == -1)) {
			// check to make sure we aren't stranded
			return remainsConnected();
		} else if(deltaX == -1 && deltaY == 0 || deltaY == 1) {
			// check to make sure we aren't stranded
			return remainsConnected();
		} else if(deltaX == 0 && (deltaY <= 1 && deltaY >= -1)) {
			// check to make sure we aren't stranded
			return remainsConnected();
		}
		return false;
	}
	
	/**
	 * Remains connected.
	 *
	 * @return true, if successful
	 */
	private boolean remainsConnected() {
		if((GammaHantoGame.containsPiece(new HantoCoordinateImpl(destX, destY + 1)) && !(destX == sourceX && destY + 1 == sourceY))
		|| (GammaHantoGame.containsPiece(new HantoCoordinateImpl(destX + 1, destY )) && !(destX + 1 == sourceX && destY == sourceY))
		|| (GammaHantoGame.containsPiece(new HantoCoordinateImpl(destX + 1, destY - 1)) && !(destX + 1 == sourceX && destY - 1 == sourceY))
		|| (GammaHantoGame.containsPiece(new HantoCoordinateImpl(destX, destY - 1)) && !(destX == sourceX && destY - 1 == sourceY))
		|| (GammaHantoGame.containsPiece(new HantoCoordinateImpl(destX - 1, destY)) && !(destX - 1 == sourceX && destY == sourceY))
		|| (GammaHantoGame.containsPiece(new HantoCoordinateImpl(destX - 1, destY + 1)) && !(destX - 1 == sourceX && destY + 1 == sourceY))) {
			return !disconnects();
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
		List<HantoCoordinate> alreadyVisited = new ArrayList<HantoCoordinate>();
		List<HantoCoordinate> frontier = new ArrayList<HantoCoordinate>();
		frontier.add(new HantoCoordinateImpl(sourceX, sourceY));
		while(!frontier.isEmpty()) {
			HantoCoordinate expanding = frontier.get(0);
			frontier.remove(0);
			int x = expanding.getX();
			int y = expanding.getY();
			if(containsPiece(x, y + 1) && !alreadyVisited.contains(new HantoCoordinateImpl(x, y + 1))) {
				frontier.add(new HantoCoordinateImpl(x, y + 1));
			}
			if(containsPiece(x + 1, y) && !alreadyVisited.contains(new HantoCoordinateImpl(x + 1, y))) {
				frontier.add(new HantoCoordinateImpl(x + 1, y));
			}
			if(containsPiece(x + 1, y - 1) && !alreadyVisited.contains(new HantoCoordinateImpl(x + 1, y - 1))) {
				frontier.add(new HantoCoordinateImpl(x + 1, y - 1));
			}
			if(containsPiece(x, y - 1) && !alreadyVisited.contains(new HantoCoordinateImpl(x, y - 1))) {
				frontier.add(new HantoCoordinateImpl(x, y - 1));
			}
			if(containsPiece(x - 1, y) && !alreadyVisited.contains(new HantoCoordinateImpl(x - 1, y))) {
				frontier.add(new HantoCoordinateImpl(x - 1, y));
			}
			if(containsPiece(x - 1, y + 1) && !alreadyVisited.contains(new HantoCoordinateImpl(x - 1, y + 1))) {
				frontier.add(new HantoCoordinateImpl(x - 1, y + 1));
			}
			alreadyVisited.add(expanding);
		}
		return alreadyVisited.size() != GammaHantoGame.numPieces() - 1;
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
	private boolean containsPiece(int x, int y) {
		if(x == sourceX && y == sourceY) {
			return false;
		} else if(x == destX && y == destY) {
			return true;
		} else {
			return GammaHantoGame.containsPiece(new HantoCoordinateImpl(x, y));
		}
	}
}
