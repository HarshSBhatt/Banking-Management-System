package asd.group2.bms.service;

import asd.group2.bms.model.term_deposit.TermDepositDetail;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface TermDepositDetailService {

  ResponseEntity<?> makeTermDepositRequest(Long userId, String email,
                                           String firstName, Double fdAmount, Date currentDate, int duration);

  TermDepositDetail getTermDepositDetailById(Long id);

  List<TermDepositDetail> getTermDepositDetail(Long userId);

}
