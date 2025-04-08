#ifdef TASK4_6

#include "fix.h"
#include "stm32f10x.h"

#include "LED.h"
#include "OLED.h"
#include "Util.h"

using namespace teaheart;

uint8_t ledNum = 0;

extern "C" {
void TIM3_IRQHandler() {
    if (TIM_GetITStatus(TIM3, TIM_IT_Update) == SET) {
        if (GPIO_ReadInputDataBit(GPIOA, GPIO_Pin_7) == Bit_SET) {
            ledNum = ledNum >= 4 ? 4 : ledNum + 1;
        } else {
            ledNum = ledNum <= 0 ? 0 : ledNum - 1;
        }
        TIM_ClearITPendingBit(TIM3, TIM_IT_Update);
    }
}
}

void init() {
    SetSysClock();
    // LED
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    GPIO_InitTypeDef GPIOInit = {
            .GPIO_Pin = GPIO_Pin_0 | GPIO_Pin_1 | GPIO_Pin_2 | GPIO_Pin_3,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_Out_PP
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
            .GPIO_Pin = GPIO_Pin_6 | GPIO_Pin_7,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_IPU
    };
    GPIO_Init(GPIOA, &GPIOInit);
    // TIM3
    RCC_APB1PeriphClockCmd(RCC_APB1Periph_TIM3, ENABLE);
    TIM_TimeBaseInitTypeDef TIMTimeBaseInit = {
            .TIM_Prescaler = 1 - 1,
            .TIM_CounterMode = TIM_CounterMode_Up,
            .TIM_Period = 80 - 1,
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
    // IT
    TIM_ClearFlag(TIM3, TIM_IT_Update);
    TIM_ITConfig(TIM3, TIM_IT_Update, ENABLE);
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

    LED leds[] = {
            LED(GPIOA, GPIO_Pin_0),
            LED(GPIOA, GPIO_Pin_1),
            LED(GPIOA, GPIO_Pin_2),
            LED(GPIOA, GPIO_Pin_3)
    };

    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    oled.showString(0, 0, "CNT: --");
    oled.showString(1, 0, "LED: -");

    while (true) {
        oled.showNum(0, 5, TIM_GetCounter(TIM3), 2);
        oled.showNum(1, 5, ledNum, 1);
        for (uint8_t i = 0; i < LENGTH_OF(leds); ++i) {
            if (i < ledNum) {
                leds[i].on();
            } else {
                leds[i].off();
            }
        }
    }
}

#endif //TASK4_6
