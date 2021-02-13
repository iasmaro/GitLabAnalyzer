package com.haumea.gitanalyzer.dto;

import java.util.List;

public class MemberResponseDTO {
    private String memberId;
    private List<String> alias;

    public MemberResponseDTO(String memberId, List<String> alias) {
        this.memberId = memberId;
        this.alias = alias;
    }
}
