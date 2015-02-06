package com.negusoft.greenmatter.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.negusoft.greenmatter.example.R;

public class ListFragment extends Fragment implements OnItemClickListener {
	
	private ListView mListView;
	private ArrayAdapter<String> mAdapter;
    private ActionMode mActionMode;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.list, null);
		
		mListView = (ListView)result.findViewById(R.id.listView);
		mAdapter = new ArrayAdapter<String>(getActivity(),
				R.layout.list_item_multiple_choice,
				android.R.id.text1,
				getResources().getStringArray(R.array.list_items));
		mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setMultiChoiceModeListener(mMultiChoiceModeListener);
		mListView.setFastScrollEnabled(true);
		mListView.setFastScrollAlwaysVisible(true);
		
		setHasOptionsMenu(true);
		
		return result;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.list, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		searchView.setOnQueryTextListener(mOnQueryTextListener);
		searchView.setOnCloseListener(mOnCloseListener);
		
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	private final SearchView.OnQueryTextListener mOnQueryTextListener = new SearchView.OnQueryTextListener() {
		@Override public boolean onQueryTextChange(String newText) {
			mAdapter.getFilter().filter(newText);
			return true;
		}
		@Override public boolean onQueryTextSubmit(String query) {
			mAdapter.getFilter().filter(query);
			return true;
		}
	};

	private final SearchView.OnCloseListener mOnCloseListener = new SearchView.OnCloseListener() {
		@Override public boolean onClose() {
			mAdapter.getFilter().filter(null);
			return false;
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (mActionMode != null) {
			if (mListView.getCheckedItemCount() == 0)
                mActionMode.finish();
		} else {
            if (getActivity() instanceof ActionBarActivity) {
                ((ActionBarActivity) getActivity()).getSupportActionBar().startActionMode(mActionModeCallback);
            }
        }
	}

    // Required for the ListView multi-choice mode, but it DOESN'T act as the ActionMode callback.
	private MultiChoiceModeListener mMultiChoiceModeListener = new MultiChoiceModeListener() {
		@Override
		public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
			return true;
		}

		@Override
		public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
			return true;
		}

		@Override
		public void onDestroyActionMode(android.view.ActionMode mode) { }

		@Override
		public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) { }
    };

    // The real ActionMode.Callback for the support action-bar.
    ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.spinner, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            mActionMode = actionMode;
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mActionMode = null;
            for (int pos = 0; pos < mListView.getCount(); pos++)
                mListView.setItemChecked(pos, false);
        }
    };

}
