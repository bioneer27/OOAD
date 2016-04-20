/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentxjjrbk.gamma;

import static hanto.common.HantoPieceType.*;
import hanto.studentxjjrbk.alpha.AlphaHantoGame;
import hanto.studentxjjrbk.beta.BetaHantoGame;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentxjjrbk.HantoGameFactory;
import hanto.studentxjjrbk.common.HantoCoordinateImpl;
import hanto.studentxjjrbk.common.HantoPieceFactory;
import hanto.studentxjjrbk.common.HantoPieceImpl;

import org.junit.*;

/**
 * Test cases for Beta Hanto.
 * 
 * @version Sep 14, 2014
 */
public class GammaHantoTest {
	
	/** The factory. */
	private static HantoGameFactory factory = null;
	
	/** The game. */
	private HantoGame game;

	
	/**
	 * The Class TestHantoCoordinate.
	 */
	public class TestHantoCoordinate implements HantoCoordinate
	{
		
		/** The y. */
		private final int x, y;
		
		/**
		 * Instantiates a new test hanto coordinate.
		 *
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 */
		public TestHantoCoordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		/* (non-Javadoc)
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		/*
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX()
		{
			return x;
		}

		/* (non-Javadoc)
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY()
		{
			return y;
		}

	}
	/**
	 * Initializes a factory to use to help setup the game to test
	 */
	@BeforeClass
	public static void initializeClass() {
		factory = HantoGameFactory.getInstance();
	}

	/**
	 * Helper method to setup a game to play and test
	 */
	@Before
	public void setup() {
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO);
	}

	/**
	 * Test that blue places initial piece at the origin
	 * 
	 * @throws HantoException
	 */
	@Test // 1
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException {
		final MoveResult mr = game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}

	/**
	 * Test that Blue can place a sparrow at the origin since Beta isn't bound
	 * to only using butterflies
	 * 
	 * @throws HantoException
	 */
	@Test // 2
	public void bluePlacesSparrowAtOrigin() throws HantoException {
		final MoveResult mr = game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}

