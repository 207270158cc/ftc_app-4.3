package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Team 1 Mecanum Teleop", group="Linear Opmode")
//@Disabled
public class Team1teleop_Mecanum10639 extends LinearOpMode {


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
            double newmotor1power;
            double newmotor2power;


            newmotor1power = gamepad2.left_stick_y;
            newmotor2power = -gamepad2.right_stick_y;
            // Setup a variable for each drive wheel to save power level for telemetry
            speed = Math.hypot(gamepad1.right_stick_x, gamepad1.right_stick_y);
            direction = Math.atan2(-gamepad1.right_stick_y, -gamepad1.right_stick_x) - (Math.PI / 4);

            final double v1 = speed * Math.cos(direction) + gamepad1.left_stick_x;
            final double v2 = speed * Math.sin(direction) - gamepad1.left_stick_x;
            final double v3 = speed * Math.cos(direction) + gamepad1.left_stick_x;
            final double v4 = speed * Math.sin(direction) - gamepad1.left_stick_x;


            final double V1max;
            final double V2max;
            final double V3max;
            final double V4max;

            final double vcap = .717;

            V1max = v1/vcap;
            V2max = v2/vcap;
            V3max = v3/vcap;
            V4max = v4/vcap;

            final double V1final;
            final double V2final;
            final double V3final;
            final double V4final;

            if (V1max>1) {
                V1final=1;

            }
            else if (V1max<-1) {
                V1final = -1;
            }
            else {
                V1final=V1max;
            }

            if (V2max>1) {
                V2final=1;

            }
            else if (V2max<-1) {
                V2final = -1;
            }
            else {
                V2final=V2max;
            }

            if (V3max>1) {
                V3final=1;

            }
            else if (V3max<-1) {
                V3final = -1;
            }
            else {
                V3final=V3max;
            }
            if (V4max>1) {
                V4final=1;

            }
            else if (V4max<-1) {
                V4final = -1;
            }
            else {
                V4final=V4max;
            }

            robot.leftFrontMotor.setPower(V1final);
            robot.rightFrontMotor.setPower(V2final);
            robot.leftRearMotor.setPower(V3final);
            robot.rightRearMotor.setPower(V4final);


            // Show the elapsed game time and wheel power.
            telemetry.addData("Function:", "Direction" + direction);
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "leftF (%.2f), rightF (%.2f), leftR (%.2f), rightR (%.2f)", V1final, V2final, V3final, V4final);
            telemetry.update();





        }   //mecanumDrive_Cartesian







    }



}


















