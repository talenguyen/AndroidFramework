package com.talenguyen.androidframework.module.http;

/**
 * Created with IntelliJ IDEA.
 * User: GIANG
 * Date: 12/20/13
 * Time: 5:11 PM
 */

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.toolbox.Volley;

public class HttpClient {

    private static RequestQueue mQueue = null;

    /**
     * The convenience method to init the {@RequestQueue} object. It should be called at the first time the application
     * started. May be good practice to call it in onCreate method of the Application
     *
     * @param context The {@Context} object
     */
    public static void init(Context context) {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(context);
        }
    }

    /**
     * Cancel all requests in queue
     */
    public static void cancelAll() {
        mQueue.cancelAll(new RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    /**
     * Send an http request. This request will be add to queue.
     *
     * @param request The request to be execute
     * @return The passed-in request
     */
    public static Request<?> sendRequest(Request<?> request) {
        return mQueue.add(request);
    }

    /**
     * Destroy the queue. This should be called when you plan to exit the application. Because, after this method has
     * fired. The queue must be re-init before in use.
     */
    public static void destroy() {
        cancelAll();
        mQueue = null;
        System.gc();
        Runtime.getRuntime().gc();
    }

}
