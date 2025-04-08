#ifdef TASK6_1

#include "fix.h"
#include "stm32f10x.h"

#include "OLED.h"
#include "Delay.h"

#include <cstdio>

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

extern "C" {
#ifdef __GNUC__
int __io_putchar(int ch) {
    USARTSendByte(ch);
    return ch;
}

int _write(int file, char *ptr, int length) {
    for (int i = 0; i < length; ++i) {
        __io_putchar(ptr[i]);
    }
    return length;
}
#else
int fputc(int ch, FILE *f) {
    // ...
}
#endif
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
            .USART_WordLength = 8,
            .USART_StopBits = USART_StopBits_1,
            .USART_Parity = USART_Parity_No,
            .USART_Mode = USART_Mode_Tx | USART_Mode_Rx,
            .USART_HardwareFlowControl = USART_HardwareFlowControl_None
    };
    USART_Init(USART1, &USARTInit);
    USART_Cmd(USART1, ENABLE);
}

int main() {
    init();

    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    while (true) {
        USARTSendString("hello\r\n");
        oled.showString(0, 0, "hello");
        Delay::millisecond(500);
        printf("world\r\n");
        oled.showString(0, 0, "world");
        Delay::millisecond(500);
    }
}

#endif //TASK6_1
