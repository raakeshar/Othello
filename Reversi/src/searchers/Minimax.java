package searchers;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import logic.Evaluation;
import logic.MoveExplorer;
import logic.PointsCompare;
import core.Board;
import core.Player;

public class Minimax  extends AbstractSearcher implements Searcher, SimpleSearcher {
	
	public static boolean canPrint = true;
	private static Map<Point, String> lookupBoard = new HashMap<Point,String>();
	private static Map<Integer, Integer> depthLookup = new HashMap<Integer,Integer>();
	static String outputPath ;
	static String output ;
	static PrintWriter writerLog ;
	
	
	
	public Minimax() throws FileNotFoundException
	{
		outputPath = "/home/rakesh/Desktop/outputlog.txt";
		output = "/home/rakesh/Desktop/output.txt";
		writerLog = new PrintWriter(outputPath);
		lookupBoard.put(new Point(0, 0),"a1" );
		lookupBoard.put(new Point(0, 1),"b1" );
		lookupBoard.put(new Point(0, 2),"c1" );
		lookupBoard.put(new Point(0, 3),"d1" );
		lookupBoard.put(new Point(0, 4),"e1" );
		lookupBoard.put(new Point(0, 5),"f1" );
		lookupBoard.put(new Point(0, 6),"g1" );
		lookupBoard.put(new Point(0, 7),"h1" );
		lookupBoard.put(new Point(1, 0),"a2" );
		lookupBoard.put(new Point(1, 1),"b2" );
		lookupBoard.put(new Point(1, 2),"c2" );
		lookupBoard.put(new Point(1, 3),"d2" );
		lookupBoard.put(new Point(1, 4),"e2" );
		lookupBoard.put(new Point(1, 5),"f2" );
		lookupBoard.put(new Point(1, 6),"g2" );
		lookupBoard.put(new Point(1, 7),"h2" );
		lookupBoard.put(new Point(2, 0),"a3" );
		lookupBoard.put(new Point(2, 1),"b3" );
		lookupBoard.put(new Point(2, 2),"c3" );
		lookupBoard.put(new Point(2, 3),"d3" );
		lookupBoard.put(new Point(2, 4),"e3" );
		lookupBoard.put(new Point(2, 5),"f3" );
		lookupBoard.put(new Point(2, 6),"g3" );
		lookupBoard.put(new Point(2, 7),"h3" );
		lookupBoard.put(new Point(3, 0),"a4" );
		lookupBoard.put(new Point(3, 1),"b4" );
		lookupBoard.put(new Point(3, 2),"c4" );
		lookupBoard.put(new Point(3, 3),"d4" );
		lookupBoard.put(new Point(3, 4),"e4" );
		lookupBoard.put(new Point(3, 5),"f4" );
		lookupBoard.put(new Point(3, 6),"g4" );
		lookupBoard.put(new Point(3, 7),"h4" );
		lookupBoard.put(new Point(4, 0),"a5" );
		lookupBoard.put(new Point(4, 1),"b5" );
		lookupBoard.put(new Point(4, 2),"c5" );
		lookupBoard.put(new Point(4, 3),"d5" );
		lookupBoard.put(new Point(4, 4),"e5" );
		lookupBoard.put(new Point(4, 5),"f5" );
		lookupBoard.put(new Point(4, 6),"g5" );
		lookupBoard.put(new Point(4, 7),"h5" );
		lookupBoard.put(new Point(5, 0),"a6" );
		lookupBoard.put(new Point(5, 1),"b6" );
		lookupBoard.put(new Point(5, 2),"c6" );
		lookupBoard.put(new Point(5, 3),"d6" );
		lookupBoard.put(new Point(5, 4),"e6" );
		lookupBoard.put(new Point(5, 5),"f6" );
		lookupBoard.put(new Point(5, 6),"g6" );
		lookupBoard.put(new Point(5, 7),"h6" );
		lookupBoard.put(new Point(6, 0),"a7" );
		lookupBoard.put(new Point(6, 1),"b7" );
		lookupBoard.put(new Point(6, 2),"c7" );
		lookupBoard.put(new Point(6, 3),"d7" );
		lookupBoard.put(new Point(6, 4),"e7" );
		lookupBoard.put(new Point(6, 5),"f7" );
		lookupBoard.put(new Point(6, 6),"g7" );
		lookupBoard.put(new Point(6, 7),"h7" );
		lookupBoard.put(new Point(7, 0),"a8" );
		lookupBoard.put(new Point(7, 1),"b8" );
		lookupBoard.put(new Point(7, 2),"c8" );
		lookupBoard.put(new Point(7, 3),"d8" );
		lookupBoard.put(new Point(7, 4),"e8" );
		lookupBoard.put(new Point(7, 5),"f8" );
		lookupBoard.put(new Point(7, 6),"g8" );
		lookupBoard.put(new Point(7, 7),"h8" );
		
	}


