/**************************************************************************************************
  Filename:       SampleApp.c
  Revised:        $Date: 2009-03-18 15:56:27 -0700 (Wed, 18 Mar 2009) $
  Revision:       $Revision: 19453 $

  Description:    Sample Application (no Profile).


  Copyright 2007 Texas Instruments Incorporated. All rights reserved.

  IMPORTANT: Your use of this Software is limited to those specific rights
  granted under the terms of a software license agreement between the user
  who downloaded the software, his/her employer (which must be your employer)
  and Texas Instruments Incorporated (the "License").  You may not use this
  Software unless you agree to abide by the terms of the License. The License
  limits your use, and you acknowledge, that the Software may not be modified,
  copied or distributed unless embedded on a Texas Instruments microcontroller
  or used solely and exclusively in conjunction with a Texas Instruments radio
  frequency transceiver, which is integrated into your product.  Other than for
  the foregoing purpose, you may not use, reproduce, copy, prepare derivative
  works of, modify, distribute, perform, display or sell this Software and/or
  its documentation for any purpose.

  YOU FURTHER ACKNOWLEDGE AND AGREE THAT THE SOFTWARE AND DOCUMENTATION ARE
  PROVIDED 揂S IS?WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED,
  INCLUDING WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, TITLE,
  NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE. IN NO EVENT SHALL
  TEXAS INSTRUMENTS OR ITS LICENSORS BE LIABLE OR OBLIGATED UNDER CONTRACT,
  NEGLIGENCE, STRICT LIABILITY, CONTRIBUTION, BREACH OF WARRANTY, OR OTHER
  LEGAL EQUITABLE THEORY ANY DIRECT OR INDIRECT DAMAGES OR EXPENSES
  INCLUDING BUT NOT LIMITED TO ANY INCIDENTAL, SPECIAL, INDIRECT, PUNITIVE
  OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, COST OF PROCUREMENT
  OF SUBSTITUTE GOODS, TECHNOLOGY, SERVICES, OR ANY CLAIMS BY THIRD PARTIES
  (INCLUDING BUT NOT LIMITED TO ANY DEFENSE THEREOF), OR OTHER SIMILAR COSTS.

  Should you have any questions regarding your right to use this Software,
  contact Texas Instruments Incorporated at www.TI.com.
**************************************************************************************************/

/*********************************************************************
  This application isn't intended to do anything useful, it is
  intended to be a simple example of an application's structure.

  This application sends it's messages either as broadcast or
  broadcast filtered group messages.  The other (more normal)
  message addressing is unicast.  Most of the other sample
  applications are written to support the unicast message model.

  Key control:
    SW1:  Sends a flash command to all devices in Group 1.
    SW2:  Adds/Removes (toggles) this device in and out
          of Group 1.  This will enable and disable the
          reception of the flash command.
*********************************************************************/

/*********************************************************************
 * INCLUDES
 */
#include "OSAL.h"
#include "ZGlobals.h"
#include "AF.h"
#include "aps_groups.h"
#include "ZDApp.h"

#include "SampleApp.h"
#include "SampleAppHw.h"

#include "OnBoard.h"

/* HAL */
#include "hal_lcd.h"
#include "hal_led.h"
#include "hal_key.h"
#include "MT_UART.h"
#include "MT_APP.h"
#include "MT.h"
#include "DHT11.h"


/*********************************************************************
 * MACROS
 */
#if defined(LIGHT_SENSOR)
#define DATA_PIN P0_5                      // 定义P0.5口为传感器的输入端
// #define DATA_PIN (osal_rand() % 2)            // 模拟光照数据 黑暗/明亮
#endif
/*********************************************************************
 * CONSTANTS
 */

/*********************************************************************
 * TYPEDEFS
 */

/*********************************************************************
 * GLOBAL VARIABLES
 */

// This list should be filled with Application specific Cluster IDs.
const cId_t SampleApp_ClusterList[SAMPLEAPP_MAX_CLUSTERS] =
{
  SAMPLEAPP_REPORTDATA_CLUSTERID,
  SAMPLEAPP_FORWARDCMD_CLUSTERID
};

