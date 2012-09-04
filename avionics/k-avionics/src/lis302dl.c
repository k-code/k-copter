#include "lis302dl.h"
#include "stm32f4xx_rcc.h"

static uint8_t LIS302DL_SendByte(uint8_t byte);
static uint8_t LIS302DL_GetByte(void);
static void LIS302DL_Read(uint8_t REG, uint8_t *DATA, uint8_t count);
static void LIS302DL_Write(uint8_t REG, uint8_t *DATA, uint8_t count);

void LIS302DL_Init() {
    GPIO_SetBits(GPIOE, GPIO_Pin_3);
    uint8_t reg = 0x47;
    LIS302DL_Write(0x20, &reg, 1);
    GPIO_ResetBits(GPIOE, GPIO_Pin_3);
}

void LIS302DL_ReadACC(int32_t *out) {
    uint8_t buffer[6];
    LIS302DL_Read(0x29, buffer, 6);

    for (int i = 0; i < 3; i++) {
        *out = (int32_t) (72 * (int8_t) buffer[2 * i]);
        out++;
    }
}

void LIS302DL_ReadACCY(int8_t* out) {
    uint8_t buffer;
    LIS302DL_Read(0x2B, &buffer, 1);

    *out = buffer;
}


void LIS302DL_ReadACCX(int8_t* out) {
    uint8_t buffer;
    LIS302DL_Read(0x29, &buffer, 1);

    *out = buffer;
}

void LIS302DL_getSatus(uint8_t *out) {
    LIS302DL_Read(0x27, out, 1);
}

static uint8_t LIS302DL_SendByte(uint8_t byte) {
    uint32_t LIS302DLTimeout = LIS302DL_FLAG_TIMEOUT;

    while (SPI_I2S_GetFlagStatus(SPI1, SPI_FLAG_TXE) == RESET) {
        if ((LIS302DLTimeout--) == 0)
            return 0x00;
    }

    // Send a Byte through the SPI peripheral
    SPI_I2S_SendData(SPI1, byte);

    return LIS302DL_GetByte();
}

static uint8_t LIS302DL_GetByte() {
    uint32_t LIS302DLTimeout = LIS302DL_FLAG_TIMEOUT;
    /* Wait to receive a Byte */
    LIS302DLTimeout = LIS302DL_FLAG_TIMEOUT;
    while (SPI_I2S_GetFlagStatus(SPI1, SPI_FLAG_RXNE) == RESET) {
        if ((LIS302DLTimeout--) == 0)
            return 0x00;
    }

    /* Return the Byte read from the SPI bus */
    return (uint8_t) SPI_I2S_ReceiveData(SPI1);
}

static void LIS302DL_Write(uint8_t REG, uint8_t *DATA, uint8_t count) {
    GPIO_ResetBits(GPIOE, GPIO_Pin_3);
    LIS302DL_SendByte(REG);
    for (int i = 0; i < count; i++) {
        LIS302DL_SendByte(*DATA);
        DATA++;
    }
    GPIO_SetBits(GPIOE, GPIO_Pin_3);
}

static void LIS302DL_Read(uint8_t REG, uint8_t *DATA, uint8_t count) {
    GPIO_ResetBits(GPIOE, GPIO_Pin_3);
    REG |= 0x80;
    if (count > 1) {
        REG |= 0x40;
    }
    LIS302DL_SendByte(REG);
    for (uint8_t i = 0; i < count; i++) {
        *DATA = LIS302DL_SendByte((uint8_t) 0x00);
        DATA++;
    }

    GPIO_SetBits(GPIOE, GPIO_Pin_3);
}
