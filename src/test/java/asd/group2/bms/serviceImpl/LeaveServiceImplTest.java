package asd.group2.bms.serviceImpl;

import asd.group2.bms.repositoryImpl.LeaveRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LeaveServiceImplTest {

  @Mock
  LeaveRepositoryImpl leaveRepository;

  @InjectMocks
  LeaveServiceImpl leaveService;

  @BeforeEach
  void setUp() {
    HttpServletRequest mockRequest = new MockHttpServletRequest();
    ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
    RequestContextHolder.setRequestAttributes(servletRequestAttributes);
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void teardown() {
    RequestContextHolder.resetRequestAttributes();
  }


  @Test
  void getLeavesByStatusTest() {

  }

  @Test
  void getLeaveListByUserIdTest() {
  }

  @Test
  void getLeaveByIdTest() {
  }

  @Test
  void setLeaveRequestStatusTest() {
  }

  @Test
  void deleteLeaveRequestByIdTest() {
  }

  @Test
  void makeLeaveRequestTest() {

  }

}