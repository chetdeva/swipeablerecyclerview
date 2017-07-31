package com.fueled.swipeablerecyclerview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Pair;
import android.view.View;

import com.fueled.swipeablerecyclerview.adapter.RVAdapter;
import com.fueled.swipeablerecyclerview.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeHandler {

	private RVAdapter adapter;
	private ActivityMainBinding binding;
	private List<String> list = new ArrayList<>();
	private Pair<Integer, String> tempItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		binding.setHandler(this);

		setRecyclerView();
	}

	private void setRecyclerView() {
		binding.rv.setLayoutManager(new LinearLayoutManager(this));
		adapter = new RVAdapter(getList());
		binding.rv.setAdapter(adapter);
	}

	public List<String> getList() {
		for (int i = 0; i < 5; i++) {
			list.add("Swipe " + i);
		}
		return list;
	}

	@Override public void onSwipedLeft(int position) {
		saveAndRemoveItem(position);
		showSnackbar("Swiped Left " + position);
	}

	@Override public void onSwipedRight(int position) {
		saveAndRemoveItem(position);
		showSnackbar("Swiped Right " + position);
	}

	private void showSnackbar(String message) {
		Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG)
				.setAction("UNDO", new View.OnClickListener() {
					@Override public void onClick(View v) {
						retractSavedItem();
					}
				}).show();
	}

	private void retractSavedItem() {
		if (null != tempItem && null != tempItem.second) {
			adapter.add(tempItem.first, tempItem.second);
		}
	}

	private void saveAndRemoveItem(int position) {
		tempItem = new Pair<>(position, list.get(position));
		adapter.remove(position);
	}
}
