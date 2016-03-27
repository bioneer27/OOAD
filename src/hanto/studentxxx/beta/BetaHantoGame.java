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

package hanto.studentxxx.beta;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;
import static hanto.common.MoveResult.BLUE_WINS;


import java.util.HashMap;

import hanto.common.*;
import hanto.studentxxx.common.HantoCoordinateImpl;
import hanto.studentxxx.common.HantoPieceImpl;

/**
 * Implementation of Beta Hanto
 * @version Mar 26, 2016
 */
public class BetaHantoGame implements HantoGame
{
	private boolean firstMove = true;
	
	private HantoCoordinateImpl blueButterflyHex = null, redButterflyHex = null;
	/*private final HantoPiece blueButterfly = new HantoPieceImpl(BLUE, BUTTERFLY);
	private final HantoPiece redButterfly = new HantoPieceImpl(RED, BUTTERFLY);*/
	private boolean gameOver= false;
	private HashMap<HantoCoordinate, HantoPieceImpl> grid = new HashMap<HantoCoordinate, HantoPieceImpl>(100);
	private int gameTurns = 0;
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate destination) throws HantoException
	{
		
		if (gameOver) {
			throw new HantoException("You cannot move after the game is finished");
		}
		
		final HantoPlayerColor hp = ((gameTurns % 2) ==0)  ? BLUE : RED;
		/*if (pieceType = BUTTERFLY && blueButterflyHex == null) {
			throw new HantoException("Only Butterflies are valid in Alpha Hanto");
		}*/		
		
		final HantoCoordinateImpl to = new HantoCoordinateImpl(destination);
		MoveResult moveResult = DRAW;
		if (firstMove) {
			try {
				if (to.getX() != 0 || to.getY() != 0) {
			
					throw new HantoException("Blue did not move to origin");
				}
			}
			catch (HantoException h){
				return null;
			}
			firstMove = false;
			/*
			HantoPieceImpl piece = new HantoPieceImpl(hp, pieceType);
			grid.put(destination, piece);*/
			moveResult = OK;
			blueButterflyHex = new HantoCoordinateImpl(destination.getX(), destination.getY());
		} else {
			try {
				if (!hexIsValid(to)) {
			
				throw new HantoException("Cannot place a piece in that hex");
				}
			}
			catch (HantoException h){
				return null;
			}
			/*
			HantoPieceImpl piece = new HantoPieceImpl(hp, pieceType);
			grid.put(destination, piece);
			gameOver = true;
			moveResult = OK;*/
		}
		HantoPieceImpl piece = new HantoPieceImpl(hp, pieceType);
		grid.put(destination, piece);
		moveResult = OK;
		gameTurns++;
		
		if (gameWonRed()) return RED_WINS;
		if (gameWonBlue()) return BLUE_WINS;
 		return moveResult;
	}

	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where)
	{
		HantoPiece piece = grid.get(where);
		return piece;
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		// TODO Auto-generated method stub
		return null;
	}

	private boolean hexIsValid(HantoCoordinate coordinate)
	{
		int x = coordinate.getX();
		int y = coordinate.getY();
		/*
		return (coordinate.equals(new HantoCoordinateImpl(0, 1))
				|| coordinate.equals(new HantoCoordinateImpl(1, 0))
				|| coordinate.equals(new HantoCoordinateImpl(1, -1))
				|| coordinate.equals(new HantoCoordinateImpl(0, -1))
				|| coordinate.equals(new HantoCoordinateImpl(-1, 0))
				|| coordinate.equals(new HantoCoordinateImpl(-1, 1)));*/
		return (grid.containsKey(new HantoCoordinateImpl(x, y + 1))
				|| grid.containsKey(new HantoCoordinateImpl(x + 1, y + 1))
				|| grid.containsKey(new HantoCoordinateImpl(x + 1, y - 1))
				|| grid.containsKey(new HantoCoordinateImpl(x, y - 1))
				|| grid.containsKey(new HantoCoordinateImpl(x - 1, y))
				|| grid.containsKey(new HantoCoordinateImpl(x - 1, y + 1)));
		
		
	}
	
	/**
	 * This method takes the position of the blue butterfly which is held as an attribute of the 
	 * BetaHanto class and checks the adjacency to see if the butterfly is surrounded and if the 
	 * game should be considered a win for Red.  This only determines if blue is surrounded, 
	 * it can not tell you if it is a draw or a loss for red
	 * @return boolean T/F on whether the game has been won or not
	 */
	private boolean gameWonRed( ){
		
		if (blueButterflyHex == null) return false;
		
		int x = blueButterflyHex.getX();
		int y = blueButterflyHex.getY();
		return (grid.containsKey(new HantoCoordinateImpl(x, y + 1))
				&& grid.containsKey(new HantoCoordinateImpl(x + 1, y + 1))
				&& grid.containsKey(new HantoCoordinateImpl(x + 1, y - 1))
				&& grid.containsKey(new HantoCoordinateImpl(x, y - 1))
				&& grid.containsKey(new HantoCoordinateImpl(x - 1, y))
				&& grid.containsKey(new HantoCoordinateImpl(x - 1, y + 1)));
		
		
	}
	
private boolean gameWonBlue( ){
		
		if (redButterflyHex == null) return false;
		
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
