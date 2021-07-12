package asd.group2.bms.serviceImpl;

import asd.group2.bms.repository.ILeaveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

//import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LeaveServiceImplTest {

  @MockBean
  private ILeaveRepository leaveRepository;

  @InjectMocks
  private LeaveServiceImpl leaveService;

  @BeforeEach
  void setUp() {
  }

  @Test
  void getLeavesByStatus() {

  }

  @Test
  void getLeaveListByUserId() {
  }

  @Test
  void getLeaveById() {
  }

  @Test
  void setLeaveRequestStatus() {
  }

  @Test
  void deleteLeaveRequestById() {
  }

  @Test
  void makeLeaveRequest() {
  }

}