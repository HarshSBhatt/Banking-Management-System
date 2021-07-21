package asd.group2.bms.repository;

public interface IChequeRepository {

  public Boolean update_cheque(Long senderAccountNumber, Long receiverAccountNumber, Long chequeNumber, Double chequeAmount);

}
