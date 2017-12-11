package com.darren.ofo_eye_study;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
/**
 * 感谢简书作者：kimier
 * */
public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor defaultSensor;
    private View leftView, rightView;
    private float normalSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    //完美做法，需要对dimens进行不同屏幕的适配即可
    private void initView() {
        leftView = findViewById(R.id.iv_leftEye);
        rightView = findViewById(R.id.iv_rightEye);
        normalSpace = getResources().getDimension(R.dimen.dimen20);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        /**
         传感器类型说明如下：
         #define SENSOR_TYPE_ACCELEROMETER 1 //加速度
         #define SENSOR_TYPE_MAGNETIC_FIELD 2 //磁力
         #define SENSOR_TYPE_ORIENTATION 3 //方向
         #define SENSOR_TYPE_GYROSCOPE 4 //陀螺仪
         #define SENSOR_TYPE_LIGHT 5 //光线感应
         #define SENSOR_TYPE_PRESSURE 6 //压力
         #define SENSOR_TYPE_TEMPERATURE 7 //温度
         #define SENSOR_TYPE_PROXIMITY 8 //接近
         #define SENSOR_TYPE_GRAVITY 9 //重力
         #define SENSOR_TYPE_LINEAR_ACCELERATION 10//线性加速度
         #define SENSOR_TYPE_ROTATION_VECTOR 11//旋转矢量
         */
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册传感器
        sensorManager.registerListener(listener, defaultSensor, SensorManager.SENSOR_DELAY_UI);
        /**
         * 四种频率
         * SENSOR_DELAY_FASTEST最灵敏，快的然你无语
         * SENSOR_DELAY_GAME游戏的时候用这个，不过一般用这个就够了
         * SENSOR_DELAY_NORMAL比较慢。
         * SENSOR_DELAY_UI最慢的
         * */
        /**
         * 注册传感器的参数
         * 1.事件监听
         * 2.默认传感器初始参数
         * 3.侦测频率（频率大小跟手机硬件有关）
         * */
    }

    @Override
    protected void onPause() {
        //注销传感器
        super.onPause();
    }

    private SensorEventListener listener = new SensorEventListener() {
        private float x, y;

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                x -= 15.0f * event.values[0];
                y += 10.0f * event.values[1];
                //越界处理
                if (x < -normalSpace) {
                    x = -normalSpace;
                }
                if (x > 30) {
                    x = 30;
                }
                if (y > 10) {
                    y = 10;
                }
                if (y < -normalSpace) {
                    y = -normalSpace;
                }
                leftView.setTranslationY(y);
                //偏移量为正数时，表示View从左向右平移。反之则从右向左平移
                leftView.setTranslationX(x);
                //设置View在Z轴上的旋转角度
                leftView.setRotation(x);
                rightView.setTranslationX(x);
                //偏移量为正数时，表示View从上向下平移。反之则从下向上平移
                rightView.setTranslationY(y);
                rightView.setRotation(x);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
