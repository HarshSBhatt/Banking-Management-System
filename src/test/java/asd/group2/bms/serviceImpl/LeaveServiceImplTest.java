package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.leaves.LeaveRequest;
import asd.group2.bms.model.leaves.RequestStatus;
import asd.group2.bms.model.resign.ResignRequest;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.LeaveListResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.payload.response.ResignListResponse;
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
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//import static org.junit.jupiter.api.Assertions.*;

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
    String username = "shivam";
    asd.group2.bms.model.leaves.RequestStatus requestStatus = RequestStatus.PENDING;
    int page = 0;
    int size = 3;

    Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");

    User user = new User();
    user.setUsername("shivam");

    asd.group2.bms.model.leaves.LeaveRequest leaveRequest = new asd.group2.bms.model.leaves.LeaveRequest();
    leaveRequest.setUser(user);

    List<LeaveRequest> leaves = new ArrayList<>();
    leaves.add(leaveRequest);

    Page<LeaveRequest> pagedLeaves = new PageImpl<>(leaves);

    when(leaveRepository.findByRequestStatusEquals(requestStatus, pageable)).thenReturn(pagedLeaves);

    PagedResponse<LeaveListResponse> leavesList =
            leaveService.getLeavesByStatus(requestStatus, page, size);

    assertEquals(username,
            leavesList.getContent().get(0).getUserMetaResponse().getUsername());
  }

  @Test
  void getLeaveListByUserIdTest() {
    String username = "shivam";

    User user = new User();
    user.setUsername("shivam");
    user.setId(1L);

    LeaveRequest leaveRequest = new LeaveRequest();
    leaveRequest.setUser(user);

    List<LeaveRequest> leaves = new ArrayList<>();
    leaves.add(leaveRequest);

    when(leaveRepository.findByUser_Id(user.getId())).thenReturn(leaves);

    List<LeaveListResponse> leaveLists = leaveService.getLeaveListByUserId(user.getId());

    assertEquals(username, leaveLists.get(0).getUserMetaResponse().getUsername());
    assertEquals(1, leaveLists.size());
  }

  @Test
  void getLeaveByIdTest() {
    when(leaveRepository.findById(1L)).thenReturn(Optional.of(new LeaveRequest()));

    leaveService.getLeaveById(1L);
    verify(leaveRepository, times(1)).findById(any());
  }

  @Test
  void setLeaveRequestStatusTest() {
    User user = new User();

    LeaveRequest leaveRequest = new LeaveRequest();
    leaveRequest.setUser(user);

    Optional<LeaveRequest> request = Optional.of(leaveRequest);
    when(leaveRepository.findById(1L)).thenReturn(request);

    leaveService.setLeaveRequestStatus(1L, asd.group2.bms.model.leaves.RequestStatus.PENDING);
    assertEquals(asd.group2.bms.model.leaves.RequestStatus.PENDING, leaveRequest.getRequestStatus());
    verify(leaveRepository, times(1)).findById(any());
    verify(leaveRepository, times(1)).update(any());
  }

  @Test
  void deleteLeaveRequestByIdTest() {

  }

  @Test
  void makeLeaveRequestTest() {

  }
}