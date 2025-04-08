#include "i2c.h"
#include "reg51.h"

#define SCL P2_1
#define SDA P2_0

void i2c_start() {
    SDA = 1;
    SCL = 1;
    SDA = 0;
    SCL = 0;
}

void i2c_stop() {
    SDA = 0;
    SCL = 1;
    SDA = 1;
}

void i2c_send_byte(uint8_t value) {
    for (uint8_t i = 0x80; i != 0x00; i >>= 1) {
        SDA = value & i;
        SCL = 1;
        SCL = 0;
    }
}

uint8_t i2c_receive_byte() {
    uint8_t value = 0x00;
    SDA = 1;
    for (uint8_t i = 0x80; i != 0x00; i >>= 1) {
        SCL = 1;
        if (SDA == 1) {
            value |= i;
        }
        SCL = 0;
    }
    return value;
}

void i2c_send_ack(bool ack) {
    SDA = ack == true ? 0 : 1;
    SCL = 1;
    SCL = 0;
}

bool i2c_receive_ack() {
    SDA = 1;
    SCL = 1;
    bool ack = SDA == 0 ? true : false;
    SCL = 0;
    return ack;
}
