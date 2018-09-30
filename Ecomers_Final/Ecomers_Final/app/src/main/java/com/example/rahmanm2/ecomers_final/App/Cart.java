package com.example.rahmanm2.ecomers_final.App;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rahmanm2.ecomers_final.Adapter.cartViewAdapter;
import com.example.rahmanm2.ecomers_final.DataBase.InsertOrderPHP;
import com.example.rahmanm2.ecomers_final.R;
import com.example.rahmanm2.ecomers_final.SampleDataModel.OrderModel;
import com.example.rahmanm2.ecomers_final.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {

    ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerview;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView txtTotal;
    private FButton btnPlaceOrder;
    List<OrderModel>listModel;
    int total_money = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listModel = new ArrayList<OrderModel>();

        String url = config.ROOT_URL+"order=view";

        mRecyclerview = (RecyclerView)findViewById(R.id.OrderListInCart);
        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLayoutManager);
        getOrder(url,Cart.this);

          txtTotal = (TextView)findViewById(R.id.txtTotal);
          btnPlaceOrder = (FButton)findViewById(R.id.btnPlaceOrder);

    }
    private void LoadData(){
        String url = config.ROOT_URL+"order=view";

    }
    public  List<OrderModel>getOrder(String url, final Context context){
        final List<OrderModel>model= new ArrayList<OrderModel>();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Please Wait...............");
        mProgressDialog.show();

        StringRequest request = new StringRequest(StringRequest.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mProgressDialog.dismiss();
                        try{
                            JSONArray order = new JSONArray(response);
                            for(int i = 0;i<order.length();i++){
                                JSONObject obj = order.getJSONObject(i);
                                model.add(new OrderModel(
                                        obj.getString("ProductID"),
                                        obj.getString("ProductName"),
                                        obj.getString("ProductDesc"),
                                        obj.getString("ProductPrice")
                                ));
                            }
                            cartViewAdapter adapter = new cartViewAdapter(model,Cart.this);
                            mRecyclerview.setAdapter(adapter);
                            total_money = totalOrder(model);
                            String money = String.valueOf(total_money);
                            txtTotal.setText(money);
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.hide();
                Toast.makeText(context,"Order Unsuccessful",Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(context).add(request);
        return model;
    }

    private int totalOrder(List<OrderModel>model){
        int total = 0;
        for(OrderModel model1:model)
            total+=(Integer.parseInt(model1.getProductprice()));
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        return total;
    }
}
