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

		/*
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		

	}
	
	private static HantoGameFactory factory;
	private HantoGame game;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, BLUE);
	}
	
	@Test	// 1
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	@Test //2
	public void bluePlacesSparrowAtOrigin() throws HantoException{
		final MoveResult mr = game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	@Test //3
	public void bluePlacesInitialButterflyAtWrongPlace() throws HantoException{
		final MoveResult mr = game.makeMove(BUTTERFLY, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,1));
		assertEquals(null, mr);
	}
	
	@Test //4
	public void redMovesAfterBlueCorrectly() throws HantoException{
		final MoveResult mr = game.makeMove(BUTTERFLY, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,0));
		final MoveResult mb = game.makeMove(SPARROW, new HantoCoordinateImpl(0,0), new HantoCoordinateImpl(0,1));
		assertEquals(OK, mb);
		final HantoPiece p = game.getPieceAt(new HantoCoordinateImpl(0,1));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());

	}
	
	
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
		
	}
}
