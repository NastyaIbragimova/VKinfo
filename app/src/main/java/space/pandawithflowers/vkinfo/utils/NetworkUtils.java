package space.pandawithflowers.vkinfo.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NetworkUtils {
    private static final String VK_API_BASE_URL = "https://api.vk.com/";
    private static final String VK_USERS_GET = "method/users.get";
    private static final String PARAM_USER_ID = "user_ids";
    private static final String PARAM_VERSION = "v";
    private static final String ACCESS = "access_token";
    private static final String SERVICE_KEY = "40b67e3a40b67e3a40b67e3a1740c5793f440b640b67e3a1fbbd2a8f389411a02685380";

    public static URL generateURL(String id){
        Uri builtUri = Uri.parse(VK_API_BASE_URL + VK_USERS_GET).buildUpon()
                .appendQueryParameter(PARAM_USER_ID, id)
                .appendQueryParameter(ACCESS, SERVICE_KEY)
                .appendQueryParameter(PARAM_VERSION, "5.21")
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
        }
        Log.d("MyLog", url.toString());
        return url;
    }

    public static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if(hasInput){
                return scanner.next();
            }
            else {
                return null;
            }
        }catch (UnknownHostException e){
            return null;
        }
        finally {
            urlConnection.disconnect();
        }
    }
}
