package asd.group2.bms.repository;

import asd.group2.bms.model.cheque.ChequeStatus;

public interface IChequeRepository {

  public Boolean update_cheque(Long chequeNumber);

  public Boolean update_chequeTransaction(Long senderAccountNumber,
      Long receiverAccountNumber, Long chequeNumber, Double chequeAmount);

  public Boolean updateChequeStatus (ChequeStatus chequeStatus);

  public ChequeStatus getChequeStatus(Long chequeNumber);

}
