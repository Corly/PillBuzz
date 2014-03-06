package ro.medapp1;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

// clasa asta deocmdata doar ofera interfata insa nu pastreaza informatia
//trebuie sa implementam noi metodele in functie de cum o sa o pastram
public class DatePickerPreference extends DialogPreference {

	public DatePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        setDialogLayoutResource(R.layout.datepicker_dialog);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
        
        setDialogIcon(null);
    }
	@Override
	protected void onDialogClosed(boolean positiveResult) {
	    // When the user selects "OK", persist the new value
//	    if (positiveResult) {
//	        persistInt(mNewValue);
//	    }
	}
	
	@Override
	protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
//	    if (restorePersistedValue) {
//	        // Restore existing state
//	        mCurrentValue = this.getPersistedInt(DEFAULT_VALUE);
//	    } else {
//	        // Set default state from the XML attribute
//	       mCurrentValue = (Integer) defaultValue;
//	       persistInt(mCurrentValue);
//	    }
	}
	
//	@Override
//	protected Object onGetDefaultValue(TypedArray a, int index) {
//	    return a.getInteger(index, DEFAULT_VALUE);
//	}

}
