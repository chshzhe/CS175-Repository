package com.example.chapter3.demo.fragment.masterdetail;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.example.chapter3.demo.R;

public class ItemsListActivity extends FragmentActivity implements ItemsListFragment.OnItemSelectedListener {
	private boolean isTwoPane = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
		determinePaneLayout();
	}

	private void determinePaneLayout() {
		FrameLayout fragmentItemDetail = findViewById(R.id.flDetailContainer);
		if (fragmentItemDetail != null) {
			isTwoPane = true;
			ItemsListFragment fragmentItemsList = 
					(ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentItemsList);
			fragmentItemsList.setActivateOnItemClick(true);
		}
	}

	@Override
	public void onItemSelected(Item item) {
		if (isTwoPane) { // single activity with list and detail
			// Replace frame layout with correct detail fragment
			ItemDetailFragment fragmentItem = ItemDetailFragment.newInstance(item);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.flDetailContainer, fragmentItem);
			ft.commit();
		} else { // separate activities
			// launch detail activity using intent
			Intent i = new Intent(this, ItemDetailActivity.class);
			i.putExtra("item", item);
			startActivity(i);
		}
	}

}
