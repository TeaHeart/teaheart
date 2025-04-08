#ifndef ROTARY_ENCODER_H
#define ROTARY_ENCODER_H

#include "stm32f10x.h"

namespace teaheart {
    class RotaryEncoder {
    private:
        GPIO_TypeDef *GPIO;
        uint16_t A;
        uint16_t B;
        uint16_t C;
    public:
        RotaryEncoder(GPIO_TypeDef *GPIO, uint16_t A, uint16_t B, uint8_t C) : GPIO(GPIO), A(A), B(B), C(C) {}
    };
}

#endif //ROTARY_ENCODER_H