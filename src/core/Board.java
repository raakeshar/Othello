package core;

import java.awt.Point;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import logic.MoveExplorer;

/**
 * the game board is a mapping of states to points
 * 
 * 
 */
public final class Board implements Cloneable {

	public static final int BOARD_LENGTH = 8;
	public static final int BOARD_WIDTH = 8;
	private Map<Point, Character> board;

	public Board() {
		board = new HashMap<Point, Character>(BOARD_LENGTH * BOARD_WIDTH);
		//init();
	}

	/**
	 * Deep copy constructor. 
	 *
	 * @param board
	 */
	private Board(Map<Point, Character> board) {
		this.board = new HashMap<Point, Character>(board.size());
		for (Point point : board.keySet()) {
			this.board.put(new Point(point), board.get(point));
		}
	}

	public void init(char initial[][]) {
		int x=0,y=0;
		Point point = new Point();
		for (point.x = 0; point.x < BOARD_LENGTH; point.x++) {
			for (point.y = 0; point.y < BOARD_WIDTH; point.y++) {
				board.put(new Point(point), initial[x][y]);
				y++;
			}
			x++;
		}
	/*	board.put(new Point(3, 3), SquareState.WHITE);
		board.put(new Point(3, 4), SquareState.BLACK);
		board.put(new Point(4, 3), SquareState.BLACK);
		board.put(new Point(4, 4), SquareState.WHITE);*/
	}

	public Character getSquareState(Point point) {
		return board.get(point);
	}

	public Set<Point> getSquares(Character state) {
		Set<Point> points = new HashSet<Point>();
		for (Point point : board.keySet()) {
			if (board.get(point) == state) {
				points.add(point);
			}
		}
		return points;
	}

	public boolean isFull() {
		for (Point point : board.keySet()) {
			if (board.get(point) == '*') {
				return false;
			}
		}
		return true;
	}

	public int count(Character state) {
		int count = 0;
		for (Point point : board.keySet()) {
			if (board.get(point) == state) {
				count++;
			}
		}
		return count;
	}

	public Set<Point> getPossibleMoves(Player player) {
		return MoveExplorer.explore(this, player.color());
	}

	public void markPossibleMoves(Set<Point> possibleMoves) {
		for (Point point : possibleMoves) {
			board.put(point, '.');
		}
	}

	public void unmarkPossibleMoves() {
		for (Point point : board.keySet()) {
			if (board.get(point) == '.') {
				board.put(point, '*');
			}
		}
	}

	public void markState(Set<Point> points, Character state) {
		for (Point point : points) {
			board.put(point, state);
		}
	}

	public Set<Point> makeMove(Point move, Player player) {
		board.put(move, player.color());
		Set<Point> changedSquares = MoveExplorer.squaresToFill(this, move);
		markState(changedSquares, player.color());
		changedSquares.add(move);
		return changedSquares;
	}

	@Override
	public String toString() {
		Point point = new Point();
		StringBuilder sb = new StringBuilder();
		sb.append("  A B C D E F G H");
		for (point.x = 0; point.x < BOARD_LENGTH; point.x++) {
			sb.append('\n').append(point.x + 1);
			for (point.y = 0; point.y < BOARD_WIDTH; point.y++) {
				sb.append(' ').append(board.get(point));
			}
		}
		sb.append('\n');
		return sb.toString();
	}

	public String toStringWithStats() {
		StringBuilder sb = new StringBuilder();
		String[] rows = toString().split("\n");
		for (int row = 0; row < rows.length; row++) {
			sb.append('\n').append(rows[row]);
			switch (row) {
				case 2:
					sb.append('\t').append('x').
						append(' ').append(Player.BLACK).
						append(": ").append(count('x'));
					break;
				case 4:
					sb.append('\t').append(SquareState.WHITE.symbol()).
						append(' ').append(Player.WHITE).
						append(": ").append(count('o'));
					break;
			}
		}
		sb.append('\n');
		return sb.toString();
	}

	public String toStringWithStatsTurn(Player player) {
		StringBuilder sb = new StringBuilder();
		String[] rows = toString().split("\n");
		for (int row = 0; row < rows.length; row++) {
			sb.append('\n').append(rows[row]);
			switch (row) {
				case 2:
					sb.append('\t').append('x').
						append(' ').append(Player.BLACK).
						append(": ").append(count('x'));
					break;
				case 4:
					sb.append('\t').append('x').
						append(' ').append(Player.WHITE).
						append(": ").append(count('x'));
					break;
				case 6:
					sb.append('\t').append(player).append("'s turn!");
					break;
			}
		}
		sb.append('\n');
		return sb.toString();
	}

	/**
	 * Deep copy of this board.
	 *
	 * @return
	 */
	@Override
	public Board clone() {
		return new Board(this.board);
	}
}