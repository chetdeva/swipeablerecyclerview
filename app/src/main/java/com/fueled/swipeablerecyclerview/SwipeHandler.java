package com.fueled.swipeablerecyclerview;

/**
 * @author chetansachdeva on 27/07/17
 */

public interface SwipeHandler {

	void onItemSwipedLeft(int position);

	void onItemSwipedRight(int position);
}