	public SearchResult minimax(Board board, Player player, int depth, Evaluation function) throws FileNotFoundException {
		depthLookup(depth);
		if (player == Player.BLACK) {
			writerLog.println("Node,Depth,Value");/* White is the maximizing player */
			return valueMax(board, player, depth, function,null);
			
		} else {			/* Black is the minimizing player */
			return valueMin(board, player, depth, function,null);
		}
	}
	
	public void depthLookup(int depth)
	{
		 int j=0;
		 for(int i=depth;i>=0;i--)
		 {
			 depthLookup.put(i, j);
			 j++;
		 }
		
	}

	private SearchResult valueMax(Board board, Player player, int depth, Evaluation function,Point root) throws FileNotFoundException 
	{
		Set<SearchResult> resultSet = new HashSet<SearchResult>();
		if (depth <= 0 || isEndState(board)) {
			return new SearchResult(null, function.evaluateScore(board, player));
		} else { /* there's more to check */
			if(canPrint)
			{
				if(root == null)
					writerLog.println("root,"+depthLookup.get(depth)+","+"-infinity");
				
				canPrint = false;
			}
			Set<Point> possibleMoves = MoveExplorer.explore(board, player);
			possibleMoves = SortPossibleMoves(possibleMoves);
			SearchResult best = new SearchResult(null, Integer.MIN_VALUE);
			if (possibleMoves.isEmpty()) { /* turn is lost - check next player */
				possibleMoves = MoveExplorer.explore(board, player.opponent());
				if (possibleMoves.isEmpty()) { /* end of game - is there a winner ? */
					return new SearchResult(null, function.evaluateScore(board, player));
				} else { /* game continues - no moves to check */
					best = valueMin(board, player.opponent(), depth - 1, function,root);
				}
			} else { /* check the score of each move */
				for (Point nextPossibleMove : possibleMoves) {
					Board subBoard = board.clone();
					subBoard.makeMove(nextPossibleMove, player);
					
					int score = valueMin(subBoard, player.opponent(), depth - 1, function,nextPossibleMove).getScore();
					if(depth == 1)
					{
						writerLog.println(lookupBoard.get(nextPossibleMove)+","+depthLookup.get(depth)+","+score);
						writerLog.println("root"+","+depthLookup.get(depth)+","+score);
					}
						
					else
						writerLog.println("root"+","+depthLookup.get(depth)+","+score);	
					
					int count =0;
					if(best.getScore() == score)
					{
						resultSet.add(new SearchResult(nextPossibleMove, score));
					}
					if ( score > best.getScore()) {
						
						/* store the best score and coresponding move */
						
						best = new SearchResult(nextPossibleMove, score);
						resultSet.add(best);
						count++;
					}
					
					
				}
				
			}
			if(resultSet.size() > 1)
			{
				ArrayList<Point> coordinateList = new ArrayList<Point>();
				for (SearchResult seed : resultSet) 
				{
					
						coordinateList.add(seed.getPoint());
				}
				Collections.sort(coordinateList, new PointsCompare());
				int val = best.getScore();
				best = new SearchResult(coordinateList.get(0),val);
			}
			board.makeMove(best.getPoint(), player);
			writerLog.close();
			writerLog = new PrintWriter(output);
			writerLog.println(board.toString());
			return best;
		}
			
				
	}

	private SearchResult valueMin(Board board, Player player, int depth, Evaluation function,Point root) throws FileNotFoundException {
		Set<SearchResult> resultSet = new HashSet<SearchResult>();
		if (depth <= 0 || isEndState(board)) {
			return new SearchResult(null, function.evaluateScore(board, player));
		} else { /* there's more to check */
			Set<Point> possibleMoves = MoveExplorer.explore(board, player);
			possibleMoves = SortPossibleMoves(possibleMoves);
			SearchResult best = new SearchResult(null, Integer.MAX_VALUE);
			if (possibleMoves.isEmpty()) { /* turn is lost - check next player */
				possibleMoves = MoveExplorer.explore(board, player.opponent());
				if (possibleMoves.isEmpty()) { /* end of game - is there a winner ? */
					return new SearchResult(null, function.evaluateScore(board, player));
				} else { /* game continues - no moves to check */
					best = valueMax(board, player.opponent(), depth - 1, function,root);
				}
			} else { /* check the score of each move */
				for (Point nextPossibleMove : possibleMoves) {
					Board subBoard = board.clone();
					subBoard.makeMove(nextPossibleMove, player);
					writerLog.println(lookupBoard.get(root)+","+depthLookup.get(depth)+","+best.getScore());
					int score = valueMax(subBoard, player.opponent(), depth - 1, function,nextPossibleMove).getScore();
					writerLog.println(lookupBoard.get(nextPossibleMove)+","+depthLookup.get(depth-1)+","+score);
					int count=0;
					if(best.getScore() == score)
					{
						resultSet.add(new SearchResult(nextPossibleMove, score));
					}
					if (best.getScore() > score) {
						/* store the best score and coresponding move */
						best = new SearchResult(nextPossibleMove, score);
						resultSet.add(best);
						count++;
					}
					
				}
			}
			if(resultSet.size() > 1)
			{
				ArrayList<Point> coordinateList = new ArrayList<Point>();
				for (SearchResult seed : resultSet) 
				{
					
						coordinateList.add(seed.getPoint());
				}
				Collections.sort(coordinateList, new PointsCompare());
				int val = best.getScore();
				best = new SearchResult(coordinateList.get(0),val);
			}
			writerLog.println(lookupBoard.get(root)+","+depthLookup.get(depth)+","+best.getScore());
			return best;
		}
	}

