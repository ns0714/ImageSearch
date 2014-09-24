package com.codepath.gridimagesearch.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.ImageFilter;

public class FilterDialog extends DialogFragment implements OnClickListener {
	private Spinner etImageSize;
	private Spinner etColorFilter;
	private Spinner etImageType;
	private EditText etSiteFilter;
	private Button btnSave;

	public FilterDialog() {

	}

	public interface FilterDialogListener {
		void onFinishedFilterDialog(ImageFilter filter);
	}

	public static FilterDialog newInstance(String title) {
		FilterDialog dialog = new FilterDialog();
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().setTitle(getResources().getText(R.string.advanced_filters));
		View view = inflater
				.inflate(R.layout.activity_filter_dialog, container);
		etImageSize = (Spinner) view.findViewById(R.id.etImageSize);
		etColorFilter = (Spinner) view.findViewById(R.id.etColorFilter);
		etImageType = (Spinner) view.findViewById(R.id.etImageType);
		etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);

		btnSave = (Button) view.findViewById(R.id.btnSave);

		btnSave.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ImageFilter filter = new ImageFilter();
		filter.setImgSize(etImageSize.getSelectedItem().toString());
		filter.setImgColor(etColorFilter.getSelectedItem().toString());
		filter.setImgType(etImageType.getSelectedItem().toString());
		filter.setSite(etSiteFilter.getText().toString());

		FilterDialogListener listener = (FilterDialogListener) getActivity();
		listener.onFinishedFilterDialog(filter);
		dismiss();
	}
}