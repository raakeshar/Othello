package core;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import logic.MoveExplorer;

//import logic.MoveExplorer;


public class Board implements Cloneable{
	public static final int BOARD_LENGTH = 8;
	public static final int BOARD_WIDTH = 8;
	private Map<Point, Character> board;
	
	
	public Board() {
		board = new HashMap<Point, Character>(BOARD_LENGTH * BOARD_WIDTH);
		
	}
	
	public Board(Map<Point, Character> board) {
		this.board = new HashMap<Point, Character>(board.size());
		for (Point point : board.keySet()) {
			this.board.put(new Point(point), board.get(point));
		}
	}
	
	public void init(char initial[][]) {
		int x=0,y=0;
		Point point = new Point();
		for (point.x = 0; point.x < BOARD_LENGTH; point.x++) {
			y=0;
			for (point.y = 0; point.y < BOARD_WIDTH; point.y++) {
				board.put(new Point(point), initial[x][y]);
				y++;
			}
			x++;
		}
	
	}
	
	public void markPossibleMoves(Set<Point> possibleMoves) {
		for (Point point : possibleMoves) {
			board.put(point, '.');
		}
	}
	
	public boolean isFull() {
		for (Point point : board.keySet()) {
			if (board.get(point) == '*') {
				return false;
			}
		}
		return true;
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
	
	/*public Set<Point> getPossibleMoves(Player player) {
		return MoveExplorer.explore(this, player.color());
	}*/
	
	public int count(Character state) {
		int count = 0;
		for (Point point : board.keySet()) {
			if (board.get(point) == state) {
				count++;
			}
		}
		return count;
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
	
	public String toString() {
		Point point = new Point();
		StringBuilder sb = new StringBuilder();
		for (point.x = 0; point.x < BOARD_LENGTH; point.x++) {
			
			for (point.y = 0; point.y < BOARD_WIDTH; point.y++) {
				sb.append(board.get(point));
			}
			sb.append('\n');
		}
		//sb.append('\n');
		return sb.toString();
	}

	public Board clone() {
		return new Board(this.board);
	}

	
}
