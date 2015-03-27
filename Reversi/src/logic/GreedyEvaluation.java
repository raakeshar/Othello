package logic;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.awt.Point;

import core.Board;
import core.Player;

public class GreedyEvaluation implements Evaluation {

	@Override
	public Point evaluate(Board board, Player player, Set<Point> possibleMoves) {

		int[][] table = new int[][]
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
		
		Set<Integer> eval = new HashSet<Integer>();
		Set<Point> result = new HashSet<Point>();
		ArrayList<Point> coordinateList = new ArrayList<Point>();
		for (Point seed : possibleMoves) 
		{
			eval.add(table[seed.x][seed.y]);
			
		}
		
		int max = Collections.max(eval);
		for (Point seed : possibleMoves) 
		{
			if(max == table[seed.x][seed.y])
			{
				coordinateList.add(seed);
			}
				
		}
		if(coordinateList.size() > 1)
		Collections.sort(coordinateList, new PointsCompare());
		return coordinateList.get(0);
	}

	@Override
	public int evaluateScore(Board board, Player player) {
		// TODO Auto-generated method stub
		return 0;
	}

}
