package core;

/**
 * Players. There are two players in the game. Black and White.
 *
 * 
 */
public enum Player {

	BLACK('X'),
	WHITE('O');
	private Character color;

	private Player(Character color) {
		this.color = color;
	}

	public Player opponent() {
		return this == BLACK ? WHITE : BLACK;
	}

	public Character color() {
		return color;
	}
}
