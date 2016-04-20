/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentxjjrbk.common;

import java.util.ArrayList;
import java.util.List;

import hanto.common.*;

/**
 * Implementation of the HantoPiece.
 * 
 * @version Mar 2,2016
 */
public class HantoPieceImpl implements HantoPiece {
	
	/** The color. */
	private final HantoPlayerColor color;
	
	/** The type. */
	private final HantoPieceType type;
	
	/** The strategy. */
	private final HantoPieceStrategy strategy;

	/**
	 * Default constructor
	 * 
	 * @param color
	 *            the piece color
	 * @param type
	 *            the piece type
	 */
	public HantoPieceImpl(HantoPlayerColor color, HantoPieceType type) {
		this.color = color;
		this.type = type;
		strategy = null;
	}
	
	/**
	 * Instantiates a new hanto piece impl.
	 *
	 * @param hp
	 *            the hp
	 * @param strategy
	 *            the strategy
	 */
	public HantoPieceImpl(HantoPiece hp, HantoPieceStrategy strategy) {
		color = hp.getColor();
		type = hp.getType();
		this.strategy = strategy;
	}

	/* (non-Javadoc)
	 * @see hanto.common.HantoPiece#getColor()
	 */
	/*
	 * @see hanto.common.HantoPiece#getColor()
	 */
	@Override
	public HantoPlayerColor getColor() {
		return color;
	}

	/* (non-Javadoc)
	 * @see hanto.common.HantoPiece#getType()
	 */
	/*
	 * @see hanto.common.HantoPiece#getType()
	 */
	@Override
	public HantoPieceType getType() {
		return type;
	}
	
	/**
	 * Can move.
	 *
	 * @param source
	 *            the source
	 * @param destination
	 *            the destination
	 * @return true, if successful
	 * @throws HantoException
	 *             the hanto exception
	 */
	public boolean canMove(HantoCoordinate source, HantoCoordinate destination)
			throws HantoException {
		return strategy.canMove(new HantoCoordinateImpl(source), new HantoCoordinateImpl(destination), color);
	}
}
