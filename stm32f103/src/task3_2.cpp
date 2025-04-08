#ifdef TASK3_2

#include "fix.h"
#include "stm32f10x.h"

#include "LED.h"
#include "Buzzer.h"
#include "Button.h"
#include "OLED.h"

using namespace teaheart;

enum {
    Setting = 0x01,         // 设置模式
    SettingHour = 0x02,     // 设置 时 模式
    SettingMinute = 0x04,   // 设置 分 模式
    SettingSecond = 0x08,   // 设置 秒 模式

    Init = 0x10,            // 初始状态
    Running = 0x20,         // 运行状态
    Pause = 0x40,           // 暂停状态
    End = 0x80              // 停止状态
} mode = Init;

const uint32_t maxTime = 24 * 60 * 60; // 定时最大时间

uint32_t settingTime;
uint32_t currTime;
uint32_t recordTime;

uint8_t count100ms = 0;

void countDown() {
    if (++count100ms >= 10) {
        // 1s 执行一次
        count100ms = 0;
        if (mode == Running) {
            if (currTime <= 0) {
                mode = End;
            } else {
                currTime--;
            }
        }
    }
}

void button0Scan() {
    static Button btn0(GPIOB, GPIO_Pin_11);
    static bool prevPress = false;
    bool currPress = btn0.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        // 设置模式 <=> 倒计时模式
        if (mode < Init) {
            mode = Init;
        } else {
            mode = Setting;
        }
    }
    prevPress = currPress;
}

void button1Scan() {
    static Button btn1(GPIOB, GPIO_Pin_1);
    static bool prevPress = false;
    bool currPress = btn1.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        // 设置{时|分|秒} <=> 重置
        if (mode < Init) {
            if (mode == SettingHour) {
                mode = SettingMinute;
            } else if (mode == SettingMinute) {
                mode = SettingSecond;
            } else if (mode == SettingSecond) {
                mode = SettingHour;
            }
        } else {
            mode = Init;
        }
    }
    prevPress = currPress;
}

void button2Scan() {
    static Button btn2(GPIOC, GPIO_Pin_15);
    static bool prevPress = false;
    bool currPress = btn2.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        // 增加 <=> {运行|暂停}
        if (mode < Init) {
            if (mode == SettingHour) {
                settingTime = settingTime >= maxTime - 3600 ? settingTime + 3600 - maxTime : settingTime + 3600;
            } else if (mode == SettingMinute) {
                settingTime = settingTime >= maxTime - 60 ? settingTime + 60 - maxTime : settingTime + 60;
            } else if (mode == SettingSecond) {
                settingTime = settingTime >= maxTime - 1 ? settingTime + 1 - maxTime : settingTime + 1;
            }
        } else {
            if (mode == Running) {
                mode = Pause;
            } else if (mode == Pause) {
                mode = Running;
            }
        }
    }
    prevPress = currPress;
}

void button3Scan() {
    static Button btn3(GPIOC, GPIO_Pin_13);
    static bool prevPress = false;
    bool currPress = btn3.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        // 减少 <=> 计时
        if (mode < Init) {
            if (mode == SettingHour) {
                settingTime = settingTime < 3600 ? maxTime - settingTime - 3600 : settingTime - 3600;
            } else if (mode == SettingMinute) {
                settingTime = settingTime < 60 ? maxTime - settingTime - 60 : settingTime - 60;
            } else if (mode == SettingSecond) {
                settingTime = settingTime < 1 ? maxTime - settingTime - 1 : settingTime - 1;
            }
        } else {
            recordTime = currTime;
        }
    }
    prevPress = currPress;
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
        TIM_ClearITPendingBit(TIM2, TIM_IT_Update);
    }
}
}

void showTime(OLED &oled, uint8_t row, uint32_t time) {
    oled.showNum(row, 8, time / 3600 % 60, 2);
    oled.showNum(row, 11, time / 60 % 60, 2);
    oled.showNum(row, 14, time / 1 % 60, 2);
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
        if (mode == Setting) {
            oled.clear();
            oled.showString(0, 0, "Setting:--:--:--"); // 8 11 14
            reset(led, buzzer, oled);
            mode = SettingHour;
        } else if (mode == Init) {
            oled.clear();
            oled.showString(0, 0, "Time   :--:--:--"); // 8 11 14
            oled.showString(1, 0, "Record :--:--:--");
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
        } else if (mode < Init) { // 设置时闪烁
            if (count100ms <= 5) {
                showTime(oled, 0, settingTime);
            } else {
                if (mode == SettingHour) {
                    oled.showString(0, 8, "  ");
                } else if (mode == SettingMinute) {
                    oled.showString(0, 11, "  ");
                } else if (mode == SettingSecond) {
                    oled.showString(0, 14, "  ");
                }
            }
        } else if (mode == End) {
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

#endif //TASK3_2