	@Override
	public SearchResult search(Board board, Player player, int alpha, int beta,
			int depth, Evaluation function) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchResult simpleSearch(Board board, Player player, int depth,
			Evaluation function) throws FileNotFoundException {
		
		
		SearchResult best = minimax(board, player, depth, function);
		writerLog.close();
		WriteFile();
		return best;
	}
	
	public void WriteFile()
	{
		try{
			
	        BufferedReader br = new BufferedReader(new FileReader("/home/rakesh/Desktop/output.txt"));
	        BufferedReader br2 = new BufferedReader(new FileReader("/home/rakesh/Desktop/outputlog.txt"));
	        String s1 = br.readLine();
	        StringBuilder sb = new StringBuilder();
	        
	        
	       // writerLog = new PrintWriter(output);
	        while(s1 != null){
	        	sb.append(s1);
	        	
	        	s1 = br.readLine();
	        	if(s1 != null)
	        		sb.append('\n');
	        }
	        s1 = br2.readLine();
	        while( s1 != null){
	        	sb.append(s1);
	        	sb.append('\n');
	        	s1 = br2.readLine();
	        }
	        br2.close();
	        br.close();
	        writerLog = new PrintWriter(output);
	        writerLog.println(sb.toString());
	        
	        writerLog.close();
		/*	String output = "/home/rakesh/Desktop/output.txt";
			BufferedReader br = new BufferedReader(new FileReader("/home/rakesh/Desktop/outputlog.txt"));
	        Stack<String> lines = new Stack<String>();
	        writerLog = new PrintWriter(output);
	        String line = br.readLine();
	        while(line != null) {
	            lines.push(line);
	            line = br.readLine();
	        }

	        while(! lines.empty()) {
	        	writerLog.println(lines.pop());
	        }
	        writerLog.close();
			/*String line;
			FileReader logReader = new FileReader("/home/rakesh/Desktop/outputlog.txt");
	        BufferedReader buffer = new BufferedReader(logReader);
	        
	        for (String line1 = buffer.readLine(); line1 != null; line1 = buffer.readLine()) {
	        	writerLog.println(line1);
	        }
	        while ((line = reader.readLine()) != null) {
	        	writerLog.println(line);
			}
	        writerLog.close();
		    /*  File sourceFile=new File("/home/rakesh/Desktop/outputlog.txt");
		      Scanner content=new Scanner(sourceFile);
		      

		      while(content.hasNextLine())
		      {
		         String s=content.nextLine();
		         StringBuffer buffer = new StringBuffer(s);
		         buffer=buffer.reverse();
		         String rs=buffer.toString();
		         writerLog.println(rs);
		      }
		      content.close();    
		      writerLog.close();*/
		      System.out.println("File is copied successful!");
		      }

		      catch(Exception e){
		          System.out.println("Something went wrong");
		      }
	}
	
	public Set<Point> SortPossibleMoves(Set<Point> possibleMoves) {

		Set<Point> result = new LinkedHashSet<Point>();
		ArrayList<Point> coordinateList = new ArrayList<Point>();
		
		for (Point seed : possibleMoves) 
		{
			
				coordinateList.add(seed);
		}
		
		Collections.sort(coordinateList, new PointsCompare());
		
		/*for(int j = coordinateList.size() - 1; j >= 0; j--){
			result.add(coordinateList.get(j));
			}*/
		
		for(Point seed : coordinateList)
		{
			result.add(seed);
		}
		/*Point last = coordinateList.get(coordinateList.size()-1);
		result.remove(last);
		result.add(e)*/
		return result;
	}

}
