#ifdef TASK4_5

#include "fix.h"
#include "stm32f10x.h"

#include "OLED.h"

using namespace teaheart;

uint8_t duty = 50;

void updateDuty(OLED &oled) {
    static uint8_t dutyBuf = 0;
    if (dutyBuf != duty) {
        TIM_SetCompare1(TIM2, duty);
        oled.showNum(0, 6, dutyBuf = duty, 3);
    }
    duty = TIM_GetCounter(TIM3);
}

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
    // PWM OUT
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_0,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_AF_PP
    };
    GPIO_Init(GPIOA, &GPIOInit);
    // GPIO IC
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_6 | GPIO_Pin_7,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_IPU
    };
    GPIO_Init(GPIOA, &GPIOInit);
    // TIM2
    RCC_APB1PeriphClockCmd(RCC_APB1Periph_TIM2, ENABLE);
    TIM_TimeBaseInitTypeDef TIMTimeBaseInit = {
            .TIM_Prescaler = 720 - 1,
            .TIM_CounterMode = TIM_CounterMode_Up,
            .TIM_Period = 100 - 1,
            .TIM_ClockDivision = TIM_CKD_DIV1,
            .TIM_RepetitionCounter = 0
    }; // 1ms
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
    // TIM3
    RCC_APB1PeriphClockCmd(RCC_APB1Periph_TIM3, ENABLE);
    TIMTimeBaseInit = {
            .TIM_Prescaler = 1 - 1,
            .TIM_CounterMode = TIM_CounterMode_Up,
            .TIM_Period = 100 - 1,
            .TIM_ClockDivision = TIM_CKD_DIV1,
            .TIM_RepetitionCounter = 0
    };
    TIM_TimeBaseInit(TIM3, &TIMTimeBaseInit);
    // TIM3 IC
    TIM_ICInitTypeDef TIMICInit = {
            .TIM_Channel = TIM_Channel_1,
            .TIM_ICPolarity = TIM_ICPolarity_Rising,
            .TIM_ICSelection = TIM_ICSelection_DirectTI,
            .TIM_ICPrescaler = TIM_ICPSC_DIV1,
            .TIM_ICFilter = 0xF
    };
    TIM_ICInit(TIM3, &TIMICInit);
    TIMICInit = {
            .TIM_Channel = TIM_Channel_2,
            .TIM_ICPolarity = TIM_ICPolarity_Rising,
            .TIM_ICSelection = TIM_ICSelection_DirectTI,
            .TIM_ICPrescaler = TIM_ICPSC_DIV1,
            .TIM_ICFilter = 0xF
    };
    TIM_ICInit(TIM3, &TIMICInit);
    // TIM Encoder
    TIM_EncoderInterfaceConfig(TIM3, TIM_EncoderMode_TI12, TIM_ICPolarity_Rising, TIM_ICPolarity_Rising);
    TIM_Cmd(TIM2, ENABLE);
    TIM_Cmd(TIM3, ENABLE);
}

int main() {
    init();

    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    oled.showString(0, 0, "Duty: ---%");

    while (true) {
        updateDuty(oled);
    }
}

#endif //TASK4_5
