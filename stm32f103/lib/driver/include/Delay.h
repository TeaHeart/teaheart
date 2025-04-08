#ifndef DELAY_H
#define DELAY_H

#include "stm32f10x.h"

namespace teaheart {
    class Delay {
    public:
        /**
         * @brief  微秒延时
         * @param  duration 持续时长 [0, 233015]
         */
        static void microsecond(uint32_t duration) {
            SysTick->LOAD = 72 * duration;              // 设置定时器重装值
            SysTick->VAL = 0x00000000;                  // 清空当前计数值
            SysTick->CTRL = 0x00000005;                 // 设置时钟源为HCLK, 启动定时器
            while ((SysTick->CTRL & 0x00010000) == 0);  // 等待计数到0
            SysTick->CTRL = 0x00000004;                 // 关闭定时器
        }

        /**
         * @brief  毫秒延时
         * @param  duration 持续时长
         */
        static void millisecond(uint32_t duration) {
            while (duration-- != 0) {
                microsecond(1000);
            }
        }

        /**
         * @brief  秒级延时
         * @param  duration 持续时长
         */
        static void second(uint32_t duration) {
            while (duration-- != 0) {
                millisecond(1000);
            }
        }
    };
}

#endif //DELAY_H
