#ifdef TASK3_3

#include "fix.h"
#include "stm32f10x.h"

#include "LED.h"
#include "Buzzer.h"
#include "Button.h"
#include "OLED.h"

using namespace teaheart;

enum {
    Init = 0x01,        // 初始状态
    Running = 0x02,     // 运行状态
    Pause = 0x04,       // 暂停状态
    Warning = 0x08,     // 警报状态
    End = 0x10          // 停止状态
} mode = Init;

uint32_t settingTime = 240;
uint32_t currTime;
uint32_t recordTime;

void button0Scan() {
    static Button btn0(GPIOB, GPIO_Pin_11);
    static bool prevPress = false;
    bool currPress = btn0.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        // 12秒 <=> 24秒
        settingTime = settingTime != 120 ? 120 : 240;
        mode = Init;
    }
    prevPress = currPress;
}

void button1Scan() {
    static Button btn1(GPIOB, GPIO_Pin_1);
    static bool prevPress = false;
    bool currPress = btn1.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        // 重置
        mode = Init;
    }
    prevPress = currPress;
}

void button2Scan() {
    static Button btn2(GPIOC, GPIO_Pin_15);
    static bool prevPress = false;
    bool currPress = btn2.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        // 运行|暂停
        if (mode == Running) {
            mode = Pause;
        } else if (mode == Pause) {
            mode = Running;
        }
    }
    prevPress = currPress;
}

void button3Scan() {
    static Button btn3(GPIOC, GPIO_Pin_13);
    static bool prevPress = false;
    bool currPress = btn3.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        // 计时
        recordTime = currTime;
    }
    prevPress = currPress;
}

uint8_t count100ms;
uint8_t warningCount;

void countDown() { // 100ms 触发一次
    if (++count100ms >= 10) { // 1s执行一次
        count100ms = 0;
        if (mode == Warning) {
            if (warningCount >= 5) {
                mode = End;
            } else {
                warningCount++;
            }
        }
    }
    if (mode == Running) {
        if (currTime <= 0) {
            warningCount = 0;
            mode = Warning;
        } else {
            currTime--;
        }
    }
}

extern "C" {
// 100ms 触发一次
void TIM2_IRQHandler() {
    if (TIM_GetITStatus(TIM2, TIM_IT_Update) == SET) {
        countDown();
        button0Scan();
        button1Scan();
        button2Scan();
        button3Scan();
        TIM_ClearFlag(TIM2, TIM_IT_Update);
    }
}
}

void showTime(OLED &oled, uint8_t row, uint32_t time) {
    oled.showNum(row, 8, time / 10, 2);
    oled.showNum(row, 11, time % 10, 1);
}

void reset(LED &led, Buzzer &buzzer, OLED &oled) {
    count100ms = 0;
    recordTime = 0;
    led.off();
    buzzer.off();
    showTime(oled, 0, currTime = settingTime);
}

void init() {
    SetSysClock();
    // LED
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    GPIO_InitTypeDef GPIOInit = {
            .GPIO_Pin = GPIO_Pin_4,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_Out_PP
    };
    GPIO_Init(GPIOA, &GPIOInit);
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB, ENABLE);
    // Buzzer
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_12,
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
    TIM_InternalClockConfig(TIM2); // 内部时钟
    // TIM TimeBase
    TIM_TimeBaseInitTypeDef TIMTimeBaseInit = {
            .TIM_Prescaler = 7200 - 1,
            .TIM_CounterMode = TIM_CounterMode_Up,
            .TIM_Period = 1000 - 1,
            .TIM_ClockDivision = TIM_CKD_DIV1,
            .TIM_RepetitionCounter = 0
    }; // 100ms
    TIM_TimeBaseInit(TIM2, &TIMTimeBaseInit);
    // IT
    TIM_ClearFlag(TIM2, TIM_FLAG_Update);
    TIM_ITConfig(TIM2, TIM_IT_Update, ENABLE);
    // NVIC
    NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2);
    NVIC_InitTypeDef NVICInit = {
            .NVIC_IRQChannel = TIM2_IRQn,
            .NVIC_IRQChannelPreemptionPriority = 0,
            .NVIC_IRQChannelSubPriority = 0,
            .NVIC_IRQChannelCmd = ENABLE,
    };
    NVIC_Init(&NVICInit);
    TIM_Cmd(TIM2, ENABLE);
}

int main() {
    init();

    LED led(GPIOA, GPIO_Pin_4);
    Buzzer buzzer(GPIOB, GPIO_Pin_12);
    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    while (true) {
        if (mode == Init) {
            oled.clear();
            oled.showString(0, 0, "Time   :--.-"); // 8
            oled.showString(1, 0, "Record :--.-");
            reset(led, buzzer, oled);
            mode = Pause;
        } else if (mode == Running || mode == Pause) {
            static uint32_t currTimeBuf;
            if (currTimeBuf != currTime) {
                showTime(oled, 0, currTimeBuf = currTime);
            }
            static uint32_t recordTimeBuf;
            if (recordTimeBuf != recordTime) {
                showTime(oled, 1, recordTimeBuf = recordTime);
            }
        } else if (mode == Warning) {
            if (warningCount >= 5) {
                led.off();
                buzzer.off();
            } else {
                if (count100ms <= 5) {
                    led.on();
                    buzzer.on();
                } else {
                    led.off();
                    buzzer.off();
                }
            }
        }
    }
}

#endif //TASK3_3
