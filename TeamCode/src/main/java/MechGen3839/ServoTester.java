package MechGen3839;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

//import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp

public class ServoTester extends LinearOpMode {

    Hardware robot = Hardware.getInstance();


    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();

        double position = 0.5;
        boolean pressingx = false;
        boolean pressingy = false;
        while (opModeIsActive()) {
            telemetry.addData("cS position", position);
            //telemetry.addData("actual postion", robot.wrist.getPosition());
            telemetry.update();

            //robot.wrist.setPosition(position);

            if (gamepad1.x && !pressingx) {
                position += .05;
                if (position > 1) {
                    position = 1;
                }
                pressingx = true;
            } else if (!gamepad1.x) {
                pressingx = false;
            }

            if (gamepad1.y && !pressingy) {
                position -= 0.05;
                if (position < 0) {
                    position = 0;
                }
                pressingy = true;
            } else if (!gamepad1.y) {
                pressingy = false;
            }

        }
    }
}
