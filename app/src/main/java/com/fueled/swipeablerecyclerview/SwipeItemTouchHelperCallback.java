package com.fueled.swipeablerecyclerview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.fueled.swipeablerecyclerview.util.ViewUtil;

/**
 * Reference @link {https://www.learn2crack.com/2016/02/custom-swipe-recyclerview.html}
 *
 * @author chetansachdeva on 26/07/17
 */

public class SwipeItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {

	private Drawable drawableLeft, drawableRight;
	private Paint paintLeft, paintRight;
	private OnItemSwipeListener onItemSwipeLeftListener, onItemSwipeRightListener;
	private boolean swipeEnabled;

	private SwipeItemTouchHelperCallback(int dragDirs, int swipeDirs) {
		super(dragDirs, swipeDirs);
	}

	private SwipeItemTouchHelperCallback(Builder builder) {
		this(builder.dragDirs, builder.swipeDirs);
		setPaintColor(paintLeft = new Paint(Paint.ANTI_ALIAS_FLAG), builder.bgColorSwipeLeft);
		setPaintColor(paintRight = new Paint(Paint.ANTI_ALIAS_FLAG), builder.bgColorSwipeRight);
		drawableLeft = builder.drawableLeft;
		drawableRight = builder.drawableRight;
		swipeEnabled = builder.swipeEnabled;
		onItemSwipeLeftListener = builder.onItemSwipeLeftListener;
		onItemSwipeRightListener = builder.onItemSwipeRightListener;
	}

	private void setPaintColor(Paint paint, int color) {
		paint.setColor(color);
	}


	@Override public boolean isItemViewSwipeEnabled() {
		return swipeEnabled;
	}

	@Override
	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
		return false;
	}

	@Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
		int position = viewHolder.getAdapterPosition();
		if (direction == ItemTouchHelper.LEFT) {
			onItemSwipeLeftListener.onItemSwiped(position);
		} else if (direction == ItemTouchHelper.RIGHT) {
			onItemSwipeRightListener.onItemSwiped(position);
		}
	}

	@Override
	public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

		if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

			View itemView = viewHolder.itemView;
			float height = (float) itemView.getBottom() - (float) itemView.getTop();
			float width = height / 3;

			if (dX > 0) {
				RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
				RectF iconDest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
				c.drawRect(background, paintLeft);
				c.drawBitmap(ViewUtil.getBitmap(drawableLeft), null, iconDest, paintLeft);
			} else {
				RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
				RectF iconDest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
				c.drawRect(background, paintRight);
				c.drawBitmap(ViewUtil.getBitmap(drawableRight), null, iconDest, paintRight);
			}
		}
		super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
	}

	public interface OnItemSwipeListener {
		void onItemSwiped(int position);
	}

	public static final class Builder {
		private int dragDirs, swipeDirs;
		private Drawable drawableLeft, drawableRight;
		private int bgColorSwipeLeft, bgColorSwipeRight;
		private OnItemSwipeListener onItemSwipeLeftListener, onItemSwipeRightListener;
		private boolean swipeEnabled;

		public Builder(int dragDirs, int swipeDirs) {
			this.dragDirs = dragDirs;
			this.swipeDirs = swipeDirs;
		}

		public Builder drawableLeft(Drawable val) {
			drawableLeft = val;
			return this;
		}

		public Builder drawableRight(Drawable val) {
			drawableRight = val;
			return this;
		}

		public Builder bgColorSwipeLeft(int val) {
			bgColorSwipeLeft = val;
			return this;
		}

		public Builder bgColorSwipeRight(int val) {
			bgColorSwipeRight = val;
			return this;
		}

		public Builder onItemSwipeLeftListener(OnItemSwipeListener val) {
			onItemSwipeLeftListener = val;
			return this;
		}

		public Builder onItemSwipeRightListener(OnItemSwipeListener val) {
			onItemSwipeRightListener = val;
			return this;
		}

		public Builder setSwipeEnabled(boolean val) {
			swipeEnabled = val;
			return this;
		}

		public SwipeItemTouchHelperCallback build() {
			return new SwipeItemTouchHelperCallback(this);
		}
	}
}
