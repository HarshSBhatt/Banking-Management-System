package asd.group2.bms.serviceImpl;

import asd.group2.bms.repository.CreditCardBillRepository;
import asd.group2.bms.repository.ICreditCardRepository;
import asd.group2.bms.service.ICustomEmail;
import asd.group2.bms.util.Helper;
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

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CreditCardBillServiceImplTest {

  @Mock
  CreditCardBillRepository creditCardBillRepository;

  @Mock
  ICustomEmail customEmail;

  @Mock
  Helper helper;

  @InjectMocks
  CreditCardServiceImpl creditCardService;

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
  void payCreditCardBill() {

  }

  @Test
  void getBills() {

  }

}