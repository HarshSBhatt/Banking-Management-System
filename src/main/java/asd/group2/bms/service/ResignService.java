package asd.group2.bms.service;

import asd.group2.bms.model.resign.RequestStatus;
import asd.group2.bms.model.resign.ResignRequest;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.payload.response.ResignListResponse;
import asd.group2.bms.repository.ResignRepository;
import asd.group2.bms.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ResignService {
    @Autowired
    ResignRepository resignRepository;

    /**
     * @param requestStatus: Resign Status (PENDING, APPROVED, REJECTED)
     * @param page:          Page Number
     * @param size:          Size of the response data
     * @description: This will return all the resignations having status resignStatus
     */
    public PagedResponse<ResignListResponse> getResignListByStatus(RequestStatus requestStatus, int page, int size) {

        /** Making list in ascending order*/
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
        Page<ResignRequest> resigns = resignRepository.findByRequestStatusEquals(requestStatus, pageable);

        if (resigns.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), resigns.getNumber(),
                    resigns.getSize(), resigns.getTotalElements(), resigns.getTotalPages(), resigns.isLast());
        }

        List<ResignListResponse> resignListResponse = resigns.map(resign -> ModelMapper.mapResignsToResignListResponse(resign)).getContent();

        return new PagedResponse<>(resignListResponse, resigns.getNumber(),
                resigns.getSize(), resigns.getTotalElements(), resigns.getTotalPages(), resigns.isLast());
    }


    public ApiResponse resign(User user, Date date, String reason){
        ResignRequest resignRequest = new ResignRequest(user, date, reason, RequestStatus.PENDING);
        resignRepository.save(resignRequest);

        return new ApiResponse(true, "Resignation request submitted successfully!");
    }
}
