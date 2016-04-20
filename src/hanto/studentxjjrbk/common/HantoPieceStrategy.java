/**
 * @author rbkillea
 */
package hanto.studentxjjrbk.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;

/**
 * The Interface HantoPieceStrategy.
 */
public interface HantoPieceStrategy {
	
	/**
	 * Can move.
	 *
	 * @param source
	 *            the source
	 * @param destination
	 *            the destination
	 * @param player
	 *            the player
	 * @return true, if successful
	 * @throws HantoException
	 *             the hanto exception
	 */
	boolean canMove(HantoCoordinateImpl source, HantoCoordinateImpl destination, HantoPlayerColor player) throws HantoException;
}
