package com.tajimz.volleyquick;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * MainActivity demonstrates how to use the VolleyQuick library
 * for making JSON HTTP requests with optional custom loading dialogs.
 *
 * Features:
 * - POST request with JSON object body
 * - Optional loading dialog while the request is ongoing
 * - Success and error callbacks
 * - View padding handling for edge-to-edge devices
 */
public class MainActivity extends AppCompatActivity {

    // Button to trigger HTTP request
    private Button btnFetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable Edge-to-Edge display for full-screen layouts
        EdgeToEdge.enable(this);

        // Set layout
        setContentView(R.layout.activity_main);

        // Find the button in layout
        btnFetch = findViewById(R.id.btnFetch);

        // Adjust root view padding to avoid system bars (status/navigation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set click listener for the button
        btnFetch.setOnClickListener(v -> callJsonObj());
    }

    /**
     * Makes a POST request with a JSON object using VolleyQuick.
     *
     * Demonstrates:
     * - Creating a JSON object
     * - Adding parameters using putInJsonObj()
     * - Making a requestObj call with a custom loading dialog
     * - Handling success and error callbacks
     */
    private void callJsonObj() {

        // Create JSON object to send as request body
        JSONObject jsonObject = new JSONObject();
        VolleyQuick.putInJsonObj(jsonObject, "par1", "par1Val");
        VolleyQuick.putInJsonObj(jsonObject, "par2", "par2Val");

        // Make POST request using VolleyQuick
        VolleyQuick.requestObj(
                this,                       // Context
                R.layout.loading,           // Custom loading layout; put 0 for no dialog
                Request.Method.POST,        // HTTP method
                "https://dummyjson.com/test", // API URL
                jsonObject,                 // JSON body
                new VolleyQuick.ObjListener() { // Listener for response
                    @Override
                    public void onSuccess(JSONObject responseJson) {
                        // Retrieve "status" field from JSON response
                        String status = VolleyQuick.getFromJsonObj(responseJson, "status");

                        // Log the status
                        Log.d("volleyQuick", "Request successful. Status: " + status);
                    }

                    @Override
                    public void onFailed(VolleyError volleyError) {
                        // Log the error message
                        String errorMsg = volleyError.getMessage() != null ? volleyError.getMessage() : "Unknown error";
                        Log.d("volleyQuick", "Request failed. Error: " + errorMsg);
                    }
                }
        );
    }
}
