package com.rossruhama.graficalinealreal;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private TextView fecha;
    private GraphView graphView;

    LineGraphSeries<DataPoint> series;
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graphView = (GraphView) findViewById(R.id.graphid);

        series = new LineGraphSeries<>(getDataPointX());

        graphView.addSeries(series);

        series.setDrawDataPoints(true);
        series.setDrawBackground(true);

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return sdf.format(new Date((long) value));
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });
        graphView.getGridLabelRenderer().setNumHorizontalLabels(5);
    }

    private DataPoint [] getDataPointX(){
        DataPoint[] dx = new DataPoint[]{

                //new DataPoint(0,0),
                new DataPoint(new Date().getTime()+1,getDataPointY()+1),//no lo pregunte mas srita.
                new DataPoint(new Date().getTime()+2, getDataPointY()+2),
                new DataPoint(new Date().getTime()+3, getDataPointY()-4),
                new DataPoint(new Date().getTime()+4, getDataPointY()+4),
                new DataPoint(new Date().getTime()+5, getDataPointY()-2),

        };
        return dx;
    }

   // private DataPoint[] getDataPointY(){
    private int  getDataPointY(){
   //  DataPoint[] dY = new DataPoint() {
         Conexion conexion = new Conexion();
       try

         {

             String response = conexion.execute("http://192.168.15.55:3000/sqlserver").get();

             JSONObject jsonObject = new JSONObject(response);

             System.out.print("________________________________________________________");
             System.out.print(jsonObject.getJSONArray("recordset"));
             JSONArray recordset = jsonObject.getJSONArray("recordset");
             JSONObject jsonObj;
             for (int i = 0; i < recordset.length(); i++) {
                 jsonObj = recordset.getJSONObject(i);
                 System.out.println("\n- " + jsonObj.optString("IdTransaccion"));

             }


             String datosY=recordset.getJSONObject(recordset.length() - 1).optString("IdTransaccion");
             int datoY = Integer.parseInt(datosY);
             return datoY;
       //  }
     }   catch (ExecutionException e) {
           e.printStackTrace();
       } catch (InterruptedException e) {
           e.printStackTrace();
       } catch (JSONException e) {
           e.printStackTrace();
       }
        return 0;

    }

}



