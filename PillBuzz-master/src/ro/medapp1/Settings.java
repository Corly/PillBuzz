package ro.medapp1;

import java.util.Locale;



import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Settings extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter1 mSectionsPagerAdapter1;


	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);


		mSectionsPagerAdapter1 = new SectionsPagerAdapter1(
				getSupportFragmentManager());
	
		
		
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.settings_pager);
		mViewPager.setAdapter(mSectionsPagerAdapter1);
	


	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.settings, menu);
//		return true;
//	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter1 extends FragmentPagerAdapter {

		public SectionsPagerAdapter1(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment=null;
			
			switch(position)
			{
				case 0:
				{
					MedSetFragment frag=new MedSetFragment();
					frag.setContext(Settings.this);
					fragment=(Fragment)frag;
			
				}break;
				case 1:
				{
					UserSetFragment frag=new UserSetFragment();
					fragment=(Fragment)frag;
				
				}break;
				case 2:
				{
					BraceletSetFragment frag=new BraceletSetFragment();
					fragment=(Fragment)frag;			
					
				}break;
			
			}
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return "MEDS";
			case 1:
				return "USER";
			case 2:
				return "BRACELET";
			}
			return null;
		}
	}

}