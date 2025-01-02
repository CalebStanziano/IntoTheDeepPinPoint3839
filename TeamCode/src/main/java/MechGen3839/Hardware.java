package MechGen3839;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

/**
 * 192.168.43.1:8080/dash
 * must be connected to robot wifi
 */

/**
 * Port-Key
 *
 *     Control Hub
 * rf -------------- m0
 * rb -------------- m2
 * lf -------------- em0
 * lb -------------- em2
 *
 * leftEncoder ----- m1
 * frontEncoder ---- m2
 * rightEncoder ---- m3
 *
 * Wrist ----------- em3
 *
 * claw -------------- s0
 *
 *
 *    Expansion Hub
 * lift1 (left) --- em1
 * lift2 (right) ---- m1
 * slide -----------
 *
 **/

public class Hardware {

    // Wheel motors
    public DcMotor rf; // right front
    public DcMotor rb; // right back
    public DcMotor lf; // left front
    public DcMotor lb; // left back

    public DcMotor lift1; // vertical lift
    public DcMotor lift2; // vertical lift
    public DcMotor slide; // horizontal slide

    public Servo claw; // claw
    //public Servo rc; // right claw
    public DcMotor wrist; // wrist

    // public TouchSensor ts; // touch sensor

    final public int WRIST_DOWN = -1789; //negative
    final public int WRIST_MID = -460; //negative 520
    final public int WRIST_HORZ = -1450;
    final public int WRIST_UP = 0;

    final public double CLAW_INIT = 0.96;
    final public double CLAW_OPEN = 0.75;
    final public double CLAW_CLOSE = 0.68;
    final public double WRIST_SPEED = 0.25;



    private static Hardware myInstance = null;

    public static Hardware getInstance() {
        if (myInstance == null) {
            myInstance = new Hardware();
        } return myInstance;
    }

    public double maxSpeed = 1;

    public void init(HardwareMap hwMap) {

        // FRONT

        // right front
        rf = hwMap.get(DcMotor.class, "m0");
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setPower(0);

        // left front
        lf = hwMap.get(DcMotor.class, "em0");
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lf.setPower(0);

        // BACK

        // right back
        rb = hwMap.get(DcMotor.class, "m2");
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setPower(0);

        // left back
        lb = hwMap.get(DcMotor.class, "em2");
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setPower(0);

        // ts = hwMap.get(TouchSensor.class, "t0");

        //vertical lift motors
        lift1 = hwMap.get(DcMotor.class, "em1");
        lift1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //lift1.setDirection(DcMotorSimple.Direction.REVERSE);
        lift1.setPower(0);

        lift2 = hwMap.get(DcMotor.class, "m1");
        lift2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift2.setDirection(DcMotorSimple.Direction.REVERSE);
        lift2.setPower(0);

        wrist = hwMap.get(DcMotor.class, "em3");
        wrist.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wrist.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wrist.setDirection(DcMotorSimple.Direction.REVERSE);
        wrist.setPower(0);
/**
        //linear slide motor
        slide = hwMap.get(DcMotor.class, "em2");
        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slide.setDirection(DcMotorSimple.Direction.REVERSE);
        slide.setPower(0);
 */

        claw = hwMap.get(Servo.class, "s0");
        //rc = hwMap.get(Servo.class, "s1");
        //wrist = hwMap.get(Servo.class, "s1");


    }
        public void setPower(double fr, double br, double fl, double bl) {
            if (rf != null) {
                rf.setPower(Range.clip(fr, -maxSpeed, maxSpeed));
            }
            if(lf != null) {
                lf.setPower(Range.clip(fl, -maxSpeed, maxSpeed));
            }
            if (rb != null) {
                rb.setPower(Range.clip(br, -maxSpeed, maxSpeed));
            }
            if (lb != null) {
                lb.setPower(Range.clip(bl, -maxSpeed, maxSpeed));
            }
    }

}
