#ifndef OLED_H
#define OLED_H

#include "stdfix.h"
#include "stm32f10x.h"

#include "I2C.h"
#include "OLEDFont.h"

namespace teaheart {
    class OLEDControl {
    public:
        virtual void sendCommand(uint8_t command) = 0;

        virtual void sendData(uint8_t data) = 0;
    };

    class OLEDI2CControl : public OLEDControl {
    private:
        teaheart::I2C &I2C;
    public:
        explicit OLEDI2CControl(teaheart::I2C &&I2C) : I2C(I2C) {}

        void sendCommand(uint8_t command) override {
            I2C.start();
            I2C.sendByte(0x78);
            I2C.sendByte(0x00);
            I2C.sendByte(command);
            I2C.stop();
        }

        void sendData(uint8_t data) override {
            I2C.start();
            I2C.sendByte(0x78);
            I2C.sendByte(0x40);
            I2C.sendByte(data);
            I2C.stop();
        }
    };

    class OLED {
    private:
        OLEDControl &control;

        void setCursor(uint8_t row, uint8_t column) {
            control.sendCommand(0xB0 | row);
            control.sendCommand(0x10 | ((column & 0xF0) >> 4));
            control.sendCommand(0x00 | ((column & 0x0F) >> 0));
        }

    public:
        explicit OLED(OLEDControl &&control) : control(control) {
            for (uint16_t i = 0; i < 1000; i++) {
                for (uint16_t j = 0; j < 1000; j++) {}
            }

            control.sendCommand(0xAE);     // 关闭显示

            control.sendCommand(0xD5);     // 设置显示时钟分频比/振荡器频率
            control.sendCommand(0x80);

            control.sendCommand(0xA8);     // 设置多路复用率
            control.sendCommand(0x3F);

            control.sendCommand(0xD3);     // 设置显示偏移
            control.sendCommand(0x00);

            control.sendCommand(0x40);     // 设置显示开始行

            control.sendCommand(0xA1);     // 设置左右方向，0xA1正常 0xA0左右反置

            control.sendCommand(0xC8);     // 设置上下方向，0xC8正常 0xC0上下反置

            control.sendCommand(0xDA);     // 设置COM引脚硬件配置
            control.sendCommand(0x12);

            control.sendCommand(0x81);     // 设置对比度控制
            control.sendCommand(0xCF);

            control.sendCommand(0xD9);     // 设置预充电周期
            control.sendCommand(0xF1);

            control.sendCommand(0xDB);     // 设置VCOMH取消选择级别
            control.sendCommand(0x30);

            control.sendCommand(0xA4);     // 设置整个显示打开/关闭

            control.sendCommand(0xA6);     // 设置正常/倒转显示

            control.sendCommand(0x8D);     // 设置充电泵
            control.sendCommand(0x14);

            control.sendCommand(0xAF);     // 开启显示

            clear();                                // OLED清屏
        }

        void clear() {
            for (uint8_t i = 0; i < 8; ++i) {
                setCursor(i, 0);
                for (uint8_t j = 0; j < 128; ++j) {
                    control.sendData(0x00);
                }
            }
        }

        /**
         * @brief  显示字符
         * @param  row     行 [0, 3]
         * @param  column  列 [0,15]
         * @param  c       字符 [ASCII]
         */
        void showChar(uint8_t row, uint8_t column, char c) {
            setCursor(row * 2, column * 8);              // 设置光标位置在上半部分
            for (uint8_t i = 0; i < 8; ++i) {
                control.sendData(OLED_F8x16[c - ' '][i]);       // 显示上半部分内容
            }
            setCursor(row * 2 + 1, column * 8);          // 设置光标位置在下半部分
            for (uint8_t i = 0; i < 8; ++i) {
                control.sendData(OLED_F8x16[c - ' '][i + 8]);   // 显示下半部分内容
            }
        }

        /**
         * @brief  显示字符串
         * @param  row     行 [0,3]
         * @param  column  列 [0,15]
         * @param  str     字符串 [ASCII]
         */
        void showString(uint8_t row, uint8_t column, const char *str) {
            for (uint8_t i = 0; str[i] != '\0'; ++i) {
                showChar(row, column + i, str[i]);
            }
        }

        /**
         * @brief  显示R进制数字
         * @param  row     行 [0,3]
         * @param  column  列 [0,15]
         * @param  radix   进制
         * @param  width   宽度
         * @param  number  数字
         */
        void showNum(uint8_t row, uint8_t column, uint32_t number, uint8_t width = 10, uint8_t radix = 10) {
            static const char *charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            for (int8_t i = width - 1; i >= 0; --i) {
                showChar(row, column + i, charset[number % radix]);
                number /= radix;
            }
        }

        /**
         * @brief  显示带符号数字
         * @param  row     行 [0,3]
         * @param  column  列 [0,15]
         * @param  width   宽度
         * @param  number  数字
         */
        void showSignedNum(uint8_t row, uint8_t column, int32_t number, uint8_t width = 11) {
            static const char *charset = "0123456789";
            showChar(row, column, number > 0 ? '+' : number < 0 ? '-' : ' ');
            for (int8_t i = width - 1; i >= 1; --i) {
                int8_t mod = number % 10;
                showChar(row, column + i, charset[mod >= 0 ? mod : -mod]);
                number /= 10;
            }
        }
    };
}

#endif //OLED_H
