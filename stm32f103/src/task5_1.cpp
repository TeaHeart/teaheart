#ifdef TASK5_1

#include "fix.h"
#include "stm32f10x.h"

#include "OLED.h"
#include "Delay.h"

using namespace teaheart;

uint8_t src[4] = {1, 2, 3, 4};
uint8_t dest[4] = {0};

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
    // DMA
    RCC_AHBPeriphClockCmd(RCC_AHBPeriph_DMA1, ENABLE);
    DMA_InitTypeDef DMAInit = {
            .DMA_PeripheralBaseAddr = (uint32_t) &src,
            .DMA_MemoryBaseAddr = (uint32_t) &dest,
            .DMA_DIR = DMA_DIR_PeripheralSRC,
            .DMA_BufferSize = 4,
            .DMA_PeripheralInc = DMA_PeripheralInc_Enable,
            .DMA_MemoryInc = DMA_MemoryInc_Enable,
            .DMA_PeripheralDataSize = DMA_MemoryDataSize_Byte,
            .DMA_MemoryDataSize = DMA_MemoryDataSize_Byte,
            .DMA_Mode = DMA_Mode_Normal,
            .DMA_Priority = DMA_Priority_Medium,
            .DMA_M2M = DMA_M2M_Enable,
    };
    DMA_Init(DMA1_Channel1, &DMAInit);
    DMA_Cmd(DMA1_Channel1, DISABLE);
}

void transfer() {
    DMA_Cmd(DMA1_Channel1, DISABLE);
    DMA_SetCurrDataCounter(DMA1_Channel1, 4);
    DMA_Cmd(DMA1_Channel1, ENABLE);
    while (DMA_GetFlagStatus(DMA1_FLAG_TC1) == RESET) {}
    DMA_ClearFlag(DMA1_FLAG_TC1);
}

int main() {
    init();

    OLED oled(OLEDI2CControl(I2C(GPIOB, GPIO_Pin_8, GPIO_Pin_9)));

    oled.showString(0, 0, "S: -- -- -- --");
    oled.showString(1, 0, "D: -- -- -- --");

    while (true) {
        transfer();
        for (uint8_t i = 0; i < 4; ++i) {
            uint8_t j = 3 * (i + 1);
            oled.showNum(0, j, src[i], 2, 16);
        }
        Delay::millisecond(500);
        for (uint8_t i = 0; i < 4; ++i) {
            uint8_t j = 3 * (i + 1);
            oled.showNum(1, j, dest[i], 2, 16);
        }
        Delay::millisecond(500);
        for (auto &item: src) {
            ++item;
        }
    }
}

#endif //TASK5_1
