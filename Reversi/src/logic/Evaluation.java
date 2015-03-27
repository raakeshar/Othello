
package logic;

import java.awt.Point;
import java.util.Map;
import java.util.Set;

import core.Board;
import core.Player;

/**
 * Evaluation Function interface. Each evaluation method must implement this
 * 
 * 
 */
public interface Evaluation {

	public Point evaluate(Board board, Player player, Set<Point> possibleMoves);
	
	public int evaluateScore(Board board, Player player);
}