const SimpleDescriptionFormat_t SampleApp_SimpleDesc =
{
  SAMPLEAPP_ENDPOINT,              //  int Endpoint;
  SAMPLEAPP_PROFID,                //  uint16 AppProfId[2];
  SAMPLEAPP_DEVICEID,              //  uint16 AppDeviceId[2];
  SAMPLEAPP_DEVICE_VERSION,        //  int   AppDevVer:4;
  SAMPLEAPP_FLAGS,                 //  int   AppFlags:4;
  SAMPLEAPP_MAX_CLUSTERS,          //  uint8  AppNumInClusters;
  (cId_t *)SampleApp_ClusterList,  //  uint8 *pAppInClusterList;
  SAMPLEAPP_MAX_CLUSTERS,          //  uint8  AppNumInClusters;
  (cId_t *)SampleApp_ClusterList   //  uint8 *pAppInClusterList;
};

// This is the Endpoint/Interface description.  It is defined here, but
// filled-in in SampleApp_Init().  Another way to go would be to fill
// in the structure here and make it a "const" (in code space).  The
// way it's defined in this sample app it is define in RAM.
endPointDesc_t SampleApp_epDesc;

/*********************************************************************
 * EXTERNAL VARIABLES
 */

/*********************************************************************
 * EXTERNAL FUNCTIONS
 */

/*********************************************************************
 * LOCAL VARIABLES
 */
uint8 SampleApp_TaskID;   // Task ID for internal task/event processing
                          // This variable will be received when
                          // SampleApp_Init() is called.
devStates_t SampleApp_NwkState;

uint8 SampleApp_TransID;  // This is the unique message ID (counter)

afAddrType_t SampleApp_ReportData_DstAddr;      
afAddrType_t SampleApp_ForwardCMD_DstAddr; 

/*********************************************************************
 * LOCAL FUNCTIONS
 */
void SampleApp_HandleKeys( uint8 shift, uint8 keys );
void SampleApp_MessageMSGCB( afIncomingMSGPacket_t *pckt );

void SampleApp_ReportData(void);                         // 用于温湿度光照采集，点播发给协调器
void SampleApp_ForwardCMD(byte* cmd, uint8 length);      // 用于广播转发上位机的命令，广播
void SampleApp_SerialRXData(uint8 port, uint8 event);    // 串口接收数据处理
/*********************************************************************
 * NETWORK LAYER CALLBACKS
 */

/*********************************************************************
 * PUBLIC FUNCTIONS
 */

/*********************************************************************
 * @fn      SampleApp_Init
 *
 * @brief   Initialization function for the Generic App Task.
 *          This is called during initialization and should contain
 *          any application specific initialization (ie. hardware
 *          initialization/setup, table initialization, power up
 *          notificaiton ... ).
 *
 * @param   task_id - the ID assigned by OSAL.  This ID should be
 *                    used to send messages and set timers.
 *
 * @return  none
 */
