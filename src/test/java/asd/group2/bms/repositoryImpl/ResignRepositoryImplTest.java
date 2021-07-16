package asd.group2.bms.repositoryImpl;

import asd.group2.bms.model.resign.ResignRequest;
import asd.group2.bms.model.user.User;
import asd.group2.bms.repositoryMapper.ResignRowMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResignRepositoryImplTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  @InjectMocks
  private ResignRepositoryImpl resignRepository;

  @Test
  public void findByUser_IdTestSuccess() {
    User user = new User();
    user.setUsername("aditya");
    user.setId(1L);

    ResignRequest resignRequest = new ResignRequest();
    resignRequest.setUser(user);
    resignRequest.setReason("reason 1");

    List<ResignRequest> resigns = new ArrayList<>();
    resigns.add(resignRequest);

    when(jdbcTemplate.query(
        ArgumentMatchers.anyString(),
        (Object[]) ArgumentMatchers.any(),
        ArgumentMatchers.any(ResignRowMapper.class)))
        .thenReturn(resigns);

    List<ResignRequest> resign = resignRepository.findByUser_Id(1L);
    assertEquals("reason 1", resign.get(0).getReason());
  }

}
