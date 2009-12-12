/*
 * Copyright (C) 2009 Alejandro Grijalba
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */


package com.latinsud.android.slidetypekeyboard;


import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/* AboutActivity is a submenu of MainActivity */
public class AboutActivity extends ListActivity {
	
	protected String versionName;
		  
	  
	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.about);

		
		// populate main menu
      	List<Map<String, String>> list = new ArrayList<Map<String, String>>();

      	String[] menu = getResources().getStringArray(R.array.about_menu);
      	String[] menuDesc = getResources().getStringArray(R.array.about_menu_desc);      	
 
      	
      	// convert 2 arrays of strings into one list of maps of strings
      	for (int i=0; i<menu.length; i++) {
      		Map<String, String> map = new HashMap<String, String>();
      		// Modifiy "About" entry to include version number 
      		if (i == M_CHANGELOG)
      			map.put("text1", menu[i] + " (v" + getVersionName() + ")");
      		else
      			map.put("text1", menu[i]);
      		map.put("text2", menuDesc[i]);
      		list.add(map);
      	}
      	String[] from = {"text1","text2"};
      	int[] to = {android.R.id.text1, android.R.id.text2};
      	
      	// let built-in function do the magic (put the strings into the textviews)
      	SimpleAdapter sa = new SimpleAdapter(this.getApplicationContext(), 
      			list, 
      			android.R.layout.simple_list_item_2, 
      			from, 
      			to);
      	
          // Use our own list adapter
          setListAdapter(sa);
	}

	private static final int M_CHANGELOG=0;
	private static final int M_LICENSE=1;
	private static final int M_EMAIL=2;
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent;
		
		switch (position) {
			case M_CHANGELOG:
				intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("http://code.google.com/p/slidetypekeyboard/wiki/ChangeLog"));
				intent.setFlags(/*Intent.FLAG_ACTIVITY_NO_HISTORY|*/Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(intent);
				break;
			case M_LICENSE:
		        intent = new Intent();
		        intent.setClass(AboutActivity.this, LicenseActivity.class);
		        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setFlags(/*Intent.FLAG_ACTIVITY_NO_HISTORY|*/Intent.FLAG_ACTIVITY_CLEAR_TOP);		        
		        startActivity(intent);
				break;
			case M_EMAIL:
				intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
						"mailto", "sdsd@latinsud.com", null));
				intent.setFlags(/*Intent.FLAG_ACTIVITY_NO_HISTORY|*/Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra(Intent.EXTRA_SUBJECT,"About SlideType " + getVersionName());
		        startActivity(intent);				
				break;
		}
	}


	

	protected String getVersionName() {
		if (versionName==null) {
			try {
				PackageInfo pinfo = this.getPackageManager().getPackageInfo(this.getClass().getPackage().getName(), 0);
				versionName=pinfo.versionName;
			} catch (Exception e) {
				versionName="";
			}
		}
		
		return versionName;
	}
	
	
}
