package logic;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import core.Board;
import core.Player;

public class MoveExplorer {
	
	private static boolean shouldSearch(final Board board, final Point seed, final Direction direction,final Player player) {
		Point nextPoint = direction.next(seed);
		return pointIsValid(nextPoint) ? (board.getSquareState(nextPoint)
						 != player.color())&&(board.getSquareState(nextPoint) != '*') : false;
	}

	private static boolean pointIsValid(Point point) {
		return point.x >= 0 && point.x < Board.BOARD_LENGTH
		       && point.y >= 0 && point.y < Board.BOARD_WIDTH;
	}
	
	
	
	public static Set<Point> explore(final Board board, final Player player) {
		/*Set<Point> possibleMoves = new HashSet<Point>();
		Set<Point> statePoints = board.getSquares(player.color());
		
		for (Point seed : statePoints) {
			for (Direction direction : Direction.values()) {
				Boolean look = true;
				int count=0;
				Point nextPoint = direction.next(seed);
				if(pointIsValid(nextPoint ) && pointIsValid(direction.next(nextPoint)) && !board.getSquareState(direction.next(nextPoint)).equals(board.getSquareState(seed)))
				if(! ((board.getSquareState(nextPoint)) == (board.getSquareState(seed))) && !board.getSquareState(nextPoint).equals('*'))
						{
							
							while (pointIsValid(nextPoint ) ) 
							{
								Point temp = nextPoint;
								
								if(board.getSquareState(temp) == player.opponent().color() )
								{
									
									nextPoint = direction.next(nextPoint);
									if(!pointIsValid(nextPoint))
									{
										
										continue;
									}
									
									count = 1;
								}
								else
								{
									break;
								}
								if(count > 0)
								{
									possibleMoves.add(nextPoint);
									count =0;
								}
							}
							
							
						}
			}
		}
		return possibleMoves;*/
		
		Set<Point> possibleMoves = new HashSet<Point>();
		Set<Point> statePoints = board.getSquares(player.color());
		for (Point seed : statePoints) {
			for (Direction direction : Direction.values()) {
				if (shouldSearch(board, seed, direction,player)) {
					Point nextPoint = direction.next(seed);
					nextPoint = direction.next(nextPoint);
					while (pointIsValid(nextPoint)) {
						if (board.getSquareState(nextPoint) == player.color()) {
							break;
						} else if (board.getSquareState(nextPoint) == '*') {
							possibleMoves.add(nextPoint);
							break;
						}
						nextPoint = direction.next(nextPoint);
					}
				}
			}
		}
		return possibleMoves;
	}
	
	public static Set<Point> squaresToFill(final Board board, final Point seed) {
		Set<Point> filledlist = new HashSet<Point>();
		Character seedState = board.getSquareState(seed);
		for (Direction direction : Direction.values()) {
			
			Point nextPoint = direction.next(seed);
			if(pointIsValid(nextPoint ))
			if(! (((board.getSquareState(nextPoint)) == (seedState)) && !board.getSquareState(nextPoint).equals('*')))
					{
						while (pointIsValid(nextPoint )) 
						{
							Point temp = nextPoint;
							
							if(board.getSquareState(temp) != seedState && board.getSquareState(temp) != '*' && board.getSquareState(direction.next(temp)) != '*')
							{
								filledlist.add(nextPoint);
								nextPoint = direction.next(nextPoint);
								continue;
								
							}
							else
							{
								break;
							}
							
						}
						
					}
			
		}
		return filledlist;
	}
	
}
