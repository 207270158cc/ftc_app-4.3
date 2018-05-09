package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Team 1 Mecanum Teleop", group="Linear Opmode")
//@Disabled
public class Team1teleop_Mecanum extends LinearOpMode {


    // Declare OpMode members.
    HardwareTeam12624_Mecanum robot = new HardwareTeam12624_Mecanum();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        robot.init(hardwareMap);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();




        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double speed ;
            double direction ;

            // Setup a variable for each drive wheel to save power level for telemetry
            speed = Math.hypot(gamepad1.right_stick_x, gamepad1.right_stick_y);
            direction = Math.atan2(-gamepad1.right_stick_y, gamepad1.right_stick_x) - Math.PI / 4;

            final double v1 = speed * Math.cos(direction) + gamepad1.left_stick_x;
            final double v2 = speed * Math.sin(direction) - gamepad1.left_stick_x;
            final double v3 = speed * Math.sin(direction) + gamepad1.left_stick_x;
            final double v4 = speed * Math.cos(direction) - gamepad1.left_stick_x;

            final double V1max;
            final double V2max;
            final double V3max;
            final double V4max;

            if (v1>1) {
                V1max=1;

            }
            else if (v1<-1) {
                V1max = -1;
            }
            else {
                V1max=v1;
            }

            if (v2>1) {
                V2max=1;

            }
            else if (v2<-1) {
                V2max = -1;
            }
            else {
                V2max=v2;
            }

            if (v3>1) {
                V3max=1;

            }
            else if (v3<-1) {
                V3max = -1;
            }
            else {
                V3max=v3;
            }
            if (v4>1) {
                V4max=1;

            }
            else if (v4<-1) {
                V4max = -1;
            }
            else {
                V4max=v4;
            }

            robot.leftFrontMotor.setPower(V1max);
            robot.rightFrontMotor.setPower(V2max);
            robot.leftRearMotor.setPower(V3max);
            robot.rightRearMotor.setPower(V4max);


            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();





        }   //mecanumDrive_Cartesian







    }



}


















