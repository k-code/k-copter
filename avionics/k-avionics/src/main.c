/* Includes ------------------------------------------------------------------*/
#include "main.h"
#include "stm32f4_discovery.h"
#include "lis302dl.h"
#include "periph_init.h"
#include "protocol.h"
#include "usbd_core.h"
#include "usbd_desc.h"
#include "usbd_cdc_core.h"
#include "usbd_cdc_vcp.h"
#include "usbd_usr.h"

/* Private typedef -----------------------------------------------------------*/
/* Private define ------------------------------------------------------------*/

/* Private macro -------------------------------------------------------------*/
/* Private variables ---------------------------------------------------------*/
#ifdef USB_OTG_HS_INTERNAL_DMA_ENABLED
#if defined ( __ICCARM__ ) /*!< IAR Compiler */
#pragma data_alignment = 4
#endif
#endif /* USB_OTG_HS_INTERNAL_DMA_ENABLED */
__ALIGN_BEGIN USB_OTG_CORE_HANDLE USB_OTG_dev __ALIGN_END;

extern __I uint32_t SysTime;
extern CDC_IF_Prop_TypeDef  VCP_fops;

/* Private function prototypes -----------------------------------------------*/
static void sendData(void);
static void Delay(__IO uint32_t nTime);
static void getData(void);

/* Private functions ---------------------------------------------------------*/

/**
 * @brief  Main program.
 * @param  None
 * @retval None
 */
int main(void) {
    Data_get = 0;
    uint32_t val = PWM_PERIOD;

    PERIPH_Init_SysTick();
    PERIPH_Init_Leds();
    PERIPH_Init_Timer();
    PERIPH_Init_PWM();
    PERIPH_Init_Spi();
    LIS302DL_Init();

    /* USB configuration */
    USBD_Init(&USB_OTG_dev, USB_OTG_FS_CORE_ID, &USR_desc, &USBD_CDC_cb, &USR_cb);

    while (1) {
        val = PWM_PERIOD - val;
        TIM_SetCompare1(TIM4, val);

        if (USBD_USR_DEVICE_CONFIGURED == SET) {
            sendData();

            if (Data_get == 1) {
                getData();
                Data_get = 0;
            }
        }

        Delay(10000);
    }
}

/**
 * @brief  Inserts a delay time.
 * @param  nTime: specifies the delay time length, in 10 ms.
 * @retval None
 */
static void Delay(__IO uint32_t nTime) {
    nTime += SysTime;

    while (nTime > SysTime) {
        __NOP();
    }
}

static void sendData(void) {
    uint8_t buf[PROTOCOL_MAX_LEN];
    int8_t status;
    int8_t acc;
    PROTOCOL_Protocol p;

    p.num = 2;
    p.framesLen = 0;

    LIS302DL_getSatus(&status);

    if ( status & 0x01) {
        LIS302DL_ReadACCX(&acc);
        p.frames[0].cmd = PROTOCOL_ANGEL_X;
        p.frames[0].type = PROTOCOL_TYPE_BYTE;
        p.frames[0].bData = acc;
        p.framesLen++;
    }
    if ( status & 0x02) {
        LIS302DL_ReadACCY(&acc);
        p.frames[1].cmd = PROTOCOL_ANGEL_Y;
        p.frames[1].type = PROTOCOL_TYPE_BYTE;
        p.frames[1].bData = acc;
        p.framesLen++;
    }

    if (p.framesLen > 0) {
        uint32_t len = PROTOCOL_toByteArray(&p, buf);
        APP_FOPS.pIf_DataTx(buf, len);
    }
}

static void getData(void) {
    PROTOCOL_Protocol p;
    PROTOCOL_parseProtocol(Data_buf, &p);
    for (int32_t i = 0; i < p.framesLen; i++) {
        switch (p.frames[i].cmd) {
        case PROTOCOL_MOTOR_1:
            TIM_SetCompare1(TIM4, p.frames[i].iData);
            break;
        case PROTOCOL_MOTOR_2:
            TIM_SetCompare2(TIM4, p.frames[i].iData);
            break;
        case PROTOCOL_MOTOR_3:
            TIM_SetCompare3(TIM4, p.frames[i].iData);
            break;
        case PROTOCOL_MOTOR_4:
            TIM_SetCompare4(TIM4, p.frames[i].iData);
            break;
        }
    }
}

#ifdef  USE_FULL_ASSERT

/**
 * @brief  Reports the name of the source file and the source line number
 *   where the assert_param error has occurred.
 * @param  file: pointer to the source file name
 * @param  line: assert_param error line source number
 * @retval None
 */
void assert_failed(uint8_t* file, uint32_t line)
{
    /* User can add his own implementation to report the file name and line number,
     ex: printf("Wrong parameters value: file %s on line %d\r\n", file, line) */

    /* Infinite loop */
    while (1)
    {
    }
}
#endif

/**
 * @}
 */

/******************* (C) COPYRIGHT 2011 STMicroelectronics *****END OF FILE****/
