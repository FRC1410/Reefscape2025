package org.frc1410.reefscape2025.subsystems;

import com.ctre.phoenix.led.*;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static com.ctre.phoenix.led.LarsonAnimation.*;
import static com.ctre.phoenix.led.ColorFlowAnimation.*;
import static com.ctre.phoenix.led.TwinkleOffAnimation.*;
import static org.frc1410.reefscape2025.subsystems.LEDs.Animation.FADE_IN_OUT;
import static org.frc1410.reefscape2025.subsystems.LEDs.Animation.RAINBOW;
import static org.frc1410.reefscape2025.subsystems.LEDs.Color.*;
import static org.frc1410.reefscape2025.util.Constants.LED_BRIGHTNESS;
import static org.frc1410.reefscape2025.util.IDs.*;

public class LEDs implements Subsystem {

    private final CANdle candle = new CANdle(LED_ID, "CTRE");
    private final int numLed = 400;
    private int r;
    private int g;
    private int b;

    public LEDs () {
        CANdleConfiguration config = new CANdleConfiguration();
        config.stripType = CANdle.LEDStripType.BRG;
        config.v5Enabled = true;
        config.brightnessScalar = LED_BRIGHTNESS;

        this.candle.configLOSBehavior(true);
        this.candle.configAllSettings(config);
    }

    public void setRGB(Color color) {
        switch (color) {
            case BLUE:
                this.r = 2;
                this.g = 2;
                this.b = 171;
                break;

            case TEAL:
                this.r = 2;
                this.g = 245;
                this.b = 11;
                break;

            case YELLOW:
                this.r = 245;
                this.g = 225;
                this.b = 2;
                break;

            case RED:
                this.r = 255;
                this.g = 0;
                this.b = 0;
                break;

            case PURPLE:
                this.r = 252;
                this.g = 2;
                this.b = 246;
                break;

            case NULL:
                this.r = 0;
                this.g = 0;
                this.b = 0;
                break;

            case LIGHT_BLUE:
                this.r = 3;
                this.g = 252;
                this.b = 194;
                break;

            case GREEN:
                this.r = 0;
                this.g = 255;
                this.b = 0;
                break;

            case ORANGE:
                this.r = 225;
                this.g = 15;
                this.b = 0;
        }
    }

    public void setColor(Color color) {
        this.candle.clearAnimation(0);
        this.setRGB(color);
        this.candle.setLEDs(this.r, this.g, this.b);
    }

    /**
     *
     * @param animation Animation you want from the Animation enum
     * @param color Color you want for the animation from the color enum
     * @param speed How fast the animation is [0,1]
     *
     */

    public void setAnimation(Animation animation, Color color, double speed) {
        this.candle.clearAnimation(0);
        this.setRGB(color);

        switch (animation) {
            case RAINBOW -> this.candle.animate(new RainbowAnimation(1, speed, this.numLed));
            case BOUNCE -> this.candle.animate(new LarsonAnimation(
                    this.r, this.g, this.b, 0, speed, this.numLed, BounceMode.Front, 7, 0));
            case STROBE -> this.candle.animate(new StrobeAnimation(
                    this.r, this.g, this.b, 0, speed, this.numLed));
            case GRADUAL_FILL -> this.candle.animate(new ColorFlowAnimation(
                    this.r, this.g, this.b, 0, speed, numLed, Direction.Forward));
            case FADE_IN_OUT -> this.candle.animate(
                    new SingleFadeAnimation(this.r, this.g, this.b, 0, speed, numLed));
            case TWINKLE -> this.candle.animate(new TwinkleOffAnimation(
                    this.r, this.g, this.b, 0, speed, numLed, TwinkleOffPercent.Percent100));
            case FIRE -> this.candle.animate(new FireAnimation(LED_BRIGHTNESS, speed, numLed, 0.2, 0.1));
            case RGB_FADE -> this.candle.animate(new RgbFadeAnimation(LED_BRIGHTNESS, speed, numLed));
        }
    }

    public enum Animation {
        RAINBOW,
        BOUNCE,
        STROBE,
        GRADUAL_FILL,
        FADE_IN_OUT,
        TWINKLE,
        FIRE,
        RGB_FADE
    }

    public enum Color {
        BLUE,
        LIGHT_BLUE,
        RED,
        GREEN,
        TEAL,
        YELLOW,
        PURPLE,
        ORANGE,
        NULL
    }
}