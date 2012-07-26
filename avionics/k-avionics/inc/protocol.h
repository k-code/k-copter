
#ifndef PROTOCOL
#define POTOCOL

#include "stm32f4xx.h"
#include "string.h"

#define MOTOR_1 (int8_t) 0x01
#define MOTOR_2 (int8_t) 0x02
#define MOTOR_3 0x03
#define MOTOR_4 0x04
#define ANGEL_X 0x05
#define ANGEL_Y 0x06
#define ANGEL_Z 0x07
#define ANGEL_R 0x08
#define MESSAGE 0x09

#define MAX_LENGTH 255

#define TYPE_BYTE (int8_t) 0x01
#define TYPE_INT 0x02
#define TYPE_STR 0x04


#endif //PROTOCOL
