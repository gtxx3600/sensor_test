package com.wsn.SenserTest;

import android.app.Activity;
import android.os.Bundle;



import java.util.Arrays;
import java.util.List;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SenserTestActivity extends Activity implements SensorEventListener{
    /** Called when the activity is first created. */
    private SensorManager sensorManager;
    private static final int ELEMENT_COUNT = 10;
    
    //Data
    private float[] samplingX = new float[ELEMENT_COUNT];      //..................①
    private float[] samplingY = new float[ELEMENT_COUNT];
    private float[] samplingZ = new float[ELEMENT_COUNT];
    private float[] orientation = {0,0,0};
    private float[] angle = {0,0,0};
    private float timestamp = 0.0f;
    private int count = 0;
    private float[] err = {0,0,0};
    private  final float FILTERING_VALAUE = 0.1f;
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float lowX,lowY,lowZ;
    private int position;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SensorManagerのインスタンスを取得
        sensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        setContentView(R.layout.main);
    }
    
    @Override
    protected void onResume(){
        super.onResume();
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        TextView tv;
        LinearLayout layout = (LinearLayout)findViewById(R.id.lay);
        for (Sensor s:sensors){
            tv = new TextView(this);
            tv.setText(s.getName());
            layout.addView(tv,new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        //sensor1
        for (Sensor s : sensors){
            sensorManager.registerListener(this,s,SensorManager.SENSOR_DELAY_NORMAL);
        }              
    }
    
    
    //
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void onSensorChanged(SensorEvent e) {
    	switch(e.sensor.getType()){
    	case Sensor.TYPE_ACCELEROMETER:{
    		float x = e.values[sensorManager.DATA_X];
            float y = e.values[sensorManager.DATA_Y];
            float z = e.values[sensorManager.DATA_Z];
            
            lowX = x;
            lowY = y;
            lowZ = z;
            TextView textX = (TextView)findViewById(R.id.x);
            textX.setText("x:" + String.valueOf(lowX));
            TextView textY = (TextView)findViewById(R.id.y);
            textY.setText("y:" + String.valueOf(lowY));
            TextView textZ = (TextView)findViewById(R.id.z);
            textZ.setText("Z:" + String.valueOf(lowZ));
            break;
    	}
    	case Sensor.TYPE_MAGNETIC_FIELD:{
    		TextView x = (TextView)findViewById(R.id.mx);
            x.setText("x:" + String.valueOf(e.values[SensorManager.DATA_X]));
            TextView y = (TextView)findViewById(R.id.my);
            y.setText("y:" + String.valueOf(e.values[SensorManager.DATA_Y]));
            
            TextView z= (TextView)findViewById(R.id.mz);
            z.setText("z:" + String.valueOf(e.values[SensorManager.DATA_Z]));
            break;
    	
        }
    	case Sensor.TYPE_ORIENTATION:{
    		TextView x = (TextView)findViewById(R.id.ox);
            x.setText("Azimuth" + String.valueOf(e.values[SensorManager.DATA_X]));
            
            TextView y = (TextView)findViewById(R.id.oy);
            y.setText("Pitch:" + String.valueOf(e.values[SensorManager.DATA_Y]));
            
            TextView z = (TextView)findViewById(R.id.oz);
            z.setText("Roll:" + String.valueOf(e.values[SensorManager.DATA_Z]));
    		break;
    	}
    	case Sensor.TYPE_PROXIMITY:{
    		 TextView x = (TextView)findViewById(R.id.prox);
             x.setText("Distance:" + String.valueOf(e.values[SensorManager.DATA_X]));
    		 break;
    	}
    	case Sensor.TYPE_LINEAR_ACCELERATION:{
    		TextView x = (TextView)findViewById(R.id.lx);
            x.setText("x:" + String.valueOf(e.values[SensorManager.DATA_X]));
            TextView y = (TextView)findViewById(R.id.ly);
            y.setText("y:" + String.valueOf(e.values[SensorManager.DATA_Y]));
            
            TextView z= (TextView)findViewById(R.id.lz);
            z.setText("z:" + String.valueOf(e.values[SensorManager.DATA_Z]));
            break;
    		
    	}
    	case Sensor.TYPE_LIGHT:{
   		 TextView x = (TextView)findViewById(R.id.light);
            x.setText("Light:" + String.valueOf(e.values[SensorManager.DATA_X]));
   		 break;
    	}
    	case Sensor.TYPE_GYROSCOPE:{
    		if(Math.abs(e.values[0]) + Math.abs(e.values[1]) + Math.abs(e.values[2]) < 0.06)
    		{
    			count++;
	    		err[0] += e.values[0];//0.01637
	    		err[1] += e.values[1];//-0.00796
	    		err[2] += e.values[2];//-0.01500
    		}
    		if (timestamp > 1 && count > 100)
    		{
    			float dt = (e.timestamp - timestamp) * NS2S;

    			angle[0] += (e.values[SensorManager.DATA_X] - err[0]/count) * dt;
    			angle[1] += (e.values[SensorManager.DATA_Y] - err[1]/count) * dt;
    			angle[2] += (e.values[SensorManager.DATA_Z] - err[2]/count) * dt;
    			TextView x = (TextView)findViewById(R.id.gax);
                x.setText("angle x:" + String.valueOf(angle[0] * 180 / Math.PI));
                TextView y = (TextView)findViewById(R.id.gay);
                y.setText("angle y:" + String.valueOf(angle[1] * 180 / Math.PI));
                TextView z= (TextView)findViewById(R.id.gaz);
                z.setText("angle z:" + String.valueOf(angle[2] * 180 / Math.PI));
                
    		}
    		timestamp = e.timestamp;
    		TextView x = (TextView)findViewById(R.id.gx);
            x.setText("x:" + String.valueOf(e.values[SensorManager.DATA_X]));
            TextView y = (TextView)findViewById(R.id.gy);
            y.setText("y:" + String.valueOf(e.values[SensorManager.DATA_Y]));
            TextView z= (TextView)findViewById(R.id.gz);
            z.setText("z:" + String.valueOf(e.values[SensorManager.DATA_Z]));
            break;
    	}
    	}
        
    }
//    
//    @Override
//    public void onSensorChanged(SensorEvent e) {
//    	if(e.sensor.getType() != Sensor.TYPE_ACCELEROMETER){  
//            //获得z轴的加速度值  
//            return; 
//        }
//        if (position == ELEMENT_COUNT - 1){                                         //..................②
//            position =0;
//        }else{
//            position++;
//        }
//        float x = e.values[sensorManager.DATA_X];
//        float y = e.values[sensorManager.DATA_Y];
//        float z = e.values[sensorManager.DATA_Z];
//        
//        samplingX[position] = x;
//        samplingY[position] = y;
//        samplingZ[position] = z;
//        
//        TextView textX = (TextView)findViewById(R.id.x);
//        textX.setText("x:" + String.valueOf(getMedian(samplingX)));
//        TextView textY = (TextView)findViewById(R.id.y);
//        textY.setText("y:" + String.valueOf(getMedian(samplingY)));
//        TextView textZ = (TextView)findViewById(R.id.z);
//        textZ.setText("Z:" + String.valueOf(getMedian(samplingZ)));
//    }  
    
    private float getMedian(float[] values) {
        // TODO Auto-generated method stub
        float[] tmp = values.clone();
        float sum = 0;
        for(float i: values)
        {
        	sum += i;
        }
        return sum / values.length;
    }

    //
    @Override
    protected void onStop(){
        super.onStop();
        sensorManager.unregisterListener(this);
    }
}  