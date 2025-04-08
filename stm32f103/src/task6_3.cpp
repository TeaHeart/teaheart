#ifdef TASK6_3

#include "fix.h"
#include "stm32f10x.h"

#include "OLED.h"
#include "Button.h"

#include <cstring>

using namespace teaheart;

void USARTSendByte(char ch) {
    USART_SendData(USART1, ch);
    while (USART_GetFlagStatus(USART1, USART_FLAG_TXE) == RESET) {}
}

void USARTSendString(const char *str) {
    for (; *str != '\0'; ++str) {
        USARTSendByte(*str);
    }
}

char USARTReceiveByte() {
    while (USART_GetFlagStatus(USART1, USART_FLAG_RXNE) == RESET) {}
    return USART_ReceiveData(USART1);
}

char receiveBuffer[16 + 1] = {0};
volatile bool isNewData = false;

extern "C" {
void USART1_IRQHandler() {
    if (USART_GetITStatus(USART1, USART_IT_RXNE) == SET) {
        static uint8_t status = 0;
        static uint8_t index = 0;
        if (status == 0) {
            char ch = USARTReceiveByte();
            if (ch == '@') {
                status = 1;
                index = 0;
            }
        } else if (status == 1) {
            char ch = USARTReceiveByte();
            if (ch == ';' || index >= 16) {
                receiveBuffer[index] = '\0';
                status = 0;
                isNewData = true;
            } else {
                receiveBuffer[index++] = ch;
            }
        }
        USART_ClearITPendingBit(USART1, USART_IT_RXNE);
    }
}
}

#ifdef MASTER

void button0Scan(OLED &oled) {
    static Button btn0(GPIOB, GPIO_Pin_11);
    static bool prevPress = false;
    bool currPress = btn0.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        USARTSendString("@HELLO;");
        oled.showString(3, 0, "HELLO");
    }
    prevPress = currPress;
}

void button1Scan(OLED &oled) {
    static Button btn1(GPIOB, GPIO_Pin_1);
    static bool prevPress = false;
    bool currPress = btn1.getStatus() == Bit_RESET;
    if (prevPress && !currPress) {
        USARTSendString("@ABC;");
        oled.showString(3, 0, "ABC  ");
    }
    prevPress = currPress;
}

void init() {
    SetSysClock();
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB, ENABLE);
    // OLED
    GPIO_InitTypeDef GPIOInit = {
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
    // USART TX
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_9,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_AF_PP
    };
    GPIO_Init(GPIOA, &GPIOInit);
    // RX
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_10,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_IPU
    };
    GPIO_Init(GPIOA, &GPIOInit);
    // USART
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_USART1, ENABLE);
    USART_InitTypeDef USARTInit = {
            .USART_BaudRate = 9600,
            .USART_WordLength = USART_WordLength_8b,
            .USART_StopBits = USART_StopBits_1,
            .USART_Parity = USART_Parity_No,
            .USART_Mode = USART_Mode_Tx | USART_Mode_Rx,
            .USART_HardwareFlowControl = USART_HardwareFlowControl_None
    };
    USART_Init(USART1, &USARTInit);
    USART_Cmd(USART1, ENABLE);
    // IT
    USART_ClearFlag(USART1, USART_FLAG_RXNE);
    USART_ITConfig(USART1, USART_IT_RXNE, ENABLE);
    // NVIC
    NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2);
    NVIC_InitTypeDef NVICInit = {
            USART1_IRQn,
            0,
            0,
            ENABLE,
    };
    NVIC_Init(&NVICInit);
}

int main() {
    init();

    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    oled.showString(0, 0, "Receive:");
    oled.showString(2, 0, "Send:");

    while (true) {
        button0Scan(oled);
        button1Scan(oled);
        if (isNewData) {
            receiveBuffer[16] = 0;
            oled.showString(1, 0, "                ");
            oled.showString(3, 0, "                ");
            oled.showString(1, 0, receiveBuffer);
            isNewData = false;
        }
    }
}

#else

void init() {
    SetSysClock();
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB, ENABLE);
    // OLED
    GPIO_InitTypeDef GPIOInit = {
            .GPIO_Pin = GPIO_Pin_8 | GPIO_Pin_9,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_Out_OD
    };
    GPIO_Init(GPIOB, &GPIOInit);
    // USART TX
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_10,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_AF_PP
    };
    GPIO_Init(GPIOA, &GPIOInit);
    // RX
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_9,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_IPU
    };
    GPIO_Init(GPIOA, &GPIOInit);
    // USART
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_USART1, ENABLE);
    USART_InitTypeDef USARTInit = {
            .USART_BaudRate = 9600,
            .USART_WordLength = USART_WordLength_8b,
            .USART_StopBits = USART_StopBits_1,
            .USART_Parity = USART_Parity_No,
            .USART_Mode = USART_Mode_Tx | USART_Mode_Rx,
            .USART_HardwareFlowControl = USART_HardwareFlowControl_None
    };
    USART_Init(USART1, &USARTInit);
    USART_Cmd(USART1, ENABLE);
    // IT
    USART_ClearFlag(USART1, USART_FLAG_RXNE);
    USART_ITConfig(USART1, USART_IT_RXNE, ENABLE);
    // NVIC
    NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2);
    NVIC_InitTypeDef NVICInit = {
            USART1_IRQn,
            0,
            0,
            ENABLE,
    };
    NVIC_Init(&NVICInit);
}

int main() {
    init();

    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    oled.showString(0, 0, "Receive:");
    oled.showString(2, 0, "Send:");

    while (true) {
        if (isNewData) {
            receiveBuffer[16] = 0;
            oled.showString(1, 0, "                ");
            oled.showString(3, 0, "                ");
            oled.showString(1, 0, receiveBuffer);
            if (strcmp("HELLO", receiveBuffer) == 0) {
                oled.showString(3, 0, "HI");
                USARTSendString("@HI;");
            } else {
                oled.showString(3, 0, "ERROR");
                USARTSendString("@ERROR;");
            }
            isNewData = false;
        }
    }
}

#endif

#endif //TASK6_3
