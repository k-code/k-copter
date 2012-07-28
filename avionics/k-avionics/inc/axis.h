
#ifndef AXIS
#define AXIS

#include "stm32f4xx.h"

#define LIS302DL_FLAG_TIMEOUT 0x1000

void initSpi(void);
void LIS302DL_Init(void);

uint8_t LIS302DL_SendByte(uint8_t byte);
uint8_t LIS302DL_GetByte(void);
void LIS302DL_Write(uint8_t REG, uint8_t *DATA, uint8_t count);
void LIS302DL_Read(uint8_t REG, uint8_t *DATA, uint8_t count);
void LIS302DL_ReadACC(int32_t* out);
void LIS302DL_ReadACCY(int32_t* out);

uint32_t abs(uint32_t n);

#endif
