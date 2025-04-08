#ifndef LIGHT_SENSOR_H
#define LIGHT_SENSOR_H

#include "stm32f10x.h"

namespace teaheart {
    class LightSensor {
    private:
        GPIO_TypeDef *GPIO;
        uint16_t pin;
    public:
        LightSensor(GPIO_TypeDef *GPIO, uint16_t pin) : GPIO(GPIO), pin(pin) {}

        BitAction getStatus() {
            return GPIO_ReadInputDataBit(GPIO, pin) == Bit_RESET ? Bit_RESET : Bit_SET;
        }
    };
}

#endif //LIGHT_SENSOR_H
