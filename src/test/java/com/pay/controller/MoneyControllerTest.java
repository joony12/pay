package com.pay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pay.service.money.MoneyService;
import com.pay.service.room.RoomService;
import com.pay.service.token.TokenService;
import com.pay.service.user.UserService;
import com.pay.util.error.ErrorTypeEnum;
import com.pay.util.header.HeaderCode;
import com.pay.vo.ErrorResponseVO;
import com.pay.vo.SpreadRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Rollback
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class MoneyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private TokenService tokenService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void 유저_더미_데이터_추가() {
        Long userId1 = 1L;
        userService.createUser(userId1);

        Long userId2 = 2L;
        userService.createUser(userId2);

        String roomId1 = "room1";
        String roomId2 = "room2";
        roomService.createRoom(userId1, roomId1);
        roomService.createRoom(userId2, roomId1);

        roomService.createRoom(userId1, roomId2);
    }

    /*
    뿌리기
     */
    @Test
    void 뿌리기() throws Exception {

        SpreadRequestVO requestVO = new SpreadRequestVO();
        requestVO.setMoney(50000);
        requestVO.setUserCount(5);

        MvcResult result = mockMvc.perform(post("/money/v1/spread")
                .header(HeaderCode.X_USER_ID, "1")
                .header(HeaderCode.X_ROOM_ID, "room1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestVO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        if (log.isInfoEnabled()) {
            log.info(String.format("token : %s", token));
        }

        Assertions.assertNotNull(token);
        Assertions.assertEquals(token.length(), 3);
    }

    /*
    받기
     */
    @Test
    void 자신이_뿌리기_한_것을_받는_경우() throws Exception {

        SpreadRequestVO requestVO = new SpreadRequestVO();
        requestVO.setMoney(50000);
        requestVO.setUserCount(5);

        Long userId = 1L;
        String roomId = "room1";
        String token = tokenService.getToken(userId);
        moneyService.spread(requestVO, userId, roomId, token);

        MvcResult result = mockMvc.perform(put("/money/v1/receive/tokens/" + token)
                .header(HeaderCode.X_USER_ID, "1")
                .header(HeaderCode.X_ROOM_ID, "room1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ErrorResponseVO responseVO = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponseVO.class);
        Assertions.assertEquals(responseVO.getErrorCode(), ErrorTypeEnum.ERROR_0004.getErrorCode());
    }

    @Test
    void 동일_대화방에_속하지_않는_유저가_받으려고_하는_경우() throws Exception {

        SpreadRequestVO requestVO = new SpreadRequestVO();
        requestVO.setMoney(50000);
        requestVO.setUserCount(5);

        Long userId = 1L;
        String roomId = "room2";
        String token = tokenService.getToken(userId);
        moneyService.spread(requestVO, userId, roomId, token);

        MvcResult result = mockMvc.perform(put("/money/v1/receive/tokens/" + token)
                .header(HeaderCode.X_USER_ID, "2")
                .header(HeaderCode.X_ROOM_ID, "room1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ErrorResponseVO responseVO = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponseVO.class);
        Assertions.assertEquals(responseVO.getErrorCode(), ErrorTypeEnum.ERROR_0002.getErrorCode());
    }

    @Test
    void 받기() throws Exception {

        SpreadRequestVO requestVO = new SpreadRequestVO();
        requestVO.setMoney(50000);
        requestVO.setUserCount(5);

        Long userId = 1L;
        String roomId = "room1";
        String token = tokenService.getToken(userId);
        moneyService.spread(requestVO, userId, roomId, token);

        MvcResult result = mockMvc.perform(put("/money/v1/receive/tokens/" + token)
                .header(HeaderCode.X_USER_ID, "2")
                .header(HeaderCode.X_ROOM_ID, "room1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        int receivedMoney = Integer.parseInt(result.getResponse().getContentAsString());

        if (log.isInfoEnabled()) {
            log.info(String.format("receivedMoney : %d", receivedMoney));
        }

        Assertions.assertTrue(receivedMoney > 0);
    }

    @Test
    void 받고_다시_받으려는_경우() throws Exception {

        SpreadRequestVO requestVO = new SpreadRequestVO();
        requestVO.setMoney(50000);
        requestVO.setUserCount(5);

        Long userId = 1L;
        String roomId = "room1";
        String token = tokenService.getToken(userId);
        moneyService.spread(requestVO, userId, roomId, token);

        MvcResult result = mockMvc.perform(put("/money/v1/receive/tokens/" + token)
                .header(HeaderCode.X_USER_ID, "2")
                .header(HeaderCode.X_ROOM_ID, "room1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        int receivedMoney = Integer.parseInt(result.getResponse().getContentAsString());

        if (log.isInfoEnabled()) {
            log.info(String.format("receivedMoney : %d", receivedMoney));
        }

        MvcResult result2 = mockMvc.perform(put("/money/v1/receive/tokens/" + token)
                .header(HeaderCode.X_USER_ID, "2")
                .header(HeaderCode.X_ROOM_ID, "room1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ErrorResponseVO responseVO = objectMapper.readValue(result2.getResponse().getContentAsString(), ErrorResponseVO.class);
        Assertions.assertEquals(responseVO.getErrorCode(), ErrorTypeEnum.ERROR_0001.getErrorCode());
    }

    @Test
    @Ignore
    void 뿌린지_10분이_지난_경우() throws Exception {

        SpreadRequestVO requestVO = new SpreadRequestVO();
        requestVO.setMoney(50000);
        requestVO.setUserCount(5);

        Long userId = 1L;
        String roomId = "room1";
        String token = tokenService.getToken(userId);
        moneyService.spread(requestVO, userId, roomId, token);

        Thread.sleep(660000); //11분 강제 대기
        MvcResult result = mockMvc.perform(put("/money/v1/receive/tokens/" + token)
                .header(HeaderCode.X_USER_ID, "2")
                .header(HeaderCode.X_ROOM_ID, "room1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ErrorResponseVO responseVO = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponseVO.class);
        Assertions.assertEquals(responseVO.getErrorCode(), ErrorTypeEnum.ERROR_0003.getErrorCode());
    }
}