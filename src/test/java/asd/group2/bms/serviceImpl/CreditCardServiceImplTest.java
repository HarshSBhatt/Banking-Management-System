package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.credit.CreditCard;
import asd.group2.bms.model.cards.credit.CreditCardStatus;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.CreditCardListResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.repository.ICreditCardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditCardServiceImplTest {

  @Mock
  ICreditCardRepository creditCardRepository;

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
  void getCreditCardListByStatusTestPass() {
    int page = 0;
    int size = 3;
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
    Long creditCardNumber = 123L;
    CreditCardStatus creditCardStatus = CreditCardStatus.PENDING;

    User user = new User();
    user.setUsername("aditya");
    Account account = new Account();
    account.setUser(user);
    CreditCard creditCard = new CreditCard();
    creditCard.setCreditCardNumber(123L);
    creditCard.setAccount(account);
    List<CreditCard> cards = new ArrayList<>();
    cards.add(creditCard);
    Page<CreditCard> pagedCards = new PageImpl<>(cards);

    when(creditCardRepository.findByCreditCardStatusEquals(creditCardStatus,
        pageable)).thenReturn(pagedCards);

    PagedResponse<CreditCardListResponse> creditCards =
        creditCardService.getCreditCardListByStatus(creditCardStatus, page,
            size);

    assertEquals(creditCardNumber, creditCards.getContent().get(0).getCreditCardNumber());
  }



}
