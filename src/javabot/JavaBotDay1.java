package javabot;

import com.pi4j.wiringpi.Gpio;
import static com.pi4j.wiringpi.Gpio.*;

public class JavaBotDay1 
{
	private final int DATA = 27;
	private final int CLK = 28;
	private final int LATCH = 29;
	
	private final int MOTOR1_A = 3;
	private final int MOTOR1_B = 2;
	private final int MOTOR2_A = 4;
	private final int MOTOR2_B = 1;
	private final int MOTOR3_A = 5;
	private final int MOTOR3_B = 7;
	private final int MOTOR4_A = 0;
	private final int MOTOR4_B = 6;

	private final int FORWARD = 1;
	private final int BACKWARD = 2;
	private final int STOP = 3;
	
	private boolean[] shiftState;



	public static void main(String[] args) throws InterruptedException
	{
		JavaBotDay1 bot = new JavaBotDay1();
		bot.run();
	}

	public JavaBotDay1()
	{
		shiftState = new boolean[8];

		wiringPiSetup();

		pinMode(LATCH, OUTPUT);
		pinMode(DATA, OUTPUT);
		pinMode(CLK, OUTPUT);

	}

	public void run() throws InterruptedException
	{
		System.out.println("JavaBot running...");
		// TODO: write your driving algorithm here
		System.out.println("JavaBot shutting down...");
	}

	public void goForward()
	{
		// TODO
	}

	public void goBackward()
	{
		// TODO
	}

	public void turnLeft()
	{
		// TODO
	}
	
	public void turnRight()
	{
		// TODO
	}

	public void brake()
	{
		// TODO
	}

	public void testAllMotors() throws InterruptedException
	{
		for (int i = 1; i <= 4; i++)
		{
			updateMotor(i, BACKWARD);
			updateShiftRegister();
			
			delay(3000);

			updateMotor(i, STOP);
			updateShiftRegister();
		}
	}

	public void updateMotor(int motor, int action)
	{
		int a, b;

		switch (motor)
		{
			case 1:
				a = MOTOR1_A;
				b = MOTOR1_B;
				break;
			case 2:
				a = MOTOR2_A;
				b = MOTOR2_B;
				break;
			case 3:
				a = MOTOR3_A;
				b = MOTOR3_B;
				break;
			case 4:
				a = MOTOR4_A;
				b = MOTOR4_B;
				break;
			default:
				throw new IllegalArgumentException(motor + " is not a valid motor! (choose 1-4)");
		}

		switch (action)
		{
			case FORWARD:
				shiftState[a] = true;
				shiftState[b] = false;
				break;
			case BACKWARD:
				shiftState[a] = false;
				shiftState[b] = true;
				break;
			case STOP:
				shiftState[a] = false;
				shiftState[b] = false;
				break;
		}

	}

	public void updateShiftRegister()
	{
		// Allow the shift register to be modified.
		digitalWrite(LATCH, LOW);
		// No data available yet.
		digitalWrite(DATA, LOW);

		// Write one byte into the shift register.
		for (int i = 7; i >= 0; i--)
		{
			delayMicroseconds(1);
			digitalWrite(CLK, LOW);

			// Determine bit to push into shift register.
			if (shiftState[i])
			{
				digitalWrite(DATA, HIGH);
			}
			else
			{
				digitalWrite(DATA, LOW);
			}
		
			// Push new data into shift register.`	
			delayMicroseconds(1);
			digitalWrite(CLK, HIGH);
		}

		// Finalize the shift register state.
		digitalWrite(LATCH, HIGH);
	}
}
