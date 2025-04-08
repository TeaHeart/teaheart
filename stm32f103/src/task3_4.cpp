#ifdef TASK3_4

#include "fix.h"
#include "stm32f10x.h"

#include "Button.h"
#include "OLED.h"

using namespace teaheart;

uint8_t duty[] = {10, 50, 50, 90};

void button0Scan() {
    static Button btn0(GPIOB, GPIO_Pin_11);
    static bool prevPress = false;
    bool currPress = btn0.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        duty[0] = duty[0] >= 100 ? 0 : duty[0] + 10;
        TIM_SetCompare1(TIM2, duty[0]);
    }
    prevPress = currPress;
}

void button1Scan() {
    static Button btn1(GPIOB, GPIO_Pin_1);
    static bool prevPress = false;
    bool currPress = btn1.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        duty[1] = duty[1] >= 100 ? 0 : duty[1] + 10;
        TIM_SetCompare2(TIM2, duty[1]);
    }
    prevPress = currPress;
}

void button2Scan() {
    static Button btn2(GPIOC, GPIO_Pin_15);
    static bool prevPress = false;
    bool currPress = btn2.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        duty[2] = duty[2] >= 100 ? 0 : duty[2] + 10;
        TIM_SetCompare3(TIM2, duty[2]);
    }
    prevPress = currPress;
}

void button3Scan() {
    static Button btn3(GPIOC, GPIO_Pin_13);
    static bool prevPress = false;
    bool currPress = btn3.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        duty[3] = duty[3] >= 100 ? 0 : duty[3] + 10;
        TIM_SetCompare4(TIM2, duty[3]);
    }
    prevPress = currPress;
}

void updateDuty(OLED &oled) {
    TIM_SetCompare1(TIM2, duty[0]);
    TIM_SetCompare2(TIM2, duty[1]);
    TIM_SetCompare3(TIM2, duty[2]);
    TIM_SetCompare4(TIM2, duty[3]);

    static uint8_t dutyBuf[] = {0, 0, 0, 0};
    for (uint8_t i = 0; i < LENGTH_OF(dutyBuf); i++) {
        if (dutyBuf[i] != duty[i]) {
            oled.showNum(i, 11, dutyBuf[i] = duty[i], 3);
        }
    }
}

void init() {
    SetSysClock();
    // PWM OUT
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    GPIO_InitTypeDef GPIOInit = {
            .GPIO_Pin = GPIO_Pin_0 | GPIO_Pin_1 | GPIO_Pin_2 | GPIO_Pin_3,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_AF_PP
    };
    GPIO_Init(GPIOA, &GPIOInit);
    // OLED
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB, ENABLE);
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_8 | GPIO_Pin_9,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_Out_OD
    };
    GPIO_Init(GPIOB, &GPIOInit);
    // Button0 Button1
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_1 | GPIO_Pin_11,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_IPU
    };
    GPIO_Init(GPIOB, &GPIOInit);
    // Button2 Button3
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOC, ENABLE);
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_13 | GPIO_Pin_15,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_IPU
    };
    GPIO_Init(GPIOC, &GPIOInit);
    // TIM
    RCC_APB1PeriphClockCmd(RCC_APB1Periph_TIM2, ENABLE);
    // TIM TimeBase
    TIM_TimeBaseInitTypeDef TIMTimeBaseInit = {
            .TIM_Prescaler = 720 - 1,
            .TIM_CounterMode = TIM_CounterMode_Up,
            .TIM_Period = 100 - 1,
            .TIM_ClockDivision = TIM_CKD_DIV1,
            .TIM_RepetitionCounter = 0
    };
    TIM_TimeBaseInit(TIM2, &TIMTimeBaseInit);
    // TIM OC
    TIM_OCInitTypeDef TIMOCInit = {
            .TIM_OCMode = TIM_OCMode_PWM1,
            .TIM_OutputState = TIM_OutputState_Enable,
            .TIM_OutputNState = 0,
            .TIM_Pulse = 50,
            .TIM_OCPolarity = 0,
            .TIM_OCNPolarity = TIM_OCPolarity_High,
            .TIM_OCIdleState = 0,
            .TIM_OCNIdleState = 0,
    };
    TIM_OC1Init(TIM2, &TIMOCInit);
    TIM_OC2Init(TIM2, &TIMOCInit);
    TIM_OC3Init(TIM2, &TIMOCInit);
    TIM_OC4Init(TIM2, &TIMOCInit);
    TIM_Cmd(TIM2, ENABLE);
}

int main() {
    init();

    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    oled.showString(0, 0, "Channel-1: ---%");
    oled.showString(1, 0, "Channel-2: ---%");
    oled.showString(2, 0, "Channel-3: ---%");
    oled.showString(3, 0, "Channel-4: ---%");

    updateDuty(oled);

    while (true) {
        updateDuty(oled);
        button0Scan();
        button1Scan();
        button2Scan();
        button3Scan();
    }
}

#endif //TASK3_4
