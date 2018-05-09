package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;


public class HardwareTeam12624_Mecanum {


    /* Public OpMode members. */
    public DcMotor  leftFrontMotor   = null;
    public DcMotor  rightFrontMotor = null;
    public DcMotor  leftRearMotor = null;
    public DcMotor  rightRearMotor = null;
    public DcMotor  leftArm     = null;
    public Servo    leftClaw    = null;
    public Servo    rightClaw   = null;

    public static final double MID_SERVO       =  0.5 ;
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareTeam12624_Mecanum(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftFrontMotor  = hwMap.get(DcMotor.class, "leftfront");
        leftRearMotor  = hwMap.get(DcMotor.class, "leftrear");
        rightFrontMotor = hwMap.get(DcMotor.class, "rightfront");
        rightRearMotor = hwMap.get(DcMotor.class, "rightrear");

        //leftArm    = hwMap.get(DcMotor.class, "left_arm");
        leftFrontMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        leftRearMotor.setDirection(DcMotor.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        rightRearMotor.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors

        // Set all motors to zero power
        leftFrontMotor.setPower(0);
        leftRearMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightRearMotor.setPower(0);
        //leftArm.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        /*leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRearMotor.setDirection(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); */

        // Define and initialize ALL installed servos.
        /*leftClaw  = hwMap.get(Servo.class, "left_hand");
        rightClaw = hwMap.get(Servo.class, "right_hand");
        leftClaw.setPosition(MID_SERVO);
        rightClaw.setPosition(MID_SERVO); */
    }
}

/* These are the two methods for Mecanum programming, one determined by assigning x and y values,
   and the other by greater than/less than arguments.

   public void mecanumDrive_Cartesian(double x, double y, double rotation)
{
    double wheelSpeeds[] = new double[4];

    wheelSpeeds[0] = x + y + rotation;
    wheelSpeeds[1] = -x + y - rotation;
    wheelSpeeds[2] = -x + y + rotation;
    wheelSpeeds[3] = x + y - rotation;

    normalize(wheelSpeeds);

    leftFrontMotor.setPower(wheelSpeeds[0]);
    rightFrontMotor.setPower(wheelSpeeds[1]);
    leftRearMotor.setPower(wheelSpeeds[2]);
    rightRearMotor.setPower(wheelSpeeds[3]);
}   //mecanumDrive_Cartesian
------------------------------------------------

private void normalize(double[] wheelSpeeds)
{
    double maxMagnitude = Math.abs(wheelSpeeds[0]);

    for (int i = 1; i < wheelSpeeds.length; i++)
    {
        double magnitude = Math.abs(wheelSpeeds[i]);

        if (magnitude > maxMagnitude)
        {
            maxMagnitude = magnitude;
        }
    }

    if (maxMagnitude > 1.0)
    {
        for (int i = 0; i < wheelSpeeds.length; i++)
        {
            wheelSpeeds[i] /= maxMagnitude;
        }
    }
}   //normalize

(Mecanum drive requires 3 analog controls: one for X, one for Y and one for rotate.
One gamepad has two 2-axis stick and two analog triggers. So it has plenty of analog controls.)

Also, potential Mecanum controller setup here:

botMotors crab(Gamepad cx)
{
botMotors m = new botMotors();
float maxDrive;
float frontMax;
float rearMax;
// turn=left.x, fwd=left.y, crab=right.x
m.leftFront = cx.left_stick_x + cx.left_stick_y + cx.right_stick_x;
m.rightFront = -cx.left_stick_x + cx.left_stick_y - cx.right_stick_x;
m.leftRear = cx.left_stick_x + cx.left_stick_y - cx.right_stick_x;
m.rightRear = -cx.left_stick_x + cx.left_stick_y + cx.right_stick_x;

// must keep things proportional when the sum of any x or y > 1
frontMax = Math.max(Math.abs(m.leftFront), Math.abs(m.rightFront));
rearMax = Math.max(Math.abs(m.leftRear), Math.abs(m.rightRear));
maxDrive = Math.max(frontMax, rearMax);
maxDrive = (maxDrive > MOTORMAX) ? maxDrive : MOTORMAX;

// scale and clip the front motors, then the aft
m.leftFront = m.leftFront/maxDrive;
m.leftFront = Range.clip(m.leftFront, MOTORMIN, MOTORMAX);
m.rightFront = m.rightFront/maxDrive;
m.rightFront = Range.clip(m.rightFront, MOTORMIN, MOTORMAX);

m.leftRear = m.leftRear/maxDrive;
m.leftRear = Range.clip(m.leftRear, MOTORMIN, MOTORMAX);
m.rightRear = m.rightRear/maxDrive;
m.rightRear = Range.clip(m.rightRear, MOTORMIN, MOTORMAX);
m.status = 0;
return m;

"cx" is an object of Gamepad class, and is passed in as an argument. We copy the gamepad to a new object so we can raise the x & y of the main stick to the 3rd or 5th power in order to give our drivers some precision around the middle yet retain full power authority when they need speed.
"botMotors" is a class we created that has 4 motor "requests' and a status (status is 0, -1, or -2 for "in progress," "achieved," or "failed").
During autonomous, the plan is to see what combination of yaw, drift, and straight ahead we want, then map that to stick positions in the object of Gamepad class, and pass that in as an argument.
MOTORMIN and MOTORMAX are "final" and -1 and 1.

This line should be the only one that's potentially difficult to understand:
maxDrive = (maxDrive > MOTORMAX) ? maxDrive : MOTORMAX;

It means that if maxDrive > 1, leave maxDrive alone. If it's <= 1, set it to 1.
---------------------------------------------------------------------------------
Here is a "no trigonometry" version. This is not original by me but result of good old Google search. It is basically "adding" the contributions of the various inputs then normalizing to keep the proportions of each contributor. MAX_SPEED is intended to provide a limited speed. Setting it to 1.0 gives full speed.

As shown, test() would be part of the tele-op op-mode. The holonomic() method is in a shared "drive" class. This is my attempt at Object Oriented Programming.

This is basically an extension of arcade/POV drive, simply adding the strafe. scaleInput() is part of the util.Range library.

One suggestion is to "scale" the inputs to decrease sensitivity near zero (finer control) and faster response toward the limits.
From other threads, I have seen various implementations:
look-up tables, x^2(with sign preservation), x^3, tanh(x)/tanh(1) (unless I am missing something - this is actually opposite - faster near zero, slower at limits)

public void test(){
        double Speed = -gamepad1.left_stick_y;
        double Turn = gamepad1.left_stick_x;
        double Strafe = gamepad1.right_stick_x;
        double MAX_SPEED = 1.0;
        holonomic(Speed, Turn, Strafe, MAX_SPEED );
    }

    public void holonomic(double Speed, double Turn, double Strafe, double MAX_SPEED){

//      Left Front = +Speed + Turn - Strafe      Right Front = +Speed - Turn + Strafe
//      Left Rear  = +Speed + Turn + Strafe      Right Rear  = +Speed - Turn - Strafe

        double Magnitude = abs(Speed) + abs(Turn) + abs(Strafe);
        Magnitude = (Magnitude > 1) ? Magnitude : 1; //Set scaling to keep -1,+1 range

        leftFrontMotor.setPower(scale((scaleInput(Speed) + scaleInput(Turn) - scaleInput(Strafe)),
                -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        if (leftRearMotor != null) {
            leftRearMotor.setPower(scale((scaleInput(Speed) + scaleInput(Turn) + scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        }
        rightFrontMotor.setPower(scale((scaleInput(Speed) - scaleInput(Turn) + scaleInput(Strafe)),
                -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        if (rightRearMotor != null) {
            rightRearMotor.setPower(scale((scaleInput(Speed) - scaleInput(Turn) - scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        }
    }

Things to keep in mind: There's a right and a wrong way to set up a mecanum drive.
When looking from the top, the rollers should form an X. When looking from the bottom, the rollers should form an O.

If you want to strafe left,
the left wheels should move toward each other and the right wheels should move away from each other. Keep this in mind when you're testing your code.

Something I like to do when writing my drive code is to split the motions into variables:

double drive;   // Power for forward and back motion
double strafe;  // Power for left and right motion
double rotate;  // Power for rotating the robot
This makes it easy to read the code.

Getting input from gamepad:

drive = -gamepad1.left_stick_y;  // Negative because the gamepad is weird
strafe = gamepad1.left_stick_x;
rotate = gamepad1.right_stick_x;
Next, you can set the powers as such:

// You might have to play with the + or - depending on how your motors are installed
frontLeftPower = drive + strafe + rotate;
backLeftPower = drive - strafe + rotate;
frontRightPower = drive - strafe - rotate;
backRightPower = drive + strafe - rotate;

Here's a way to program TANK DRIVE on a mecanum bot so that you can tune the joystick sensitivity to all three motions (fwd/rev, strafe)
independently: Let Kf, Kt, and Ks be the tuning parameters (0 to +1) for the forward/reverse, turn, and strafe motions, respectively.
Let X1 and Y1 represent the joystick outputs for the driver's left-hand joystick (-1 to +1);
Let Y2 represent the joystick outputs for the driver's right-hand joystick (-1 to +1)

When each joystick is pushed forward, its Y output should be positive. When the joystick is pushed to the right, its X output
should be positive. If not, add code to invert the sign if necesarry)

Let W1, W2, W3 abd W4 be the front left, front right, rear left, and rear right wheels, respectively. ("left and "right" in this context means "port" and "starboard", respectively)

Calculate the following:

Yf = (Y1 + Y2)/2
YT = (Y1 - Y2)/2

--------------------
**Example of Mecanum Autonomous:

package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import java.util.Locale;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

@Autonomous(name="Servo_code_2", group="test")
//@Disabled
public class Servo_code_2 extends LinearOpMode {

    /* Declare OpMode members.
HardwarePushbot robot   = new HardwarePushbot();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;

    Servo Servo2;

    ColorSensor SensorColor2;
    //DistanceSensor sensorDistance2;

    DcMotor motorLeft = null;
    DcMotor motorRight = null;
    DcMotor motorLeft2 = null;
    DcMotor motorRight2 = null;
    DcMotor motor5 = null;

    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;
    static final double     WHEEL_DIAMETER_INCHES   = 3.0 ;
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3);
    static final double     DRIVE_SPEED             = 0.5;



    @Override
    public void runOpMode() {

        Servo2 = hardwareMap.servo.get("Servo2");

        motorLeft  = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("MotorRight");
        motorLeft2  = hardwareMap.dcMotor.get("motorLeft2");
        motorRight2 = hardwareMap.dcMotor.get("MotorRight2");
        motor5 = hardwareMap.dcMotor.get("motor_5");

        SensorColor2 = hardwareMap.get(ColorSensor.class, "sensor_color_distance_2");
        //sensorDistance2 = hardwareMap.get(DistanceSensor.class, "sensor_color_distance_2");

        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorRight.setDirection(DcMotor.Direction.FORWARD);
        motorLeft2.setDirection(DcMotor.Direction.REVERSE);
        motorRight2.setDirection(DcMotor.Direction.FORWARD);
        motor5.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        idle();

        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        telemetry.addData("Path0",  "Starting at %7d :%7d",

                motorLeft.getCurrentPosition(),
                motorRight.getCurrentPosition());
        motorLeft2.getCurrentPosition();
        motorRight2.getCurrentPosition();
        telemetry.update();

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();


        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        //final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way
        Servo2.setPosition(0.62);

        runtime.reset();

        telemetry.addData("Red  ", SensorColor2.red());
        telemetry.addData("Green", SensorColor2.green());
        telemetry.addData("Blue ", SensorColor2.blue());
        telemetry.update();

        if(SensorColor2.blue() > 20 ){
            encoderDrive(DRIVE_SPEED, -2.0, -2.0, 1.0);
            telemetry.addData("Red  ", SensorColor2.red());
            telemetry.addData("Green", SensorColor2.green());
            telemetry.addData("Blue ", SensorColor2.blue());
            telemetry.update();
        }
        else if(SensorColor2.blue() < 20 ){
            encoderDrive(DRIVE_SPEED, 4.5, 4.5, 5.0);
            telemetry.addData("Red  ", SensorColor2.red());
            telemetry.addData("Green", SensorColor2.green());
            telemetry.addData("Blue ", SensorColor2.blue());
            telemetry.update();
        }



        telemetry.addData("Red  ", SensorColor2.red());
        telemetry.addData("Green", SensorColor2.green());
        telemetry.addData("Blue ", SensorColor2.blue());
        telemetry.update();



        sleep(1000);



        telemetry.addData("Red  ", SensorColor2.red());
        telemetry.addData("Green", SensorColor2.green());
        telemetry.addData("Blue ", SensorColor2.blue());
        telemetry.update();
        Servo2.setPosition(-0.6);
        //  sleep(250);   // optional pause after each move







        public void encoderDrive(double speed,
        double leftInches, double rightInches,
        double timeoutS) {
            int newLeftTarget;
            int newRightTarget;

            if (opModeIsActive()) {

                newLeftTarget = motorLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
                newRightTarget = motorRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);

                motorLeft.setTargetPosition(newLeftTarget);
                motorRight.setTargetPosition(newRightTarget);
                motorLeft2.setTargetPosition(newLeftTarget);
                motorRight2.setTargetPosition(newRightTarget);

                motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorLeft2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorRight2.setMode(DcMotor.RunMode.RUN_TO_POSITION);


                runtime.reset();
                motorLeft.setPower(Math.abs(speed));
                motorRight.setPower(Math.abs(speed));
                motorLeft2.setPower(Math.abs(speed));
                motorRight2.setPower(Math.abs(speed));

                while (opModeIsActive() &&
                        (runtime.seconds() < timeoutS) &&
                        (motorLeft.isBusy() && motorRight.isBusy())) {


                    telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                    telemetry.addData("Path2",  "Running at %7d :%7d",
                            motorLeft.getCurrentPosition(),
                            motorRight.getCurrentPosition());
                    motorLeft2.getCurrentPosition();
                    motorRight2.getCurrentPosition();
                    telemetry.update();
                }

                // Stop all motion;
                motorLeft.setPower(0);
                motorRight.setPower(0);
                motorLeft2.setPower(0);
                motorRight2.setPower(0);

                // Turn off RUN_TO_POSITION
                motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorLeft2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorRight2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                //  sleep(250);   // optional pause after each move
            }
        }
    }
}

 */