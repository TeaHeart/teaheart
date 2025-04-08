#include "reg51.h"
#include "motor.h"

#define MOTOR P3_6

void motor_set(bool b) {
    MOTOR = b;
}