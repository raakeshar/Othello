package logic;

import core.Board;
import core.Player;

/**
 * Evaluation Function interface. Each evaluation method must implement this
 * 
 * 
 */
public interface Evaluation {

	public Point evaluate(final Board board, final Player player);
}