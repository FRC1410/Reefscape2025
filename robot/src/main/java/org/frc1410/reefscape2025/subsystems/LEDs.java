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
            case GOT_PIECE_GREEN -> this.candle.setLEDs(10,247,2);
            case LEVEL_ONE_PINK -> this.candle.setLEDs(247,2,174);
            case LEVEL_TWO_VIOLET -> this.candle.setLEDs(146,28,189);
            case LEVEL_THREE_CYAN -> this.candle.setLEDs(30, 250, 224);
            case LEVEL_FOUR_YELLOW -> this.candle.setLEDs(250, 239, 30);
            case LEVEL_FIVE_DEEP_BLUE -> this.candle.setLEDs(24, 24, 214);
            case STOLEN_LIFEBLOOD_RED -> this.candle.animate(new StrobeAnimation(255,18,26));
            case CLIMBING_RAINBOW_ANIMATION -> this.candle.animate(new RainbowAnimation());
            case OCEAN_BREEZE -> this.candle.setLEDs(30, 100, 250);
        }
    }

    public enum Color {
        GOT_PIECE_GREEN,
        LEVEL_ONE_PINK,
        LEVEL_TWO_VIOLET,
        LEVEL_THREE_CYAN,
        LEVEL_FOUR_YELLOW,
        LEVEL_FIVE_DEEP_BLUE,
        STOLEN_LIFEBLOOD_RED,
        OCEAN_BREEZE,
        CLIMBING_RAINBOW_ANIMATION,
    }


}
