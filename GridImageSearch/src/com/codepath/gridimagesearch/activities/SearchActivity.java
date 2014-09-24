package com.codepath.gridimagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.adapter.ImageResultsAdapter;
import com.codepath.gridimagesearch.dialog.FilterDialog;
import com.codepath.gridimagesearch.dialog.FilterDialog.FilterDialogListener;
import com.codepath.gridimagesearch.listener.EndlessScrollListener;
import com.codepath.gridimagesearch.models.ImageFilter;
import com.codepath.gridimagesearch.models.ImageResult;
import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SearchActivity extends SherlockFragmentActivity implements FilterDialogListener {
	private SearchView searchView;
	private StaggeredGridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	private String srchQuery;
	private int start = 0;
	private ImageFilter imgFilter;
	private String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupView();
		imageResults = new ArrayList<ImageResult>();
		aImageResults = new ImageResultsAdapter(this, imageResults);
		gvResults.setAdapter(aImageResults);
		if (!isNetworkAvailable()) {
			Toast.makeText(this, "No network available", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void setupView() {

		gvResults = (StaggeredGridView) findViewById(R.id.gvResults);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(SearchActivity.this,
						ImageDisplayActivity.class);
				ImageResult result = imageResults.get(position);
				i.putExtra("result", result);
				startActivity(i);
			}
		});

		gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				if (totalItemsCount < 64) {
					start = totalItemsCount;
					customLoadMoreDataFromApi(page);
				}
			}

			// Append more data into the adapter
			private void customLoadMoreDataFromApi(int offset) {
				makeNetworkCall();
			}
		});
		getCustomizedActionBar();

	}

	public void makeNetworkCall() {
		if (isNetworkAvailable()) {
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			params.put("v", "1.0");
			params.put("rsz", "8");
			params.put("q", srchQuery);
			params.put("start", start);
			if (imgFilter != null) {
				if (!(imgFilter.getImgSize().equals("any"))) {
					params.put("imgsz", imgFilter.getImgSize());
				}
				if (!(imgFilter.getImgColor().equals("any"))) {
					params.put("imgcolor", imgFilter.getImgColor());
				}
				if (!(imgFilter.getImgType().equals("any"))) {
					params.put("imgtype", imgFilter.getImgType());
				}
				if (!(imgFilter.getSite().equals(""))) {
					params.put("as_sitesearch", imgFilter.getSite());
				}
			}

			client.get(searchUrl, params, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					Log.d("DEBUG", response.toString());
					JSONArray imageResultsJson = null;
					try {
						imageResultsJson = response.getJSONObject(
								"responseData").getJSONArray("results");
						if (imageResultsJson.length() == 0) {
							Toast.makeText(getBaseContext(),
									"No results found", Toast.LENGTH_SHORT)
									.show();
							return;
						}
						aImageResults.addAll(ImageResult
								.fromJSONArray(imageResultsJson));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.d("INFO", imageResults.toString());
				};
			});
		}else{
			Toast.makeText(this, "No Network available", Toast.LENGTH_SHORT).show();
		}
	}
	public void newImageSearch() {
		start = 0;
		imageResults.clear();
		aImageResults.clear();
		makeNetworkCall();
	}

	private void getCustomizedActionBar() {
		ActionBar actBar = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#FF949494"));
		actBar.setBackgroundDrawable(colorDrawable);
		/*
		 * int titleId = getResources().getIdentifier("action_bar_title", "id",
		 * "android"); TextView instaView = (TextView) findViewById(titleId);
		 * Typeface font = Typeface.createFromAsset(getAssets(),
		 * "fonts/TR Lucida Handwriting Italic.ttf");
		 * instaView.setTypeface(font);
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.action_search, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);

		MenuInflater settingsMenu = getSupportMenuInflater();
		settingsMenu.inflate(R.menu.search_settings, menu);

		searchView = (SearchView) searchItem.getActionView();
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				// perform query here
				srchQuery = query.trim();
				
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
				newImageSearch();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.settings:
			Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
			/*Intent i = new Intent(this, FilterDialogActivity.class);
			startActivityForResult(i, 50);*/
			FragmentManager frag = getSupportFragmentManager();
			FilterDialog diag = FilterDialog.newInstance("Advanced Filters");
			diag.show(frag, "activity_filter_dialog");
			return true;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 50) {
			if (resultCode == RESULT_OK) {
				imgFilter = (ImageFilter) data.getSerializableExtra("filter");
				newImageSearch();
			}
		}
	}

	private Boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null
				&& activeNetworkInfo.isConnectedOrConnecting();
	}

	@Override
	public void onFinishedFilterDialog(ImageFilter filter) {
		// TODO Auto-generated method stub
		imgFilter = filter;
		newImageSearch();
	}
}
