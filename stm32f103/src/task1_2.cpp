#ifdef TASK1_2

#include "fix.h"
#include "stm32f10x.h"

#include "LED.h"
#include "Delay.h"
#include "Util.h"

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
}

int main() {
    init();

    LED leds[] = {
            LED(GPIOA, GPIO_Pin_0),
            LED(GPIOA, GPIO_Pin_2),
            LED(GPIOA, GPIO_Pin_4),
            LED(GPIOA, GPIO_Pin_6),
    };

    while (true) {
        flowLED(leds, LENGTH_OF(leds), 1, 200);
    }
}

#endif //TASK1_2
