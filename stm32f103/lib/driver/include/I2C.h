#ifndef I2C_H
#define I2C_H

#include "stm32f10x.h"

namespace teaheart {
    class I2C {
    private:
        GPIO_TypeDef *GPIO;
        uint16_t SCL;
        uint16_t SDA;

        void writeSCL(BitAction action) {
            GPIO_WriteBit(GPIO, SCL, action);
        }

        void writeSDA(BitAction action) {
            GPIO_WriteBit(GPIO, SDA, action);
        }

    public:
        explicit I2C(GPIO_TypeDef *GPIO, uint16_t SCL, uint16_t SDA) : GPIO(GPIO), SCL(SCL), SDA(SDA) {
            writeSCL(Bit_SET);
            writeSDA(Bit_SET);
        }

        void start() {
            writeSDA(Bit_SET);
            writeSCL(Bit_SET);
            writeSDA(Bit_RESET);
            writeSCL(Bit_RESET);
        }

        void stop() {
            writeSDA(Bit_RESET);
            writeSCL(Bit_SET);
            writeSDA(Bit_SET);
        }

        void sendByte(uint8_t byte) {
            for (uint8_t i = 0x80; i != 0x00; i >>= 1) {
                writeSDA((byte & i) != 0 ? Bit_SET : Bit_RESET);
                writeSCL(Bit_SET);
                writeSCL(Bit_RESET);
            }
            writeSCL(Bit_SET);
            writeSCL(Bit_RESET);
        }
    };
}

#endif //I2C_H
