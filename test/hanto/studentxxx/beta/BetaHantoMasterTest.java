/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentxxx.beta;

import static hanto.common.HantoPieceType.*;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentxxx.HantoGameFactory;
import hanto.studentxxx.common.HantoCoordinateImpl;

import org.junit.*;

/**
 * Test cases for Beta Hanto.
 * @version Sep 14, 2014
 */
public class BetaHantoMasterTest
{
	/**
	 * Internal class for these test cases.
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
		/**
		 * constructor for creating the TestHantoCoordinate instance
		 * @param x
		 * @param y
		 */
		public TestHantoCoordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		/*
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX()
		{
			return x;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY()
		{
			return y;
		}
		
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		
		public boolean equals(Object obj)
		{
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof TestHantoCoordinate)) {
				return false;
			}
			final TestHantoCoordinate other = (TestHantoCoordinate) obj;
			if (x != other.x) {
				return false;
			}
			if (y != other.y) {
				return false;
			}
			return true;
		}

		/*
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		

	}
	
	private static HantoGameFactory factory;
	private HantoGame game;
	
	/**
	 *Initializes a factory to use to help setup the game to test 
	 */
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	/**
	 * Helper method to setup a game to play and test
	 */
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, BLUE);
	}
	
	/**
	 * Test that blue places initial piece at the origin
	 * @throws HantoException
	 */
	@Test	// 1
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	/**
	 * Test that Blue can place a sparrow at the origin since Beta isn't bound to only using butterflies
	 * @throws HantoException
	 */
	@Test //2
	public void bluePlacesSparrowAtOrigin() throws HantoException{
		final MoveResult mr = game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * Test that an exception is thrown if the first move isn't at the origin and that the exception is caught and a 
	 * null moveResult is returned
	 * @throws HantoException
	 */
	@Test //3
	public void bluePlacesInitialButterflyAtWrongPlace() throws HantoException{
		final MoveResult mr = game.makeMove(BUTTERFLY, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,1));
		assertEquals(null, mr);
	}
	
	/**
	 * Check that red moves after blue correctly, meaning that it is put in a valid position that is adjacent to blue
	 * @throws HantoException
	 */
	@Test //4
	public void redMovesAfterBlueCorrectly() throws HantoException{
		final MoveResult mr = game.makeMove(BUTTERFLY, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,0));
		final MoveResult mb = game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,1));
		assertEquals(OK, mb);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(0,1));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());

	}
	/**
	 * Check that red can move to multiple different places after blue moves to origin and these moves are accepted
	 * as valid
	 * @throws HantoException
	 */
	@Test // 5
	public void redMovesAfterBlueSomewhereElse() throws HantoException{
		final MoveResult mr = game.makeMove(BUTTERFLY, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,0));
		final MoveResult mb = game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(-1,1));
		assertEquals(OK, mb);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(-1,1));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());

	}
	
	/**
	 * Test that the methods work properly with TestHantoCoordinates as well as HantoCoordinateImpl
	 * @throws HantoException
	 */
	@Test //6
	public void bluePlacesInitialButterflyWithTestCoordinate() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, makeCoordinate(0,0), makeCoordinate(0,0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	/**
	 * Check that an exception is thrown and caught if Red tries to move to an invalid position after blue moves first
	 * @throws HantoException
	 */
	@Test //7
	public void redMovesAfterBlueWrong() throws HantoException{
		final MoveResult mr = game.makeMove(BUTTERFLY, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,0));
		final MoveResult mb = game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(-2,1));
		assertEquals(null, mb);
		
		
	}
	
	/**
	 * Run a quick simulation of multiple valid moves being made to ensure that game play can proceed past the 
	 * initial phase
	 * @throws HantoException
	 */
	@Test //8
	public void blueMovesRedMovesBlueMoves() throws HantoException{
		final MoveResult mr = game.makeMove(BUTTERFLY, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,0));
		final MoveResult mb = game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(-1,1));
		final MoveResult mr1 = game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,1));
		assertEquals(OK, mr1);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(0,1));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * An extension of the previous test to make sure you can extend game play
	 * 
	 * @throws HantoException
	 */
	@Test //9
	public void blueRedBlueRedMoves() throws HantoException{
		final MoveResult mr = game.makeMove(BUTTERFLY, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,0));
		final MoveResult mb = game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(-1,1));
		final MoveResult mr1 = game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,1));
		final MoveResult mb1 = game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(1,0));
		assertEquals(OK, mb1);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(1,0));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * Run a full game where blue makes really stupid moves to check that a winner can be determined correctly 
	 * @throws HantoException
	 */
	@Test //10
	public void bluePlaysVeryStupidAndLosesQuickly() throws HantoException{
		game.makeMove(BUTTERFLY, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,0));
		game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(1,-1));
		game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,1));
		game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(1,1));
		game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,-1));
		game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(-1,1));
		final MoveResult mr3 = game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(-1,0));


		assertEquals(RED_WINS, mr3);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(-1,0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
		
	}
}
