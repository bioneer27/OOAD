/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2015 Gary F. Pollice
 *******************************************************************************/

package hanto.studentxjjrbk.common;

import hanto.common.HantoCoordinate;

/**
 * The implementation for my version of Hanto.
 * 
 * @version Mar 2, 2016
 */
public class HantoCoordinateImpl implements HantoCoordinate {
	
	/** The y. */
	final private int x, y;

	/**
	 * The only constructor.
	 * 
	 * @param x
	 *            the x-coordinate
	 * @param y
	 *            the y-coordinate
	 */
	public HantoCoordinateImpl(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Copy constructor that creates an instance of HantoCoordinateImpl from an
	 * object that implements HantoCoordinate.
	 * 
	 * @param coordinate
	 *            an object that implements the HantoCoordinate interface.
	 */
	public HantoCoordinateImpl(HantoCoordinate coordinate) {
		this(coordinate.getX(), coordinate.getY());
	}
	
	public HantoCoordinateImpl(HantoCoordinate coordinate1, HantoCoordinate coordinate2) {
		this(coordinate1.getX() + coordinate2.getX(), coordinate1.getY() + coordinate2.getY());
	}

	/* (non-Javadoc)
	 * @see hanto.common.HantoCoordinate#getX()
	 */
	@Override
	public int getX() {
		return x;
	}

	/* (non-Javadoc)
	 * @see hanto.common.HantoCoordinate#getY()
	 */
	@Override
	public int getY() {
		return y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof HantoCoordinate)) {
			return false;
		}
		final HantoCoordinate other = (HantoCoordinate) obj;
		if(x != other.getX() || y != other.getY()) {
			return false;
		}
		return true;
	}

}