void SampleApp_Init( uint8 task_id )
{ 
  SampleApp_TaskID = task_id;
  SampleApp_NwkState = DEV_INIT;
  SampleApp_TransID = 0;

#if defined(COORDINATOR)
    {
      halUARTCfg_t uartConfig;
      uartConfig.configured           = TRUE; // 2x30 don't care - see uart driver.
      uartConfig.baudRate             = HAL_UART_BR_9600;
      uartConfig.flowControl          = FALSE;
      uartConfig.flowControlThreshold = 64;   // 2x30 don't care - see uart driver.
      uartConfig.rx.maxBufSize        = 128;  // 2x30 don't care - see uart driver.
      uartConfig.tx.maxBufSize        = 128;  // 2x30 don't care - see uart driver.
      uartConfig.idleTimeout          = 6;    // 2x30 don't care - see uart driver.
      uartConfig.intEnable            = TRUE; // 2x30 don't care - see uart driver.
      uartConfig.callBackFunc         = SampleApp_SerialRXData;
      HalUARTOpen (0, &uartConfig); 
    }
#elif !defined(LIGHT_SENSOR)      // 温湿度
  P0SEL &= 0x7F;                  // P0_7配置成通用IO
#else                             // 光照
  P0SEL &= 0xDF;                  // 设置P0.5口为普通IO
  P0DIR &= 0xDF;                  // 设置端口为输入
#endif

  // Device hardware initialization can be added here or in main() (Zmain.c).
  // If the hardware is application specific - add it here.
  // If the hardware is other parts of the device add it in main().

 #if defined ( BUILD_ALL_DEVICES )
  // The "Demo" target is setup to have BUILD_ALL_DEVICES and HOLD_AUTO_START
  // We are looking at a jumper (defined in SampleAppHw.c) to be jumpered
  // together - if they are - we will start up a coordinator. Otherwise,
  // the device will start as a router.
  if ( readCoordinatorJumper() )
    zgDeviceLogicalType = ZG_DEVICETYPE_COORDINATOR;
  else
    zgDeviceLogicalType = ZG_DEVICETYPE_ROUTER;
#endif // BUILD_ALL_DEVICES

#if defined ( HOLD_AUTO_START )
  // HOLD_AUTO_START is a compile option that will surpress ZDApp
  //  from starting the device and wait for the application to
  //  start the device.
  ZDOInitDevice(0);
#endif

  // 点播，温湿度光照采集，发送给协调器
  SampleApp_ReportData_DstAddr.addrMode = (afAddrMode_t)Addr16Bit; 
  SampleApp_ReportData_DstAddr.endPoint = SAMPLEAPP_ENDPOINT; 
  SampleApp_ReportData_DstAddr.addr.shortAddr = 0x0000;     

  // 广播，转发/处理 协调器/上位机 发送的命令
  SampleApp_ForwardCMD_DstAddr.addrMode = (afAddrMode_t)AddrBroadcast;
  SampleApp_ForwardCMD_DstAddr.endPoint = SAMPLEAPP_ENDPOINT;
  SampleApp_ForwardCMD_DstAddr.addr.shortAddr = 0xFFFF;

  // Fill out the endpoint description.
  SampleApp_epDesc.endPoint = SAMPLEAPP_ENDPOINT;
  SampleApp_epDesc.task_id = &SampleApp_TaskID;
  SampleApp_epDesc.simpleDesc = (SimpleDescriptionFormat_t *)&SampleApp_SimpleDesc;
  SampleApp_epDesc.latencyReq = noLatencyReqs;

  // Register the endpoint description with the AF
  afRegister( &SampleApp_epDesc );

  // Register for all key events - This app will handle all key events
  RegisterForKeys( SampleApp_TaskID );

  // // By default, all devices start out in Group 1
  // {
  //   aps_Group_t SampleApp_Default_Group;
  //   SampleApp_Default_Group.ID = 0x0001;
  //   osal_memcpy( SampleApp_Default_Group.name, "Group 1", 7 );
  //   aps_AddGroup( SAMPLEAPP_ENDPOINT, &SampleApp_Default_Group );
  // }

#if defined(COORDINATOR)                              // 协调器
  HalLcdWriteString( "Coordinator", HAL_LCD_LINE_4 );
#elif !defined (LIGHT_SENSOR)                         // Sensor1 温湿度
  HalLcdWriteString( "Sensor1", HAL_LCD_LINE_4 );
#else                                                 // Sensor2 光照
  HalLcdWriteString( "Sensor2", HAL_LCD_LINE_4 );
#endif
}

/*********************************************************************
 * @fn      SampleApp_ProcessEvent
 *
 * @brief   Generic Application Task event processor.  This function
 *          is called to process all events for the task.  Events
 *          include timers, messages and any other user defined events.
 *
 * @param   task_id  - The OSAL assigned task ID.
 * @param   events - events to process.  This is a bit map and can
 *                   contain more than one event.
 *
 * @return  none
 */
