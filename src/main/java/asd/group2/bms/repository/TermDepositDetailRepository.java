package asd.group2.bms.repository;

import org.springframework.stereotype.Repository;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.term_deposit.TermDepositDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TermDepositDetailRepository extends JpaRepository<TermDepositDetail, Long> {

}
