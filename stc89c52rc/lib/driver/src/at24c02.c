#include "at24c02.h"
#include "i2c.h"
#include "delay.h"

#define ADDRESS 0xA0
#define WRITE 0x00
#define READ 0x01

void at24c02_write_byte_at(uint8_t address, uint8_t value) {
    i2c_start();
    i2c_send_byte(ADDRESS | WRITE);
    i2c_receive_ack();
    i2c_send_byte(address);
    i2c_receive_ack();
    i2c_send_byte(value);
    i2c_receive_ack();
    i2c_stop();
    delay_ms(5);
}

uint8_t at24c02_read_byte_at(uint8_t address) {
    i2c_start();
    i2c_send_byte(ADDRESS | WRITE);
    i2c_receive_ack();
    i2c_send_byte(address);
    i2c_receive_ack();

    i2c_start();
    i2c_send_byte(ADDRESS | READ);
    i2c_receive_ack();
    uint8_t value = i2c_receive_byte();
    i2c_send_ack(false);
    i2c_stop();
    return value;
}