uint16 SampleApp_ProcessEvent( uint8 task_id, uint16 events )
{
  afIncomingMSGPacket_t *MSGpkt;
  (void)task_id;  // Intentionally unreferenced parameter

  if ( events & SYS_EVENT_MSG )
  {
    MSGpkt = (afIncomingMSGPacket_t *)osal_msg_receive( SampleApp_TaskID );
    while ( MSGpkt )
    {
      switch ( MSGpkt->hdr.event )
      {
        // Received when a key is pressed
        case KEY_CHANGE:
          SampleApp_HandleKeys( ((keyChange_t *)MSGpkt)->state, ((keyChange_t *)MSGpkt)->keys );
          break;

        // Received when a messages is received (OTA) for this endpoint
        case AF_INCOMING_MSG_CMD:
          SampleApp_MessageMSGCB( MSGpkt );
          break;

        // Received whenever the device changes state in the network
        case ZDO_STATE_CHANGE:
          SampleApp_NwkState = (devStates_t)(MSGpkt->hdr.status);
          if ( // (SampleApp_NwkState == DEV_ZB_COORD) ||
               // (SampleApp_NwkState == DEV_ROUTER) ||
                (SampleApp_NwkState == DEV_END_DEVICE) )
          {
            osal_start_timerEx(SampleApp_TaskID,
                               SAMPLEAPP_REPORTDATA_MSG_EVT,
                               (SAMPLEAPP_REPORTDATA_MSG_TIMEOUT + osal_rand() % 0x00FF) );
          }
          else
          {
            // Device is no longer in the network
          }
          break;
        default:
          break;
      }

      // Release the memory
      osal_msg_deallocate( (uint8 *)MSGpkt );

      // Next - if one is available
      MSGpkt = (afIncomingMSGPacket_t *)osal_msg_receive( SampleApp_TaskID );
    }

    // return unprocessed events
    return (events ^ SYS_EVENT_MSG);
  }

  // Send a message out - This event is generated by a timer
  //  (setup in SampleApp_Init()).
  if ( events & SAMPLEAPP_REPORTDATA_MSG_EVT )
  {
    SampleApp_ReportData();
    osal_start_timerEx(SampleApp_TaskID,
                       SAMPLEAPP_REPORTDATA_MSG_EVT,
                       SAMPLEAPP_REPORTDATA_MSG_TIMEOUT );

    // return unprocessed events
    return (events ^ SAMPLEAPP_REPORTDATA_MSG_EVT);
  }

  // Discard unknown events
  return 0;
}

/*********************************************************************
 * Event Generation Functions
 */
/*********************************************************************
 * @fn      SampleApp_HandleKeys
 *
 * @brief   Handles all key events for this device.
 *
 * @param   shift - true if in shift/alt.
 * @param   keys - bit field for key events. Valid entries:
 *                 HAL_KEY_SW_2
 *                 HAL_KEY_SW_1
 *
 * @return  none
 */
void SampleApp_HandleKeys( uint8 shift, uint8 keys )
{
#if defined(COORDINATOR)
  if ( keys & HAL_KEY_SW_6 )
  {
    SampleApp_ForwardCMD("S1", 3);
    HalLcdWriteString( "S1", HAL_LCD_LINE_3 );
  }
  if ( keys & HAL_KEY_SW_7 )
  {
    SampleApp_ForwardCMD("S2", 3);
    HalLcdWriteString( "S2", HAL_LCD_LINE_3 );
  }
#endif
}

/*********************************************************************
 * LOCAL FUNCTIONS
 */
