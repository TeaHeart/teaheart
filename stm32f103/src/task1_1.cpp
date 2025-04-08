#ifdef TASK1_1

#include "fix.h"
#include "stm32f10x.h"

#include "LED.h"

using namespace teaheart;

void init() {
    SetSysClock();
    // LED
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    GPIO_InitTypeDef GPIOInit = {
            .GPIO_Pin = GPIO_Pin_0,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_Out_PP
    };
    GPIO_Init(GPIOA, &GPIOInit);
}

int main() {
    init();

    LED led0(GPIOA, GPIO_Pin_0);
    led0.on();

    while (true) {}
}

#endif //TASK1_1
