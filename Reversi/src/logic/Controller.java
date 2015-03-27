package logic;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import searchers.AbstractSearcher;
import searchers.AlphaBeta;
import searchers.Greedy;
import searchers.Minimax;
import searchers.SearchResult;
//import searchers.NegaMax;
import core.Board;
import core.Player;

public class Controller {
	private Board board;
	private Player player;
	public static final int DEFAULT_DEPTH = 3;
	private static int depth = DEFAULT_DEPTH;
	/* 0: all good , 1: one cant move , 2: none can move */
	private final short CANMOVE = 0, CANNOTMOVE = 2;
	private short canMove = CANMOVE;

	private Controller() {
		this.board = new Board();
		//init();
	}

/*	public Set<Point> markPossibleMoves() {
		Set<Point> moves = board.getPossibleMoves(player);
		board.markPossibleMoves(moves);
		canMove = moves.isEmpty() ? ++canMove : CANMOVE;
		return moves;
	}*/

	public void unmarkPossibleMoves() {
		board.unmarkPossibleMoves();
	}

	/*public Set<Point> makeMove(Point move) {
		return board.makeMove(move, player.color());
	}*/

	private int calcScore(Character state) {
		return board.count(state);
	}

	public int getBlackScore() {
		return board.count('x');
	}

	public int getWhiteScore() {
		return board.count('o');
	}

	public Player getWinner() {
		return getBlackScore() < getWhiteScore() ? Player.WHITE : Player.BLACK;
	}

	public boolean isDraw() {
		return getBlackScore() == getWhiteScore();
	}

	/**
	 * Game stops if <br/>
	 * <ol>
	 * <li> board is full</li>
	 * <li> one's score is 0/zero</li>
	 * <li> none has a valid next move</li>
	 * </ol>
	 *
	 * @return if the game is over
	 */
	public boolean endOfGame() {
		return board.isFull() || checkZeroScore() || canMove == CANNOTMOVE;
	}

	private boolean checkZeroScore() {
		return getBlackScore() == 0 || getWhiteScore() == 0;
	}

	public void changeTurn() {
		player = player.opponent();
	}

	public Player currentPlayer() {
		return player;
	}

/*	public String boardWithTurn() {
		return board.toStringWithStatsTurn(player);
	}*/

	public void init(char[][] input,Player player) {
		board.init(input);
		player = player;
		//canMove = CANMOVE;
	}

	//public void setDifficulty(DifficultyLevel level) {
		//depth = level.level();
	//}
/*public void asdf(char[][] input)
{
	Set<Point> possibleMoves = new HashSet<Point>();
	char input1[][] = new char[][]
			{
			{'*','*','*','*'},
			{'*','o','x','*'},
			{'*','x','o','*'},
			{'*','*','*','*'}
			};
	
	int[][] table = new int[][]
			{
			{7,4,4,7},
			{4,0,0,4},
			{4,0,0,4},
			{7,4,4,7}
			};
	
	this.board = new Board();
	this.board.init(input1);
	player = Player.BLACK;
	possibleMoves = MoveExplorer.explore(this.board, player );
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
	possibleMoves = this.board.makeMove(coordinateList.get(0), player);
	System.out.println(this.board.toString());
	
}*/

	public void evalMove(int task,int depth,Player player) throws FileNotFoundException {
		AbstractSearcher searcher;
		Evaluation evalfunc;
		SearchResult result ;
		switch(task)
		{
		case 1:  searcher = new Greedy();
				evalfunc = new GreedyEvaluation(); 	;
				result = searcher.simpleSearch(board, player, depth, evalfunc);
				break;
		case 2: searcher = new Minimax();
				evalfunc = new ReversiEvaluation();
				result = searcher.simpleSearch(board, player, depth, evalfunc);
				break;
		case 3: searcher = new AlphaBeta();
				evalfunc = new ReversiEvaluation();
				result = searcher.simpleSearch(board, player, depth, evalfunc);
				break;
		}

	}

	private static class ControllerHolder {

		private static final Controller INSTANCE = new Controller();
	}

	public static Controller getInstance() {
		return ControllerHolder.INSTANCE;
	}
	
	
}

