package MechGen3839;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

//import org.firstinspires.ftc.teamcode.Hardware;


import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

@TeleOp(name = "TeleOp")

public class TeleOpMain extends LinearOpMode { // REMOVED MAX LIMITS CAUTION!!!!!!!
    Hardware robot = Hardware.getInstance();

    private ElapsedTime runtime = new ElapsedTime();

    //private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.addData("CAUTION", "Uncertain Programming - Use at Own Risk");
        telemetry.addData("CAUTIÖN", "REMOVED MAX LIMITS CAUTION");
        telemetry.update();

        robot.lift1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.lift1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.lift2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.wrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        //This is for checking if the x, y, a, b buttons are currently being pressed
        boolean pressingx = false;
        boolean pressingy = false;
        boolean pressinga = false;
        boolean pressingb = false;

        //This is the variable used to set the position of the Lift Motor
        int lift1Pos = 0;
        int lift2Pos = 0;
        int wristPos = 0;

        //These are the possible positions of the Lift Motor
        final int LIFT_MAX = 3600;
        final int LIFT_MIN = 0;
        final int LOW_BAR = 950;
        final int HIGH_BAR = 2515;

        //This is the speed for the Lift Motor
        final double LIFT_SPEED = 0.75;


//        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//
//        telemetry.setMsTransmissionInterval(11);
//
//        limelight.pipelineSwitch(0);
//
//        limelight.start();

