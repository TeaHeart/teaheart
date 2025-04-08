#ifdef TASK4_3

#include "fix.h"
#include "stm32f10x.h"

#include "Button.h"
#include "OLED.h"

using namespace teaheart;

uint8_t hz = 50;
uint8_t duty = 50;

void updateParam() {
    TIM_PrescalerConfig(TIM2, 720 * 1000 / hz - 1, TIM_PSCReloadMode_Immediate);
    TIM_SetCompare1(TIM2, duty);
}

// hz - 10
void button0Scan() {
    static Button btn0(GPIOB, GPIO_Pin_11);
    static bool prevPress = false;
    bool currPress = btn0.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        hz = hz <= 20 ? 200 : hz - 10;
        updateParam();
    }
    prevPress = currPress;
}

// hz + 10
void button1Scan() {
    static Button btn1(GPIOB, GPIO_Pin_1);
    static bool prevPress = false;
    bool currPress = btn1.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        hz = hz >= 200 ? 20 : hz + 10;
        updateParam();
    }
    prevPress = currPress;
}

// duty - 10
void button2Scan() {
    static Button btn2(GPIOC, GPIO_Pin_15);
    static bool prevPress = false;
    bool currPress = btn2.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        duty = duty <= 0 ? 100 : duty - 10;
        updateParam();
    }
    prevPress = currPress;
}

// duty + 10
void button3Scan() {
    static Button btn3(GPIOC, GPIO_Pin_13);
    static bool prevPress = false;
    bool currPress = btn3.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        duty = duty >= 100 ? 0 : duty + 10;
        updateParam();
    }
    prevPress = currPress;
}

void showParam(OLED &oled) {
    static uint8_t hzBuf = 0;
    static uint8_t dutyBuf = 0;
    if (hzBuf != hz) {
        oled.showNum(0, 6, hzBuf = hz, 3);
    }
    if (dutyBuf != duty) {
        oled.showNum(1, 6, dutyBuf = duty, 3);
    }
}

void init() {
    SetSysClock();
    // PWM OUT
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    GPIO_InitTypeDef GPIOInit = {
            .GPIO_Pin = GPIO_Pin_0,
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
    TIM_Cmd(TIM2, ENABLE);
}

int main() {
    init();

    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    oled.showString(0, 0, "hz  : --- Hz");
    oled.showString(1, 0, "duty: --- %");

    updateParam();

    while (true) {
        showParam(oled);
        button0Scan();
        button1Scan();
        button2Scan();
        button3Scan();
    }
}

#endif //TASK4_3
