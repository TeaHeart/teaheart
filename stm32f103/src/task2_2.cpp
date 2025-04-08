#ifdef TASK2_2

#include "fix.h"
#include "stm32f10x.h"

#include "LED.h"
#include "Delay.h"
#include "OLED.h"
#include "Button.h"

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

    LED led0(GPIOA, GPIO_Pin_0);
    Button btn0(GPIOB, GPIO_Pin_11);
    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    uint8_t pressCount = 0;
    bool prevPress = false;

    oled.showNum(0, 0, pressCount, 3);

    while (true) {
        bool currPress = btn0.getStatus() == Bit_RESET;
        if (prevPress && !currPress) {
            led0.toggle();
            oled.showNum(0, 0, ++pressCount, 3);
        }
        prevPress = currPress;
        Delay::millisecond(20);
    }
}

#endif //TASK2_2
