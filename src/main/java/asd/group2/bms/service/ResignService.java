package asd.group2.bms.service;

import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.resign.RequestStatus;
import asd.group2.bms.model.resign.ResignRequest;
import asd.group2.bms.model.user.User;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.payload.response.ResignListResponse;
import asd.group2.bms.repository.ResignRepository;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    /** Making list in ascending order */
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
    Page<ResignRequest> resigns = resignRepository.findByRequestStatusEquals(requestStatus, pageable);

    if (resigns.getNumberOfElements() == 0) {
      return new PagedResponse<>(Collections.emptyList(), resigns.getNumber(),
          resigns.getSize(), resigns.getTotalElements(), resigns.getTotalPages(), resigns.isLast());
    }

    List<ResignListResponse> resignListResponse = resigns.map(ModelMapper::mapResignsToResignListResponse).getContent();

    return new PagedResponse<>(resignListResponse, resigns.getNumber(),
        resigns.getSize(), resigns.getTotalElements(), resigns.getTotalPages(), resigns.isLast());
  }

  /**
   * @param userId: id of the user
   * @description: This will return all the resignations having user id userId
   */
  public List<ResignListResponse> getResignListByUserId(Long userId) {
    /** Making list in ascending order */
    List<ResignRequest> resigns = resignRepository.findByUser_Id(userId);
    List<ResignListResponse> resignRequests = new ArrayList<>();
    resigns.forEach(resign -> resignRequests.add(ModelMapper.mapResignsToResignListResponse(resign)));
    return resignRequests;
  }


  public ResignRequest getResignById(Long resignId) {
    return resignRepository.findById(resignId).orElseThrow(() -> new ResourceNotFoundException("Resign ID", "resignId", resignId));
  }

  public ResignRequest setResignRequestStatus(Long resignId, RequestStatus requestStatus) {
    ResignRequest resignRequest = getResignById(resignId);
    resignRequest.setRequestStatus(requestStatus);
    return resignRepository.save(resignRequest);
  }

  public ResponseEntity<?> deleteResignationRequestById(UserPrincipal currentUser, Long resignId) {
    try {
      ResignRequest resignRequest = getResignById(resignId);
      if (resignRequest.getUser().getId() != currentUser.getId()) {
        return new ResponseEntity<>(new ApiResponse(false, "You are not authorized to perform this operation"),
            HttpStatus.FORBIDDEN);
      }
      resignRepository.delete(resignRequest);
      return ResponseEntity.ok(new ApiResponse(true, "Resignation request deleted successfully"));
    } catch (Exception e) {
      return new ResponseEntity<>(new ApiResponse(false, "Something went wrong!"),
          HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<?> makeResignRequest(User user, Date date, String reason) {
    try {
      ResignRequest resignRequest = new ResignRequest(user, date, reason, RequestStatus.PENDING);
      List<ResignRequest> resignList = resignRepository.findByUserOrderByCreatedAtDesc(user);
      if (resignList.size() > 0) {
        ResignRequest lastRequest = resignList.get(0);
        RequestStatus lastStatus = lastRequest.getRequestStatus();
        if (lastStatus == RequestStatus.PENDING || lastStatus == RequestStatus.APPROVED) {
          return new ResponseEntity<>(new ApiResponse(false, "Resign request already " + lastStatus.toString().toLowerCase()),
              HttpStatus.NOT_ACCEPTABLE);
        }
      }
      resignRepository.save(resignRequest);
      return ResponseEntity.ok(new ApiResponse(true, "Request made successfully!"));
    } catch (Exception e) {
      return new ResponseEntity<>(new ApiResponse(false, "Something went wrong!"),
          HttpStatus.BAD_REQUEST);
    }
  }

}