	/**
	 * Test that an exception is thrown if the first move isn't at the origin
	 * 
	 * @throws HantoException
	 */
	@Test // 3
	public void bluePlacesInitialButterflyAtWrongPlace() throws HantoException {
		try {
			game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 1));
			assertTrue(false);
		} catch(HantoException e) {
			assertEquals(e.getMessage(), "Blue did not move to origin");
		}
	}

	/**
	 * Check that red moves after blue correctly, meaning that it is put in a
	 * valid position that is adjacent to blue
	 * 
	 * @throws HantoException
	 */
	@Test // 4
	public void redMovesAfterBlueCorrectly() throws HantoException {
		final MoveResult mr = game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
		final MoveResult mb = game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, 1));
		assertEquals(OK, mb);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(0, 1));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());

	}

	/**
	 * Check that red can move to multiple different places after blue moves to
	 * origin and these moves are accepted as valid
	 * 
	 * @throws HantoException
	 */
	@Test // 5
	public void redMovesAfterBlueSomewhereElse() throws HantoException {
		final MoveResult mr = game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
		final MoveResult mb = game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, 1));
		assertEquals(OK, mb);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(-1, 1));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());

	}

	/**
	 * Test that the methods work properly with TestHantoCoordinates as well as
	 * HantoCoordinateImpl
	 * 
	 * @throws HantoException
	 */
	@Test // 6
	public void bluePlacesInitialButterflyWithTestCoordinate() throws HantoException {
		final MoveResult mr = game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}

	/**
	 * Check that an exception is thrown and caught if Red tries to move to an
	 * invalid position after blue moves first
	 * 
	 * @throws HantoException
	 */
	@Test // 7
	public void redMovesAfterBlueWrong() throws HantoException {
		game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
		try {
			game.makeMove(SPARROW, null, new HantoCoordinateImpl(-2, 1));
			assertTrue(false);
		} catch(HantoException e) {
			assertEquals(e.getMessage(), "You cannot place a piece in that hex");
		}

	}

	/**
	 * Run a quick simulation of multiple valid moves being made to ensure that
	 * game play can proceed past the initial phase
	 * 
	 * @throws HantoException
	 */
	@Test // 8
	public void blueMovesRedMovesBlueMoves() throws HantoException {
		game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, 1));
		final MoveResult mr = game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, 1));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(0, 1));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}

	/**
	 * An extension of the previous test to make sure you can extend game play
	 * 
	 * @throws HantoException
	 */
	@Test // 9
	public void blueRedBlueRedMoves() throws HantoException {
		final MoveResult mr = game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
		final MoveResult mb = game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, 1));
		final MoveResult mr1 = game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, 1));
		final MoveResult mb1 = game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, 0));
		assertEquals(OK, mb1);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(-1, 0));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * Test to make sure the isEqual method works correctly for a HantoCoordinateImpl
	 * @throws HantoException
	 */
	@Test // 12
	public void hantoCoordinateIsEqual() throws HantoException {
		HantoCoordinate hc = new HantoCoordinateImpl(0, 0);
		assertFalse(hc.equals(null));
		assertFalse(hc.equals(""));
		assertTrue(hc.equals(hc));
		assertFalse(hc.equals(new HantoCoordinateImpl(1, 0)));
		assertFalse(hc.equals(new HantoCoordinateImpl(0, 1)));
	}
	
	/**
	 * Tests creating the Hanto board as string representation.
	 *
	 * @throws HantoException
	 */
	@Test // 13
	public void hantoBoardAsString() throws HantoException {
		assertEquals(game.getPrintableBoard(), "The board is empty\n");
		game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
		game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, -1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, -1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, -2));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, 0));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, -1));
		
		assertEquals(game.getPrintableBoard(), "|     | R S |\n|     | R B |\n| R S | B S |\n");
	}
	
	/**
	 * This tests that red can move first if the factory is created with red as the first player
	 * By the way, red shouldn't move first.  In chess, white always moves first.  Black never gets to 
	 * move first. 
	 * @throws HantoException
	 */
	@Test //14
	public void redMovesFirst() throws HantoException{
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, RED); 
		final MoveResult mr = game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(0, 0));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}

	
	/**
	 * Blue places initial sparrow at origin.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test	// 2
	public void bluePlacesInitialSparrowAtOrigin() throws HantoException	{
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * Red places initial sparrow at origin.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test	// 3
	public void redPlacesInitialSparrowAtOrigin() throws HantoException	{
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, RED);	// RedFirst
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * Valid first and second move.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test	// 4
	public void validFirstAndSecondMove() throws HantoException	{
		// Blue Butterfly -> (0, 0)
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		assertEquals(OK, mr);
		p = game.getPieceAt(makeCoordinate(1, 0));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * Valid first move non adjacent hex second move.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test(expected = HantoException.class)	// 5
	public void validFirstMoveNonAdjacentHexSecondMove() throws HantoException	{
		assertTrue(true);
		game.makeMove(SPARROW,  null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
	}
	
	/**
	 * First move is not at origin.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test(expected = HantoException.class)	// 6
	public void firstMoveIsNotAtOrigin() throws HantoException	{
		assertTrue(true);
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
	}
	
	/**
	 * Blue attempts to place two butterflies.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test(expected = HantoException.class)	// 7
	public void blueAttemptsToPlaceTwoButterflies() throws HantoException	{
		assertTrue(true);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
	}
	
	/**
	 * Red attempts to place two butterflies.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test(expected = HantoException.class)	// 8
	public void redAttemptsToPlaceTwoButterflies() throws HantoException	{
		assertTrue(true);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, 	null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	}
	
	/**
	 * Blue tries to place piece on occupied hex.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test(expected = HantoException.class)	// 9
	public void blueTriesToPlacePieceOnOccupiedHex() throws HantoException 	{
		assertTrue(true);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
	}
	
	/**
	 * Red tries to place piece on occupied hex.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test(expected = HantoException.class)	// 10
	public void redTriesToPlacePieceOnOccupiedHex() throws HantoException	{
		assertTrue(true);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, 	null, makeCoordinate(0, -1));
	}
	
	/**
	 * Blue does not place butterfly by fourth move.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test(expected = HantoException.class)	// 11
	public void blueDoesNotPlaceButterflyByFourthMove() throws HantoException	{
		assertTrue(true);
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));	// Move 4
	}
	
	/**
	 * Red does not place butterfly by fourth turn.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test(expected = HantoException.class)	// 12
	public void redDoesNotPlaceButterflyByFourthTurn() throws HantoException    {
		assertTrue(true);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));	// Move 4
		game.makeMove(SPARROW, null, makeCoordinate(0, -4));
	}
	
	/**
	 * Move after game is over less than six turns.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test(expected = HantoException.class)	// 16
	public void moveAfterGameIsOverLessThanSixTurns() throws HantoException	{
		assertTrue(true);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));		// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));		// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(-1,1));		// Move 4
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
	}
	
	/**
	 * Move piece away and disconnect.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test(expected = HantoException.class)
	public void movePieceAwayAndDisconnect() throws HantoException {
		assertTrue(true);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(0, -1));
	}
	
	/**
	 * Make a piece not supported.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test(expected = HantoException.class)
	public void makeAPieceNotSupported() throws HantoException {
		assertTrue(true);
		HantoPieceFactory hpf = new HantoPieceFactory(HantoGameID.GAMMA_HANTO, 0, 0, 0);
		hpf.makePiece(new HantoPieceImpl(RED, CRANE), true);
	}
	
	/**
	 * Move piece
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	public void movePiece() throws HantoException {
		assertTrue(true);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(BUTTERFLY, makeCoordinate(0,0), makeCoordinate(-1, 1));
	}
	
	/**
	 * Move piece un successfully.
	 *
	 * @throws HantoException
	 *             the hanto exception
	 */
	@Test
	public void movePieceSuccessfully() throws HantoException {
		assertTrue(true);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(1, 2));
		game.makeMove(SPARROW, makeCoordinate(2,0), makeCoordinate(2, 1));
	}
	
	/**
	 * Make coordinate.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the hanto coordinate
	 */
	private HantoCoordinate makeCoordinate(int x, int y)	{
		return new TestHantoCoordinate(x, y);
	}
}