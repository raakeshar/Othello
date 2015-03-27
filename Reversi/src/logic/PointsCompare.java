package logic;

import java.awt.Point;
import java.util.Comparator;

public class PointsCompare
implements Comparator<Point> {

public int compare(final Point a, final Point b) {
    if (a.x < b.x) {
        return -1;
    }
    else if (a.x > b.x) {
        return 1;
    }
    else if(a.x == b.x){
    	if(a.y < b.y)
    		return -1;
    	else if(a.y > b.y)
        return 1;
    	else
    		return 0;
    }
    else
    	return 0;
}
}
