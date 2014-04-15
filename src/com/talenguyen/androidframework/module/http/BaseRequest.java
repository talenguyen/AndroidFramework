package com.talenguyen.androidframework.module.http;

import android.util.Log;
import com.android.volley.*;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: GIANG
 * Date: 11/29/13
 * Time: 4:34 PM
 */
public class BaseRequest<T> extends Request<T> {

    /**
     * Charset for request.
     */
    private static final String PROTOCOL_CHARSET = "utf-8";
    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);
    private static final String TAG = BaseRequest.class.getSimpleName();

    private final Class<? extends T> mClazz;

    private Map<String, String> mParams;
    private Listener<T> mListener;
    private ErrorListener mErrorListener;
    private String mRequestBody;

    public BaseRequest(int method, String url, Class<? extends T> clazz, Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        mErrorListener = errorListener;
        mListener = listener;
        mClazz = clazz;
    }

    public void setParam(Map<String, String> params) {
        mParams = params;
    }

    public void setParamJSON(JSONObject paramJSON) {
        mRequestBody = paramJSON == null ? null : paramJSON.toString();
    }

    public Listener<T> getListener() {
        return mListener;
    }

    public ErrorListener getErrorListener() {
        return mErrorListener;
    }

    /**
     * @deprecated Use {@link #getBodyContentType()}.
     */
    @Override
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    /**
     * @deprecated Use {@link #getBody()}.
     */
    @Override
    public byte[] getPostBody() {
        return getBody();
    }

    @Override
    public byte[] getBody() {
        try {
            Log.d(TAG, "Request body: " + mRequestBody);
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String responseData =
                    new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(parseResponse(responseData), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T t) {
        mListener.onResponse(t);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mParams == null) {
            return super.getParams();
        }
        return mParams;
    }

    protected T parseResponse(String response) {
        return new Gson().fromJson(response, mClazz);
    }
}
