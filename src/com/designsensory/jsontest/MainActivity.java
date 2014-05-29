package com.designsensory.jsontest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	public static TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
        Log.d("JSON part", "made it thus far.");    
		new DownloadJSON().execute("http://tntrailsandbyways.com/action/datapull.php?trail=1");
		Log.d("JSON part", "made it past JSON.");
		try {
			Log.d("TESTING JSON", new JSONObject().put("id", 1).toString());
			Log.d("TESTING JSON", new JSONObject().put("id", "1").toString());
			Log.d("TESTING JSON", new JSONObject().put("datboolean", true).toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }
    
    private class DownloadJSON extends AsyncTask<String, Void, String>{

    	@Override
    	protected String doInBackground(String... urls) {
    		// TODO Auto-generated method stub
    		try{
    			JSONObject json = null;
    			json = readJsonFromUrl(urls[0]);
    			return json.toString();
    		}catch(IOException e){
    			e.printStackTrace();
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		return null;
    	}
    	
    	// onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        	Log.d("In PostExecute", result);
            tv.setText(result);
            Log.d("In PostExecute", "text set.");
       }

    	public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    		InputStream is;
    		try{
    			is = new URL(url).openStream();
    		 Log.d("Deep in JSON", "opened stream");
    		}finally{
    			Log.d("Deep in JSON", "failed to opened stream");
    		}
    		try{
    			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
    			StringBuilder sb = new StringBuilder();
    			int charval;
    			while ((charval = rd.read()) != -1)
    			{
    				sb.append((char) charval);
    			}
    			String jsonText = sb.toString();
    			JSONObject json = new JSONObject(jsonText);
    			return json;
    		} finally {
    			is.close(); //close stream
    		}
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            tv = (TextView) rootView.findViewById(R.id.tv1);
            return rootView;
        }
    }

}
