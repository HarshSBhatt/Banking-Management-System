package asd.group2.bms.controller;

import asd.group2.bms.model.leaves.LeaveRequest;
import asd.group2.bms.model.leaves.RequestStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.serviceImpl.LeaveServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = LeaveController.class)
@AutoConfigureMockMvc
class LeaveControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LeaveServiceImpl leaveService;

  @BeforeEach
  void setUp() {
  }

  @Test
  void getLeavesByStatus() throws Exception {
//    LeaveRequest leaveRequest = new LeaveRequest();
//    Mockito.when(
//        leaveService.getLeavesByStatus(RequestStatus.valueOf(Mockito.anyString()),
//            Mockito.anyInt(), Mockito.anyInt())).thenReturn("A");

//  mockMvc.perform(get("/")).andDo(print()).andExpect()
  }

  @Test
  void getLeavesByUserId() {
  }

  @Test
  void deleteLeaveRequestById() {
  }

  @Test
  void makeLeaveRequest() {
//    User user = new User();
//    user.setId(1L);
//    LeaveRequest leaveRequest = new LeaveRequest(user, new Date(), new Date()
//        , "test", RequestStatus.PENDING);
//    Mockito.when(leaveR.save(newBook)).thenReturn(newBook);
//
//    mockMvc.perform(post("/books")
//        .content("{json}")
//        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//        .andExpect(status().isCreated());
  }

  @Test
  void updateLeaveRequestStatus() {
  }

}