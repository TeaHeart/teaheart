#ifdef TASK2_4

#include "fix.h"
#include "stm32f10x.h"

#include "LED.h"

using namespace teaheart;

LED *led;

extern "C" {
void EXTI1_IRQHandler() {
    if (EXTI_GetITStatus(EXTI_Line1) == SET) {
        led->off();
        EXTI_ClearITPendingBit(EXTI_Line1);
    }
}

void EXTI15_10_IRQHandler() {
    if (EXTI_GetITStatus(EXTI_Line11) == SET) {
        led->on();
        EXTI_ClearITPendingBit(EXTI_Line11);
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
            .GPIO_Mode = GPIO_Mode_Out_PP
    };
    GPIO_Init(GPIOA, &GPIOInit);
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB, ENABLE);
    // Button0 Button1
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_1 | GPIO_Pin_11,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_IPU
    };
    GPIO_Init(GPIOB, &GPIOInit);
    // AFIO
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_AFIO, ENABLE);
    GPIO_EXTILineConfig(GPIO_PortSourceGPIOB, GPIO_PinSource1);
    GPIO_EXTILineConfig(GPIO_PortSourceGPIOB, GPIO_PinSource11);
    // EXTI
    EXTI_InitTypeDef EXTIInit = {
            .EXTI_Line = EXTI_Line1 | EXTI_Line11,
            .EXTI_Mode = EXTI_Mode_Interrupt,
            .EXTI_Trigger = EXTI_Trigger_Rising,
            .EXTI_LineCmd = ENABLE
    };
    EXTI_Init(&EXTIInit);
    // NVIC
    NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2);
    NVIC_InitTypeDef NVICInit = {
            .NVIC_IRQChannel = EXTI15_10_IRQn,
            .NVIC_IRQChannelPreemptionPriority = 0,
            .NVIC_IRQChannelSubPriority = 0,
            .NVIC_IRQChannelCmd = ENABLE
    };
    NVIC_Init(&NVICInit);
    NVICInit = {
            .NVIC_IRQChannel = EXTI1_IRQn,
            .NVIC_IRQChannelPreemptionPriority = 0,
            .NVIC_IRQChannelSubPriority = 0,
            .NVIC_IRQChannelCmd = ENABLE
    };
    NVIC_Init(&NVICInit);
}

int main() {
    init();

    LED led0(GPIOA, GPIO_Pin_0);
    led = &led0;

    while (true) {}
}

#endif //TASK2_4
