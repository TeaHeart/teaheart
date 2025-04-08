#ifndef I2C_H
#define I2C_H

#include "stdbool.h"
#include "stdint.h"

void i2c_start();

void i2c_stop();

void i2c_send_byte(uint8_t value);

uint8_t i2c_receive_byte();

void i2c_send_ack(bool ack);

bool i2c_receive_ack();

#endif //I2C_H
