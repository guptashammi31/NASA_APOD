package com.technogenr.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView title,explanation,zoom,dateob;
    private RequestQueue mQueue;
ImageView imageView;
    int day,month,year,hour,min;
    String dmy,ymd,mh,davl,email;
    Calendar c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
        explanation=(TextView)findViewById(R.id.explanation) ;
        title=(TextView)findViewById(R.id.title) ;
        zoom=(TextView)findViewById(R.id.zoom) ;
       imageView=(ImageView)findViewById(R.id.image);


        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        String hr = String.valueOf(hour);
        String mn = String.valueOf(min);

        mh=hr+" : "+mn;

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        month=month+1;
        day = c.get(Calendar.DAY_OF_MONTH);

        String dd = String.valueOf(day);
        String mm = String.valueOf(month);
        String yy = String.valueOf(year);
        dmy=dd +"/" + mm + "/"+ yy;
        ymd=yy+"/"+mm+"/"+dd;

       dateob=(TextView)findViewById(R.id.date);


        dateob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date

                DatePickerDialog dd = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                try {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    String dateInString = year + "-" +(monthOfYear )  + "-" +dayOfMonth ;
                                    Date date = formatter.parse(dateInString);
                                    explanation.setText("Loading Data..");
                                    dateob.setText(formatter.format(date).toString());
                                    String dob_var=(dateob.getText().toString());
                                    jsonParse1(dob_var);

                                } catch (Exception ex) {

                                }


                            }
                        }, year, month, day);
                dd.show();
            }
        });



    }
    private void jsonParse1(final String dos) {

        String url = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if( dos.equals(response.getString("date"))){
                                String  exp = response.getString("explanation");
                                String  titles = response.getString("title");
                                String  imageUrl=response.getString("hdurl");
//                            if (response.getString("media_type").equals("image")){
//                              zoom.setText("Zoom");
//                            }

                                Picasso.get().load(imageUrl).into(imageView);

                                explanation.setText(exp);
                                title.setText(titles);

                            }

                               // }
                           // }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
