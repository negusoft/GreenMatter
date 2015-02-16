package com.negusoft.greenmatter.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.negusoft.greenmatter.drawable.IndeterminateProgressBarDrawable;
import com.negusoft.greenmatter.example.R;

public class ProgressFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.progress, null);
	}
	
	@Override
	public void onStart() {
		super.onStart();
        getActivity().findViewById(R.id.myseekbar1).setEnabled(false);
        getActivity().findViewById(R.id.myseekbar2).setEnabled(false);
	}

}
