package com.contentstack.volley.error;

import android.content.Context;

import com.contentstack.volley.NetworkResponse;

public class VolleyErrorHelper {
	/**
	 * Returns appropriate message which is to be displayed to the user against
	 * the specified error object.
	 * 
	 * @param error
	 * @param context
	 * @return
	 */
	public static String getMessage(Object error, Context context) {
		if (error instanceof TimeoutError) {
			return "Server down";
		} else if (isServerProblem(error)) {
			return handleServerError(error, context);
		} else if (isNetworkProblem(error)) {
			return "No network connection found";
		}
		return "No internet";
	}

	/**
	 * Determines whether the error is related to network
	 * 
	 * @param error
	 * @return
	 */
	private static boolean isNetworkProblem(Object error) {
		return (error instanceof NetworkError) || (error instanceof NoConnectionError);
	}

	/**
	 * Determines whether the error is related to server
	 * 
	 * @param error
	 * @return
	 */
	private static boolean isServerProblem(Object error) {
		return (error instanceof ServerError) || (error instanceof AuthFailureError);
	}

	/**
	 * Handles the server error, tries to determine whether to show a stock
	 * message or to show a message retrieved from the server.
	 * 
	 * @param err
	 * @param context
	 * @return
	 */
	private static String handleServerError(Object err, Context context) {
		VolleyError error = (VolleyError) err;

		NetworkResponse response = error.networkResponse;

		if (response != null) {
			switch (response.statusCode) {
			case 404:
			case 422:
			case 401:
				try {
					// server might return error like this { "error":
					// "Some error occured" }
					// Use "Gson" to parse the result
					String res = new String(response.data);
					/*HashMap<String, String> result = new Gson().fromJson(new String(response.data),
							new TypeToken<Map<String, String>>() { }.getType());*/

					if (res != null /*&& result.containsKey("error")*/) {
						return res;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				// invalid request
				return error.getMessage();

			default:
				return "Server down";
			}
		}
		return "No internet";
	}
}