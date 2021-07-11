package asd.group2.bms.serviceImpl;

import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.credit.CreditCard;
import asd.group2.bms.model.cards.credit.CreditCardStatus;
import asd.group2.bms.payload.response.CreditCardListResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.repositoryImpl.CreditCardRepositoryImpl;
import asd.group2.bms.service.CreditCardService;
import asd.group2.bms.util.AppConstants;
import asd.group2.bms.util.Helper;
import asd.group2.bms.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class CreditCardServiceImpl implements CreditCardService {

  @Autowired
  CreditCardRepositoryImpl creditCardRepository;

  @Autowired
  CustomEmailImpl customEmail;

  /**
   * @param creditCardStatus: Credit Card Status (PENDING, APPROVED, REJECTED)
   * @param page:             Page Number
   * @param size:             Size of the response data
   * @description: This will return all the credit cards having status resignStatus
   */
  public PagedResponse<CreditCardListResponse> getCreditCardListByStatus(CreditCardStatus creditCardStatus, int page, int size) {
    // Making list in ascending order
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
    Page<CreditCard> cards = creditCardRepository.findByCreditCardStatusEquals(creditCardStatus, pageable);

    if (cards.getNumberOfElements() == 0) {
      return new PagedResponse<>(Collections.emptyList(), cards.getNumber(),
          cards.getSize(), cards.getTotalElements(), cards.getTotalPages(), cards.isLast());
    }

    List<CreditCardListResponse> creditCardListResponses = cards.map(ModelMapper::mapCreditCardToCreditCardListResponse).getContent();

    return new PagedResponse<>(creditCardListResponses, cards.getNumber(),
        cards.getSize(), cards.getTotalElements(), cards.getTotalPages(), cards.isLast());
  }

  /**
   * @param creditCardNumber: credit card number
   * @return credit card based on credit card number
   */
  public CreditCard getCreditCardByCreditCardNumber(Long creditCardNumber) {
    return creditCardRepository.findById(creditCardNumber).orElseThrow(() -> new ResourceNotFoundException("Credit Card Number", "creditCardNumber", creditCardNumber));
  }

  /**
   * @param creditCardNumber: credit card number
   * @param creditCardStatus: Status of the credit card (APPROVED, REJECTED, PENDING)
   * @return the updated status of the credit card having credit card number - creditCardNumber
   */
  public Boolean setCreditCardRequestStatus(Long creditCardNumber,
                                            CreditCardStatus creditCardStatus
  ) throws MessagingException,
      UnsupportedEncodingException {
    CreditCard creditCard = getCreditCardByCreditCardNumber(creditCardNumber);
    String email = creditCard.getAccount().getUser().getEmail();
    String firstName = creditCard.getAccount().getUser().getFirstName();
    Integer transactionLimit = creditCard.getTransactionLimit();
    creditCard.setCreditCardStatus(creditCardStatus);
    boolean response = creditCardRepository.update(creditCard);
    if (response) {
      customEmail.sendCreditCardApprovalMail(email, firstName,
          transactionLimit);

    }
    return response;
  }


  /**
   * @param account: account for which credit card would be created
   * @return This will return the debit card details
   */
  public CreditCard createCreditCard(Account account,
                                     Integer requestedTransactionLimit) {
    Random random = new Random();
    Date date = new Date();

    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    int month = localDate.getMonthValue();
    int currentYear = localDate.getYear();

    String expiryMonth = String.valueOf(month);
    String expiryYear = String.valueOf(currentYear + 4);

    String pin = String.format("%04d", random.nextInt(AppConstants.FOUR_DIGIT));
    String cvv = String.format("%06d", random.nextInt(AppConstants.SIX_DIGIT));

    String creditCardNumber = new Helper().generateRandomDigits(16);
    CreditCard creditCard = new CreditCard(Long.parseLong(creditCardNumber),
        account, pin, requestedTransactionLimit, CreditCardStatus.PENDING, expiryYear, expiryMonth,
        cvv, false);

    return creditCardRepository.save(creditCard);
  }
}
