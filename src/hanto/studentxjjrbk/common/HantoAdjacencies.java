package hanto.studentxjjrbk.common;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;

public class HantoAdjacencies {
	private static List<HantoCoordinateImpl> adjacentCoordinates = new ArrayList<HantoCoordinateImpl>();
	public static List<HantoCoordinateImpl> getHantoAdjacencies() {
		if(adjacentCoordinates.isEmpty()) {
			adjacentCoordinates.add(new HantoCoordinateImpl(0,1));
			adjacentCoordinates.add(new HantoCoordinateImpl(1,0));
			adjacentCoordinates.add(new HantoCoordinateImpl(1,-1));
			adjacentCoordinates.add(new HantoCoordinateImpl(0,-1));
			adjacentCoordinates.add(new HantoCoordinateImpl(-1,0));
			adjacentCoordinates.add(new HantoCoordinateImpl(-1,1));
		}
		return adjacentCoordinates;
	}
}
