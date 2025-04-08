#ifdef TASK4_4

#include "fix.h"
#include "stm32f10x.h"

#include "LED.h"
#include "OLED.h"

using namespace teaheart;

LED *led;

uint8_t n = 0;

extern "C" {
void TIM3_IRQHandler() {
    static bool flag = false;
    if (TIM_GetITStatus(TIM3, TIM_IT_CC2) == SET) {
        TIM_Cmd(TIM3, ENABLE);
        TIM_SetCounter(TIM3, 0);
        if (flag) {
            led->toggle();
            flag = false;
            n++;
        }
        TIM_ClearITPendingBit(TIM3, TIM_IT_CC2);
    } else if (TIM_GetITStatus(TIM3, TIM_IT_Update) == SET) {
        flag = true;
        TIM_Cmd(TIM3, DISABLE);
        TIM_ClearITPendingBit(TIM3, TIM_IT_Update);
    }
}
}

void init() {
    SetSysClock();
    // LED
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    GPIO_InitTypeDef GPIOInit = {
            .GPIO_Pin = GPIO_Pin_0,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_Out_OD
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
    // GPIO IC
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_6,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_IPU
    };
    GPIO_Init(GPIOA, &GPIOInit);
    // TIM3
    RCC_APB1PeriphClockCmd(RCC_APB1Periph_TIM3, ENABLE);
    TIM_InternalClockConfig(TIM3);
    // TIM TimeBase
    TIM_TimeBaseInitTypeDef TIMTimeBaseInit = {
            .TIM_Prescaler = 7200 - 1,
            .TIM_CounterMode = TIM_CounterMode_Up,
            .TIM_Period = 30000 - 1,
            .TIM_ClockDivision = TIM_CKD_DIV1,
            .TIM_RepetitionCounter = 0xF
    }; // 3s
    TIM_TimeBaseInit(TIM3, &TIMTimeBaseInit);
    // TIM3 IC
    TIM_ICInitTypeDef TIMICInit = {
            .TIM_Channel = TIM_Channel_1,
            .TIM_ICPolarity = TIM_ICPolarity_Falling,
            .TIM_ICSelection = TIM_ICSelection_DirectTI,
            .TIM_ICPrescaler = TIM_ICPSC_DIV1,
            .TIM_ICFilter = 0xF
    };
    TIM_ICInit(TIM3, &TIMICInit);
    TIM_PWMIConfig(TIM3, &TIMICInit);
    // TIM3 MST
    TIM_SelectInputTrigger(TIM3, TIM_TS_TI1FP1);
    TIM_SelectSlaveMode(TIM3, TIM_SlaveMode_Gated);
    // IT
    TIM_ClearFlag(TIM3, TIM_IT_Update | TIM_IT_CC2);
    TIM_ITConfig(TIM3, TIM_IT_Update | TIM_IT_CC2, ENABLE);
    // NVIC
    NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2);
    NVIC_InitTypeDef NVICInit = {
            .NVIC_IRQChannel = TIM3_IRQn,
            .NVIC_IRQChannelPreemptionPriority = 0,
            .NVIC_IRQChannelSubPriority = 0,
            .NVIC_IRQChannelCmd = ENABLE
    };
    NVIC_Init(&NVICInit);
    TIM_Cmd(TIM3, ENABLE);
}

int main() {
    init();

    LED led0(GPIOA, GPIO_Pin_0);
    led = &led0;

    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    oled.showString(0, 0, "CNT: ");
    oled.showString(1, 0, "NUM: ");
    oled.showString(2, 0, "IC1: ");
    oled.showString(3, 0, "IC2: ");


    while (true) {
        oled.showNum(0, 5, TIM_GetCounter(TIM3), 5);
        oled.showNum(1, 5, n, 5);
        oled.showNum(2, 5, TIM_GetCapture1(TIM3), 5);
        oled.showNum(3, 5, TIM_GetCapture2(TIM3), 5);
    }
}

#endif //TASK4_4