        while (opModeIsActive()) {
            /** G A M E P A D   O N E  **/
            double forward;
            double strafing;
            double turning;

            /* if(robot.ts.isPressed()) {
                telemetry.addData("Pressed: ", robot.ts.isPressed());
                telemetry.update();
            } */

            forward = -gamepad1.left_stick_y;
            turning = gamepad1.right_stick_x;
            strafing = gamepad1.left_stick_x;

            //Driving
            double rfm = (forward - strafing - turning);
            double rbm = (forward + strafing - turning);
            double lfm = (forward + strafing + turning);
            double lbm = (forward - strafing + turning);

            double denominator = Math.max(Math.abs(forward) + Math.abs(turning) + Math.abs(strafing), robot.maxSpeed);
            double rfmPower = rfm / denominator;
            double rbmPower = rbm / denominator;
            double lfmPower = lfm / denominator;
            double lbmPower = lbm / denominator;

            robot.setPower(rfmPower, rbmPower, lfmPower, lbmPower);

            //Slow Drive
            if (gamepad1.dpad_up) {
                robot.setPower(0.25, 0.25, 0.25, 0.25);
            }
            if (gamepad1.dpad_down) {
                robot.setPower(-0.25, -0.25, -0.25, -0.25);
            }
            if (gamepad1.dpad_left) {
                robot.setPower(0.35, -0.35, -0.35, 0.35);
            }
            if (gamepad1.dpad_right) {
                robot.setPower(-0.35, 0.35, 0.35, -0.35);
            }

            //Precision turning
            if (gamepad1.left_bumper) {
                robot.setPower(0.35, 0.35, -0.35, -0.35);
            }
            if (gamepad1.right_bumper) {
                robot.setPower(-0.35, -0.35, 0.35, 0.35);
            }

            if(robot.lift1.getCurrentPosition() > 2500 ){
                robot.maxSpeed = 0.3;
            } else {
                robot.maxSpeed = 1;
            }


            /** G A M E P A D   T W O */

            // L I F T S
            //Manual Controls
            if (gamepad2.right_stick_y > 0) {
                robot.lift1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                robot.lift1.setPower(-LIFT_SPEED);
                robot.lift2.setPower(-LIFT_SPEED);

                lift1Pos = robot.lift1.getCurrentPosition();
                lift2Pos = robot.lift2.getCurrentPosition();
            } else if (gamepad2.right_stick_y < 0) {
                robot.lift1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                robot.lift1.setPower(LIFT_SPEED);
                robot.lift2.setPower(LIFT_SPEED);

                lift1Pos = robot.lift1.getCurrentPosition();
                lift2Pos = robot.lift2.getCurrentPosition();
            } else if (gamepad2.right_stick_x > 0) {
                robot.lift1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                robot.lift1.setPower(-LIFT_SPEED * 0.5);
                robot.lift2.setPower(-LIFT_SPEED * 0.5);

                lift1Pos = robot.lift1.getCurrentPosition();
                lift2Pos = robot.lift2.getCurrentPosition();
            } else if (gamepad2.right_stick_x < 0) {
                robot.lift1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                robot.lift1.setPower(LIFT_SPEED * 0.5);
                robot.lift2.setPower(LIFT_SPEED * 0.5);

                lift1Pos = robot.lift1.getCurrentPosition();
                lift2Pos = robot.lift2.getCurrentPosition();
            } else {
                robot.lift1.setTargetPosition(lift1Pos);
                robot.lift2.setTargetPosition(lift2Pos);

                robot.lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                robot.lift1.setPower(LIFT_SPEED);
                robot.lift2.setPower(LIFT_SPEED);
            }

            //Reset Lift Encoder
            if (gamepad2.x) {
                robot.lift1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.lift2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                lift1Pos = 0;
                lift2Pos = 0;
                telemetry.addData("Position", "Reset to Zero");
                telemetry.update();
            }

            /* C L A W */
            if (gamepad2.left_trigger > 0) {
                clawPos("CLOSE");
            }
            if (gamepad2.right_trigger > 0) {
                clawPos("OPEN");
            }

            // W R I S T
            //Manual Controls
            if (gamepad2.left_stick_y > 0) {
                robot.wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.wrist.setPower(0.5);
                wristPos = robot.wrist.getCurrentPosition();

            } else if (gamepad2.left_stick_y < 0) {
                robot.wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.wrist.setPower(-0.5);
                wristPos = robot.wrist.getCurrentPosition();

            } else if (gamepad2.left_stick_x > 0) {
                robot.wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.wrist.setPower(0.1);
                wristPos = robot.wrist.getCurrentPosition();

            } else if (gamepad2.left_stick_x < 0) {
                robot.wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.wrist.setPower(-0.1);
                wristPos = robot.wrist.getCurrentPosition();

            } else {
                robot.wrist.setTargetPosition(wristPos);
                robot.wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.wrist.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                robot.wrist.setPower(0.5);
            }

            if (gamepad2.dpad_down) {
                wristPos = robot.WRIST_DOWN;
            }
            if (gamepad2.dpad_left) {
                wristPos = robot.WRIST_MID;
            }
            if (gamepad2.dpad_right) {
                wristPos = robot.WRIST_HORZ;
            }
            if (gamepad2.dpad_up) {
                wristPos = robot.WRIST_UP;

                /* if(robot.ts.isPressed()) {
                    robot.wrist.setPower(0);
                    robot.wrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    break;
                } */
                //robot.wrist.setTargetPosition(robot.WRIST_UP);
//                robot.wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.wrist.setPower(.1);
                //wristEncoderPos(robot.WRIST_UP, robot.WRIST_SPEED);
                //wristPos("UP"); }
            }

            //P R E S E T S
            //Lift Down
            if (gamepad2.a && !pressinga && robot.wrist.getCurrentPosition() != robot.WRIST_DOWN) {
                lift1Pos = 0;
                lift2Pos = 0;
                pressinga = true;
            } else if (!gamepad2.a) {
                pressinga = false;
            }
            //Lift Mid
            if (gamepad2.b && !pressingb) {
                lift1Pos = LOW_BAR;
                lift2Pos = LOW_BAR;
                pressingb = true;
                wristPos = robot.WRIST_MID;
            } else if (!gamepad2.b) {
                pressingb = false;
            }
            //Lift High
            if (gamepad2.y && !pressingy) {
                lift1Pos = HIGH_BAR;
                lift2Pos = HIGH_BAR;
                pressingy = true;
                wristPos = robot.WRIST_MID;
            } else if (!gamepad2.y) {
                pressingy = false;
            }

            telemetry.addData("Lift 1 Pos", lift1Pos);
            telemetry.addData("Lift 2 Pos", lift2Pos);
            telemetry.addData("Wrist Target Pos", wristPos);
            telemetry.addData("Right Stick X", gamepad2.right_stick_x);
            telemetry.addData("Wrist Current Pos", robot.wrist.getCurrentPosition());
            telemetry.update();



//            LLResult result = limelight.getLatestResult();
//            if (result != null) {
//                if (result.isValid()) {
//                    Pose3D botpose = result.getBotpose();
//                    telemetry.addData("tx", result.getTx());
//                    telemetry.addData("ty", result.getTy());
//                    telemetry.addData("Botpose", botpose.toString());
//                }
//            }
//
//            while (result.getTa() < 0.4) {forward = 0.25;}
//            while (result.getTa() > 0.5) {forward = -0.25;}

        }
    }


        /** F U N C T I O N S */
        //Sets Claw Servo positions for easy access
        public void clawPos (String posClaw){
            if (posClaw.equals("OPEN")) {
                robot.claw.setPosition(robot.CLAW_OPEN);
            }
            if (posClaw.equals("INIT")) {
                robot.claw.setPosition(robot.CLAW_INIT);
            }
            if (posClaw.equals("CLOSE")) {
                robot.claw.setPosition(robot.CLAW_CLOSE);
            }
        }
        //Sets Wrist Servo positions for easy access
    public void wristPos (String posWrist){
        if(posWrist.equals("UP")){ robot.wrist.setTargetPosition(robot.WRIST_UP); }
        if(posWrist.equals("MID")){ robot.wrist.setTargetPosition(robot.WRIST_MID); }
        if(posWrist.equals("DOWN")){ robot.wrist.setTargetPosition(robot.WRIST_DOWN); }
    }
    public void liftEncoderPos ( int EncoderPos, double speed){
        robot.lift1.setTargetPosition(EncoderPos);
            //robot.lift2.setTargetPosition(EncoderPos);
        robot.lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //robot.lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.lift1.setPower(speed);
            //robot.lift2.setPower(speed);
        }

        public void wristEncoderPos ( int EncoderPos, double speed){
            robot.wrist.setTargetPosition(EncoderPos);
            robot.wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.wrist.setPower(speed);
        }
}
