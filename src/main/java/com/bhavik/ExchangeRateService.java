package com.bhavik;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExchangeRateService {
    private static final String API_URL = "https://open.er-api.com/v6/latest/";

    public double getExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            // 1. Create connection
            URL url = new URL(API_URL + baseCurrency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // 2. Read response
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("API request failed with code: " + responseCode);
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 3. Parse JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            if (!jsonResponse.getString("result").equals("success")) {
                throw new RuntimeException("API returned error: " + jsonResponse.getString("error-type"));
            }

            JSONObject rates = jsonResponse.getJSONObject("rates");
            return rates.getDouble(targetCurrency);

        } catch (Exception e) {
            System.err.println("Error in ExchangeRateService: " + e.getMessage());
            return -1; // Return -1 to indicate failure
        }
    }
}