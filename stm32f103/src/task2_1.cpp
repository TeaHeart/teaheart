#ifdef TASK2_1

#include "fix.h"
#include "stm32f10x.h"

#include "OLED.h"

using namespace teaheart;

void init() {
    SetSysClock();
    // OLED
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB, ENABLE);
    GPIO_InitTypeDef GPIOInit = {
            .GPIO_Pin = GPIO_Pin_8 | GPIO_Pin_9,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_Out_OD
    };
    GPIO_Init(GPIOB, &GPIOInit);
}

int main() {
    init();

    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));
    oled.showString(0, 0, "teaheart");

    uint8_t count = 0;

    while (true) {
        oled.showNum(1, 0, count, 3);
        oled.showNum(2, 0, count, 2, 16);
        oled.showSignedNum(3, 0, (int8_t) count, 4);
        ++count;
    }
}

#endif //TASK2_1
