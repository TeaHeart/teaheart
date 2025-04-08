#ifdef TASK5_2

#include "fix.h"
#include "stm32f10x.h"

#include "OLED.h"
#include "Delay.h"

using namespace teaheart;

uint16_t adValue[1] = {0};

float ad2Volt(uint16_t ad) {
    return (float) ad / 4095 * 3.3f;
}

void init() {
    SetSysClock();
    // OLED
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB, ENABLE);
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
    GPIO_Init(GPIOB, &GPIOInit);
    // DMA
    RCC_AHBPeriphClockCmd(RCC_AHBPeriph_DMA1, ENABLE);
    DMA_InitTypeDef DMAInit = {
            .DMA_PeripheralBaseAddr = (uint32_t) &ADC1->DR,
            .DMA_MemoryBaseAddr = (uint32_t) &adValue,
            .DMA_DIR = DMA_DIR_PeripheralSRC,
            .DMA_BufferSize = 1,
            .DMA_PeripheralInc = DMA_PeripheralInc_Disable,
            .DMA_MemoryInc = DMA_MemoryInc_Enable,
            .DMA_PeripheralDataSize = DMA_PeripheralDataSize_HalfWord,
            .DMA_MemoryDataSize = DMA_MemoryDataSize_HalfWord,
            .DMA_Mode = DMA_Mode_Circular,
            .DMA_Priority = DMA_Priority_Medium,
            .DMA_M2M = DMA_M2M_Disable,
    };
    DMA_Init(DMA1_Channel1, &DMAInit);
    DMA_Cmd(DMA1_Channel1, ENABLE);
    // ADC
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_ADC1, ENABLE);
    RCC_ADCCLKConfig(RCC_PCLK2_Div6);
    ADC_RegularChannelConfig(ADC1, ADC_Channel_0, 1, ADC_SampleTime_55Cycles5);
    ADC_InitTypeDef ADCInit = {
            .ADC_Mode = ADC_Mode_Independent,
            .ADC_ScanConvMode = DISABLE,
            .ADC_ContinuousConvMode = ENABLE,
            .ADC_ExternalTrigConv = ADC_ExternalTrigConv_None,
            .ADC_DataAlign = ADC_DataAlign_Right,
            .ADC_NbrOfChannel = 1
    };
    ADC_Init(ADC1, &ADCInit);
    ADC_Cmd(ADC1, ENABLE);
    ADC_DMACmd(ADC1, ENABLE);
    // ADC Init
    ADC_ResetCalibration(ADC1);
    while (ADC_GetResetCalibrationStatus(ADC1) == SET) {}
    ADC_StartCalibration(ADC1);
    while (ADC_GetCalibrationStatus(ADC1) == SET) {}
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);
}

int main() {
    init();

    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    oled.showString(0, 0, "----  |  -.-- V");

    while (true) {
        uint8_t n = 100;
        uint32_t sum = 0;
        for (uint8_t i = 0; i < n; ++i) {
            sum += adValue[0];
            Delay::millisecond(1);
        }
        oled.showNum(0, 0, sum / n, 4);
        float vo = ad2Volt(sum / n);
        oled.showNum(0, 9, vo, 1);
        oled.showNum(0, 11, (uint16_t) (vo * 100) % 100, 2);
    }
}

#endif //TASK5_2
