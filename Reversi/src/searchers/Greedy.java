package searchers;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import logic.Evaluation;
import logic.MoveExplorer;
import core.Board;
import core.Player;

public class Greedy extends AbstractSearcher implements  SimpleSearcher{

	@Override
	public SearchResult search(Board board, Player player, int alpha, int beta,
			int depth, Evaluation function) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchResult simpleSearch(Board board, Player player, int depth,
			Evaluation function) {
		SearchResult result ;
		Set<Point> possibleMoves = new HashSet<Point>();
		Set<Point> flippedPoints = new HashSet<Point>();
		possibleMoves = MoveExplorer.explore(board, player );
		Point move = function.evaluate(board, player, possibleMoves);
		flippedPoints = board.makeMove(move, player);
		System.out.println(board.toString());
		SearchResult best = new SearchResult(null, 0);
		return best;
		}
	}
	
	
