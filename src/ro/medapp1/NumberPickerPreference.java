package ro.medapp1;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

public class NumberPickerPreference extends DialogPreference {
	private NumberPicker picker;

	public NumberPickerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		setDialogLayoutResource(R.layout.numberpicker_dialog);
		setPositiveButtonText(android.R.string.ok);
		setNegativeButtonText(android.R.string.cancel);
		setDialogIcon(null);
	}

	@Override
	protected View onCreateDialogView() {
		picker = new NumberPicker(getContext());
		picker.setMaxValue(500);
		picker.setMinValue(0);
		return picker;
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		// When the user selects "OK", persist the new value
		if (positiveResult) {
			int number = picker.getValue();
			persistInt(number);
			setSummary(number + "");
		}
	}

	@Override
	protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
		if (restorePersistedValue) {
			// Restore existing state
			// mCurrentValue = this.getPersistedInt(DEFAULT_VALUE);
		} else {
			// Set default state from the XML attribute
			//mCurrentValue = (Integer) defaultValue;
			//persistInt(mCurrentValue);
		}
	}

	//		@Override
	//		protected Object onGetDefaultValue(TypedArray a, int index) {
	//		    return a.getInteger(index, DEFAULT_VALUE);
	//		}
}