void SampleApp_MessageMSGCB( afIncomingMSGPacket_t *pkt )
{
  switch ( pkt->clusterId )
  {
    case SAMPLEAPP_REPORTDATA_CLUSTERID:
    {
      // 协调器接收到数据后发送到PC机
      HalUARTWrite(0, pkt->cmd.Data, pkt->cmd.DataLength);
      HalUARTWrite(0, "\n", 1);
      break;
    }    
    case SAMPLEAPP_FORWARDCMD_CLUSTERID:
    {
#if !defined(LIGHT_SENSOR) // 是 Sensor1 且命令是 S1 就切换LED
      if (strcmp(pkt->cmd.Data, "S1") == 0) {
        HalLedSet(HAL_LED_1, HAL_LED_MODE_TOGGLE);
      }
#elif defined(LIGHT_SENSOR) // 是 Sensor2 且命令是 S2 就切换LED
      if (strcmp(pkt->cmd.Data, "S2") == 0) {
        HalLedSet(HAL_LED_2, HAL_LED_MODE_TOGGLE);
      }
#endif
      break;
    }
  }
}

void SampleApp_ReportData(void)
{
#if !defined(LIGHT_SENSOR)
  byte data[8];         // 1:XX;YY  -> sensor1:温度;湿度
  Delay_ms(500);
  DHT11();              // 获取温湿度
  Delay_ms(500);
  sprintf(data, "%d:%d%d;%d%d", 1, wendu_shi, wendu_ge, shidu_shi, shidu_ge);
 
  LCD_P16x16Ch(0,  4, 0);                  // 温
  LCD_P16x16Ch(16, 4, 1 * 16);             // 度
  LCD_P8x16Str_2(40, 4, data + 2, 2);      // XX
  LCD_P16x16Ch(64,  4, 3 * 16);            // 湿
  LCD_P16x16Ch(80,  4, 1 * 16);            // 度
  LCD_P8x16Str_2(104, 4, data + 2 + 3, 2); // YY
 
  if ( AF_DataRequest( &SampleApp_ReportData_DstAddr, &SampleApp_epDesc,
                       SAMPLEAPP_REPORTDATA_CLUSTERID,
                       sizeof(data) - 1,
                       data,
                       &SampleApp_TransID,
                       AF_DISCV_ROUTE,
                       AF_DEFAULT_RADIUS ) == afStatus_SUCCESS )
  {
  }
  else
  {
    // Error occurred in request to send.
  }
#elif defined(LIGHT_SENSOR)
  byte data[4];         // 2:X -> sensor2:光照状态
  if(DATA_PIN == 1)
  { 
    MicroWait (10000);  
    if(DATA_PIN == 1)
    {     
      sprintf(data, "%d:%d", 2, 0);
      HalLcdWriteString( "lignt 0", HAL_LCD_LINE_3 );
    }
  }  
  else 
  {     
    sprintf(data, "%d:%d", 2, 1);
    HalLcdWriteString( "light 1", HAL_LCD_LINE_3 );
  }  
   
  if ( AF_DataRequest( &SampleApp_ReportData_DstAddr, &SampleApp_epDesc,
                       SAMPLEAPP_REPORTDATA_CLUSTERID,
                       sizeof(data) - 1,
                       data,
                       &SampleApp_TransID,
                       AF_DISCV_ROUTE,
                       AF_DEFAULT_RADIUS ) == afStatus_SUCCESS )
  {
  }
  else
  {
    // Error occurred in request to send.
  }
#endif
}

void SampleApp_ForwardCMD(byte* cmd, uint8 length)
{
    if ( AF_DataRequest( &SampleApp_ForwardCMD_DstAddr, &SampleApp_epDesc,
                       SAMPLEAPP_FORWARDCMD_CLUSTERID,
                       length,
                       cmd,
                       &SampleApp_TransID,
                       AF_DISCV_ROUTE,
                       AF_DEFAULT_RADIUS ) == afStatus_SUCCESS )
  {
  }
  else
  {
    // Error occurred in request to send.
  }
}

void SampleApp_SerialRXData(uint8 port, uint8 event)
{
  static byte rxData[32];
  static uint8 length;
  if ((event & (HAL_UART_RX_FULL | HAL_UART_RX_ABOUT_FULL | HAL_UART_RX_TIMEOUT)) && length == 0)
  {
    length = HalUARTRead(0, rxData, sizeof(rxData));
    if (length != 0)
    {
      rxData[length] = '\0';
      SampleApp_ForwardCMD(rxData, length + 1);
      length = 0;
    }
  }
}
