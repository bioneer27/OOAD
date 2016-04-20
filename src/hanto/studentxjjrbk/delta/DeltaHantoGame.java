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

package hanto.studentxjjrbk.delta;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanto.common.*;
import hanto.studentxjjrbk.common.HantoAdjacencies;
import hanto.studentxjjrbk.common.HantoCoordinateImpl;
import hanto.studentxjjrbk.common.HantoFlyStrategy;
import hanto.studentxjjrbk.common.HantoPieceFactory;
import hanto.studentxjjrbk.common.HantoPieceImpl;
import hanto.studentxjjrbk.common.HantoWalkStrategy;
import hanto.studentxjjrbk.common.PieceInfo;

/**
 * Implementation of Beta Hanto
 * 
 * @version Mar 26, 2016
 */
public class DeltaHantoGame implements HantoGame, PieceInfo {
	
	/** The red butterfly hex. */
	private static HantoCoordinate blueButterflyHex = null, redButterflyHex = null;
	
	/** The blue pieces. */
	private List<HantoCoordinate> redPieces, bluePieces;
	
	/** The game over. */
	private boolean firstMove, gameOver;
	
	/** The grid. */
	private static Map<HantoCoordinateImpl, HantoPieceImpl> grid = null;
	
	/** The game turns. */
	private int gameTurns;
	
	/** The first color. */
	private HantoPlayerColor firstColor;
	
	/** The piece factory. */
	private HantoPieceFactory pieceFactory;
	
	/**
	 * Instantiates a new gamma hanto game.
	 * @param color
	 */
	public DeltaHantoGame(HantoPlayerColor color){
		firstMove = true;
		gameOver = false;
		grid = new HashMap<HantoCoordinateImpl, HantoPieceImpl>(100);
		gameTurns = 0;
		redPieces = new ArrayList<HantoCoordinate>();
		bluePieces = new ArrayList<HantoCoordinate>();
		firstColor = color;
		pieceFactory = new HantoPieceFactory(HantoGameID.DELTA_HANTO, 4, 1, 4);
		blueButterflyHex = null;
		redButterflyHex = null;
		HantoWalkStrategy.setGame(this);
		HantoFlyStrategy.setGame(this);
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
		final HantoPlayerColor hp = currentColor();
		if(source == null && destination == null) {
			gameOver = true;
			if(hp.equals(BLUE)) {
				return RED_WINS;
			} else {
				return BLUE_WINS;
			}
		}
		final HantoCoordinateImpl to = new HantoCoordinateImpl(destination);
		final HantoPieceImpl piece = pieceFactory.makePiece(new HantoPieceImpl(hp, pieceType), source == null);
		if(piece == null) {
			throw new HantoException("You can't place that piece type");
		}
		if(source != null) {
			// we're movin'
			final HantoCoordinate from = new HantoCoordinateImpl(source);
			if(piece.canMove(from, destination) && pieceType == grid.get(from).getType()) {
				grid.remove(from);
				if(hp.equals(BLUE)) {
					bluePieces.remove(from);
				} else {
					redPieces.remove(from);
				}
			} else {
				throw new HantoException("You can't move that way");
			}
		} else {
			if(firstMove) {
				if(to.getX() != 0 || to.getY() != 0) {
					throw new HantoException("Blue did not move to origin");
				}
				firstMove = false;
			} else {
				if(!hexIsValid(to, hp)) {
					throw new HantoException("You cannot place a piece in that hex");
				}
			}
		}
		if(hp.equals(BLUE)) {
			bluePieces.add(to);
		} else {
			redPieces.add(to);
		}
		grid.put(to, piece);
		gameTurns++;
		if(hp.equals(BLUE) && pieceType.equals(BUTTERFLY)) {
			blueButterflyHex = to;
		} else if(pieceType.equals(BUTTERFLY)) {
			redButterflyHex = to;
		}
		return gameResult();
	}

	/**
	 * Returns the piece at a particular HantoCoordinate
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		HantoPiece piece = grid.get(new HantoCoordinateImpl(where));
		return piece;
	}
	
	/**
	 * Returns the piece at a particular HantoCoordinate
	 * @param where
	 * @return HantoPiece
	 */
	public HantoPieceImpl getPiece(HantoCoordinate where) {
		HantoPieceImpl piece = grid.get(new HantoCoordinateImpl(where));
		return piece;
	}
	
	/**
	 * Gets the a piece coordinate.
	 *
	 * @return the a piece coordinate
	 */
	public HantoCoordinate getAPieceCoordinate() {
		return (HantoCoordinate) grid.keySet().toArray()[0];
	}
	
	/**
	 * Num pieces.
	 *
	 * @return the int
	 */
	public int numPieces() {
		return grid.size();
	}
	
	/**
	 * Contains piece.
	 *
	 * @param where
	 *            the where
	 * @return true, if successful
	 */
	public boolean containsPiece(HantoCoordinate where) {
		return grid.containsKey(where);
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

	/**
	 * Hex is valid.
	 *
	 * @param coordinate
	 *            the coordinate
	 * @param color
	 *            the color
	 * @return true, if successful
	 */
	private boolean hexIsValid(HantoCoordinate coordinate, HantoPlayerColor color) {
		int x = coordinate.getX();
		int y = coordinate.getY();
		
		if (grid.containsKey(new HantoCoordinateImpl(x,y))){
			return false;
		}
		for(HantoCoordinate h : HantoAdjacencies.getHantoAdjacencies()) {
			if(hexContainsSharedColor(new HantoCoordinateImpl(h, coordinate), color)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Hex contains shared color.
	 *
	 * @param coord
	 *            the coord
	 * @param color
	 *            the color
	 * @return true, if successful
	 */
	private boolean hexContainsSharedColor(HantoCoordinateImpl coord, HantoPlayerColor color) {
		return grid.containsKey(coord) && (grid.get(coord).getColor() == color ||  gameTurns <= 1);
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
		for(HantoCoordinate h : HantoAdjacencies.getHantoAdjacencies()) {
			if(!grid.containsKey(new HantoCoordinateImpl(h, blueButterflyHex))) {
				return false;
			}
		}
		return true;

	}

	/**
	 * Game won blue.
	 *
	 * @return true, if successful
	 */
	private boolean gameWonBlue() {

		if(redButterflyHex == null) {
			return false;
		}
		for(HantoCoordinate h : HantoAdjacencies.getHantoAdjacencies()) {
			if(!grid.containsKey(new HantoCoordinateImpl(h, redButterflyHex))) {
				return false;
			}
		}
		return true;

	}
	
	/**
	 * Current color.
	 *
	 * @return the hanto player color
	 */
	private HantoPlayerColor currentColor(){
		if ((gameTurns % 2) == 0) {
			return firstColor;
		}
		else {// Not the first player's turn
			if (firstColor.equals(HantoPlayerColor.BLUE)) return HantoPlayerColor.RED;
			else return HantoPlayerColor.BLUE;
		}
	}
	
	/**
	 * Game result.
	 *
	 * @return the move result
	 */
	private MoveResult gameResult() {
		boolean red = gameWonRed();
		boolean blue = gameWonBlue();
		if(red && blue) {
			gameOver = true;
			return DRAW;
		}
		if(red) {
			gameOver = true;
			return RED_WINS;
		}
		if(blue) {
			gameOver = true;
			return BLUE_WINS;
		}
		if (gameTurns == 20) return DRAW;
		
		return OK;
	}
}