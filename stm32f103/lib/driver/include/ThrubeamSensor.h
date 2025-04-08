#ifndef THRUBEAM_SENSOR_H
#define THRUBEAM_SENSOR_H

#include "stm32f10x.h"

namespace teaheart {
    class ThrubeamSensor {
    private:
        GPIO_TypeDef *GPIO;
        uint16_t pin;
    public:
        ThrubeamSensor(GPIO_TypeDef *GPIO, uint16_t pin) : GPIO(GPIO), pin(pin) {}

        BitAction getStatus() {
            return GPIO_ReadInputDataBit(GPIO, pin) == Bit_RESET ? Bit_RESET : Bit_SET;
        }
    };
}

#endif //THRUBEAM_SENSOR_H
