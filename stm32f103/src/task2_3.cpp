#ifdef TASK2_3

#include "fix.h"
#include "stm32f10x.h"

#include "LED.h"
#include "Delay.h"
#include "Button.h"
#include "OLED.h"

using namespace teaheart;

void flowLED(LED leds[], uint8_t size, int8_t step, uint32_t duration) {
    static int8_t i = 0;
    leds[i].on();
    Delay::millisecond(duration);
    leds[i].off();
    Delay::millisecond(duration);
    if (step > 0) {
        i = i >= size - 1 ? 0 : i + step;
    } else if (step < 0) {
        i = i <= 0 ? size - 1 : i + step;
    }
}

void init() {
    SetSysClock();
    // LED
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    GPIO_InitTypeDef GPIOInit = {
            .GPIO_Pin = GPIO_Pin_0 | GPIO_Pin_2 | GPIO_Pin_4 | GPIO_Pin_6,
            .GPIO_Speed = GPIO_Speed_2MHz,
            .GPIO_Mode = GPIO_Mode_Out_PP
    };
    GPIO_Init(GPIOA, &GPIOInit);
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB, ENABLE);
    // Button0
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_11,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_IPU
    };
    GPIO_Init(GPIOB, &GPIOInit);
    // OLED
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_8 | GPIO_Pin_9,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_Out_OD
    };
    GPIO_Init(GPIOB, &GPIOInit);
}

int main() {
    init();

    LED leds[] = {
            LED(GPIOA, GPIO_Pin_0),
            LED(GPIOA, GPIO_Pin_2),
            LED(GPIOA, GPIO_Pin_4),
            LED(GPIOA, GPIO_Pin_6),
    };
    Button btn0(GPIOB, GPIO_Pin_11);
    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    uint8_t pressCount = 0;
    bool prevPress = false;

    oled.showNum(0, 0, pressCount, 3);

    while (true) {
        bool currPress = btn0.getStatus() == Bit_RESET;
        if (prevPress && !currPress) {
            oled.showNum(0, 0, ++pressCount, 3);
        }
        prevPress = currPress;
        flowLED(leds, LENGTH_OF(leds), pressCount % 2 != 0 ? -1 : 1, 200);
    }
}

#endif //TASK2_3
