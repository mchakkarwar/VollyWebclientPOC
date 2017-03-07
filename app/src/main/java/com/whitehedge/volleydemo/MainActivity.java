package com.whitehedge.volleydemo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request.Method;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button id_btn_json_arr_req;
    Button id_btn_json_obj_req;
    Button id_btn_img_req;
    TextView id_txt_json_obj_resp;
    TextView id_txt_json_arr_resp;
    ImageView id_img_resp;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id_btn_json_arr_req = (Button) findViewById(R.id.id_btn_json_arr_req);
        id_btn_json_obj_req = (Button) findViewById(R.id.id_btn_json_obj_req);
        id_btn_img_req = (Button) findViewById(R.id.id_btn_img_req);
        id_txt_json_obj_resp = (TextView) findViewById(R.id.id_txt_json_obj_resp);
        id_txt_json_arr_resp = (TextView) findViewById(R.id.id_txt_json_arr_resp);
        id_img_resp = (ImageView) findViewById(R.id.id_img_resp);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        id_btn_json_arr_req.setOnClickListener(this);
        id_btn_json_obj_req.setOnClickListener(this);
        id_btn_img_req.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_json_arr_req:
                makeJsonArrayRequest();
                break;
            case R.id.id_btn_json_obj_req:
                makeJsonObjRequest();
                break;
            case R.id.id_btn_img_req:
                makeImageRequest();
                break;
        }
    }

    public void makeJsonObjRequest() {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.GET, UrlConstant.URL_JSON_OBJECT, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT);
                Log.v("MainActivity.this", response.toString());
                id_txt_json_obj_resp.setText(response.toString());
            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT);
            }
        });
        NetworkManager.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest, "json_req");
    }

    public void makeJsonArrayRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Method.GET, UrlConstant.URL_JSON_ARRAY, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT);
                id_txt_json_arr_resp.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT);
            }
        });
        NetworkManager.getInstance(MainActivity.this).addToRequestQueue(jsonArrayRequest, "json_array");
    }

    private void makeImageRequest() {
        ImageLoader imageLoader = NetworkManager.getInstance(MainActivity.this).getImageLoader();
        imageLoader.get(UrlConstant.URL_IMAGE, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                id_img_resp.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
