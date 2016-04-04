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
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentxjjrbk.HantoGameFactory;
import hanto.studentxjjrbk.common.HantoCoordinateImpl;

import org.junit.*;

/**
 * Test cases for Beta Hanto.
 * 
 * @version Sep 14, 2014
 */
public class GammaHantoTest {
	private static HantoGameFactory factory = null;
	private HantoGame game;

	
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
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
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO);
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
		final MoveResult mb1 = game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, 0));
		assertEquals(OK, mb1);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(1, 0));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}

	/**
	 * Run a full game where blue makes really stupid moves to check that a
	 * winner can be determined correctly
	 * 
	 * @throws HantoException
	 */
	@Test // 10
	public void bluePlaysVeryStupidAndLosesQuickly() throws HantoException {
		game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, -1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, 1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, 1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, -1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, 1));
		final MoveResult mr3 = game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, 0));

		assertEquals(RED_WINS, mr3);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(-1, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * Run a full game where blue makes really stupid moves to check that a
	 * winner can be determined correctly
	 * 
	 * @throws HantoException
	 */
	@Test // 11
	public void redPlaysVeryStupidAndLosesQuickly() throws HantoException {
		game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
		game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, -1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, -1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, -2));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, -2));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, -1));
		final MoveResult mr3 = game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, 0));

		assertEquals(BLUE_WINS, mr3);
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
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, -2));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, -1));
		
		assertEquals(game.getPrintableBoard(), "|     | R S |\n| B S | R B |\n| R S | B S |\n");
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
	
	@Test //15
	public void redWinsOnLastMove()throws HantoException{
		game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
		game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(-1,1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, 1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, 1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, 1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, -1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, -1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, -2));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, -2));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, -2));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(2, 0));
		final MoveResult mr = game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, 0));

		assertEquals(RED_WINS, mr);
		
	}
	@Test //16
	public void blueWinsOnLastMove()throws HantoException{
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, RED);
		game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
		game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(-1, 1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, 1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, 0));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, -1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, -1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, 2));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, 2));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, 1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(2, 0));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(2, -1));
		final MoveResult mr = game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, 0));

		assertEquals(BLUE_WINS, mr);
		
	}
	
	@Test //17
	public void noOneWinsByTwelfthMoveSoItsADraw()throws HantoException{
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, RED);
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(-1, 1));
		game.makeMove(SPARROW, null, new TestHantoCoordinate(0, 1));
		game.makeMove(SPARROW, null, new TestHantoCoordinate(1, 0));
		game.makeMove(SPARROW, null, new TestHantoCoordinate(1, -1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, -1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(-1, 2));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(0, 2));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(1, 1));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(2, 0));
		game.makeMove(SPARROW, null, new HantoCoordinateImpl(2, -1));
		final MoveResult mr = game.makeMove(SPARROW, null, new HantoCoordinateImpl(-2, 2));

		assertEquals(DRAW, mr);
		
	}
	

	
	@Test	// 2
	public void bluePlacesInitialSparrowAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	@Test	// 3
	public void redPlacesInitialSparrowAtOrigin() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, RED);	// RedFirst
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	@Test	// 4
	public void validFirstAndSecondMove() throws HantoException
	{
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
	
	@Test(expected = HantoException.class)	// 5
	public void validFirstMoveNonAdjacentHexSecondMove() throws HantoException
	{
		game.makeMove(SPARROW,  null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
	}
	
	@Test(expected = HantoException.class)	// 6
	public void firstMoveIsNotAtOrigin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
	}
	
	@Test(expected = HantoException.class)	// 7
	public void blueAttemptsToPlaceTwoButterflies() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
	}
	
	@Test(expected = HantoException.class)	// 8
	public void redAttemptsToPlaceTwoButterflies() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, 	null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	}
	
	@Test(expected = HantoException.class)	// 9
	public void blueTriesToPlacePieceOnOccupiedHex() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
	}
	
	@Test(expected = HantoException.class)	// 10
	public void redTriesToPlacePieceOnOccupiedHex() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, 	null, makeCoordinate(0, -1));
	}
	
	@Test(expected = HantoException.class)	// 11
	public void blueDoesNotPlaceButterflyByFourthMove() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));	// Move 4
	}
	
	@Test(expected = HantoException.class)	// 12
	public void redDoesNotPlaceButterflyByFourthTurn() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));	// Move 4
		game.makeMove(SPARROW, null, makeCoordinate(0, -4));
	}
	
	@Test	// 13
	public void blueWinsBeforeLastTurn() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));		// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));		// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		assertEquals(BLUE_WINS, game.makeMove(SPARROW, null, makeCoordinate(-1,1)));	// Move 4
	}
	
	@Test	// 14
	public void redSelfLosesBeforeLastTurn() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));		// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));		// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));	// Move 4
		assertEquals(BLUE_WINS, game.makeMove(SPARROW, null, makeCoordinate(-1,1)));
	}
	
	@Test	// 15
	public void redWinsOnLastTurn() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));		// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));		// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));		// Move 4
		game.makeMove(SPARROW, null, makeCoordinate(0, 4));
		game.makeMove(SPARROW, null, makeCoordinate(0, 5));		// Move 5
		game.makeMove(SPARROW, null, makeCoordinate(0, 6));
		game.makeMove(SPARROW, null, makeCoordinate(0, 7));		// Move 6
		assertEquals(RED_WINS, game.makeMove(SPARROW, null, makeCoordinate(-1,1)));
	}
	
	@Test(expected = HantoException.class)	// 16
	public void moveAfterGameIsOverLessThanSixTurns() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));		// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));		// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(-1,1));		// Move 4
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
	}
	
	@Test	// 17
	public void drawnGame() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));		// Move 4
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));		// Move 5
		assertEquals(DRAW, game.makeMove(SPARROW, null, makeCoordinate(-1, 1)));
	}
	
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
}