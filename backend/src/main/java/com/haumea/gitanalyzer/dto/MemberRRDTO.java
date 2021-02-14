package com.haumea.gitanalyzer.dto;

import java.util.List;

/**
 * TODO: for POST & GET /api/v1/members/alias endpoints
 * client will send a JSON object in the request body
 * or you sent the client a JSON object in the response body
 * the format of the JSON object is:
 * [{"memberId": "member_id1", "alias": [commit_author1, commit_author1,]},
 * {"memberId":member_id1, "alias":[commit_author1, commit_author1,]}]
 * since this case the JSON object in both request body & response body
 * has the same format, we can use a single class bot both
 * RR = RequestResponse
 * DTO = Data Transfer Object
 * you may want to map that JSON to this object
 * */
public class MemberRRDTO {
    private List<MemberDTO> members;

    public MemberRRDTO(List<MemberDTO> members) {
        this.members = members;
    }
}
