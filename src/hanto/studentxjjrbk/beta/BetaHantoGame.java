/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package hanto.studentxjjrbk.beta;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanto.common.*;
import hanto.studentxjjrbk.common.HantoCoordinateImpl;
import hanto.studentxjjrbk.common.HantoPieceImpl;

/**
 * Implementation of Beta Hanto
 * 
 * @version Mar 26, 2016
 */
public class BetaHantoGame implements HantoGame {
	private HantoCoordinate blueButterflyHex, redButterflyHex;
	private List<HantoCoordinate> redPieces, bluePieces;
	private boolean firstMove, gameOver;
	private Map<HantoCoordinate, HantoPiece> grid;
	private int gameTurns;

	/**
	 * Instantiates a new beta hanto game.
	 */
	public BetaHantoGame() {
		firstMove = true;
		gameOver = false;
		grid = new HashMap<HantoCoordinate, HantoPiece>(100);
		gameTurns = 0;
		redPieces = new ArrayList<HantoCoordinate>();
		bluePieces = new ArrayList<HantoCoordinate>();
	}

	/**
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType,
	 * hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate source, HantoCoordinate destination)
			throws HantoException {
		if(gameOver) {
			throw new HantoException("You cannot move after the game is finished");
		}
		if(source != null) {
			throw new HantoException("You cannot move your pieces across the board");
		}
		final HantoPlayerColor hp = ((gameTurns % 2) == 0) ? BLUE : RED;

		final HantoCoordinate to = new HantoCoordinateImpl(destination);
		if(firstMove) {
			if(to.getX() != 0 || to.getY() != 0) {
				throw new HantoException("Blue did not move to origin");
			}
			firstMove = false;
		} else {
			if(!hexIsValid(to)) {
				throw new HantoException("You cannot place a piece in that hex");
			}
		}
		HantoPiece piece = new HantoPieceImpl(hp, pieceType);
		grid.put(to, piece);
		if(hp.equals(BLUE)) {
			bluePieces.add(destination);
		} else {
			redPieces.add(destination);
		}
		gameTurns++;
		if(gameTurns >= 12) {
			gameOver = true;
		}
		if(hp.equals(BLUE) && pieceType.equals(BUTTERFLY)) {
			blueButterflyHex = to;
		} else if(pieceType.equals(BUTTERFLY)) {
			redButterflyHex = to;
		}
		if(gameWonRed() && gameWonBlue()) {
			gameOver = true;
			return DRAW;
		}
		if(gameWonRed()) {
			gameOver = true;
			return RED_WINS;
		}
		if(gameWonBlue()) {
			gameOver = true;
			return BLUE_WINS;
		}

		return OK;
	}

	/**
	 * Returns the piece at a particular HantoCoordinate
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		HantoPiece piece = grid.get(where);
		return piece;
	}

	/**
	 * Returns a string that displays a (grid) representation of the current game state
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard() {
		// if the board's empty, return a placeholder
		if(redPieces.size() + bluePieces.size() == 0) {
			return "The board is empty\n";
		}
		// first get the min's and max's
		int minX = 0, maxX = 0, minY = 0, maxY = 0;
		for(HantoCoordinate h : redPieces) {
			minX = Math.min(minX, h.getX());
			minY = Math.min(minY, h.getY());
			maxX = Math.max(maxX, h.getX());
			maxY = Math.max(maxY, h.getY());
		}
		for(HantoCoordinate h : bluePieces) {
			minX = Math.min(minX, h.getX());
			minY = Math.min(minY, h.getY());
			maxX = Math.max(maxX, h.getX());
			maxY = Math.max(maxY, h.getY());
		}
		String rtn = "";
		for(int ix = minX; ix <= maxX; ix++) {
			rtn += "|";
			for(int iy = minY; iy < maxY; iy++) {
				HantoCoordinate coord = new HantoCoordinateImpl(ix, iy);
				if(grid.containsKey(coord)) {
					if(redPieces.contains(coord)) {
						rtn += " R " + grid.get(coord).getType().getSymbol() + " |";
					} else {
						rtn += " B " + grid.get(coord).getType().getSymbol() + " |";
					}
				} else {
					rtn += "     |";
				}
			}
			rtn += "\n";
		}
		return rtn;
	}

	private boolean hexIsValid(HantoCoordinate coordinate) {
		int x = coordinate.getX();
		int y = coordinate.getY();
		return (grid.containsKey(new HantoCoordinateImpl(x, y + 1))
				|| grid.containsKey(new HantoCoordinateImpl(x + 1, y + 1))
				|| grid.containsKey(new HantoCoordinateImpl(x + 1, y - 1))
				|| grid.containsKey(new HantoCoordinateImpl(x, y - 1))
				|| grid.containsKey(new HantoCoordinateImpl(x - 1, y))
				|| grid.containsKey(new HantoCoordinateImpl(x - 1, y + 1)));

	}

	/**
	 * This method takes the position of the blue butterfly which is held as an
	 * attribute of the BetaHanto class and checks the adjacency to see if the
	 * butterfly is surrounded and if the game should be considered a win for
	 * Red. This only determines if blue is surrounded, it can not tell you if
	 * it is a draw or a loss for red
	 * 
	 * @return boolean T/F on whether the game has been won or not
	 */
	private boolean gameWonRed() {

		if(blueButterflyHex == null) {
			return false;
		}

		int x = blueButterflyHex.getX();
		int y = blueButterflyHex.getY();
		return (grid.containsKey(new HantoCoordinateImpl(x, y + 1))
				&& grid.containsKey(new HantoCoordinateImpl(x + 1, y + 1))
				&& grid.containsKey(new HantoCoordinateImpl(x + 1, y - 1))
				&& grid.containsKey(new HantoCoordinateImpl(x, y - 1))
				&& grid.containsKey(new HantoCoordinateImpl(x - 1, y))
				&& grid.containsKey(new HantoCoordinateImpl(x - 1, y + 1)));

	}

	private boolean gameWonBlue() {

		if(redButterflyHex == null) {
			return false;
		}

		int x = redButterflyHex.getX();
		int y = redButterflyHex.getY();
		return (grid.containsKey(new HantoCoordinateImpl(x, y + 1))
				&& grid.containsKey(new HantoCoordinateImpl(x + 1, y))
				&& grid.containsKey(new HantoCoordinateImpl(x + 1, y - 1))
				&& grid.containsKey(new HantoCoordinateImpl(x, y - 1))
				&& grid.containsKey(new HantoCoordinateImpl(x - 1, y))
				&& grid.containsKey(new HantoCoordinateImpl(x - 1, y + 1)));

	}

}
