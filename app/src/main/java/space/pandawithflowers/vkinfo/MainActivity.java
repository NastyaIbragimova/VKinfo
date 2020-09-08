package space.pandawithflowers.vkinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import static space.pandawithflowers.vkinfo.utils.NetworkUtils.generateURL;
import static space.pandawithflowers.vkinfo.utils.NetworkUtils.getResponseFromURL;

public class MainActivity extends AppCompatActivity {
    private EditText searchField;
    private TextView result;
    private TextView errorMessage;
    private ProgressBar load;

    private void showResultTextView() {
        result.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
    }

    private void showErrorTextView() {
        result.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    class VKQueryTask extends AsyncTask<URL, Void, String> {


        @Override
        protected void onPreExecute(){
            load.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(URL... urls) {
            String response = "Something";
            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e) {
                response = "WTF";
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            String firstName = null;
            String lastName = null;
            String resultString = "";
            if (response != null && !response.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject userInfo = jsonArray.getJSONObject(i);
                        firstName = userInfo.getString("first_name");
                        lastName = userInfo.getString("last_name");
                        resultString += "Имя: " + firstName + "\n" + "Фамилия: " + lastName + "\n\n";
                    }
                    result.setText(resultString);
                } catch (JSONException e) {
                }


                showResultTextView();
            }
            else {
                showErrorTextView();
            }
            load.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchField = findViewById(R.id.search_field);
        result = findViewById(R.id.result);
        errorMessage = findViewById(R.id.error_message);
        load = findViewById(R.id.loaded);
    }

    public void onClickSearch(View view) {
        URL generateURL = generateURL(searchField.getText().toString());
        new VKQueryTask().execute(generateURL);
    }
}