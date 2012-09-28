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
    private float[] samplingX = new float[ELEMENT_COUNT];      //..................��
    private float[] samplingY = new float[ELEMENT_COUNT];
    private float[] samplingZ = new float[ELEMENT_COUNT];
    private  final float FILTERING_VALAUE = 0.1f;
    private float lowX,lowY,lowZ;
    private int position;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SensorManager�Υ��󥹥��󥹤�ȡ��
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
            
            
//            lowX = x * FILTERING_VALAUE + lowX * (1.0f - FILTERING_VALAUE);  //..................��
//            lowY = y * FILTERING_VALAUE + lowY * (1.0f - FILTERING_VALAUE);
//            lowZ = z * FILTERING_VALAUE + lowZ * (1.0f - FILTERING_VALAUE);
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
    	}
        
    }
//    
//    @Override
//    public void onSensorChanged(SensorEvent e) {
//    	if(e.sensor.getType() != Sensor.TYPE_ACCELEROMETER){  
//            //���z��ļ��ٶ�ֵ  
//            return; 
//        }
//        if (position == ELEMENT_COUNT - 1){                                         //..................��
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