package org.frc1410.reefscape2025.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.StrobeAnimation;

import static org.frc1410.reefscape2025.util.Constants.LED_BRIGHTNESS;
import static org.frc1410.reefscape2025.util.IDs.*;

public class LEDs {
    private CANdle candle = new CANdle(LED_ID);
    public LEDs () {
        CANdleConfiguration config = new CANdleConfiguration();
        config.stripType = CANdle.LEDStripType.RGB;
        config.brightnessScalar = LED_BRIGHTNESS;

        this.candle.configLOSBehavior(true);
        this.candle.configAllSettings(config);

        this.setColor(Color.OCEAN_BREEZE);
    }

    public void setColor(Color color) {
        switch (color) {
            case PRANCING_PONY_PINK -> this.candle.setLEDs(247,2,174);
            case LIMELIGHT_GREEN -> this.candle.setLEDs(10,247,2);
            case STOLEN_LIFEBLOOD_RED -> this.candle.animate(new StrobeAnimation(255,18,26));
            case VIVACIOUS_VIOLENT_VIOLET -> this.candle.setLEDs(146,28,189);
            case CLIMBING_RAINBOW_ANIMATION -> this.candle.animate(new RainbowAnimation());
            case OCEAN_BREEZE -> this.candle.setLEDs(30, 100, 250);
        }
    }

    public enum Color {
        STOLEN_LIFEBLOOD_RED,
        LIMELIGHT_GREEN,
        PRANCING_PONY_PINK,
        VIVACIOUS_VIOLENT_VIOLET,
        OCEAN_BREEZE,
        CLIMBING_RAINBOW_ANIMATION,
    }


}
