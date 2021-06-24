package asd.group2.bms.service;

import asd.group2.bms.model.resign.RequestStatus;
import asd.group2.bms.model.resign.ResignRequest;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.repository.ResignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ResignService {
    @Autowired
    ResignRepository resignRepository;


    public ApiResponse resign(User user, Date date, String reason){
        ResignRequest resignRequest = new ResignRequest(user, date, reason, RequestStatus.PENDING);
        resignRepository.save(resignRequest);


        return new ApiResponse(true, "Resignation request submitted successfully!");
    }
}
