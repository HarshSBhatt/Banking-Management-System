package asd.group2.bms.serviceImpl;

import asd.group2.bms.repositoryImpl.TermDepositDetailRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class TermDepositDetailServiceImplTest {

  @Mock
  TermDepositDetailRepositoryImpl termDepositDetailRepository;

  @Mock
  AccountServiceImpl accountService;

  @Mock
  CustomEmailImpl customEmail;

  @InjectMocks
  TermDepositDetailServiceImpl termDepositDetailService;

  @BeforeEach
  public void setup() {
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
  void getTermDepositDetailByIdTest(){

  }
}
