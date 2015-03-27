package logic;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import core.Board;
import core.Player;

public class ReversiEvaluation implements Evaluation{

	public int[][] table = new int[][]
			{
			{99,-8,8,6,6,8,-8,99},
			{-8,-24,-4,-3,-3,-4,-24,-8},
			{8,-4,7,4,4,7,-4,8},
			{6,-3,4,0,0,4,-3,6},
			{6,-3,4,0,0,4,-3,6},
			{8,-4,7,4,4,7,-4,8},
			{-8,-24,-4,-3,-3,-4,-24,-8},
			{99,-8,8,6,6,8,-8,99}
			};

	@Override
	public int evaluateScore(Board board, Player player) {
		
		Set<Point> maxPlayer = new HashSet<Point>();
		Set<Point> minPlayer = new HashSet<Point>();
		int maxScore=0,minScore=0;
		
		maxPlayer = board.getSquares('X');
		minPlayer = board.getSquares('O');
		
		for(Point point:maxPlayer)
		{
			maxScore += table[point.x][point.y];
		}
		for(Point point:minPlayer)
		{
			minScore += table[point.x][point.y];
		}
		
		
		return (maxScore-minScore);
		
		
	}

	@Override
	public Point evaluate(Board board, Player player, Set<Point> possibleMoves) {
		// TODO Auto-generated method stub
		return null;
	}

}
