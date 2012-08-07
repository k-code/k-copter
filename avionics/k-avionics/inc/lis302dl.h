
#ifndef __LIS302DL__H__
#define __LIS302DL__H__

#include "stm32f4xx.h"

#define LIS302DL_FLAG_TIMEOUT 0x1000

void LIS302DL_Init(void);
void LIS302DL_Read(uint8_t REG, uint8_t *DATA, uint8_t count);
void LIS302DL_ReadACC(int32_t* out);
void LIS302DL_ReadACCY(int32_t* out);

uint32_t abs(uint32_t n);

#endif //__LIS302DL__H__
