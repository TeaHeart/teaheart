#ifndef LED_H
#define LED_H

#include "stm32f10x.h"

namespace teaheart {
    class LED {
    private:
        GPIO_TypeDef *GPIO;
        uint16_t pin;
    public:
        LED(GPIO_TypeDef *GPIO, uint16_t pin) : GPIO(GPIO), pin(pin) {
            off();
        }

        void on() {
            GPIO_ResetBits(GPIO, pin);
        }

        void off() {
            GPIO_SetBits(GPIO, pin);
        }

        void toggle() {
            if (GPIO_ReadOutputDataBit(GPIO, pin) == Bit_RESET) {
                off();
            } else {
                on();
            }
        }

        BitAction getStatus() {
            return GPIO_ReadOutputDataBit(GPIO, pin) == Bit_RESET ? Bit_RESET : Bit_SET;
        }

        void setStatus(BitAction action) {
            GPIO_WriteBit(GPIO, pin, action);
        }
    };
}

#endif //LED_H
