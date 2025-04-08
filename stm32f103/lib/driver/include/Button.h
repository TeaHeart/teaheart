#ifndef BUTTON_H
#define BUTTON_H

#include "stm32f10x.h"
#include "Util.h"

namespace teaheart {
    class Button {
    private:
        GPIO_TypeDef *GPIO;
        uint16_t pin;
    public:
        Button(GPIO_TypeDef *GPIO, uint16_t pin) : GPIO(GPIO), pin(pin) {}

        BitAction getStatus() {
            return GPIO_ReadInputDataBit(GPIO, pin) == Bit_RESET ? Bit_RESET : Bit_SET;
        }
    };
}

#endif //BUTTON_H
