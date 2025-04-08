#ifdef TASK6_4

#include "fix.h"
#include "stm32f10x.h"

#include "OLED.h"
#include "Delay.h"

#include <cstdio>

using namespace teaheart;

float ad2Volt(uint16_t ad) {
    return (float) ad / 4095 * 3.3f;
}

uint16_t getADValue() {
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);
    while (ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC) == RESET);
    return ADC_GetConversionValue(ADC1);
}

void USARTSendByte(char ch) {
    USART_SendData(USART1, ch);
    while (USART_GetFlagStatus(USART1, USART_FLAG_TXE) == RESET) {}
}

char USARTReceiveByte() {
    while (USART_GetFlagStatus(USART1, USART_FLAG_RXNE) == RESET) {}
    return USART_ReceiveData(USART1);
}


void USART2SendByte(char ch) {
    USART_SendData(USART2, ch);
    while (USART_GetFlagStatus(USART2, USART_FLAG_TXE) == RESET) {}
}

void USART2SendString(char *str) {
    for (; *str != 0; ++str) {
        USART2SendByte(*str);
    }
}

#ifdef MASTER

char receiveBuffer[2] = {0};
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
            receiveBuffer[index++] = USARTReceiveByte();
            if (index >= 2) {
                status = 2;
            }
        } else if (status == 2) {
            char ch = USARTReceiveByte();
            if (ch == ';') {
                status = 0;
                isNewData = true;
            }
        }
        USART_ClearITPendingBit(USART1, USART_IT_RXNE);
    }
}

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
    // GPIO ADC
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_0,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_AIN
    };
    GPIO_Init(GPIOA, &GPIOInit);
    // USART TX
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_9 | GPIO_Pin_2,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_AF_PP
    };
    GPIO_Init(GPIOA, &GPIOInit);
    // RX
    GPIOInit = {
            .GPIO_Pin = GPIO_Pin_10 | GPIO_Pin_3,
            .GPIO_Speed = GPIO_Speed_50MHz,
            .GPIO_Mode = GPIO_Mode_IPU
    };
    GPIO_Init(GPIOA, &GPIOInit);
    // USART1
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
    // USART2
    RCC_APB1PeriphClockCmd(RCC_APB1Periph_USART2, ENABLE);
    USARTInit = {
            .USART_BaudRate = 9600,
            .USART_WordLength = USART_WordLength_8b,
            .USART_StopBits = USART_StopBits_1,
            .USART_Parity = USART_Parity_No,
            .USART_Mode = USART_Mode_Tx,
            .USART_HardwareFlowControl = USART_HardwareFlowControl_None
    };
    USART_Init(USART2, &USARTInit);
    USART_Cmd(USART2, ENABLE);
    // ADC
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_ADC1, ENABLE);
    RCC_ADCCLKConfig(RCC_PCLK2_Div6);
    ADC_RegularChannelConfig(ADC1, ADC_Channel_0, 1, ADC_SampleTime_55Cycles5);
    ADC_InitTypeDef ADCInit = {
            .ADC_Mode = ADC_Mode_Independent,
            .ADC_ScanConvMode = DISABLE,
            .ADC_ContinuousConvMode = DISABLE,
            .ADC_ExternalTrigConv = ADC_ExternalTrigConv_None,
            .ADC_DataAlign = ADC_DataAlign_Right,
            .ADC_NbrOfChannel = 1
    };
    ADC_Init(ADC1, &ADCInit);
    ADC_Cmd(ADC1, ENABLE);
    // ADC Init
    ADC_ResetCalibration(ADC1);
    while (ADC_GetResetCalibrationStatus(ADC1) == SET) {}
    ADC_StartCalibration(ADC1);
    while (ADC_GetCalibrationStatus(ADC1) == SET) {}
}

int main() {
    init();

    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    oled.showString(0, 0, "Receive:");
    oled.showString(2, 0, "Read & Send:");

    while (true) {
        if (isNewData) {
            uint16_t ad = receiveBuffer[0] << 8 | receiveBuffer[1];
            oled.showNum(1, 0, ad, 4);
            static char buffer[30] = {0};
            float vo = ad2Volt(ad);
            sprintf(buffer, "AD:%04d, Vo:%d.%d V\r\n", ad, (uint16_t) vo, (uint16_t) (vo * 100) % 100);
            USART2SendString(buffer);
            isNewData = false;
        }
        USARTSendByte('@');
        for (uint8_t i = 0; i < 8; ++i) {
            uint16_t ad = getADValue();
            USARTSendByte(ad >> 8);
            USARTSendByte(ad >> 0);
            oled.showNum(3, 0, ad, 4);
            Delay::millisecond(10);
        }
        USARTSendByte(';');
        Delay::millisecond(100);
    }
}

#else

char receiveBuffer[16] = {0};
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
            receiveBuffer[index++] = USARTReceiveByte();
            if (index >= 16) {
                status = 2;
            }
        } else if (status == 2) {
            char ch = USARTReceiveByte();
            if (ch == ';') {
                status = 0;
                isNewData = true;
            }
        }
        USART_ClearITPendingBit(USART1, USART_IT_RXNE);
    }
}
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
    oled.showString(1, 14, "..");
    oled.showString(2, 0, "Send:");

    while (true) {
        if (isNewData) {
            for (uint8_t i = 0; i < 3; ++i) {
                oled.showNum(1, 5 * i, receiveBuffer[2 * i] << 8 | receiveBuffer[2 * i + 1], 4);
            }
            uint16_t average = 0;
            for (uint8_t i = 0; i < 8; ++i) {
                average += receiveBuffer[2 * i] << 8 | receiveBuffer[2 * i + 1];
            }
            average /= 8;
            USARTSendByte('@');
            USARTSendByte(average >> 8);
            USARTSendByte(average >> 0);
            USARTSendByte(';');
            oled.showNum(3, 0, average, 4);
            isNewData = false;
        }
    }
}

#endif

#endif //TASK6_4
