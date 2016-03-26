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
	private HashMap<HantoCoordinateImpl, HantoPieceImpl> grid = new HashMap<HantoCoordinateImpl, HantoPieceImpl>(100);
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
		/*if (pieceType != BUTTERFLY) {
			throw new HantoException("Only Butterflies are valid in Alpha Hanto");
		}*/
		
		final HantoCoordinateImpl to = new HantoCoordinateImpl(destination);
		
		if (firstMove) {
			if (to.getX() != 0 || to.getY() != 0) {
				throw new HantoException("Blue did not move to origin");
			}
			//blueButterflyHex = to;
		} else {
			if (!hexIsValid(to)) {
				throw new HantoException("Cannot place a piece in that hex");
			}
			//redButterflyHex = to;
			gameOver = true;
		}
		
		final MoveResult moveResult = firstMove ? OK : DRAW;
		firstMove = false;
		gameTurns++;
		return moveResult;
	}

	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where)
	{
		// TODO Auto-generated method stub
		return grid.get(where);
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
	
	private boolean hexIsValid(HantoCoordinateImpl coordinate)
	{
		
		
		return (coordinate.equals(new HantoCoordinateImpl(0, 1))
				|| coordinate.equals(new HantoCoordinateImpl(1, 0))
				|| coordinate.equals(new HantoCoordinateImpl(1, -1))
				|| coordinate.equals(new HantoCoordinateImpl(0, -1))
				|| coordinate.equals(new HantoCoordinateImpl(-1, 0))
				|| coordinate.equals(new HantoCoordinateImpl(-1, 1)));
	}

}
