package MechGen3839;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

//import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp

public class EncoderTester extends LinearOpMode{
    Hardware robot = Hardware.getInstance();

    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        int position = 0;
        //robot.slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        boolean aPressed = false;
        boolean yPressed = false;
        //robot.lm.setMode(DcMotor.RunMode.RESET_ENCODERS);
        //robot.slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.wrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //robot.lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (opModeIsActive()) {
/*
            //y to increase
            if (gamepad1.y) { //max: 1966
                position += 5;
                robot.slide.setTargetPosition(position);
                robot.slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.slide.setPower(.1);
                //telemetry.addData("Position", position);
                //telemetry.update();
            }
            telemetry.addData("Lift 1 ", robot.lift1.getCurrentPosition());
            telemetry.addData("Lift 2 ", robot.lift1.getCurrentPosition());
            telemetry.update();

            //a to decrease
            if (gamepad1.a) {
                position -= 5;
                robot.slide.setTargetPosition(position);
//                telemetry.addData("Position", position);
//                telemetry.update();
                robot.slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.slide.setPower(-.1);
            }

 */

            if (gamepad1.x) { //max: 1966
                position += 100;
                robot.wrist.setTargetPosition(position);
                //robot.lift2.setTargetPosition(position);
                robot.wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //robot.lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.wrist.setPower(.1);
                //robot.lift2.setPower(.1);
            }
            telemetry.addData("Position", robot.wrist.getCurrentPosition());
            telemetry.update();

            //a to decrease
            if (gamepad1.b) {
                position -= 100;
                robot.wrist.setTargetPosition(position);
                //robot.lift2.setTargetPosition(position);
//                telemetry.addData("Position", position);
//                telemetry.update();
                robot.wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //robot.lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.wrist.setPower(-.1);
                //robot.lift2.setPower(-.1);
            }
            telemetry.addData("Position", robot.wrist.getCurrentPosition());
            telemetry.update();

//            if (gamepad1.a) {
//                robot.wrist.setTargetPosition(100);
//                robot.wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                //robot.lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.wrist.setPower(.1);
//            }
            if (gamepad1.y) {
                robot.wrist.setTargetPosition(-520);
                robot.wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.wrist.setPower(.1);
            }
            telemetry.addData("Position", robot.wrist.getCurrentPosition());
            telemetry.update();

            if (gamepad1.right_bumper) {
                robot.slide.setPower(0.05);
            }
            if (gamepad1.left_bumper) {
                robot.slide.setPower(-0.05);
            }
        }

    }}
