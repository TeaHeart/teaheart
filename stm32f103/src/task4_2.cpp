#ifdef TASK4_2

#include "fix.h"
#include "stm32f10x.h"

#include "OLED.h"
#include "Util.h"

using namespace teaheart;

uint8_t duty[] = {20, 40, 60, 80};

extern "C" {
void TIM2_IRQHandler() {
    if (TIM_GetITStatus(TIM2, TIM_IT_Update) == SET) {
        static uint16_t _1ms = 0;
        static int8_t dir[] = {1, 1, 1, 1};
        if (++_1ms >= 16) {
            _1ms = 0;
            for (uint8_t i = 0; i < LENGTH_OF(duty); i++) {
                if (duty[i] == 100) {
                    dir[i] = -1;
                } else if (duty[i] == 0) {
                    dir[i] = 1;
                }
                duty[i] += dir[i];
            }
        }
        TIM_ClearITPendingBit(TIM2, TIM_IT_Update);
    }
}
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
    // TIM
    RCC_APB1PeriphClockCmd(RCC_APB1Periph_TIM2, ENABLE);
    // TIM TimeBase
    TIM_TimeBaseInitTypeDef TIMTimeBaseInit = {
            .TIM_Prescaler = 720 - 1,
            .TIM_CounterMode = TIM_CounterMode_Up,
            .TIM_Period = 100 - 1,
            .TIM_ClockDivision = TIM_CKD_DIV1,
            .TIM_RepetitionCounter = 0
    }; // 1ms
    TIM_TimeBaseInit(TIM2, &TIMTimeBaseInit);
    // IT
    TIM_ClearFlag(TIM2, TIM_FLAG_Update);
    TIM_ITConfig(TIM2, TIM_IT_Update, ENABLE);
    // NVIC
    NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2);
    NVIC_InitTypeDef NVICInit = {
            TIM2_IRQn,
            0,
            0,
            ENABLE,
    };
    NVIC_Init(&NVICInit);
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
    }
}

#endif //TASK4_2
