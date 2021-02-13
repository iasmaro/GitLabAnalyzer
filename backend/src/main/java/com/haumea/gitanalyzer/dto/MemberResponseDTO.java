package com.haumea.gitanalyzer.dto;

import java.util.List;

public class MemberResponseDTO {
    private List<MemberDTO> members;

    public MemberResponseDTO(List<MemberDTO> members) {
        this.members = members;
    }
}
