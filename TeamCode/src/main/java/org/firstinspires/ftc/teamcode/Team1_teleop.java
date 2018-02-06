package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Team1_Telop", group="Linear Opmode")
//@Disabled
public class Team1_teleop extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftRDrive = null;
    private DcMotor rightRDrive = null;
    private DcMotor leftFDrive = null;
    private DcMotor rightFDrive = null;
    private DcMotor elevatorDrive = null;
    public Servo GrabLift = null;
    public Servo GrabMove = null;
    double servoStartPos = 0;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftRDrive = hardwareMap.get(DcMotor.class, "leftRDrive");
        rightRDrive = hardwareMap.get(DcMotor.class, "rightRDrive");
        leftFDrive = hardwareMap.get(DcMotor.class, "leftFDrive");
        rightFDrive = hardwareMap.get(DcMotor.class, "rightFDrive");
        elevatorDrive = hardwareMap.get(DcMotor.class, "elevatorDrive");
        GrabLift = hardwareMap.get(Servo.class, "grabLift");
        GrabMove = hardwareMap.get(Servo.class, "grabMove");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftRDrive.setDirection(DcMotor.Direction.FORWARD);
        rightRDrive.setDirection(DcMotor.Direction.FORWARD);
        leftFDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFDrive.setDirection(DcMotor.Direction.FORWARD);
        elevatorDrive.setDirection(DcMotor.Direction.FORWARD);
        GrabLift.setDirection(Servo.Direction.FORWARD);
        GrabMove.setDirection(Servo.Direction.REVERSE);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        GrabLift.setPosition(servoStartPos);
        GrabMove.setPosition(servoStartPos);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftRPower;
            double rightRPower;
            double leftFPower;
            double rightFPower;
            double elevatorPower;
            double GrabServoPower;
            double maxspeed = 1;
            double nospeed = 0;
            double halfspeed = .5;
            final double CLAW_SPEED = 0.02;


            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            //double drive = -gamepad1.left_stick_y;
            //double turn  =  gamepad1.right_stick_x;
            //leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            //rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            leftRPower  = -gamepad1.left_stick_y ;
            rightRPower = -gamepad1.right_stick_y ;
            leftFPower  = -gamepad1.left_stick_x ;
            rightFPower = -gamepad1.right_stick_x ;
            elevatorPower = gamepad2.left_stick_y;
            GrabServoPower = gamepad2.right_stick_y;

            if (gamepad2.a) {
                GrabMove.setPosition(GrabMove.getPosition()+CLAW_SPEED);
                GrabLift.setPosition(GrabLift.getPosition()+CLAW_SPEED);}
            else if (gamepad2.b) {
                GrabMove.setPosition(GrabMove.getPosition()-CLAW_SPEED);
                GrabLift.setPosition(GrabLift.getPosition()-CLAW_SPEED);}

            // Send calculated power to wheels
            leftRDrive.setPower(leftRPower);
            rightRDrive.setPower(rightRPower);
            leftFDrive.setPower(leftFPower);
            rightFDrive.setPower(rightFPower);
            elevatorDrive.setPower(elevatorPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftFPower, leftRPower, rightFPower, rightRPower);
            telemetry.update();
        }
    }
}
