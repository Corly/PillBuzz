package ro.medapp1;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;


////////////////clasa asta e doar un mic artificiu prin care setam valorile min si max din xml si nu programatic
///////////////am facut asta deoarece acolo unde trebuia sa le setez nu ma lasa sa instantiez numberpicker-ul


public class CustomNumberPicker  extends NumberPicker {

	    public CustomNumberPicker(Context context) {
	        super(context);
	    }

	    public CustomNumberPicker(Context context, AttributeSet attrs) {
	        super(context, attrs);
	        processAttributeSet(attrs);
	    }

	    public CustomNumberPicker(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	        processAttributeSet(attrs);
	    }
	    private void processAttributeSet(AttributeSet attrs) {
	        //This method reads the parameters given in the xml file and sets the properties according to it
	        this.setMinValue(attrs.getAttributeIntValue(null, "min", 0));
	        this.setMaxValue(attrs.getAttributeIntValue(null, "max", 0));
	    }
	}

