package com.codepath.gridimagesearch.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.ImageFilter;

public class FilterDialog extends DialogFragment implements OnClickListener {
	private Spinner etImageSize;
	private Spinner etColorFilter;
	private Spinner etImageType;
	private EditText etSiteFilter;
	private Button btnSave;
	private Button btnCancel;

	public FilterDialog() {

	}

	public interface FilterDialogListener {
		void onFinishedFilterDialog(ImageFilter filter);
	}

	public static FilterDialog newInstance(String title) {
		FilterDialog dialog = new FilterDialog();
		dialog.setStyle(dialog.STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setUpDialog();
		
		View view = inflater
				.inflate(R.layout.activity_filter_dialog, container);
		etImageSize = (Spinner) view.findViewById(R.id.etImageSize);
		ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.image_sizes,
						R.layout.spinner_text);
		sizeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		etImageSize.setAdapter(sizeAdapter);

		etColorFilter = (Spinner) view.findViewById(R.id.etColorFilter);
		ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.image_colors,
						R.layout.spinner_text);
		colorAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		etColorFilter.setAdapter(colorAdapter);

		etImageType = (Spinner) view.findViewById(R.id.etImageType);
		ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.image_types,
						R.layout.spinner_text);
		typeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		etImageType.setAdapter(typeAdapter);

		etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);

		btnSave = (Button) view.findViewById(R.id.btnSave);
		btnCancel = (Button) view.findViewById(R.id.btnCancel);

		btnSave.setOnClickListener(this);

		btnCancel.setOnClickListener(this);
		return view;
	}

	public void setUpDialog(){
		getDialog().setTitle(getResources().getText(R.string.advanced_filters));
		getDialog().getWindow().setBackgroundDrawable(
				new ColorDrawable(getResources().getColor(
						R.color.transperant_gray)));
		final WindowManager.LayoutParams params = getDialog().getWindow()
				.getAttributes();
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.gravity = Gravity.CENTER;

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSave: {
			ImageFilter filter = new ImageFilter();
			filter.setImgSize(etImageSize.getSelectedItem().toString());
			filter.setImgColor(etColorFilter.getSelectedItem().toString());
			filter.setImgType(etImageType.getSelectedItem().toString());
			filter.setSite(etSiteFilter.getText().toString());

			setSpinnerToValue(etImageSize, etImageSize.getSelectedItem()
					.toString());
			setSpinnerToValue(etColorFilter, etColorFilter.getSelectedItem()
					.toString());
			setSpinnerToValue(etImageType, etImageType.getSelectedItem()
					.toString());

			FilterDialogListener listener = (FilterDialogListener) getActivity();
			listener.onFinishedFilterDialog(filter);
			dismiss();
		}
		case R.id.btnCancel: {
			dismiss();
		}
		}
	}

	public void setSpinnerToValue(Spinner spinner, String value) {
		int index = 0;
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).equals(value)) {
				index = i;
				break; // terminate loop
			}
		}
		spinner.setSelection(index);
	}
}