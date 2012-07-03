package ru.kcode.service;

public class MotorComputing {
    private static final float SENSETIV = 0.2f;
    private static final int LOWER_MOTOR_LIMIT = Math.round(1000 * 0.15f);

    private static int motorsSpeed = 0;
    
    private int motor0 = 0;
    private int motor1 = 0;
    private int motor2 = 0;
    private int motor3 = 0;
    
    private MotorComputing() {
    }
    
    public static MotorComputing getInstance(KJoystick j) {
        MotorComputing result = new MotorComputing();
        changeMotorsSpeed(Math.round(j.getY() * 1000f * SENSETIV));
        result.setMotorsValues(j);
        return result;
    }

    private static synchronized void changeMotorsSpeed(int deltaSpeed) {
        motorsSpeed += deltaSpeed;
        if (motorsSpeed > 1000) {
            motorsSpeed = 1000;
        }
        if (motorsSpeed < 0) {
            motorsSpeed = 0;
        }
    }
    
    private static synchronized int getMotorsSpeed() {
        return motorsSpeed;
    }

    private synchronized void setMotorsValues(KJoystick j) {
        int ms = getMotorsSpeed();
        motor0 = validateMotor(ms + calcMotor0(j));
        motor1 = validateMotor(ms + calcMotor1(j));
        motor2 = validateMotor(ms + calcMotor2(j));
        motor3 = validateMotor(ms + calcMotor3(j));
    }

    public synchronized int getMotor0() {
        return motor0;
    }

    public synchronized int getMotor1() {
        return motor1;
    }

    public synchronized int getMotor2() {
        return motor2;
    }

    public synchronized int getMotor3() {
        return motor3;
    }
    
    private int calcMotor0(KJoystick j) {
        return  - Math.round(j.getZ() * 1000f)
                + Math.round(j.getX() * 1000f)
                + Math.round(j.getR() * 1000f);
    }
    
    private int calcMotor1(KJoystick j) {
        return  - Math.round(j.getZ() * 1000f)
                - Math.round(j.getX() * 1000f)
                - Math.round(j.getR() * 1000f);
    }
    
    private int calcMotor2(KJoystick j) {
        return  + Math.round(j.getZ() * 1000f)
                - Math.round(j.getX() * 1000f)
                + Math.round(j.getR() * 1000f);
    }
    
    private int calcMotor3(KJoystick j) {
        return  Math.round(j.getZ() * 1000f)
                + Math.round(j.getX() * 1000f)
                - Math.round(j.getR() * 1000f);
    }
    
    private int validateMotor(int i) {
        if ( getMotorsSpeed() < LOWER_MOTOR_LIMIT) {
            return getMotorsSpeed();
        }
        if (i > 1000) {
            return 1000;
        }
        if (i < LOWER_MOTOR_LIMIT) {
            return getMotorsSpeed() < LOWER_MOTOR_LIMIT ? getMotorsSpeed() : LOWER_MOTOR_LIMIT;
        }
        return i;
    }
}
