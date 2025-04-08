#ifdef TASK5_4

#include "fix.h"
#include "stm32f10x.h"

#include "OLED.h"
#include "Delay.h"

#include <cstdio>

using namespace teaheart;

char str[16 + 1] = {0};

void USARTSendByte(char ch) {
    USART_SendData(USART1, ch);
    while (USART_GetFlagStatus(USART1, USART_FLAG_TXE) == RESET) {}
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
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    // USART TX
    GPIO_InitTypeDef GPIOInit = {
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
    // OLED
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB, ENABLE);
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_8 | GPIO_Pin_9,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_Out_OD
    };
    GPIO_Init(GPIOB, &GPIOInit);
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
    // DMA
    RCC_AHBPeriphClockCmd(RCC_AHBPeriph_DMA1, ENABLE);
    DMA_InitTypeDef DMAInit = {
            .DMA_PeripheralBaseAddr = (uint32_t) &USART1->DR,
            .DMA_MemoryBaseAddr = (uint32_t) &str,
            .DMA_DIR = DMA_DIR_PeripheralSRC,
            .DMA_BufferSize = 16,
            .DMA_PeripheralInc = DMA_PeripheralInc_Disable,
            .DMA_MemoryInc = DMA_MemoryInc_Enable,
            .DMA_PeripheralDataSize = DMA_MemoryDataSize_Byte,
            .DMA_MemoryDataSize = DMA_MemoryDataSize_Byte,
            .DMA_Mode = DMA_Mode_Circular,
            .DMA_Priority = DMA_Priority_Medium,
            .DMA_M2M = DMA_M2M_Disable,
    };
    DMA_Init(DMA1_Channel5, &DMAInit);
    DMA_Cmd(DMA1_Channel5, ENABLE);
    USART_DMACmd(USART1, USART_DMAReq_Rx, ENABLE);
}

int main() {
    init();

    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    oled.showString(0, 0, "DMA:");

    while (true) {
        str[16] = 0;
        oled.showString(1, 0, str);
        printf("%s\r\n", str);
        Delay::millisecond(500);
    }
}

#endif //TASK5_4
